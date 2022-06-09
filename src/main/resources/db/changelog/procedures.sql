-- liquibase formatted sql

-- changeset franco:1 stripComments:false splitStatements:true endDelimiter:$ runOnChange:true
create procedure bill_client(IN pClientId int)
begin
    declare vTotal float;
    declare vIdBill bigint;
    declare vIdCall bigint;
    declare vAmount int default 0;
    declare vSum float default 0;
    declare vFinished int default 0;
    declare cur_bill cursor for select call_id, total
                                from calls c
                                where phone_origin = (select cli.phone_number
                                                      from clients cli
                                                      where cli.client_id = pClientID)
                                  and bill_id is null;
    declare continue handler for not found set vFinished = 1;

    start transaction;

    insert into bills(client_id, total, bill_date, expiration_date)
    values (pClientID, 0, now(), DATE_ADD(now(), INTERVAL 15 DAY));
    set vIdBill = last_insert_id();

    open cur_bill;
    fetch cur_bill into vIdCall, vTotal;
    while (vFinished = 0)
        do
            set vSum = vSum + vTotal;
            set vAmount = vAmount + 1;

            update calls set bill_id = vIdBill where call_id = vIdCall;
            fetch cur_bill into vIdCall, vTotal;
        end while;
    update bills set calls_amount = vAmount, total = vSum where bill_id = vIdBill;
    if (vAmount = 0) then
        rollback;
    else
        commit;
    end if;
    close cur_bill;
end $

-- rollback drop procedure bill_client;

-- changeset franco:2 stripComments:false splitStatements:true endDelimiter:$ runOnChange:true
create procedure bill_all_clients()
begin
    declare vIdClient varchar(9);
    declare vFinished int default 0;
    declare cur_clients cursor for select client_id from clients where delete_at is NULL;
    declare continue handler for not found set vFinished = 1;

    open cur_clients;
    fetch cur_clients into vIdClient;
    while (vFinished = 0)
        do
            start transaction;
            call bill_client(vIdClient);
            fetch cur_clients into vIdClient;
            commit;
        end while;
end $

-- rollback drop procedure bill_all_clients;