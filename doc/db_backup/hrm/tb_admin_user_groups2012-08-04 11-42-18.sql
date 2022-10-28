USE hrm;

CREATE TABLE `tb_admin_user_groups` (
  `tbaug_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbaug_admin_user_groups_id` varchar(45) DEFAULT NULL,
  `tbaug_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbaug_id`)
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=latin1;


insert into `tb_admin_user_groups`(`tbaug_id`,`tbaug_admin_user_groups_id`,`tbaug_name`) values (243,'USG243','Admin User Groups 1');
insert into `tb_admin_user_groups`(`tbaug_id`,`tbaug_admin_user_groups_id`,`tbaug_name`) values (244,'USG244','Admin User Groups 2');
