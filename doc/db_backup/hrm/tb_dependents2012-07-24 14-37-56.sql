USE hrm;

CREATE TABLE `tb_dependents` (
  `tbd_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbd_name` varchar(45) DEFAULT NULL,
  `tbd_dob` datetime DEFAULT NULL,
  `tbd_dependents_type` int(11) DEFAULT NULL,
  `tbd_relationship` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbd_id`),
  KEY `tbd_fk_1` (`tbe_id`),
  CONSTRAINT `tbd_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_dependents`(`tbd_id`,`tbe_id`,`tbd_name`,`tbd_dob`,`tbd_dependents_type`,`tbd_relationship`) values (4,1,'Nurul Ana','1983-07-27 00:00:00',0,'Wife');
insert into `hrm`.`tb_dependents`(`tbd_id`,`tbe_id`,`tbd_name`,`tbd_dob`,`tbd_dependents_type`,`tbd_relationship`) values (5,1,'Miykaal Abrar','2008-10-29 00:00:00',1,'Children');
insert into `hrm`.`tb_dependents`(`tbd_id`,`tbe_id`,`tbd_name`,`tbd_dob`,`tbd_dependents_type`,`tbd_relationship`) values (6,2,'Miykaal Abrar','2008-10-29 00:00:00',1,'Children');
insert into `hrm`.`tb_dependents`(`tbd_id`,`tbe_id`,`tbd_name`,`tbd_dob`,`tbd_dependents_type`,`tbd_relationship`) values (7,2,'Achmad Amri','1981-08-19 00:00:00',0,'Husband');
