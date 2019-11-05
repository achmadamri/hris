USE hrm;

CREATE TABLE `tb_assigned_loan` (
  `tbalo_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbc_id` int(11) DEFAULT NULL,
  `tbalo_name` varchar(45) DEFAULT NULL,
  `tbalo_created_time` datetime DEFAULT NULL,
  `tbalo_updated_time` datetime DEFAULT NULL,
  `tbalo_start_date` varchar(10) DEFAULT NULL,
  `tbalo_end_date` varchar(10) DEFAULT NULL,
  `tbalo_nominal` double DEFAULT NULL,
  `tbalo_interest` double DEFAULT NULL,
  `tbalo_tenor` int(11) DEFAULT NULL,
  `tbalo_nominal_total` double DEFAULT NULL,
  `tbalo_monthly_payment` double DEFAULT NULL,
  `tbalo_comments` varchar(500) DEFAULT NULL,
  `tbalo_status` int(11) DEFAULT NULL,
  `tbalo_nominal_total_left` double DEFAULT NULL,
  PRIMARY KEY (`tbalo_id`),
  KEY `tbale_fk_1` (`tbe_id`),
  KEY `tbalo_fk_1` (`tbe_id`),
  KEY `tbalo_fk_2` (`tbc_id`),
  CONSTRAINT `tbalo_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbalo_fk_2` FOREIGN KEY (`tbc_id`) REFERENCES `tb_currency` (`tbc_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_assigned_loan`(`tbalo_id`,`tbe_id`,`tbc_id`,`tbalo_name`,`tbalo_created_time`,`tbalo_updated_time`,`tbalo_start_date`,`tbalo_end_date`,`tbalo_nominal`,`tbalo_interest`,`tbalo_tenor`,`tbalo_nominal_total`,`tbalo_monthly_payment`,`tbalo_comments`,`tbalo_status`,`tbalo_nominal_total_left`) values (15,6,56,'pinjaman 1','2012-01-01 18:22:14','2012-01-01 18:23:35','2012-01','2012-12',10000000,0.1,12,10120000,843333.333333333,null,1,-843333.333333328);
insert into `hrm`.`tb_assigned_loan`(`tbalo_id`,`tbe_id`,`tbc_id`,`tbalo_name`,`tbalo_created_time`,`tbalo_updated_time`,`tbalo_start_date`,`tbalo_end_date`,`tbalo_nominal`,`tbalo_interest`,`tbalo_tenor`,`tbalo_nominal_total`,`tbalo_monthly_payment`,`tbalo_comments`,`tbalo_status`,`tbalo_nominal_total_left`) values (16,6,56,'pinjaman 2','2012-01-01 18:23:04','2012-01-01 18:23:40','2012-01','2012-12',2000000,0.1,12,2024000,168666.666666667,null,2,null);
