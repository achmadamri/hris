package com.gwt.hris.server.service.tax;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeePttBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeePttBeanModel;
import com.gwt.hris.client.service.tax.EmployeePTTInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEmployeePttBean;
import com.gwt.hris.db.TbEmployeePttManager;
import com.gwt.hris.db.ViewEmployeePttBean;
import com.gwt.hris.db.ViewEmployeePttManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeePTTImpl extends MainRemoteServiceServlet implements EmployeePTTInterface {
	
	private static final long serialVersionUID = -7961569030655255347L;

	public ReturnBean submitPtt(TbEmployeePttBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbEmployeePttBean bean = null;

			if (beanModel.getTbepttId() != null) {
				bean = TbEmployeePttManager.getInstance().loadByPrimaryKey(beanModel.getTbepttId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 126, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbEmployeePttManager.getInstance().createTbEmployeePttBean();

				bean = TbEmployeePttManager.getInstance().toBean(beanModel, bean);

				bean.setTbepttInsertTime(new Date().getTime());

				TbEmployeePttManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				beanModel.setTbepttInsertTime(bean.getTbepttInsertTime());

				bean = TbEmployeePttManager.getInstance().toBean(beanModel, bean);

				TbEmployeePttManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbEmployeePttManager.getInstance().toBeanModel(bean);
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

	public TbEmployeePttBeanModel getPtt(int id) {
		TbEmployeePttBeanModel returnValue = new TbEmployeePttBeanModel();

		try {
			TbEmployeePttBean TbEmployeePttBean = TbEmployeePttManager.getInstance().loadByPrimaryKey(id);

			if (TbEmployeePttBean != null) {
				returnValue = TbEmployeePttManager.getInstance().toBeanModel(TbEmployeePttBean);

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

	public ReturnBean deletePtt(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 126, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbEmployeePttManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkPtt(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 126, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbEmployeePttManager.getInstance().deleteByWhere("where tbeptt_id in (" + strId + ")");

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

	public PagingLoadResult<ViewEmployeePttBeanModel> getPttPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeePttBeanModel> list = new ArrayList<ViewEmployeePttBeanModel>();

		ViewEmployeePttBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 126, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Employee Name".equals(searchBy)) {
				strWhere = "where tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewEmployeePttManager.getInstance().countAll();
			} else {
				size = ViewEmployeePttManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeePttManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeePttBeanModel data = ViewEmployeePttManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeePttBeanModel data = new ViewEmployeePttBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeePttBeanModel>(list, config.getOffset(), size);
	}

	public PagingLoadResult<ViewEmployeePttBeanModel> getPttPagingESS(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeePttBeanModel> list = new ArrayList<ViewEmployeePttBeanModel>();

		ViewEmployeePttBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where tbe_id = " + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewEmployeePttManager.getInstance().countAll();
			} else {
				size = ViewEmployeePttManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeePttManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeePttBeanModel data = ViewEmployeePttManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeePttBeanModel data = new ViewEmployeePttBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeePttBeanModel>(list, config.getOffset(), size);
	}

	public ViewEmployeePttBeanModel getViewEmployeePttAll() {
		ViewEmployeePttBeanModel returnValue = new ViewEmployeePttBeanModel();

		try {
			ViewEmployeePttBean ViewEmployeePttBeans[] = ViewEmployeePttManager.getInstance().loadAll();
			ViewEmployeePttBeanModel ViewEmployeePttBeanModels[] = ViewEmployeePttManager.getInstance().toBeanModels(ViewEmployeePttBeans);

			returnValue.setModels(ViewEmployeePttBeanModels);
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
