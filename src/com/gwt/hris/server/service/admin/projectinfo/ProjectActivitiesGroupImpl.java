package com.gwt.hris.server.service.admin.projectinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.projectinfo.ProjectActivitiesGroupInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbProjectActivitiesGroupBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbProjectActivitiesGroupBean;
import com.gwt.hris.db.TbProjectActivitiesGroupManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ProjectActivitiesGroupImpl extends MainRemoteServiceServlet implements ProjectActivitiesGroupInterface {
	
	private static final long serialVersionUID = -3925363723861526855L;

	public ReturnBean submitProjectActivitiesGroup(TbProjectActivitiesGroupBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbProjectActivitiesGroupBean bean = null;

			if (beanModel.getTbpagId() != null) {
				bean = TbProjectActivitiesGroupManager.getInstance().loadByPrimaryKey(beanModel.getTbpagId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 94, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbProjectActivitiesGroupManager.getInstance().createTbProjectActivitiesGroupBean();

				bean = TbProjectActivitiesGroupManager.getInstance().toBean(beanModel, bean);

				TbProjectActivitiesGroupManager.getInstance().save(bean);

				bean.setTbpagProjectActivitiesGroupId("PAG" + bean.getTbpagId());

				TbProjectActivitiesGroupManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 94, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbProjectActivitiesGroupManager.getInstance().toBean(beanModel, bean);

				TbProjectActivitiesGroupManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbProjectActivitiesGroupManager.getInstance().toBeanModel(bean);
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

	public TbProjectActivitiesGroupBeanModel getProjectActivitiesGroup(int id) {
		TbProjectActivitiesGroupBeanModel returnValue = new TbProjectActivitiesGroupBeanModel();

		try {
			TbProjectActivitiesGroupBean TbProjectActivitiesGroupBean = TbProjectActivitiesGroupManager.getInstance().loadByPrimaryKey(id);

			if (TbProjectActivitiesGroupBean != null) {
				returnValue = TbProjectActivitiesGroupManager.getInstance().toBeanModel(TbProjectActivitiesGroupBean);

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

	public ReturnBean deleteProjectActivitiesGroup(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 94, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbProjectActivitiesGroupManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkProjectActivitiesGroup(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 94, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbProjectActivitiesGroupManager.getInstance().deleteByWhere("where tbpag_id in (" + strId + ")");

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

	public PagingLoadResult<TbProjectActivitiesGroupBeanModel> getProjectActivitiesGroupPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbProjectActivitiesGroupBeanModel> list = new ArrayList<TbProjectActivitiesGroupBeanModel>();

		TbProjectActivitiesGroupBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 94, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Activity Group ID".equals(searchBy)) {
				strWhere = "where tbpag_project_activities_group_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Activity Group Name".equals(searchBy)) {
				strWhere = "where tbpag_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbProjectActivitiesGroupManager.getInstance().countAll();
			} else {
				size = TbProjectActivitiesGroupManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbProjectActivitiesGroupManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbProjectActivitiesGroupBeanModel data = TbProjectActivitiesGroupManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbProjectActivitiesGroupBeanModel data = new TbProjectActivitiesGroupBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbProjectActivitiesGroupBeanModel>(list, config.getOffset(), size);
	}
}
