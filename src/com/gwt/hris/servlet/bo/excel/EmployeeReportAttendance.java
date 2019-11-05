package com.gwt.hris.servlet.bo.excel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewReportEmployeeAttendanceBeanModel;
import com.gwt.hris.db.ViewReportEmployeeAttendanceBean;
import com.gwt.hris.db.ViewReportEmployeeAttendanceManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.util.StringUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class EmployeeReportAttendance {
	public void process(HttpServletRequest request, HttpServletResponse response, String searchBy, String searchValue) throws DAOException, RowsExceededException, WriteException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String strFileName = "employee_report_attendance_" + df.format(new Date());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + strFileName + ".xls");

		WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
		WritableSheet s = w.createSheet("employeereportattendance", 0);

		String strFields[] = ViewReportEmployeeAttendanceManager.FIELD_NAMES;
		int colA = 0;
		for (int i = strFields.length - 1; i >= 0; i--) {
			s.addCell(new Label(colA++, 0, strFields[i]));
		}

		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) request.getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) request.getSession().getAttribute("TbLoginBeanModel");

		String strWhere = "where 1=1 ";

		if (tbLoginBeanModel.getTbaugId() == null) {
			strWhere += "and tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + " or tbe_id = " + tbEmployeeBeanModel.getTbeId() + ") ";
		}

		if (!"".equals(searchBy) && !"".equals(searchValue)) {
			if ("Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue + "%' ";
			} else if ("Date".equals(searchBy)) {
				strWhere += "and rtba_date like '%" + searchValue + "%' ";
			} else if ("In Time".equals(searchBy)) {
				strWhere += "and rtba_in_time like '%" + searchValue + "%' ";
			} else if ("In Time Diff".equals(searchBy)) {
				strWhere += "and rtba_in_time_diff like '%" + searchValue + "%' ";
			} else if ("Out Time".equals(searchBy)) {
				strWhere += "and rtba_out_time like '%" + searchValue + "%' ";
			} else if ("Out Time Diff".equals(searchBy)) {
				strWhere += "and rtba_out_time_diff like '%" + searchValue + "%' ";
			} else if ("In Note".equals(searchBy)) {
				strWhere += "and rtba_in_note like '%" + searchValue + "%' ";
			} else if ("Out Note".equals(searchBy)) {
				strWhere += "and rtba_out_note like '%" + searchValue + "%' ";
			} else if ("Shift ID".equals(searchBy)) {
				strWhere += "and tbs_shift_id like '%" + searchValue + "%' ";
			} else if ("Shift Name".equals(searchBy)) {
				strWhere += "and tbs_name like '%" + searchValue + "%' ";
			} else if ("In Name".equals(searchBy)) {
				strWhere += "and tbs_in_time like '%" + searchValue + "%' ";
			} else if ("Out Name".equals(searchBy)) {
				strWhere += "and tbs_out_time like '%" + searchValue + "%' ";
			}
		}

		ViewReportEmployeeAttendanceBean viewReportEmployeeAttendanceBeans[] = ViewReportEmployeeAttendanceManager.getInstance().loadByWhere(strWhere);
		for (int i = 0; i < viewReportEmployeeAttendanceBeans.length; i++) {
			int colB = 0;
			for (int ii = strFields.length - 1; ii >= 0; ii--) {
				String strField = StringUtil.getInstance().getFieldFormat(strFields[ii]);
				ViewReportEmployeeAttendanceBeanModel viewReportEmployeeAttendanceBeanModel = ViewReportEmployeeAttendanceManager.getInstance().toBeanModel(viewReportEmployeeAttendanceBeans[i]);
				s.addCell(new Label(colB++, i + 1, viewReportEmployeeAttendanceBeanModel.get(strField) == null ? "" : viewReportEmployeeAttendanceBeanModel.get(strField).toString()));
			}
		}

		w.write();
		w.close();
	}
}
