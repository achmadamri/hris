USE hrm;

CREATE TABLE `tb_job` (
  `tbe_id` int(11) NOT NULL,
  `tbjt_id` int(11) DEFAULT NULL,
  `tbes_id` int(11) DEFAULT NULL,
  `tbejc_id` int(11) DEFAULT NULL,
  `tbo_id` int(11) DEFAULT NULL,
  `tbj_joined_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tbe_id`),
  KEY `tbj_fk_1` (`tbjt_id`),
  KEY `tbj_fk_2` (`tbes_id`),
  KEY `tbj_fk_3` (`tbejc_id`),
  KEY `tbj_fk_4` (`tbo_id`),
  KEY `tbj_fk_5` (`tbe_id`),
  CONSTRAINT `tbj_fk_1` FOREIGN KEY (`tbjt_id`) REFERENCES `tb_job_title` (`tbjt_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_2` FOREIGN KEY (`tbes_id`) REFERENCES `tb_employment_status` (`tbes_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_3` FOREIGN KEY (`tbejc_id`) REFERENCES `tb_eeo_job_category` (`tbejc_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_4` FOREIGN KEY (`tbo_id`) REFERENCES `tb_organization` (`tbo_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_5` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbj_joined_date`) values (1,241,241,244,16,'2012-07-06 13:37:45');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbj_joined_date`) values (2,241,241,244,19,'2012-07-06 13:42:33');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbj_joined_date`) values (5,242,241,243,20,'2012-06-26 12:31:35');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbj_joined_date`) values (6,241,242,243,16,'2012-06-29 15:16:51');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbj_joined_date`) values (7,242,241,243,16,'2012-01-01 17:25:46');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbj_joined_date`) values (8,242,241,244,16,'2012-01-01 23:23:58');
