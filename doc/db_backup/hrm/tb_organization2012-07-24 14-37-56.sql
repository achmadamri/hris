USE hrm;

CREATE TABLE `tb_organization` (
  `tbo_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbo_parent_id` int(11) DEFAULT NULL,
  `tbo_nama` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tbo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_organization`(`tbo_id`,`tbo_parent_id`,`tbo_nama`) values (16,0,'IT Division');
insert into `hrm`.`tb_organization`(`tbo_id`,`tbo_parent_id`,`tbo_nama`) values (17,16,'IT Operations Team');
insert into `hrm`.`tb_organization`(`tbo_id`,`tbo_parent_id`,`tbo_nama`) values (18,16,'IT Services Team');
insert into `hrm`.`tb_organization`(`tbo_id`,`tbo_parent_id`,`tbo_nama`) values (19,0,'Marketing Division');
insert into `hrm`.`tb_organization`(`tbo_id`,`tbo_parent_id`,`tbo_nama`) values (20,19,'Sales 1 Team');
insert into `hrm`.`tb_organization`(`tbo_id`,`tbo_parent_id`,`tbo_nama`) values (21,19,'Sales 2 Team');
