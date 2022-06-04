-- liquibase formatted sql

-- changeset franco:1 splitStatements:false stripComments:false endDelimiter:$
create trigger TBI_calls
    before insert
    on calls
    for each row
begin
    declare vIdCityOrg bigint default 0;
    declare vIdCityDest bigint default 0;
    declare vFeeId bigint;
    declare vPrice float default 0;

    set vIdCityOrg = findCityFromPhoneNumber(NEW.phone_origin);

    set vIdCityDest = findCityFromPhoneNumber(NEW.phone_destination);

    select call_fee_id, price
    into vFeeId, vPrice
    from vw_calls_fees
    where city_origin = vIdCityOrg
      and city_destination = vIdCityDest
      and NEW.call_date between start_at and end_at;

    if (vPrice = 0)
    then
        signal sqlstate '45000' set message_text = 'the call does not correspond to a fee';
    end if;

    set NEW.call_fee_id = vFeeId;
    set NEW.total = (NEW.duration * vPrice) / 60;

end
-- $

-- rollback drop trigger TBI_calls;