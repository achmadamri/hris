USE hrm;

CREATE TABLE `tb_login` (
  `tbl_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl_login_id` varchar(45) DEFAULT NULL,
  `tbl_username` varchar(45) DEFAULT NULL,
  `tbl_password` varchar(45) DEFAULT NULL,
  `tbl_status` int(11) DEFAULT NULL,
  `tbaug_id` int(11) DEFAULT NULL,
  `tbe_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbl_id`),
  KEY `tbl_fk_1A` (`tbaug_id`),
  KEY `tbl_fk_2B` (`tbe_id`),
  CONSTRAINT `tbl_fk_1A` FOREIGN KEY (`tbaug_id`) REFERENCES `tb_admin_user_groups` (`tbaug_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbl_fk_2B` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_login`(`tbl_id`,`tbl_login_id`,`tbl_username`,`tbl_password`,`tbl_status`,`tbaug_id`,`tbe_id`) values (12,'USR12','admin1','admin1',1,243,1);
insert into `hrm`.`tb_login`(`tbl_id`,`tbl_login_id`,`tbl_username`,`tbl_password`,`tbl_status`,`tbaug_id`,`tbe_id`) values (21,'USR21','admin2','admin2',1,244,2);
insert into `hrm`.`tb_login`(`tbl_id`,`tbl_login_id`,`tbl_username`,`tbl_password`,`tbl_status`,`tbaug_id`,`tbe_id`) values (22,'USR22','aan','aan',1,null,1);
insert into `hrm`.`tb_login`(`tbl_id`,`tbl_login_id`,`tbl_username`,`tbl_password`,`tbl_status`,`tbaug_id`,`tbe_id`) values (23,'USR23','ana','ana',1,null,2);
insert into `hrm`.`tb_login`(`tbl_id`,`tbl_login_id`,`tbl_username`,`tbl_password`,`tbl_status`,`tbaug_id`,`tbe_id`) values (24,'USR24','mika','mika',1,null,5);
insert into `hrm`.`tb_login`(`tbl_id`,`tbl_login_id`,`tbl_username`,`tbl_password`,`tbl_status`,`tbaug_id`,`tbe_id`) values (25,'USR25','abc','abc',1,null,6);
insert into `hrm`.`tb_login`(`tbl_id`,`tbl_login_id`,`tbl_username`,`tbl_password`,`tbl_status`,`tbaug_id`,`tbe_id`) values (26,'USR26','user1','user1',1,null,8);
