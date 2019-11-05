USE hrm;

CREATE TABLE `tb_assigned_skills` (
  `tbas_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbs_id` int(11) DEFAULT NULL,
  `tbas_year` int(11) DEFAULT NULL,
  `tbas_comments` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbas_id`),
  KEY `tbas_fk_1` (`tbe_id`),
  KEY `tbas_fk_2` (`tbs_id`),
  CONSTRAINT `tbas_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbas_fk_2` FOREIGN KEY (`tbs_id`) REFERENCES `tb_skills` (`tbs_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_assigned_skills`(`tbas_id`,`tbe_id`,`tbs_id`,`tbas_year`,`tbas_comments`) values (5,1,1,1,'a');
insert into `hrm`.`tb_assigned_skills`(`tbas_id`,`tbe_id`,`tbs_id`,`tbas_year`,`tbas_comments`) values (10,1,2,2,'b');
