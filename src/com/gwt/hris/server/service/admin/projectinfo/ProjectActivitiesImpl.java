package com.gwt.hris.server.service.admin.projectinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.projectinfo.ProjectActivitiesInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbProjectActivitiesBeanModel;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.client.service.bean.ViewProjectActivitiesGroupBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbProjectActivitiesBean;
import com.gwt.hris.db.TbProjectActivitiesGroupBean;
import com.gwt.hris.db.TbProjectActivitiesGroupManager;
import com.gwt.hris.db.TbProjectActivitiesManager;
import com.gwt.hris.db.ViewProjectActivitiesGroupBean;
import com.gwt.hris.db.ViewProjectActivitiesGroupManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ProjectActivitiesImpl extends MainRemoteServiceServlet implements ProjectActivitiesInterface {
	
	private static final long serialVersionUID = 75187015301444821L;

	public ReturnBean submitProjectActivities(TbProjectActivitiesBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbProjectActivitiesBean bean = null;

			if (beanModel.getTbpaId() != null) {
				bean = TbProjectActivitiesManager.getInstance().loadByPrimaryKey(beanModel.getTbpaId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 95, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbProjectActivitiesManager.getInstance().createTbProjectActivitiesBean();

				bean = TbProjectActivitiesManager.getInstance().toBean(beanModel, bean);

				TbProjectActivitiesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 95, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbProjectActivitiesManager.getInstance().toBean(beanModel, bean);

				TbProjectActivitiesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbProjectActivitiesManager.getInstance().toBeanModel(bean);
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

	public TbProjectActivitiesBeanModel getProjectActivities(int id) {
		TbProjectActivitiesBeanModel returnValue = new TbProjectActivitiesBeanModel();

		try {
			TbProjectActivitiesBean TbProjectActivitiesBean = TbProjectActivitiesManager.getInstance().loadByPrimaryKey(id);

			if (TbProjectActivitiesBean != null) {
				returnValue = TbProjectActivitiesManager.getInstance().toBeanModel(TbProjectActivitiesBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("");
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}

	public ReturnBean deleteProjectActivities(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 95, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbProjectActivitiesManager.getInstance().deleteByPrimaryKey(id);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Deleted");

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

	public ReturnBean deleteBulkProjectActivities(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 95, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbProjectActivitiesManager.getInstance().deleteByWhere("where tbpa_id in (" + strId + ")");

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Deleted");

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

	public PagingLoadResult<ViewProjectActivitiesGroupBeanModel> getProjectActivitiesPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewProjectActivitiesGroupBeanModel> list = new ArrayList<ViewProjectActivitiesGroupBeanModel>();

		ViewProjectActivitiesGroupBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 95, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Activity Group Name".equals(searchBy)) {
				strWhere = "where tbpag_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Activity Name".equals(searchBy)) {
				strWhere = "where tbpa_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewProjectActivitiesGroupManager.getInstance().countAll();
			} else {
				size = ViewProjectActivitiesGroupManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewProjectActivitiesGroupManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewProjectActivitiesGroupBeanModel data = ViewProjectActivitiesGroupManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewProjectActivitiesGroupBeanModel data = new ViewProjectActivitiesGroupBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewProjectActivitiesGroupBeanModel>(list, config.getOffset(), size);
	}

	public TbProjectActivitiesGroupBeanModel getTbProjectActivitiesGroupAll() {
		TbProjectActivitiesGroupBeanModel returnValue = new TbProjectActivitiesGroupBeanModel();

		try {
			TbProjectActivitiesGroupBean TbProjectActivitiesGroupBeans[] = TbProjectActivitiesGroupManager.getInstance().loadAll();
			TbProjectActivitiesGroupBeanModel TbProjectActivitiesGroupBeanModels[] = TbProjectActivitiesGroupManager.getInstance().toBeanModels(TbProjectActivitiesGroupBeans);

			returnValue.setModels(TbProjectActivitiesGroupBeanModels);
			returnValue.setOperationStatus(true);
			returnValue.setMessage("");
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}
}
