create table if not exists customer
(
    id integer not null primary key,
    firstname varchar(255),
    lastname varchar(255),
    email varchar(255)
);

create table if not exists address
(
    id integer not null primary key,
    street varchar(255),
    house_number varchar(255),
    zip_code varchar(255),
    customer_id integer,
    constraint fk_customer_id foreign key (customer_id) references customer(id)
);

create sequence if not exists customer_seq increment by 50;
create sequence if not exists address_seq increment by 50;