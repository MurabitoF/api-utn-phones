
-- changeset index:1
create index idx_calls_date on calls(phone_origin, call_date)
using btree;

create index idx_bills_date on bills(client_id, bill_date)
using btree;

create index idx_username on users(username)
using hash;

create index idx_account_deleteAt on clients(delete_at)
using hash;

create index idx_client_phone_number on clients(phone_line_id)
using hash;

create index idx_area_code on cities(area_code, (length(area_code)))
using btree;
