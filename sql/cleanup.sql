INSERT INTO hrm.tb_admin_user_groups
   SELECT NULL, tbaug_admin_user_groups_id, tbaug_name
     FROM hrm_130319.tb_admin_user_groups;

INSERT INTO hrm.tb_calendar
   SELECT tbc_date FROM hrm_130319.tb_calendar;

INSERT INTO hrm.tb_currency
   SELECT NULL,
          tbc_currency_id,
          tbc_name,
          tbc_local_currency_kurs
     FROM hrm_130319.tb_currency;

INSERT INTO hrm.tb_leave_types
   SELECT NULL,
          tblt_leave_types_id,
          tblt_name,
          tblt_reduction
     FROM hrm_130319.tb_leave_types;

INSERT INTO hrm.tb_eeo_job_category
   SELECT NULL, tbejc_eeo_job_category_id, tbejc_title
     FROM hrm_130319.tb_eeo_job_category;

INSERT INTO hrm.tb_leave_types
   SELECT NULL,
          tblt_leave_types_id,
          tblt_name,
          tblt_reduction
     FROM hrm_130319.tb_leave_types;

INSERT INTO hrm.tb_employment_status
   SELECT NULL, tbes_employment_status_id, tbes_name
     FROM hrm_130319.tb_employment_status;

INSERT INTO hrm.tb_ethnic_races
   SELECT NULL, tber_ethnic_races_id, tber_name
     FROM hrm_130319.tb_ethnic_races;

INSERT INTO hrm.tb_job_specifications
   SELECT NULL,
          tbjs_name,
          tbjs_description,
          tbjs_duties
     FROM hrm_130319.tb_job_specifications;

INSERT INTO hrm.tb_kpi_group
   SELECT NULL, tbkg_kpi_group_id, tbkg_name FROM hrm_130319.tb_kpi_group;

INSERT INTO hrm.tb_language
   SELECT NULL, tbl_language_id, tbl_name FROM hrm_130319.tb_language;

INSERT INTO hrm.tb_menu
   SELECT NULL,
          tbm_parent_id,
          tbm_nama,
          tbm_index,
          tbm_disabled
     FROM hrm_130319.tb_menu;

INSERT INTO hrm.tb_nationality
   SELECT NULL, tbn_nationality_id, tbn_name FROM hrm_130319.tb_nationality;

INSERT INTO hrm.tb_negara
   SELECT NULL, tbn_nama FROM hrm_130319.tb_negara;

INSERT INTO hrm.tb_organization
   SELECT NULL, tbo_parent_id, tbo_nama FROM hrm_130319.tb_organization;

INSERT INTO hrm.tb_paygrade
   SELECT NULL, tbp_paygrade_id, tbp_name FROM hrm_130319.tb_paygrade;

INSERT INTO hrm.tb_perusahaan
   SELECT tbp_id,
          tbp_name,
          tbp_tax_id,
          tbp_phone,
          tbp_fax,
          fk_tbn_id,
          tbp_address1,
          tbp_city,
          tbp_province,
          tbp_zip_code,
          tbp_comments,
          tbp_jkk,
          tbp_jkm,
          tbp_local_currency_id
     FROM hrm_130319.tb_perusahaan;

INSERT INTO hrm.tb_project_activities_group
   SELECT NULL,
          tbpag_project_activities_group_id,
          tbpag_name,
          tbpag_description
     FROM hrm_130319.tb_project_activities_group;

INSERT INTO hrm.tb_project_activities
   SELECT NULL,
          tbpag_id,
          tbpa_name,
          tbpa_description
     FROM hrm_130319.tb_project_activities;

INSERT INTO hrm.tb_ptkp
   SELECT NULL,
          tbptkp_status,
          tbptkp_keterangan,
          tbptkp_jumlah
     FROM hrm_130319.tb_ptkp;

INSERT INTO hrm.tb_ptt
   SELECT NULL, tbptt_ptt_id, tbptt_name FROM hrm_130319.tb_ptt;

INSERT INTO hrm.tb_renumeration
   SELECT NULL,
          tbc_id,
          tbr_renumeration_id,
          tbr_name,
          tbr_nominal
     FROM hrm_130319.tb_renumeration;

INSERT INTO hrm.tb_shift
   SELECT NULL,
          tbs_shift_id,
          tbs_name,
          tbs_in_time,
          tbs_out_time,
          tbs_default
     FROM hrm_130319.tb_shift;

INSERT INTO hrm.tb_skills
   SELECT NULL,
          tbs_skill_id,
          tbs_name,
          tbs_description
     FROM hrm_130319.tb_skills;

INSERT INTO hrm.tb_tarif_pajak
   SELECT NULL,
          tbtp_pkp_dari,
          tbtp_pkp_sampai,
          tbtp_npwp,
          tbtp_non_npwp
     FROM hrm_130319.tb_tarif_pajak;
	 
	 INSERT INTO hrm.tb_job_specifications
   SELECT NULL,
          tbjs_name,
          tbjs_description,
          tbjs_duties
     FROM hrm_130319.tb_job_specifications;
	 
	 INSERT INTO hrm.tb_job_title
   SELECT NULL,
          tbjt_job_title_id,
          tbjt_name,
          tbjt_description,
          tbjt_comments,
          4,
          4,
          4
     FROM hrm_130319.tb_job_title
    WHERE tbjt_id = 247;