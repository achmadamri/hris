USE hrm;

CREATE TABLE `tb_project_employee` (
  `tbpe_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbp_id` int(11) DEFAULT NULL,
  `tbe_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbpe_id`),
  KEY `tbpe_fk_1` (`tbe_id`),
  KEY `tbpe_fk_2` (`tbp_id`),
  CONSTRAINT `tbpe_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbpe_fk_2` FOREIGN KEY (`tbp_id`) REFERENCES `tb_project` (`tbp_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_project_employee`(`tbpe_id`,`tbp_id`,`tbe_id`) values (35,7,1);
insert into `hrm`.`tb_project_employee`(`tbpe_id`,`tbp_id`,`tbe_id`) values (37,8,1);
insert into `hrm`.`tb_project_employee`(`tbpe_id`,`tbp_id`,`tbe_id`) values (40,7,2);
insert into `hrm`.`tb_project_employee`(`tbpe_id`,`tbp_id`,`tbe_id`) values (41,12,1);
insert into `hrm`.`tb_project_employee`(`tbpe_id`,`tbp_id`,`tbe_id`) values (42,12,2);
