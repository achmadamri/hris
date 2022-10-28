USE hrm;

CREATE TABLE `tb_employee_shift` (
  `tbes_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbs_id` int(11) DEFAULT NULL,
  `tbes_date` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`tbes_id`),
  KEY `tbes_fk_1a` (`tbe_id`),
  KEY `tbes_fk_2a` (`tbs_id`),
  CONSTRAINT `tbes_fk_1a` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbes_fk_2a` FOREIGN KEY (`tbs_id`) REFERENCES `tb_shift` (`tbs_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (1,1,1,'2012-04-25');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (2,5,1,'2012-05-23');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (3,5,1,'2012-05-24');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (4,1,1,'2012-05-23');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (5,1,1,'2012-05-25');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (6,1,1,'2012-06-18');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (7,1,1,'2012-06-22');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (8,1,1,'2012-07-06');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (9,1,1,'2012-07-07');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (10,1,1,'2012-01-01');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (11,1,1,'2012-07-24');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (12,2,1,'2012-07-24');
insert into `hrm`.`tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (13,2,1,'2012-07-23');
