drop table if exists CURRENCY_RATE;

create table CURRENCY_RATE
(
   ID bigint not null auto_increment,
   RATE decimal(20,4) not null, 
   TIME timestamp not null,
   INSERT_TIME timestamp not null default current_timestamp,
   primary key(ID)
);

create index IDX_INSERT_TIME on CURRENCY_RATE(INSERT_TIME);