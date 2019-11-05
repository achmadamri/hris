USE hrm;

CREATE TABLE `tb_job_title` (
  `tbjt_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbjt_job_title_id` varchar(45) DEFAULT NULL,
  `tbjt_name` varchar(45) DEFAULT NULL,
  `tbjt_description` varchar(500) DEFAULT NULL,
  `tbjt_comments` varchar(500) DEFAULT NULL,
  `tbjs_id` int(11) DEFAULT NULL,
  `tbp_id` int(11) DEFAULT NULL,
  `tbjt_leave_entitled` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbjt_id`),
  KEY `tbjt_fk_1` (`tbjs_id`),
  KEY `tbjt_fk_2` (`tbp_id`),
  CONSTRAINT `tbjt_fk_1` FOREIGN KEY (`tbjs_id`) REFERENCES `tb_job_specifications` (`tbjs_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbjt_fk_2` FOREIGN KEY (`tbp_id`) REFERENCES `tb_paygrade` (`tbp_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=latin1;


insert into `tb_job_title`(`tbjt_id`,`tbjt_job_title_id`,`tbjt_name`,`tbjt_description`,`tbjt_comments`,`tbjs_id`,`tbp_id`,`tbjt_leave_entitled`) values (241,'JOB001','Job Direktur','Job Direktur Description','Job Direktur Comments',1,1,24);
insert into `tb_job_title`(`tbjt_id`,`tbjt_job_title_id`,`tbjt_name`,`tbjt_description`,`tbjt_comments`,`tbjs_id`,`tbp_id`,`tbjt_leave_entitled`) values (242,'JOB242','Job Manager','Job Manager Description','Job Manager Comments',2,11,15);
