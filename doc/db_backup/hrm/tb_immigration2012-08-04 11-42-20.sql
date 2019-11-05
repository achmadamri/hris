USE hrm;

CREATE TABLE `tb_immigration` (
  `tbe_id` int(11) NOT NULL,
  `tbi_immigration_type` int(11) DEFAULT NULL,
  `tbi_immigration_no` varchar(45) DEFAULT NULL,
  `tbi_l9_status` varchar(45) DEFAULT NULL,
  `tbi_l9_review_date` datetime DEFAULT NULL,
  `tbn_id` int(11) DEFAULT NULL,
  `tbi_issued_date` datetime DEFAULT NULL,
  `tbi_expiry_date` datetime DEFAULT NULL,
  `tbi_comments` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`tbe_id`),
  KEY `tbi_fk_1` (`tbe_id`),
  KEY `tbi_fk_2` (`tbn_id`),
  CONSTRAINT `tbi_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE,
  CONSTRAINT `tbi_fk_2` FOREIGN KEY (`tbn_id`) REFERENCES `tb_negara` (`tbn_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


insert into `tb_immigration`(`tbe_id`,`tbi_immigration_type`,`tbi_immigration_no`,`tbi_l9_status`,`tbi_l9_review_date`,`tbn_id`,`tbi_issued_date`,`tbi_expiry_date`,`tbi_comments`) values (1,1,'123',null,'2012-01-27 00:00:00',100,'2012-04-19 00:00:00','2012-01-26 00:00:00',null);
insert into `tb_immigration`(`tbe_id`,`tbi_immigration_type`,`tbi_immigration_no`,`tbi_l9_status`,`tbi_l9_review_date`,`tbn_id`,`tbi_issued_date`,`tbi_expiry_date`,`tbi_comments`) values (2,null,'456',null,null,null,null,null,null);
insert into `tb_immigration`(`tbe_id`,`tbi_immigration_type`,`tbi_immigration_no`,`tbi_l9_status`,`tbi_l9_review_date`,`tbn_id`,`tbi_issued_date`,`tbi_expiry_date`,`tbi_comments`) values (5,null,'',null,null,null,null,null,null);
insert into `tb_immigration`(`tbe_id`,`tbi_immigration_type`,`tbi_immigration_no`,`tbi_l9_status`,`tbi_l9_review_date`,`tbn_id`,`tbi_issued_date`,`tbi_expiry_date`,`tbi_comments`) values (6,null,null,null,null,null,null,null,null);
insert into `tb_immigration`(`tbe_id`,`tbi_immigration_type`,`tbi_immigration_no`,`tbi_l9_status`,`tbi_l9_review_date`,`tbn_id`,`tbi_issued_date`,`tbi_expiry_date`,`tbi_comments`) values (7,null,null,null,null,null,null,null,null);
insert into `tb_immigration`(`tbe_id`,`tbi_immigration_type`,`tbi_immigration_no`,`tbi_l9_status`,`tbi_l9_review_date`,`tbn_id`,`tbi_issued_date`,`tbi_expiry_date`,`tbi_comments`) values (8,null,null,null,null,null,null,null,null);
