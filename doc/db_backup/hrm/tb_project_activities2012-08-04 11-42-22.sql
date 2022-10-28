USE hrm;

CREATE TABLE `tb_project_activities` (
  `tbpa_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbpag_id` int(11) DEFAULT NULL,
  `tbpa_name` varchar(45) DEFAULT NULL,
  `tbpa_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbpa_id`),
  KEY `tbpa_fk_1` (`tbpag_id`),
  CONSTRAINT `tbpa_fk_1` FOREIGN KEY (`tbpag_id`) REFERENCES `tb_project_activities_group` (`tbpag_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;


insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (23,16,'Sales Proposal','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (24,16,'High Level Requirement','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (25,16,'Letter Of Intent','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (26,17,'Technical Proposal','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (27,17,'Technical Requirement','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (28,17,'Proof Of Concept','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (29,18,'Analyze','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (30,18,'Coding','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (31,18,'Bug Fixing','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (32,19,'Annual Leave','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (33,19,'Maternity Leave','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (34,19,'Employee Wedding','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (35,19,'Children Wedding','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (36,19,'Bereavement','');
insert into `tb_project_activities`(`tbpa_id`,`tbpag_id`,`tbpa_name`,`tbpa_description`) values (37,19,'Unpaid Leave','');
