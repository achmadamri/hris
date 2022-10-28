USE hrm;

CREATE TABLE `tb_company_property` (
  `tbcp_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbcp_nama` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`tbcp_id`),
  KEY `tbcp_fk_1` (`tbe_id`),
  CONSTRAINT `tbcp_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;


insert into `hrm`.`tb_company_property`(`tbcp_id`,`tbe_id`,`tbcp_nama`) values (1,1,'Laptop Acer 4745G 14". Serial: ABC001');
insert into `hrm`.`tb_company_property`(`tbcp_id`,`tbe_id`,`tbcp_nama`) values (15,2,'Toyota Kijang Innova 2011');
