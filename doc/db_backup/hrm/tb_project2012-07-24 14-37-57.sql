USE hrm;

CREATE TABLE `tb_project` (
  `tbp_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbp_project_id` varchar(45) DEFAULT NULL,
  `tbp_name` varchar(45) DEFAULT NULL,
  `tbp_description` varchar(500) DEFAULT NULL,
  `tbc_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbp_id`),
  KEY `tb_project_fk_1` (`tbc_id`),
  CONSTRAINT `tb_project_fk_1` FOREIGN KEY (`tbc_id`) REFERENCES `tb_customer` (`tbc_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_project`(`tbp_id`,`tbp_project_id`,`tbp_name`,`tbp_description`,`tbc_id`) values (7,'PRJ7','Implementasi SAP All Division',null,1);
insert into `hrm`.`tb_project`(`tbp_id`,`tbp_project_id`,`tbp_name`,`tbp_description`,`tbc_id`) values (8,'PRJ8','Middleware Implementation',null,6);
insert into `hrm`.`tb_project`(`tbp_id`,`tbp_project_id`,`tbp_name`,`tbp_description`,`tbc_id`) values (9,'PRJ9','Dafba Billing System',null,1);
insert into `hrm`.`tb_project`(`tbp_id`,`tbp_project_id`,`tbp_name`,`tbp_description`,`tbc_id`) values (10,'PRJ10','Middleware Implementation II',null,6);
insert into `hrm`.`tb_project`(`tbp_id`,`tbp_project_id`,`tbp_name`,`tbp_description`,`tbc_id`) values (11,'PRJ11','Implementasi HRM System',null,7);
insert into `hrm`.`tb_project`(`tbp_id`,`tbp_project_id`,`tbp_name`,`tbp_description`,`tbc_id`) values (12,'PRJ12','Implementasi HRM System','',1);
