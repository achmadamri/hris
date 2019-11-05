USE hrm;

CREATE TABLE `tb_work_experience` (
  `tbwe_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbwe_work_experience_id` varchar(45) DEFAULT NULL,
  `tbwe_employer` varchar(45) DEFAULT NULL,
  `tbwe_job_title` varchar(45) DEFAULT NULL,
  `tbwe_comments` varchar(500) DEFAULT NULL,
  `tbwe_start_date` datetime DEFAULT NULL,
  `tbwe_end_date` datetime DEFAULT NULL,
  `tbwe_internal` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbwe_id`),
  KEY `tbec_fk_1` (`tbe_id`),
  KEY `tbwe_fk_1` (`tbe_id`),
  CONSTRAINT `tbwe_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;


insert into `tb_work_experience`(`tbwe_id`,`tbe_id`,`tbwe_work_experience_id`,`tbwe_employer`,`tbwe_job_title`,`tbwe_comments`,`tbwe_start_date`,`tbwe_end_date`,`tbwe_internal`) values (2,1,null,'PT. Sampoerna Telekomunikasi Indonesia','Technical Design Architecture','','2008-07-19 00:00:00','2010-11-23 00:00:00',1);
