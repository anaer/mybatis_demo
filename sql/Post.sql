drop table post;
 create table post ( id int not null, blog_id int not null, author_id int not null, created_on varchar(30), section varchar(30), subject varchar(30), body varchar(100));
 insert into post(id,blog_id,author_id,created_on,section,subject,body) values(1,1,1,'2010-08-04','photo','ddd','nothing');
 insert into post(id,blog_id,author_id,created_on,section,subject,body) values(2,1,1,'2010-08-05','photo',' hello ','nothing too');
 insert into post(id,blog_id,author_id,created_on,section,subject,body) values(3,1,1,'2010-08-06','photo','ddfdfdd','also nothing');
 insert into post(id,blog_id,author_id,created_on,section,subject,body) values(4,2,2,'2010-08-06','photo',' hi ','nothing more');

