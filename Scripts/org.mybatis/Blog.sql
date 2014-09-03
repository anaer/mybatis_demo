drop table blog;
 create table blog ( id int not null primary key, title varchar(30) default 'My Blog', author_id int not null);
 insert into blog(id,title,author_id) values(1,'just fun',1);
 insert into blog(id,title,author_id) values(2,'just funny',2);
 insert into blog(id,author_id) values(3,3);
 insert into blog(id,author_id) values(4,4);
 insert into blog(id,title,author_id) values(5,'hello one',5);
 insert into blog(id,title,author_id) values(6,'hello two',6);

