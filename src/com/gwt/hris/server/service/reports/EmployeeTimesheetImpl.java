package com.gwt.hris.server.service.reports;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewTimesheetProjectBeanModel;
import com.gwt.hris.client.service.reports.EmployeeTimesheetInterface;
import com.gwt.hris.db.ViewTimesheetProjectBean;
import com.gwt.hris.db.ViewTimesheetProjectManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeeTimesheetImpl extends MainRemoteServiceServlet implements EmployeeTimesheetInterface {

	private static final long serialVersionUID = -4777262746803856292L;

	public PagingLoadResult<ViewTimesheetProjectBeanModel> getPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
		
		List<ViewTimesheetProjectBeanModel> list = new ArrayList<ViewTimesheetProjectBeanModel>();

		ViewTimesheetProjectBean datas[] = null;
		int size = 0;
		try {
			if (this.getThreadLocalRequest() != null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 135, SystemUtil.ACCESS_VIEW) == false) {
					throw new SystemException("No view access");
				}
			}

			String strWhere = "where 1=1 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere = "where tbp_id in (select tbp_id from tb_project_employee where tbe_id = " + tbEmployeeBeanModel.getTbeId() + ")";
			}
			
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

			if ("".equals(strWhere)) {
				size = ViewTimesheetProjectManager.getInstance().countAll();
			} else {
				size = ViewTimesheetProjectManager.getInstance().countWhere(strWhere);
			}

			if (config != null) {
				if (config.getSortField() != null) {
					if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
						strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
					}
				}

				datas = ViewTimesheetProjectManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());
			} else {
				datas = ViewTimesheetProjectManager.getInstance().loadByWhere(strWhere);
			}

			for (int i = 0; i < datas.length; i++) {
				ViewTimesheetProjectBeanModel data = ViewTimesheetProjectManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewTimesheetProjectBeanModel data = new ViewTimesheetProjectBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		if (config != null) {
			return new BasePagingLoadResult<ViewTimesheetProjectBeanModel>(list, config.getOffset(), size);
		} else {
			return new BasePagingLoadResult<ViewTimesheetProjectBeanModel>(list, 0, size);
		}
	}
}
