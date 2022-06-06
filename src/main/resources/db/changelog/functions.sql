-- liquibase formatted sql

-- changeset franco:1 splitStatements:false stripComments:false endDelimiter:$
create function findCityFromPhoneNumber(pPhoneNumber varchar(10))
    returns bigint
    reads sql data
begin
    DECLARE vCityId BIGINT default 0;
    select city_id
    into vCityId
    from cities
    where pPhoneNumber like concat(area_code, '%')
      and length(area_code) =
          (select max(length(area_code)) from cities where pPhoneNumber like concat(area_code, '%'));

    return vCityId;
end $

-- rollback drop function findCityFromPhoneNumber;

-- changeset franco:2
create view vw_calls_fees
as
select cf.call_fee_id,
       co.city_id as city_origin,
       co.area_code as area_origin,
       cd.city_id as city_destination,
       cd.area_code as area_detination,
       cfr.start_at,
       cfr.end_at,
       cf.price
from calls_fees cf
         join calls_fees_ranges cfr on cf.call_fee_id = cfr.call_fee_id
         join cities co on cf.city_origin = co.city_id
         join cities cd on cf.city_destination = cd.city_id;

-- rollback drop view vw_calls_fees;
