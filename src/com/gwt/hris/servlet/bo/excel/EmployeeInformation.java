package com.gwt.hris.servlet.bo.excel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwt.hris.client.service.bean.ViewReportEmployeeInformationBeanModel;
import com.gwt.hris.db.ViewReportEmployeeInformationBean;
import com.gwt.hris.db.ViewReportEmployeeInformationManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.util.StringUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class EmployeeInformation {
	public void process(HttpServletRequest request, HttpServletResponse response, String searchBy, String searchValue) throws DAOException, RowsExceededException, WriteException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String strFileName = "employee_information_" + df.format(new Date());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + strFileName + ".xls");

		WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
		WritableSheet s = w.createSheet("employeeinformation", 0);

		String strFields[] = ViewReportEmployeeInformationManager.FIELD_NAMES;
		int colA = 0;
		for (int i = strFields.length - 1; i >= 0; i--) {
			s.addCell(new Label(colA++, 0, strFields[i]));
		}

		String strWhere = "";
		if ("City".equals(searchBy)) {
			strWhere += "where tbcd_city like '%" + searchValue + "%' ";
		} else if ("Country".equals(searchBy)) {
			strWhere += "where tbn_nama like '%" + searchValue + "%' ";
		} else if ("Currency".equals(searchBy)) {
			strWhere += "where tbes_currency_name like '%" + searchValue + "%' ";
		} else if ("DOB".equals(searchBy)) {
			strWhere += "where tbe_dob like '%" + searchValue + "%' ";
		} else if ("Driver Lic. Exp.".equals(searchBy)) {
			strWhere += "where tbe_driver_license_expiry like '%" + searchValue + "%' ";
		} else if ("Driver Lic. No".equals(searchBy)) {
			strWhere += "where tbe_driver_license_no like '%" + searchValue + "%' ";
		} else if ("Employee ID".equals(searchBy)) {
			strWhere += "where tbe_employee_id like '%" + searchValue + "%' ";
		} else if ("Employee Name".equals(searchBy)) {
			strWhere += "where tbe_name like '%" + searchValue + "%' ";
		} else if ("Employment Status".equals(searchBy)) {
			strWhere += "where tbes_name like '%" + searchValue + "%' ";
		} else if ("Gender".equals(searchBy)) {
			strWhere += "where tbe_gender like '%" + searchValue + "%' ";
		} else if ("Home Phone".equals(searchBy)) {
			strWhere += "where tbcd_home_phone like '%" + searchValue + "%' ";
		} else if ("ID No".equals(searchBy)) {
			strWhere += "where tbe_id_no like '%" + searchValue + "%' ";
		} else if ("Job Title".equals(searchBy)) {
			strWhere += "where tbjt_name like '%" + searchValue + "%' ";
		} else if ("Joined Date".equals(searchBy)) {
			strWhere += "where tbj_joined_date like '%" + searchValue + "%' ";
		} else if ("Marital Status".equals(searchBy)) {
			strWhere += "where tbe_marital_status like '%" + searchValue + "%' ";
		} else if ("Mobile Phone".equals(searchBy)) {
			strWhere += "where tbcd_mobile_phone like '%" + searchValue + "%' ";
		} else if ("Organization".equals(searchBy)) {
			strWhere += "where tbo_nama like '%" + searchValue + "%' ";
		} else if ("Province".equals(searchBy)) {
			strWhere += "where tbcd_province like '%" + searchValue + "%' ";
		} else if ("Report To".equals(searchBy)) {
			strWhere += "where tbe_name_report_to like '%" + searchValue + "%' ";
		} else if ("Salary Grade".equals(searchBy)) {
			strWhere += "where tbp_name like '%" + searchValue + "%' ";
		} else if ("Smoker".equals(searchBy)) {
			strWhere += "where tbe_smoker like '%" + searchValue + "%' ";
		} else if ("Street".equals(searchBy)) {
			strWhere += "where tbcd_street like '%" + searchValue + "%' ";
		} else if ("Tax No".equals(searchBy)) {
			strWhere += "where tbe_tax_no like '%" + searchValue + "%' ";
		} else if ("Work Phone".equals(searchBy)) {
			strWhere += "where tbcd_work_phone like '%" + searchValue + "%' ";
		} else if ("Zip".equals(searchBy)) {
			strWhere += "where tbcd_zip_code like '%" + searchValue + "%' ";
		}

		ViewReportEmployeeInformationBean ViewReportEmployeeInformationBeans[] = ViewReportEmployeeInformationManager.getInstance().loadByWhere(strWhere);
		for (int i = 0; i < ViewReportEmployeeInformationBeans.length; i++) {
			int colB = 0;
			for (int ii = strFields.length - 1; ii >= 0; ii--) {
				String strField = StringUtil.getInstance().getFieldFormat(strFields[ii]);
				ViewReportEmployeeInformationBeanModel ViewReportEmployeeInformationBeanModel = ViewReportEmployeeInformationManager.getInstance().toBeanModel(ViewReportEmployeeInformationBeans[i]);
				s.addCell(new Label(colB++, i + 1, ViewReportEmployeeInformationBeanModel.get(strField) == null ? "" : ViewReportEmployeeInformationBeanModel.get(strField).toString()));
			}
		}

		w.write();
		w.close();
	}
}
