create table currency_rate
(
   id bigint not null auto_increment,
   rate decimal(20,2) not null, 
   time timestamp,
   primary key(id)
);