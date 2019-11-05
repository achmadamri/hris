USE hrm;

CREATE TABLE `tb_project_activities` (
  `tbpa_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbpa_name` varchar(45) DEFAULT NULL,
  `tbpa_description` varchar(500) DEFAULT NULL,
  `tbp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbpa_id`),
  KEY `tbpa_fk_1` (`tbp_id`),
  CONSTRAINT `tbpa_fk_1` FOREIGN KEY (`tbp_id`) REFERENCES `tb_project` (`tbp_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_project_activities`(`tbpa_id`,`tbpa_name`,`tbpa_description`,`tbp_id`) values (2,'Act 1',null,7);
insert into `hrm`.`tb_project_activities`(`tbpa_id`,`tbpa_name`,`tbpa_description`,`tbp_id`) values (5,'Act 1',null,8);
insert into `hrm`.`tb_project_activities`(`tbpa_id`,`tbpa_name`,`tbpa_description`,`tbp_id`) values (8,'Act 1','x',11);
insert into `hrm`.`tb_project_activities`(`tbpa_id`,`tbpa_name`,`tbpa_description`,`tbp_id`) values (10,'Act 1',null,10);
insert into `hrm`.`tb_project_activities`(`tbpa_id`,`tbpa_name`,`tbpa_description`,`tbp_id`) values (13,'Act 1',null,9);
