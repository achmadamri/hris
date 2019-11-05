package com.gwt.hris.servlet.bo.excel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewPphPotonganBeanModel;
import com.gwt.hris.client.service.bean.ViewPphTambahanBeanModel;
import com.gwt.hris.client.service.bean.ViewReportEmployeePphBeanModel;
import com.gwt.hris.db.ViewPphPotonganBean;
import com.gwt.hris.db.ViewPphPotonganManager;
import com.gwt.hris.db.ViewPphTambahanBean;
import com.gwt.hris.db.ViewPphTambahanManager;
import com.gwt.hris.db.ViewReportEmployeePphBean;
import com.gwt.hris.db.ViewReportEmployeePphManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.util.StringUtil;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class EmployeeReportPph {
	public void process(HttpServletRequest request, HttpServletResponse response, String searchBy, String searchValue) throws DAOException, RowsExceededException, WriteException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String strFileName = "employee_report_pph_" + df.format(new Date());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=" + strFileName + ".xls");

		WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
		WritableSheet s = w.createSheet("employeereportpph", 0);

		String strFields[] = ViewReportEmployeePphManager.FIELD_NAMES;
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
			if ("Employee ID".equals(searchBy)) {
				strWhere += "and tbe_employee_id like '%" + searchValue + "%' ";
			} else if ("Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue + "%' ";
			} else if ("Status Ptkp".equals(searchBy)) {
				strWhere += "and tbptkp_status like '%" + searchValue + "%' ";
			} else if ("Jumlah Ptkp".equals(searchBy)) {
				strWhere += "and tbptkp_jumlah like '%" + searchValue + "%' ";
			} else if ("Periode".equals(searchBy)) {
				strWhere += "and tbpph_periode like '%" + searchValue + "%' ";
			} else if ("Salary".equals(searchBy)) {
				strWhere += "and tbpph_salary like '%" + searchValue + "%' ";
			} else if ("Jkk/Jkm".equals(searchBy)) {
				strWhere += "and tbpph_jkkjkm like '%" + searchValue + "%' ";
			} else if ("Gaji Pokok Gross".equals(searchBy)) {
				strWhere += "and tbpph_gaji_pokok_gross like '%" + searchValue + "%' ";
			} else if ("Tunjangan Tetap".equals(searchBy)) {
				strWhere += "and tbpph_tunjangan_tetap like '%" + searchValue + "%' ";
			} else if ("Tunjangan Jamsostek Jkk/Jkm".equals(searchBy)) {
				strWhere += "and tbpph_tunjangan_jamsostek_jkkjkm like '%" + searchValue + "%' ";
			} else if ("Total Gross".equals(searchBy)) {
				strWhere += "and tbpph_total_gross like '%" + searchValue + "%' ";
			} else if ("Iuran Jht Jamsostek".equals(searchBy)) {
				strWhere += "and tbpph_iuran_jht_jamsostek like '%" + searchValue + "%' ";
			} else if ("Biaya Jabatan".equals(searchBy)) {
				strWhere += "and tbpph_biaya_jabatan like '%" + searchValue + "%' ";
			} else if ("Loan".equals(searchBy)) {
				strWhere += "and tbpph_loan like '%" + searchValue + "%' ";
			} else if ("Total THP".equals(searchBy)) {
				strWhere += "and tbpph_total_take_home_pay like '%" + searchValue + "%' ";
			}
		}

		ViewReportEmployeePphBean viewReportEmployeePphBeans[] = ViewReportEmployeePphManager.getInstance().loadByWhere(strWhere);
		for (int i = 0; i < viewReportEmployeePphBeans.length; i++) {
			int colB = 0;
			for (int ii = strFields.length - 1; ii >= 0; ii--) {
				String strField = StringUtil.getInstance().getFieldFormat(strFields[ii]);
				ViewReportEmployeePphBeanModel viewReportEmployeePphBeanModel = ViewReportEmployeePphManager.getInstance().toBeanModel(viewReportEmployeePphBeans[i]);
				s.addCell(new Label(colB++, i + 1, viewReportEmployeePphBeanModel.get(strField) == null ? "" : viewReportEmployeePphBeanModel.get(strField).toString()));
			}
		}
		
		s = w.createSheet("potongan", 1);

		strFields = ViewPphPotonganManager.FIELD_NAMES;
		colA = 0;
		for (int i = strFields.length - 1; i >= 0; i--) {
			s.addCell(new Label(colA++, 0, strFields[i]));
		}

		String strWhereA = "where tbpph_id in (select tbpph_id from view_report_employee_pph " + strWhere + ")";

		ViewPphPotonganBean viewPphPotonganBeans[] = ViewPphPotonganManager.getInstance().loadByWhere(strWhereA);
		for (int i = 0; i < viewPphPotonganBeans.length; i++) {
			int colB = 0;
			for (int ii = strFields.length - 1; ii >= 0; ii--) {
				String strField = StringUtil.getInstance().getFieldFormat(strFields[ii]);
				ViewPphPotonganBeanModel viewPphPotonganBeanModel = ViewPphPotonganManager.getInstance().toBeanModel(viewPphPotonganBeans[i]);
				s.addCell(new Label(colB++, i + 1, viewPphPotonganBeanModel.get(strField) == null ? "" : viewPphPotonganBeanModel.get(strField).toString()));
			}
		}
		
		s = w.createSheet("tambahan", 2);

		strFields = ViewPphTambahanManager.FIELD_NAMES;
		colA = 0;
		for (int i = strFields.length - 1; i >= 0; i--) {
			s.addCell(new Label(colA++, 0, strFields[i]));
		}

		String strWhereB = "where tbpph_id in (select tbpph_id from view_report_employee_pph " + strWhere + ")";

		ViewPphTambahanBean viewPphTambahanBeans[] = ViewPphTambahanManager.getInstance().loadByWhere(strWhereB);
		for (int i = 0; i < viewPphTambahanBeans.length; i++) {
			int colB = 0;
			for (int ii = strFields.length - 1; ii >= 0; ii--) {
				String strField = StringUtil.getInstance().getFieldFormat(strFields[ii]);
				ViewPphTambahanBeanModel viewPphTambahanBeanModel = ViewPphTambahanManager.getInstance().toBeanModel(viewPphTambahanBeans[i]);
				s.addCell(new Label(colB++, i + 1, viewPphTambahanBeanModel.get(strField) == null ? "" : viewPphTambahanBeanModel.get(strField).toString()));
			}
		}

		w.write();
		w.close();
	}
}
