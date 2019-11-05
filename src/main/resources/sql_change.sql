--liquibase formatted sql

--changeset wfw:1
-- 创建用户表
CREATE TABLE `user_test` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `age` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--rollback drop table user;

--changeset wfw:2
--新增用户
insert into user_test(id, name, age) values(1,'张三',29);
insert into user_test(id, name, age) values(2,'李四',20);
--rollback delete from user_test where id = 1;

--changeset wfw:3
--新增用户
insert into user_test(id, name, age) values(3,'王五',29);
--rollback delete from user_test where id = 3;