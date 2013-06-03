alter table Blueprint 
add automaticallyUpdateSalePrice bit(1) not null default 0;

alter table Type 
add autoUpdate bit(1) not null default 0;