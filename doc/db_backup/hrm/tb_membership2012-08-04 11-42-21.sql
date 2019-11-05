USE hrm;

CREATE TABLE `tb_membership` (
  `tbm_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbm_membership_id` varchar(45) DEFAULT NULL,
  `tbm_name` varchar(45) DEFAULT NULL,
  `tbmt_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbm_id`),
  KEY `tbm_fk_1` (`tbmt_id`),
  CONSTRAINT `tbm_fk_1` FOREIGN KEY (`tbmt_id`) REFERENCES `tb_membership_types` (`tbmt_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=latin1;


insert into `tb_membership`(`tbm_id`,`tbm_membership_id`,`tbm_name`,`tbmt_id`) values (241,'MEM1','Membership 1',241);
insert into `tb_membership`(`tbm_id`,`tbm_membership_id`,`tbm_name`,`tbmt_id`) values (242,'MEM242','Membership 2',242);
