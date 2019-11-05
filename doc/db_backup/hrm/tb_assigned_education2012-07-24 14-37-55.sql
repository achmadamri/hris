USE hrm;

CREATE TABLE `tb_assigned_education` (
  `tbae_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbemp_id` int(11) DEFAULT NULL,
  `tbedu_id` int(11) DEFAULT NULL,
  `tbae_gpa` double DEFAULT NULL,
  `tbae_major` varchar(45) DEFAULT NULL,
  `tbae_year` int(11) DEFAULT NULL,
  `tbae_start_date` datetime DEFAULT NULL,
  `tbae_end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tbae_id`),
  KEY `tbes_fk_1` (`tbemp_id`),
  KEY `tbae_fk_2` (`tbedu_id`),
  CONSTRAINT `tbae_fk_1` FOREIGN KEY (`tbemp_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbae_fk_2` FOREIGN KEY (`tbedu_id`) REFERENCES `tb_education` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_assigned_education`(`tbae_id`,`tbemp_id`,`tbedu_id`,`tbae_gpa`,`tbae_major`,`tbae_year`,`tbae_start_date`,`tbae_end_date`) values (11,1,1,5,'Universitas Gunadarma Sistem Informasi (S1)',2006,'2000-01-01 00:00:00','2006-01-01 00:00:00');
insert into `hrm`.`tb_assigned_education`(`tbae_id`,`tbemp_id`,`tbedu_id`,`tbae_gpa`,`tbae_major`,`tbae_year`,`tbae_start_date`,`tbae_end_date`) values (12,1,2,0,'SMU Negeri 10 Palembang',1999,'1996-01-01 00:00:00','1999-01-01 00:00:00');
insert into `hrm`.`tb_assigned_education`(`tbae_id`,`tbemp_id`,`tbedu_id`,`tbae_gpa`,`tbae_major`,`tbae_year`,`tbae_start_date`,`tbae_end_date`) values (13,1,3,0,'SMP Negeri 1 Palembang',1996,'1996-01-01 00:00:00','1993-01-01 00:00:00');
insert into `hrm`.`tb_assigned_education`(`tbae_id`,`tbemp_id`,`tbedu_id`,`tbae_gpa`,`tbae_major`,`tbae_year`,`tbae_start_date`,`tbae_end_date`) values (16,1,1,2012,'2012',2012,'2012-07-04 00:00:00','2012-07-04 00:00:00');
