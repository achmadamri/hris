USE hrm;

CREATE TABLE `tb_assigned_languages` (
  `tbal_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbl_id` int(11) DEFAULT NULL,
  `tbal_fluency` int(11) DEFAULT NULL,
  `tbal_competency` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbal_id`),
  KEY `tbes_fk_1` (`tbe_id`),
  KEY `tbal_fk_1` (`tbe_id`),
  KEY `tbal_fk_2` (`tbl_id`),
  CONSTRAINT `tbal_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbal_fk_2` FOREIGN KEY (`tbl_id`) REFERENCES `tb_language` (`tbl_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;


insert into `tb_assigned_languages`(`tbal_id`,`tbe_id`,`tbl_id`,`tbal_fluency`,`tbal_competency`) values (5,1,1,0,3);
insert into `tb_assigned_languages`(`tbal_id`,`tbe_id`,`tbl_id`,`tbal_fluency`,`tbal_competency`) values (7,1,1,1,3);
insert into `tb_assigned_languages`(`tbal_id`,`tbe_id`,`tbl_id`,`tbal_fluency`,`tbal_competency`) values (8,1,1,1,1);
insert into `tb_assigned_languages`(`tbal_id`,`tbe_id`,`tbl_id`,`tbal_fluency`,`tbal_competency`) values (9,1,3,0,1);
insert into `tb_assigned_languages`(`tbal_id`,`tbe_id`,`tbl_id`,`tbal_fluency`,`tbal_competency`) values (10,1,3,1,1);
insert into `tb_assigned_languages`(`tbal_id`,`tbe_id`,`tbl_id`,`tbal_fluency`,`tbal_competency`) values (11,1,3,2,1);
