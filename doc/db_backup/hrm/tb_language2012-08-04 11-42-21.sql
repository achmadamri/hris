USE hrm;

CREATE TABLE `tb_language` (
  `tbl_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbl_language_id` varchar(45) DEFAULT NULL,
  `tbl_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;


insert into `tb_language`(`tbl_id`,`tbl_language_id`,`tbl_name`) values (1,'LAN1','Bahasa');
insert into `tb_language`(`tbl_id`,`tbl_language_id`,`tbl_name`) values (2,'LAN2','Melayu');
insert into `tb_language`(`tbl_id`,`tbl_language_id`,`tbl_name`) values (3,'LAN3','English');
insert into `tb_language`(`tbl_id`,`tbl_language_id`,`tbl_name`) values (4,'LAN4','Chinese');
