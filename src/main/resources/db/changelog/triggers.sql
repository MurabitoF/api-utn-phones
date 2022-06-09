-- liquibase formatted sql

-- changeset franco:1 stripComments:false splitStatements:true endDelimiter:$ runOnChange:true
create trigger TBI_calls
    before insert
    on calls
    for each row
begin
    declare vIdCityOrg bigint default 0;
    declare vIdCityDest bigint default 0;
    declare vFeeId bigint;
    declare vRangeId bigint;
    declare vPrice float default 0;

    set vIdCityOrg = findCityFromPhoneNumber(NEW.phone_origin);

    set vIdCityDest = findCityFromPhoneNumber(NEW.phone_destination);

    select call_fee_id, call_fee_range_id, price
    into vFeeId, vRangeId, vPrice
    from vw_calls_fees
    where city_origin = vIdCityOrg
      and city_destination = vIdCityDest
      and TIME(NEW.call_date) between start_at and end_at;

    if (vPrice = 0)
    then
        signal sqlstate '45000' set message_text = 'the call does not correspond to a fee';
    end if;

    set NEW.call_fee_id = vFeeId;
    set NEW.call_fee_range_id = vRangeId;
    set NEW.total = (NEW.duration * vPrice) / 60;

end $
-- rollback drop trigger TBI_calls;

-- chageset franco:2 stripComments:false splitStatements:true endDelimiter:$ runOnChange:true
create trigger TAU_accounts_delete
    after update
    on accounts
    for each row
begin
    if(NEW.delete_at is not NULL) then
        update users set delete_at = now() where user_id = NEW.user_id;
    end if;
end $
-- rollback drop trigger TAU_accounts_delete