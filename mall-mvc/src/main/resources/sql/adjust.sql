
create table proxy_rejection(
  id int primary key auto_increment,
  proxy_id int not null,
  host char(200) not null,
  url char(200) not null,
  reject_time datetime not null,
  unique(proxy_id, url)
);
