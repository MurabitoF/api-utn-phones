create user 'client'@'localhost' identified by '1234';

grant select on utn_phones.calls to 'client'@'localhost';
grant select on utn_phones.clients to 'client'@'localhost';
grant select on utn_phones.bills to 'client'@'localhost';

create user 'employee'@'localhost' identified by '1234';
grant all privileges on utn_phones.* to 'employee'@'localhost';
