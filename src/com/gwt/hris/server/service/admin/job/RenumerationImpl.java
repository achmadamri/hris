package com.gwt.hris.server.service.admin.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.job.RenumerationInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewRenumerationBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbRenumerationBean;
import com.gwt.hris.db.TbRenumerationManager;
import com.gwt.hris.db.ViewEmployeeRenumerationBean;
import com.gwt.hris.db.ViewEmployeeRenumerationManager;
import com.gwt.hris.db.ViewRenumerationBean;
import com.gwt.hris.db.ViewRenumerationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class RenumerationImpl extends MainRemoteServiceServlet implements RenumerationInterface {
	
	private static final long serialVersionUID = 5090005863087716639L;

	public ReturnBean submitRenumeration(TbRenumerationBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbRenumerationBean bean = null;

			if (beanModel.getTbrId() != null) {
				bean = TbRenumerationManager.getInstance().loadByPrimaryKey(beanModel.getTbrId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 106, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbRenumerationManager.getInstance().createTbRenumerationBean();

				bean = TbRenumerationManager.getInstance().toBean(beanModel, bean);

				TbRenumerationManager.getInstance().save(bean);

				bean.setTbrRenumerationId("REN" + bean.getTbrId());

				TbRenumerationManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 106, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbRenumerationManager.getInstance().toBean(beanModel, bean);

				TbRenumerationManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbRenumerationManager.getInstance().toBeanModel(bean);
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

	public TbRenumerationBeanModel getRenumeration(int id) {
		TbRenumerationBeanModel returnValue = new TbRenumerationBeanModel();

		try {
			TbRenumerationBean TbRenumerationBean = TbRenumerationManager.getInstance().loadByPrimaryKey(id);

			if (TbRenumerationBean != null) {
				returnValue = TbRenumerationManager.getInstance().toBeanModel(TbRenumerationBean);

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

	public ReturnBean deleteRenumeration(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 106, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbRenumerationManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkRenumeration(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 106, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbRenumerationManager.getInstance().deleteByWhere("where tbr_id in (" + strId + ")");

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

	public PagingLoadResult<ViewRenumerationBeanModel> getRenumerationPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewRenumerationBeanModel> list = new ArrayList<ViewRenumerationBeanModel>();

		ViewRenumerationBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 106, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Renumeration ID".equals(searchBy)) {
				strWhere = "where tbr_renumeration_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Renumeration Name".equals(searchBy)) {
				strWhere = "where tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewRenumerationManager.getInstance().countAll();
			} else {
				size = ViewRenumerationManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewRenumerationManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewRenumerationBeanModel data = ViewRenumerationManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewRenumerationBeanModel data = new ViewRenumerationBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewRenumerationBeanModel>(list, config.getOffset(), size);
	}

	public PagingLoadResult<ViewEmployeeRenumerationBeanModel> getRenumerationPagingEss(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeRenumerationBeanModel> list = new ArrayList<ViewEmployeeRenumerationBeanModel>();

		ViewEmployeeRenumerationBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 106, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where " + searchBy + " = " + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewEmployeeRenumerationManager.getInstance().countAll();
			} else {
				size = ViewEmployeeRenumerationManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeRenumerationManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeRenumerationBeanModel data = ViewEmployeeRenumerationManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeRenumerationBeanModel data = new ViewEmployeeRenumerationBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeRenumerationBeanModel>(list, config.getOffset(), size);
	}
}
