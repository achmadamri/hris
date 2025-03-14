﻿USE hrm;

CREATE TABLE `tb_pph_tambahan` (
  `tbppht_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbpph_id` int(11) DEFAULT NULL,
  `tbppht_name` varchar(45) DEFAULT NULL,
  `tbppht_nominal` double DEFAULT NULL,
  PRIMARY KEY (`tbppht_id`),
  KEY `tbppht_fk_1` (`tbpph_id`),
  CONSTRAINT `tbppht_fk_1` FOREIGN KEY (`tbpph_id`) REFERENCES `tb_pph` (`tbpph_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;


insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (21,13,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (22,13,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (23,14,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (24,14,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (25,15,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (26,16,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (27,16,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (28,16,'Bonus',9000000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (29,17,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (30,18,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (31,19,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (32,19,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (33,20,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (34,20,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (35,21,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (36,22,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (37,22,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (38,23,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (39,24,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (40,25,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (41,25,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (42,26,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (43,26,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (44,27,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (45,28,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (46,28,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (47,29,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (48,30,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (49,31,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (50,31,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (51,32,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (52,32,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (53,33,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (54,34,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (55,34,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (56,35,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (57,36,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (58,37,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (59,37,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (60,38,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (61,38,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (62,39,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (63,40,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (64,40,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (65,41,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (66,42,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (67,43,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (68,43,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (69,44,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (70,44,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (71,45,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (72,46,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (73,46,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (74,47,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (75,48,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (76,49,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (77,49,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (78,49,'Bonus',9000000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (79,50,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (80,50,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (81,50,'Bonus',9000000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (82,51,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (83,52,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (84,52,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (85,53,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (86,54,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (87,55,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (88,55,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (89,56,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (90,56,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (91,57,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (92,58,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (93,58,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (94,59,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (95,60,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (96,61,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (97,61,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (98,62,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (99,62,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (100,63,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (101,64,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (102,64,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (103,65,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (104,66,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (105,67,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (106,67,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (107,68,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (108,68,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (109,69,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (110,70,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (111,70,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (112,71,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (113,72,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (114,73,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (115,73,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (116,74,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (117,74,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (118,75,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (119,76,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (120,76,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (121,77,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (122,78,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (123,79,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (124,79,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (125,80,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (126,80,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (127,81,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (128,82,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (129,82,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (130,83,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (131,84,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (132,85,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (133,85,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (134,86,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (135,86,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (136,87,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (137,88,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (138,88,'Tunjangan 2',100000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (139,89,'Tunjangan 1',10000);
insert into `tb_pph_tambahan`(`tbppht_id`,`tbpph_id`,`tbppht_name`,`tbppht_nominal`) values (140,90,'Tunjangan 1',10000);
