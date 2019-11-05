package com.gwt.hris.server.service.reports;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewReportEmployeePayrollBeanModel;
import com.gwt.hris.client.service.reports.EmployeePayrollInterface;
import com.gwt.hris.db.ViewReportEmployeePayrollBean;
import com.gwt.hris.db.ViewReportEmployeePayrollManager;
import com.gwt.hris.db.ViewReportEmployeePphManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeePayrollImpl extends MainRemoteServiceServlet implements EmployeePayrollInterface {
	
	private static final long serialVersionUID = -6923124747045112411L;

	public PagingLoadResult<ViewReportEmployeePayrollBeanModel> getPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
		
		List<ViewReportEmployeePayrollBeanModel> list = new ArrayList<ViewReportEmployeePayrollBeanModel>();

		ViewReportEmployeePayrollBean datas[] = null;
		int size = 0;
		try {
			if (this.getThreadLocalRequest() != null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 136, SystemUtil.ACCESS_VIEW) == false) {
					throw new SystemException("No view access");
				}
			}

			String strWhere = "where 1=1 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + " or tbe_id = " + tbEmployeeBeanModel.getTbeId() + ") ";
			}
			
			if ("Employee ID".equals(searchBy)) {
				strWhere += "and tbe_employee_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
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

				datas = ViewReportEmployeePayrollManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());
			} else {
				datas = ViewReportEmployeePayrollManager.getInstance().loadByWhere(strWhere);
			}

			for (int i = 0; i < datas.length; i++) {
				ViewReportEmployeePayrollBeanModel data = ViewReportEmployeePayrollManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewReportEmployeePayrollBeanModel data = new ViewReportEmployeePayrollBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		if (config != null) {
			return new BasePagingLoadResult<ViewReportEmployeePayrollBeanModel>(list, config.getOffset(), size);
		} else {
			return new BasePagingLoadResult<ViewReportEmployeePayrollBeanModel>(list, 0, size);
		}
	}
}
