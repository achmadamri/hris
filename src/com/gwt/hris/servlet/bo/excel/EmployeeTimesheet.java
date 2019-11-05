package com.gwt.hris.servlet.bo.excel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwt.hris.client.service.bean.ViewTimesheetProjectBeanModel;
import com.gwt.hris.db.ViewTimesheetProjectBean;
import com.gwt.hris.db.ViewTimesheetProjectManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.util.StringUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class EmployeeTimesheet {
	public void process(HttpServletRequest request, HttpServletResponse response, String searchBy, String searchValue) throws DAOException, RowsExceededException, WriteException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String strFileName = "employee_timesheet_" + df.format(new Date());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + strFileName + ".xls");

		WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
		WritableSheet s = w.createSheet("employeeinformation", 0);

		String strFields[] = ViewTimesheetProjectManager.FIELD_NAMES;
		int colA = 0;
		for (int i = strFields.length - 1; i >= 0; i--) {
			s.addCell(new Label(colA++, 0, strFields[i]));
		}

		String strWhere = "";
		if ("Customer Name".equals(searchBy)) {
			strWhere += "and tbc_name like '%" + searchValue.replaceAll("'", "") + "%' ";
		} else if ("Project Name".equals(searchBy)) {
			strWhere += "and tbp_name like '%" + searchValue.replaceAll("'", "") + "%' ";
		} else if ("Employee Name".equals(searchBy)) {
			strWhere += "and tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
		} else if ("Activity Group".equals(searchBy)) {
			strWhere += "and tbpag_name like '%" + searchValue.replaceAll("'", "") + "%' ";
		} else if ("Activity".equals(searchBy)) {
			strWhere += "and tbpa_name like '%" + searchValue.replaceAll("'", "") + "%' ";
		} else if ("Start Of Week".equals(searchBy)) {
			strWhere += "and tbt_start_of_week like '%" + searchValue.replaceAll("'", "") + "%' ";
		}

		ViewTimesheetProjectBean ViewTimesheetProjectBeans[] = ViewTimesheetProjectManager.getInstance().loadByWhere(strWhere);
		for (int i = 0; i < ViewTimesheetProjectBeans.length; i++) {
			int colB = 0;
			for (int ii = strFields.length - 1; ii >= 0; ii--) {
				String strField = StringUtil.getInstance().getFieldFormat(strFields[ii]);
				ViewTimesheetProjectBeanModel ViewTimesheetProjectBeanModel = ViewTimesheetProjectManager.getInstance().toBeanModel(ViewTimesheetProjectBeans[i]);
				s.addCell(new Label(colB++, i + 1, ViewTimesheetProjectBeanModel.get(strField) == null ? "" : ViewTimesheetProjectBeanModel.get(strField).toString()));
			}
		}

		w.write();
		w.close();
	}
}
