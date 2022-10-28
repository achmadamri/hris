USE hrm;

CREATE TABLE `tb_job_employment_status` (
  `tbjes_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbjt_id` int(11) DEFAULT NULL,
  `tbes_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbjes_id`),
  KEY `tbjes_fk_1` (`tbjt_id`),
  KEY `tbjes_fk_2` (`tbes_id`),
  CONSTRAINT `tbjes_fk_1` FOREIGN KEY (`tbjt_id`) REFERENCES `tb_job_title` (`tbjt_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbjes_fk_2` FOREIGN KEY (`tbes_id`) REFERENCES `tb_employment_status` (`tbes_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_job_employment_status`(`tbjes_id`,`tbjt_id`,`tbes_id`) values (42,242,241);
insert into `hrm`.`tb_job_employment_status`(`tbjes_id`,`tbjt_id`,`tbes_id`) values (43,241,241);
insert into `hrm`.`tb_job_employment_status`(`tbjes_id`,`tbjt_id`,`tbes_id`) values (44,241,242);
