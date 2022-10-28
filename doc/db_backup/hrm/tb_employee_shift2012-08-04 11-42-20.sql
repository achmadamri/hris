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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;


insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (1,2,2,'2012-07-25');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (2,2,2,'2012-07-26');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (3,2,2,'2012-07-27');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (4,2,1,'2012-07-31');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (5,2,1,'2012-08-01');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (6,2,1,'2012-08-02');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (7,8,4,'2012-07-01');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (8,8,4,'2012-07-02');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (9,8,4,'2012-07-03');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (10,8,4,'2012-07-04');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (11,1,6,'2012-07-01');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (12,1,6,'2012-07-02');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (13,1,6,'2012-07-03');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (14,1,6,'2012-07-04');
insert into `tb_employee_shift`(`tbes_id`,`tbe_id`,`tbs_id`,`tbes_date`) values (15,1,1,'2012-07-26');
