USE hrm;

CREATE TABLE `tb_project_activities_group` (
  `tbpag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbpag_project_activities_group_id` varchar(45) DEFAULT NULL,
  `tbpag_name` varchar(45) DEFAULT NULL,
  `tbpag_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbpag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_project_activities_group`(`tbpag_id`,`tbpag_project_activities_group_id`,`tbpag_name`,`tbpag_description`) values (16,'PAG16','Sales',null);
insert into `hrm`.`tb_project_activities_group`(`tbpag_id`,`tbpag_project_activities_group_id`,`tbpag_name`,`tbpag_description`) values (17,'PAG17','Pre Sales',null);
insert into `hrm`.`tb_project_activities_group`(`tbpag_id`,`tbpag_project_activities_group_id`,`tbpag_name`,`tbpag_description`) values (18,'PAG18','Development',null);
insert into `hrm`.`tb_project_activities_group`(`tbpag_id`,`tbpag_project_activities_group_id`,`tbpag_name`,`tbpag_description`) values (19,'PAG19','Vacation',null);
