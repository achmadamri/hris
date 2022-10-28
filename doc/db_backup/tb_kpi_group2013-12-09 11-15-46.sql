USE hrm;

CREATE TABLE `tb_kpi_group` (
  `tbkg_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbkg_kpi_group_id` varchar(45) DEFAULT NULL,
  `tbkg_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbkg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_kpi_group`(`tbkg_id`,`tbkg_kpi_group_id`,`tbkg_name`) values (1,'KPG1','KPI Group A');
insert into `hrm`.`tb_kpi_group`(`tbkg_id`,`tbkg_kpi_group_id`,`tbkg_name`) values (2,'KPG2','KPI Group B');
insert into `hrm`.`tb_kpi_group`(`tbkg_id`,`tbkg_kpi_group_id`,`tbkg_name`) values (3,'KPG3','KPI Group C');
