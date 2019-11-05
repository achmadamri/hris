USE hrm;

CREATE TABLE `tb_skills` (
  `tbs_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbs_skill_id` varchar(45) DEFAULT NULL,
  `tbs_name` varchar(45) DEFAULT NULL,
  `tbs_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbs_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_skills`(`tbs_id`,`tbs_skill_id`,`tbs_name`,`tbs_description`) values (1,'SKI1','Java','Java programming');
insert into `hrm`.`tb_skills`(`tbs_id`,`tbs_skill_id`,`tbs_name`,`tbs_description`) values (2,'SKI2','Php','Php Programming');
insert into `hrm`.`tb_skills`(`tbs_id`,`tbs_skill_id`,`tbs_name`,`tbs_description`) values (3,'SKI3','VB','VB Programming');
