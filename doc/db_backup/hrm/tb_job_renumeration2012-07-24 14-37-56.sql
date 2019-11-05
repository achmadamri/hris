USE hrm;

CREATE TABLE `tb_job_renumeration` (
  `tbjr_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbjt_id` int(11) DEFAULT NULL,
  `tbr_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbjr_id`),
  KEY `tbjr_fk_1` (`tbjt_id`),
  KEY `tbjr_fk_2` (`tbr_id`),
  CONSTRAINT `tbjr_fk_1` FOREIGN KEY (`tbjt_id`) REFERENCES `tb_job_title` (`tbjt_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbjr_fk_2` FOREIGN KEY (`tbr_id`) REFERENCES `tb_renumeration` (`tbr_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_job_renumeration`(`tbjr_id`,`tbjt_id`,`tbr_id`) values (114,241,5);
insert into `hrm`.`tb_job_renumeration`(`tbjr_id`,`tbjt_id`,`tbr_id`) values (115,241,6);
insert into `hrm`.`tb_job_renumeration`(`tbjr_id`,`tbjt_id`,`tbr_id`) values (116,242,5);
