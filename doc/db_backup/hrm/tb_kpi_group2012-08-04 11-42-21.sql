USE hrm;

CREATE TABLE `tb_kpi_group` (
  `tbkg_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbkg_kpi_group_id` varchar(45) DEFAULT NULL,
  `tbkg_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbkg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;


insert into `tb_kpi_group`(`tbkg_id`,`tbkg_kpi_group_id`,`tbkg_name`) values (3,'KPG3','Implementation');
insert into `tb_kpi_group`(`tbkg_id`,`tbkg_kpi_group_id`,`tbkg_name`) values (4,'KPG4','Pre Sales');
insert into `tb_kpi_group`(`tbkg_id`,`tbkg_kpi_group_id`,`tbkg_name`) values (5,'KPG5','Personal Development');
insert into `tb_kpi_group`(`tbkg_id`,`tbkg_kpi_group_id`,`tbkg_name`) values (9,'KPG9','Administration');
