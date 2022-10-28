USE hrm;

CREATE TABLE `tb_kpi_assign` (
  `tbka_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbe_id` int(11) DEFAULT NULL,
  `tbk_id` int(11) DEFAULT NULL,
  `tbka_evaluasi` varchar(500) DEFAULT NULL,
  `tbka_action` varchar(500) DEFAULT NULL,
  `tbka_poin` int(11) DEFAULT NULL,
  `tbka_status` int(11) DEFAULT NULL,
  `tbka_spv_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbka_id`),
  KEY `tbka_fk_1` (`tbe_id`),
  KEY `tbka_fk_2` (`tbk_id`),
  KEY `tbka_fk3` (`tbka_spv_id`),
  CONSTRAINT `tbka_fk3` FOREIGN KEY (`tbka_spv_id`) REFERENCES `tb_employee` (`tbe_id`) ON UPDATE NO ACTION,
  CONSTRAINT `tbka_fk_1` FOREIGN KEY (`tbe_id`) REFERENCES `tb_employee` (`tbe_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbka_fk_2` FOREIGN KEY (`tbk_id`) REFERENCES `tb_kpi` (`tbk_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;


insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (1,5,11,'qwe','asd',4,4,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (2,5,4,'lorem ipsum dolor sit amet lorem ipsum dolor sit amet lorem ipsum dolor sit amet','amet sit dolor ipsum lorem amet sit dolor ipsum lorem amet sit dolor ipsum lorem',5,4,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (3,5,9,'qweqwe qweqw qweq wqeqwe qweqw qeqweqw qwe','asdas asda asda  asdasd asdas dasdasd asdadas asdasdasdas asdasdas',2,4,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (4,5,10,'1','1',1,4,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (5,5,13,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (6,5,14,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (7,5,15,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (8,5,12,'10','10',10,3,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (9,6,11,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (10,6,4,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (11,6,9,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (12,6,10,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (13,6,13,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (14,6,14,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (15,6,15,null,null,null,1,2);
insert into `tb_kpi_assign`(`tbka_id`,`tbe_id`,`tbk_id`,`tbka_evaluasi`,`tbka_action`,`tbka_poin`,`tbka_status`,`tbka_spv_id`) values (16,6,12,null,null,null,1,2);
