package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedEducationBeanModel;
import com.gwt.hris.client.service.bean.TbEducationBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeEducationBeanModel;
import com.gwt.hris.client.service.pim.AssignedEducationInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAssignedEducationBean;
import com.gwt.hris.db.TbAssignedEducationManager;
import com.gwt.hris.db.TbEducationBean;
import com.gwt.hris.db.TbEducationManager;
import com.gwt.hris.db.ViewEmployeeEducationBean;
import com.gwt.hris.db.ViewEmployeeEducationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class AssignedEducationImpl extends MainRemoteServiceServlet implements AssignedEducationInterface {
	
	private static final long serialVersionUID = -3373326569009469271L;

	public ReturnBean submitAssignedEducation(TbAssignedEducationBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbAssignedEducationBean bean = null;

			if (beanModel.getTbaeId() != null) {
				bean = TbAssignedEducationManager.getInstance().loadByPrimaryKey(beanModel.getTbaeId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbAssignedEducationManager.getInstance().createTbAssignedEducationBean();

				bean = TbAssignedEducationManager.getInstance().toBean(beanModel, bean);

				TbAssignedEducationManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbAssignedEducationManager.getInstance().toBean(beanModel, bean);

				TbAssignedEducationManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbAssignedEducationManager.getInstance().toBeanModel(bean);
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

	public PagingLoadResult<ViewEmployeeEducationBeanModel> getAssignedEducationPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeEducationBeanModel> list = new ArrayList<ViewEmployeeEducationBeanModel>();

		ViewEmployeeEducationBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			strWhere = "where " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewEmployeeEducationManager.getInstance().countAll();
			} else {
				size = ViewEmployeeEducationManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeEducationManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeEducationBeanModel data = ViewEmployeeEducationManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeEducationBeanModel data = new ViewEmployeeEducationBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeEducationBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteAssignedEducation(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbAssignedEducationManager.getInstance().deleteByPrimaryKey(id);

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

	public TbAssignedEducationBeanModel getAssignedEducation(int id) {
		TbAssignedEducationBeanModel returnValue = new TbAssignedEducationBeanModel();

		try {
			TbAssignedEducationBean TbAssignedEducationBean = TbAssignedEducationManager.getInstance().loadByPrimaryKey(id);

			if (TbAssignedEducationBean != null) {
				returnValue = TbAssignedEducationManager.getInstance().toBeanModel(TbAssignedEducationBean);

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

	public TbEducationBeanModel getTbEducationAll() {
		TbEducationBeanModel returnValue = new TbEducationBeanModel();

		try {
			TbEducationBean tbCurrencyBeans[] = TbEducationManager.getInstance().loadAll();
			TbEducationBeanModel tbCurrencyBeanModels[] = TbEducationManager.getInstance().toBeanModels(tbCurrencyBeans);

			returnValue.setModels(tbCurrencyBeanModels);
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
