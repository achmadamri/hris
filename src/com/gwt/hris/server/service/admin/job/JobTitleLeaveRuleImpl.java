package com.gwt.hris.server.service.admin.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.job.JobTitleLeaveRuleInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbJobTitleLeaveBeanModel;
import com.gwt.hris.client.service.bean.ViewJobTitleLeaveBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbJobTitleLeaveBean;
import com.gwt.hris.db.TbJobTitleLeaveManager;
import com.gwt.hris.db.ViewJobTitleLeaveBean;
import com.gwt.hris.db.ViewJobTitleLeaveManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class JobTitleLeaveRuleImpl extends MainRemoteServiceServlet implements JobTitleLeaveRuleInterface {
	
	private static final long serialVersionUID = 8521348629954864421L;

	public ReturnBean submitAssignedEducation(TbJobTitleLeaveBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbJobTitleLeaveBean bean = null;

			if (beanModel.getTbjtlId() != null) {
				bean = TbJobTitleLeaveManager.getInstance().loadByPrimaryKey(beanModel.getTbjtlId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbJobTitleLeaveManager.getInstance().createTbJobTitleLeaveBean();

				bean = TbJobTitleLeaveManager.getInstance().toBean(beanModel, bean);

				TbJobTitleLeaveManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbJobTitleLeaveManager.getInstance().toBean(beanModel, bean);

				TbJobTitleLeaveManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbJobTitleLeaveManager.getInstance().toBeanModel(bean);
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

	public PagingLoadResult<ViewJobTitleLeaveBeanModel> getJobTitleLeavePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewJobTitleLeaveBeanModel> list = new ArrayList<ViewJobTitleLeaveBeanModel>();

		ViewJobTitleLeaveBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			strWhere = "where " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewJobTitleLeaveManager.getInstance().countAll();
			} else {
				size = ViewJobTitleLeaveManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewJobTitleLeaveManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewJobTitleLeaveBeanModel data = ViewJobTitleLeaveManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewJobTitleLeaveBeanModel data = new ViewJobTitleLeaveBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewJobTitleLeaveBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteAssignedEducation(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 8, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbJobTitleLeaveManager.getInstance().deleteByPrimaryKey(id);

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

	public TbJobTitleLeaveBeanModel getJobTitleLeave(int id) {
		TbJobTitleLeaveBeanModel returnValue = new TbJobTitleLeaveBeanModel();

		try {
			TbJobTitleLeaveBean TbJobTitleLeaveBean = TbJobTitleLeaveManager.getInstance().loadByPrimaryKey(id);

			if (TbJobTitleLeaveBean != null) {
				returnValue = TbJobTitleLeaveManager.getInstance().toBeanModel(TbJobTitleLeaveBean);

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
}
