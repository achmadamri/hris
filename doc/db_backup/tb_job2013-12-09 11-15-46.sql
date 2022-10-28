USE hrm;

CREATE TABLE `tb_job` (
  `tbe_id` int(11) NOT NULL,
  `tbjt_id` int(11) DEFAULT NULL,
  `tbes_id` int(11) DEFAULT NULL,
  `tbejc_id` int(11) DEFAULT NULL,
  `tbo_id` int(11) DEFAULT NULL,
  `tbl_id` int(11) DEFAULT NULL,
  `tbj_joined_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tbe_id`),
  KEY `tbj_fk_1` (`tbjt_id`),
  KEY `tbj_fk_2` (`tbes_id`),
  KEY `tbj_fk_3` (`tbejc_id`),
  KEY `tbj_fk_4` (`tbo_id`),
  KEY `tbj_fk_5` (`tbe_id`),
  KEY `tbj_fk_6` (`tbl_id`),
  CONSTRAINT `tbj_fk_1` FOREIGN KEY (`tbjt_id`) REFERENCES `tb_job_title` (`tbjt_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_2` FOREIGN KEY (`tbes_id`) REFERENCES `tb_employment_status` (`tbes_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_3` FOREIGN KEY (`tbejc_id`) REFERENCES `tb_eeo_job_category` (`tbejc_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_4` FOREIGN KEY (`tbo_id`) REFERENCES `tb_organization` (`tbo_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_5` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbj_fk_6` FOREIGN KEY (`tbl_id`) REFERENCES `tb_location` (`tbl_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (1,null,null,null,null,null,null);
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (2,1,1,2,1,1,'2013-06-05 15:37:05');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (3,2,1,2,1,1,'2013-06-05 15:37:31');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (4,3,1,2,1,1,'2013-06-05 15:37:44');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (5,4,1,2,1,1,'2013-06-05 15:37:58');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (6,4,1,2,1,1,'2013-06-05 15:38:15');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (7,4,1,2,1,1,'2013-06-13 11:40:31');
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (8,null,null,null,null,null,null);
insert into `hrm`.`tb_job`(`tbe_id`,`tbjt_id`,`tbes_id`,`tbejc_id`,`tbo_id`,`tbl_id`,`tbj_joined_date`) values (9,4,1,2,1,1,'2013-10-14 18:44:42');
