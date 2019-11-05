package com.gwt.hris.server.service.performance;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiAssignBeanModel;
import com.gwt.hris.client.service.performance.KPIEmployeeInterface;
import com.gwt.hris.db.ViewKpiAssignBean;
import com.gwt.hris.db.ViewKpiAssignManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class KPIEmployeeImpl extends MainRemoteServiceServlet implements KPIEmployeeInterface {
	
	private static final long serialVersionUID = 8458069291243046071L;

	public PagingLoadResult<ViewKpiAssignBeanModel> getKpiAssignPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		List<ViewKpiAssignBeanModel> list = new ArrayList<ViewKpiAssignBeanModel>();

		ViewKpiAssignBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 116, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_id = " + tbEmployeeBeanModel.getTbeId() + " and tbka_status in (0, 1, 3, 4) ";

			if ("KPI Group".equals(searchBy)) {
				strWhere += "and tbkg_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Description".equals(searchBy)) {
				strWhere += "and tbk_description like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewKpiAssignManager.getInstance().countAll();
			} else {
				size = ViewKpiAssignManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewKpiAssignManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewKpiAssignBeanModel data = ViewKpiAssignManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewKpiAssignBeanModel data = new ViewKpiAssignBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewKpiAssignBeanModel>(list, config.getOffset(), size);
	}
}
