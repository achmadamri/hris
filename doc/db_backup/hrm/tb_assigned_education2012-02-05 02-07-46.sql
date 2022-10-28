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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_assigned_education`(`tbae_id`,`tbemp_id`,`tbedu_id`,`tbae_gpa`,`tbae_major`,`tbae_year`,`tbae_start_date`,`tbae_end_date`) values (5,1,1,2.75,'Teknik Informatika',1,'2012-01-29 00:00:00','2012-01-30 00:00:00');
insert into `hrm`.`tb_assigned_education`(`tbae_id`,`tbemp_id`,`tbedu_id`,`tbae_gpa`,`tbae_major`,`tbae_year`,`tbae_start_date`,`tbae_end_date`) values (6,1,1,3,'Manajemen Informatika',6,'2012-01-31 00:00:00','2012-02-01 00:00:00');
insert into `hrm`.`tb_assigned_education`(`tbae_id`,`tbemp_id`,`tbedu_id`,`tbae_gpa`,`tbae_major`,`tbae_year`,`tbae_start_date`,`tbae_end_date`) values (8,1,1,3.75,'Ekonomi',3,'2012-01-30 00:00:00','2012-01-31 00:00:00');
