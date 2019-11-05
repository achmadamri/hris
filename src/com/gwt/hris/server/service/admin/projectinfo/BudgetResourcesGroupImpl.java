package com.gwt.hris.server.service.admin.projectinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.projectinfo.BudgetResourcesGroupInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbBudgetResourcesGroupBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbBudgetResourcesGroupBean;
import com.gwt.hris.db.TbBudgetResourcesGroupManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class BudgetResourcesGroupImpl extends MainRemoteServiceServlet implements BudgetResourcesGroupInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitBudgetResourcesGroup(TbBudgetResourcesGroupBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbBudgetResourcesGroupBean bean = null;

			if (beanModel.getTbbrgId() != null) {
				bean = TbBudgetResourcesGroupManager.getInstance().loadByPrimaryKey(beanModel.getTbbrgId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 130, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbBudgetResourcesGroupManager.getInstance().createTbBudgetResourcesGroupBean();

				bean = TbBudgetResourcesGroupManager.getInstance().toBean(beanModel, bean);

				TbBudgetResourcesGroupManager.getInstance().save(bean);

				bean.setTbbrgBudgetResourcesGroupId("BRG" + bean.getTbbrgId());

				TbBudgetResourcesGroupManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 130, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbBudgetResourcesGroupManager.getInstance().toBean(beanModel, bean);

				TbBudgetResourcesGroupManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbBudgetResourcesGroupManager.getInstance().toBeanModel(bean);
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

	public TbBudgetResourcesGroupBeanModel getBudgetResourcesGroup(int id) {
		TbBudgetResourcesGroupBeanModel returnValue = new TbBudgetResourcesGroupBeanModel();

		try {
			TbBudgetResourcesGroupBean TbBudgetResourcesGroupBean = TbBudgetResourcesGroupManager.getInstance().loadByPrimaryKey(id);

			if (TbBudgetResourcesGroupBean != null) {
				returnValue = TbBudgetResourcesGroupManager.getInstance().toBeanModel(TbBudgetResourcesGroupBean);

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

	public ReturnBean deleteBudgetResourcesGroup(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 130, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbBudgetResourcesGroupManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkBudgetResourcesGroup(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 130, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbBudgetResourcesGroupManager.getInstance().deleteByWhere("where tbbrg_id in (" + strId + ")");

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

	public PagingLoadResult<TbBudgetResourcesGroupBeanModel> getBudgetResourcesGroupPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbBudgetResourcesGroupBeanModel> list = new ArrayList<TbBudgetResourcesGroupBeanModel>();

		TbBudgetResourcesGroupBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 130, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Budget Resources Group ID".equals(searchBy)) {
				strWhere = "where tbbrg_budget_resources_group_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Name".equals(searchBy)) {
				strWhere = "where tbbrg_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbBudgetResourcesGroupManager.getInstance().countAll();
			} else {
				size = TbBudgetResourcesGroupManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbBudgetResourcesGroupManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbBudgetResourcesGroupBeanModel data = TbBudgetResourcesGroupManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbBudgetResourcesGroupBeanModel data = new TbBudgetResourcesGroupBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbBudgetResourcesGroupBeanModel>(list, config.getOffset(), size);
	}
}
