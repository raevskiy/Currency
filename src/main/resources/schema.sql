create table currency_rate
(
   id bigint not null auto_increment,
   rate decimal(20,4) not null, 
   time timestamp not null,
   primary key(id)
);