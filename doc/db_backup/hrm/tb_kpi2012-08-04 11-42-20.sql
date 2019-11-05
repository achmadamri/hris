USE hrm;

CREATE TABLE `tb_kpi` (
  `tbk_id` int(11) NOT NULL AUTO_INCREMENT,
  `tbkg_id` int(11) DEFAULT NULL,
  `tbe_Id` int(11) DEFAULT NULL,
  `tbk_description` varchar(500) DEFAULT NULL,
  `tbk_target_nilai_1` varchar(500) DEFAULT NULL,
  `tbk_target_nilai_2` varchar(500) DEFAULT NULL,
  `tbk_target_nilai_3` varchar(500) DEFAULT NULL,
  `tbk_target_nilai_4` varchar(500) DEFAULT NULL,
  `tbk_target_nilai_5` varchar(500) DEFAULT NULL,
  `tbk_bobot` int(11) DEFAULT NULL,
  PRIMARY KEY (`tbk_id`),
  KEY `tbk_fk_1` (`tbkg_id`),
  KEY `tbk_fk_2` (`tbe_Id`),
  CONSTRAINT `tbk_fk_1` FOREIGN KEY (`tbkg_id`) REFERENCES `tb_kpi_group` (`tbkg_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbk_fk_2` FOREIGN KEY (`tbe_Id`) REFERENCES `tb_employee` (`tbe_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;


insert into `tb_kpi`(`tbk_id`,`tbkg_id`,`tbe_Id`,`tbk_description`,`tbk_target_nilai_1`,`tbk_target_nilai_2`,`tbk_target_nilai_3`,`tbk_target_nilai_4`,`tbk_target_nilai_5`,`tbk_bobot`) values (4,3,2,'Penyelesaian projek sesuai dengan alokasi mandays berdasarkan Project Assignment Letter','Rata-rata projek diselesaikan melebihi 130% dari alokasi mandays yang telah ditetapkan','Rata-rata projek diselesaikan dalam batas sampai 130% dari alokasi mandays yang telah ditetapkan ','Rata-rata projek diselesaikan sampai batas  120% dari alokasi mandays yang telah ditetapkan ','Rata-rata projek diselesaikan dalam batas 100% dari alokasi mandays yang telah ditetapkan ','Rata-rata projek diselesaikan dalam batas kurang dari 100% dari alokasi mandays ',25);
insert into `tb_kpi`(`tbk_id`,`tbkg_id`,`tbe_Id`,`tbk_description`,`tbk_target_nilai_1`,`tbk_target_nilai_2`,`tbk_target_nilai_3`,`tbk_target_nilai_4`,`tbk_target_nilai_5`,`tbk_bobot`) values (9,3,2,'Qualitas / Metodologi Pekerjaan yang dihasilkan termasuk Dokumentasi','Tidak ada metodologi yang diterapkan','Tidak konsisten dalam menerapkan metodologi','Konsisten mengikuti Metodologi Pekerjaan dibuktikan melalui pembuatan dokumentasi yang memenuhi standar','Syarat sebelumnya ditambah semua dokumentasi diupload ke Intranet','Membuat standar kerangka untuk dokumentasi pekerjaan',15);
insert into `tb_kpi`(`tbk_id`,`tbkg_id`,`tbe_Id`,`tbk_description`,`tbk_target_nilai_1`,`tbk_target_nilai_2`,`tbk_target_nilai_3`,`tbk_target_nilai_4`,`tbk_target_nilai_5`,`tbk_bobot`) values (10,3,2,'Customer Satisfaction','Complain Resmi tertulis atau Feedback dari customer rata-rata 1','Ada complain atau Feedback dari customer rata-rata 2','No Complain atau Feedback dari customer minimum 3','Feedback rata-rata dari customer >=3.5','Ada Written Complement Letter dari Customer',10);
insert into `tb_kpi`(`tbk_id`,`tbkg_id`,`tbe_Id`,`tbk_description`,`tbk_target_nilai_1`,`tbk_target_nilai_2`,`tbk_target_nilai_3`,`tbk_target_nilai_4`,`tbk_target_nilai_5`,`tbk_bobot`) values (11,9,2,'Pengisian Time Report','Missing 20%','Missing 10%','Tidak ada missing','-','-',15);
insert into `tb_kpi`(`tbk_id`,`tbkg_id`,`tbe_Id`,`tbk_description`,`tbk_target_nilai_1`,`tbk_target_nilai_2`,`tbk_target_nilai_3`,`tbk_target_nilai_4`,`tbk_target_nilai_5`,`tbk_bobot`) values (12,4,2,'Melakukan: (1) Identifikasi Kebutuhan Pelanggan; (2) Architecting Solution; (3) Presentation; (4) Proposal Preparation; (5) POC/Demo; (6) Handover to Delivery (7) Project Closing Review.  Pengukuran berdasarkan feedback form untuk setiap opportunity baik ','Feedback rata-rata dari tim Sales atau tim Delivery adalah < 2','Feedback rata-rata dari tim Sales atau tim Delivery adalah <3','Feedback rata-rata atas aktifitas yang sudah dilakukan dari tim sales dan tim delivery adalah => 3 dan <4','Feedback rata-rata atas aktifitas yang sudah dilakukan dari tim sales dan tim delivery adalah >=4','Feedback 4 + compliment letter dari DM Sales',10);
insert into `tb_kpi`(`tbk_id`,`tbkg_id`,`tbe_Id`,`tbk_description`,`tbk_target_nilai_1`,`tbk_target_nilai_2`,`tbk_target_nilai_3`,`tbk_target_nilai_4`,`tbk_target_nilai_5`,`tbk_bobot`) values (13,5,2,'Soft-Skill
- Problem solving
','','','- mencari alternatif jawaban
- mencari hal-hal baru
- mendaya gunakan berbagai resource','* point sebelumnya +
- mencipta cara baru yg lebih baik
- menimbang pros dan cons dari alternatif','* point sebelumnya +
- menjadi tempat bertanya tim
- membuat dan merumuskan konsep"',10);
insert into `tb_kpi`(`tbk_id`,`tbkg_id`,`tbe_Id`,`tbk_description`,`tbk_target_nilai_1`,`tbk_target_nilai_2`,`tbk_target_nilai_3`,`tbk_target_nilai_4`,`tbk_target_nilai_5`,`tbk_bobot`) values (14,5,2,'Hard-Skill
- OSB & ADF','','tidak berhasil ','','bisa digunakan untuk development','bisa demo dengan memuaskan',5);
insert into `tb_kpi`(`tbk_id`,`tbkg_id`,`tbe_Id`,`tbk_description`,`tbk_target_nilai_1`,`tbk_target_nilai_2`,`tbk_target_nilai_3`,`tbk_target_nilai_4`,`tbk_target_nilai_5`,`tbk_bobot`) values (15,5,2,'Certification- SOA Implementer Certified','','Tidak diambil','<= Des 2011','<= Agustus 2011','<= Juni 2011',10);
