-- liquibase formatted sql

-- changeset franco:1
create table provinces
(
    province_id   bigint      not null auto_increment,
    province_name varchar(50) not null,
    constraint pk_provinces primary key (province_id)
);

-- rollback drop table provinces;

-- changeset franco:2
create table cities
(
    city_id     bigint       not null auto_increment,
    province_id bigint       not null,
    city_name   varchar(100) not null,
    area_code   varchar(6)   not null,
    constraint pk_city primary key (city_id),
    constraint fk_cities_provinces foreign key (province_id) references provinces (province_id) on delete restrict on update cascade,
    constraint uk_area_code unique (area_code)
);

-- rollback drop table cities;

-- changeset franco:3
create table phone_lines
(
    phone_line_id bigint      not null auto_increment,
    phone_number  varchar(10) not null,
    constraint pk_phone_lines primary key (phone_line_id),
    constraint uk_phone_number unique (phone_number)
);

-- rollback drop table phone_lines;

-- changeset franco:4
create table users
(
    user_id  bigint       not null auto_increment,
    username varchar(100) not null,
    password varchar(60)  not null,
    role     varchar(10)  not null,
    constraint pk_users primary key (user_id)
);

-- rollback drop table users;

-- changeset franco:5
create table accounts
(
    account_id bigint      not null auto_increment,
    city_id    bigint      not null,
    user_id    bigint,
    dni        varchar(10) not null,
    first_name varchar(50) not null,
    surname    varchar(50) not null,
    constraint pk_accounts primary key (account_id),
    constraint fk_employees_cities foreign key (city_id) references cities (city_id) on delete restrict on update cascade,
    constraint fk_employees_users foreign key (user_id) references users (user_id),
    constraint uk_dni unique (dni)
);

-- rollback drop table accounts;

-- changeset franco:6
create table employees
(
    employee_id   bigint      not null auto_increment,
    employee_area varchar(60) not null,
    constraint pk_employees primary key (employee_id),
    constraint fk_employees_accounts foreign key (employee_id) references accounts (account_id)

);

-- rollback drop table employees;


-- changeset franco:7
create table clients
(
    client_id     bigint not null auto_increment,
    phone_line_id bigint not null,
    delete_at     datetime,
    constraint pk_clients primary key (client_id),
    constraint fk_clients_phone_lines foreign key (phone_line_id) references phone_lines (phone_line_id),
    constraint fk_clients_accounts foreign key (client_id) references accounts (account_id)
);
-- rollback drop table clients;

-- changeset franco:8
create table calls_fees
(
    call_fee_id      bigint not null auto_increment,
    city_origin      bigint not null,
    city_destination bigint not null,
    constraint pk_calls_fees primary key (call_fee_id),
    constraint fk_calls_fees_cities_org foreign key (city_origin) references cities (city_id) on delete restrict on update cascade,
    constraint fk_calls_fees_cities_dest foreign key (city_destination) references cities (city_id) on delete restrict on update cascade
);

-- rollback drop table calls_fees;

-- changeset franco:9
create table calls_fees_ranges
(
    call_fee_range_id bigint not null auto_increment,
    call_fee_id       bigint not null,
    start_at          time   not null,
    end_at            time   not null,
    price             float  not null,
    constraint pk_calls_fees_range primary key (call_fee_range_id),
    constraint fk_calls_fees_ranges foreign key (call_fee_id) references calls_fees (call_fee_id)
);

-- rollback drop table calls_fees_ranges

-- changeset franco:10
create table bills
(
    bill_id         bigint not null auto_increment,
    client_id       bigint not null,
    calls_amount    int,
    total           float  not null,
    bill_date       date   not null,
    expiration_date date   not null,
    bill_paid_at    datetime,
    constraint pk_bills primary key (bill_id),
    constraint fk_bills_clients foreign key (client_id) references clients (client_id) on delete restrict on update cascade
);

-- rollback drop table bills;

-- changeset franco:11
create table calls
(
    call_id           bigint      not null auto_increment,
    phone_origin      varchar(10) not null,
    phone_destination varchar(10) not null,
    call_fee_id       bigint      not null,
    call_date         datetime    not null,
    duration          int         not null,
    total             float       not null,
    bill_id           bigint,
    constraint pk_calls primary key (call_id),
    constraint fk_calls_clients_org foreign key (phone_origin) references phone_lines (phone_number) on delete restrict on update cascade,
    constraint fk_calls_clients_dest foreign key (phone_destination) references phone_lines (phone_number) on delete restrict on update cascade,
    constraint fk_calls_fees foreign key (call_fee_id) references calls_fees (call_fee_id) on delete restrict on update cascade,
    constraint fk_calls_bills foreign key (bill_id) references bills (bill_id) on delete restrict on update cascade
);

-- rollback drop table calls;

