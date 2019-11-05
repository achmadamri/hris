package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewNotificationBeanModel;
import com.gwt.hris.client.service.pim.NotificationInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbNotificationBean;
import com.gwt.hris.db.TbNotificationManager;
import com.gwt.hris.db.ViewNotificationBean;
import com.gwt.hris.db.ViewNotificationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class NotificationImpl extends MainRemoteServiceServlet implements NotificationInterface {

	private static final long serialVersionUID = 1068343375462187824L;

	public PagingLoadResult<ViewNotificationBeanModel> getNotificationPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewNotificationBeanModel> list = new ArrayList<ViewNotificationBeanModel>();

		ViewNotificationBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}
			
			String strWhere = "where tbn_read_date is null and " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewNotificationManager.getInstance().countAll();
			} else {
				size = ViewNotificationManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewNotificationManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewNotificationBeanModel data = ViewNotificationManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewNotificationBeanModel data = new ViewNotificationBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewNotificationBeanModel>(list, config.getOffset(), size);
	}
	
	public ReturnBean submitNotificationRead(ViewNotificationBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbNotificationBean bean = TbNotificationManager.getInstance().loadByWhere("where tbn_id = " + beanModel.getTbnId() + " and tbe_id_to = " + tbEmployeeBeanModel.getTbeId())[0];

			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No insert access");
			}

			bean.setTbnReadDate(new Date().getTime());
			
			TbNotificationManager.getInstance().save(bean);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Updated");

			returnValue.set("model", beanModel);

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}
}
