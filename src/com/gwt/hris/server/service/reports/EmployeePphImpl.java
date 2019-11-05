package com.gwt.hris.server.service.reports;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewReportEmployeePphBeanModel;
import com.gwt.hris.client.service.reports.EmployeePphInterface;
import com.gwt.hris.db.ViewReportEmployeePphBean;
import com.gwt.hris.db.ViewReportEmployeePphManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeePphImpl extends MainRemoteServiceServlet implements EmployeePphInterface {
	
	private static final long serialVersionUID = -6923124747045112411L;

	public PagingLoadResult<ViewReportEmployeePphBeanModel> getPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
		
		List<ViewReportEmployeePphBeanModel> list = new ArrayList<ViewReportEmployeePphBeanModel>();

		ViewReportEmployeePphBean datas[] = null;
		int size = 0;
		try {
			if (this.getThreadLocalRequest() != null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 132, SystemUtil.ACCESS_VIEW) == false) {
					throw new SystemException("No view access");
				}
			}

			String strWhere = "where tbpph_dummy = 0 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + " or tbe_id = " + tbEmployeeBeanModel.getTbeId() + ") ";
			}
			
			if ("Employee ID".equals(searchBy)) {
				strWhere += "and tbe_employee_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Status Ptkp".equals(searchBy)) {
				strWhere += "and tbptkp_status like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Jumlah Ptkp".equals(searchBy)) {
				strWhere += "and tbptkp_jumlah like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Periode".equals(searchBy)) {
				strWhere += "and tbpph_periode like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Salary".equals(searchBy)) {
				strWhere += "and tbpph_salary like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Jkk/Jkm".equals(searchBy)) {
				strWhere += "and tbpph_jkkjkm like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Gaji Pokok Gross".equals(searchBy)) {
				strWhere += "and tbpph_gaji_pokok_gross like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Tunjangan Tetap".equals(searchBy)) {
				strWhere += "and tbpph_tunjangan_tetap like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Tunjangan Jamsostek Jkk/Jkm".equals(searchBy)) {
				strWhere += "and tbpph_tunjangan_jamsostek_jkkjkm like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Total Gross".equals(searchBy)) {
				strWhere += "and tbpph_total_gross like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Iuran Jht Jamsostek".equals(searchBy)) {
				strWhere += "and tbpph_iuran_jht_jamsostek like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Biaya Jabatan".equals(searchBy)) {
				strWhere += "and tbpph_biaya_jabatan like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Loan".equals(searchBy)) {
				strWhere += "and tbpph_loan like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Total THP".equals(searchBy)) {
				strWhere += "and tbpph_total_take_home_pay like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewReportEmployeePphManager.getInstance().countAll();
			} else {
				size = ViewReportEmployeePphManager.getInstance().countWhere(strWhere);
			}

			if (config != null) {
				if (config.getSortField() != null) {
					if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
						strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
					}
				}

				datas = ViewReportEmployeePphManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());
			} else {
				datas = ViewReportEmployeePphManager.getInstance().loadByWhere(strWhere);
			}

			for (int i = 0; i < datas.length; i++) {
				ViewReportEmployeePphBeanModel data = ViewReportEmployeePphManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewReportEmployeePphBeanModel data = new ViewReportEmployeePphBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		if (config != null) {
			return new BasePagingLoadResult<ViewReportEmployeePphBeanModel>(list, config.getOffset(), size);
		} else {
			return new BasePagingLoadResult<ViewReportEmployeePphBeanModel>(list, 0, size);
		}
	}
}
