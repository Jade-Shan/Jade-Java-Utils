CREATE DATABASE `jade_dao_test`;

CREATE USER 'test' IDENTIFIED BY 'qwer1234';
GRANT select, insert, update, delete ON jade_dao_test.* TO 'test'@'%' IDENTIFIED BY 'qwer1234'; 

use `jade_dao_test`;
CREATE TABLE `resident` (
  `id` varchar(45) NOT NULL,
  `userName` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nick` varchar(20) NOT NULL,
  `status` varchar(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
