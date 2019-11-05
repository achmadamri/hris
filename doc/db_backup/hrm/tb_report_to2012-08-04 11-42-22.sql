USE hrm;

CREATE TABLE `tb_report_to` (
  `tbrt_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbrt_spv` int(11) DEFAULT NULL,
  `tbrt_reporting_method` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbrt_id`),
  KEY `tbrt_fk_1` (`tbe_id`),
  KEY `tbrt_fk_2` (`tbrt_spv`),
  CONSTRAINT `tbrt_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbrt_fk_2` FOREIGN KEY (`tbrt_spv`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;


insert into `tb_report_to`(`tbrt_id`,`tbe_id`,`tbrt_spv`,`tbrt_reporting_method`) values (53,2,1,0);
insert into `tb_report_to`(`tbrt_id`,`tbe_id`,`tbrt_spv`,`tbrt_reporting_method`) values (54,5,2,0);
insert into `tb_report_to`(`tbrt_id`,`tbe_id`,`tbrt_spv`,`tbrt_reporting_method`) values (55,5,1,1);
insert into `tb_report_to`(`tbrt_id`,`tbe_id`,`tbrt_spv`,`tbrt_reporting_method`) values (56,6,2,0);
