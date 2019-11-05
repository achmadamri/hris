package com.gwt.hris.server.service.admin.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.job.PaygradeInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewPaygradeCurrencyBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbCurrencyBean;
import com.gwt.hris.db.TbCurrencyManager;
import com.gwt.hris.db.TbPaygradeBean;
import com.gwt.hris.db.TbPaygradeCurrencyBean;
import com.gwt.hris.db.TbPaygradeCurrencyManager;
import com.gwt.hris.db.TbPaygradeManager;
import com.gwt.hris.db.ViewPaygradeCurrencyBean;
import com.gwt.hris.db.ViewPaygradeCurrencyManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class PaygradeImpl extends MainRemoteServiceServlet implements PaygradeInterface {
	
	private static final long serialVersionUID = -9130232253110656682L;

	public ReturnBean deletePaygradeCurrency(Integer tbpcId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbPaygradeCurrencyManager.getInstance().deleteByPrimaryKey(tbpcId);

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

	public PagingLoadResult<ViewPaygradeCurrencyBeanModel> getPaygradeCurrencyPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewPaygradeCurrencyBeanModel> list = new ArrayList<ViewPaygradeCurrencyBeanModel>();

		ViewPaygradeCurrencyBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Currency ID".equals(searchBy)) {
				strWhere = "where tbc_currency_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Currency Name".equals(searchBy)) {
				strWhere = "where tbc_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("tbp_id".equals(searchBy)) {
				strWhere = "where tbp_id = '" + searchValue.replaceAll("'", "") + "' ";
			}

			if ("".equals(strWhere)) {
				size = ViewPaygradeCurrencyManager.getInstance().countAll();
			} else {
				size = ViewPaygradeCurrencyManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewPaygradeCurrencyManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewPaygradeCurrencyBeanModel data = ViewPaygradeCurrencyManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewPaygradeCurrencyBeanModel data = new ViewPaygradeCurrencyBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewPaygradeCurrencyBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean submitPaygrade(TbPaygradeBeanModel tbPaygradeBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbPaygradeBean tbPaygradeBean = null;

			if (tbPaygradeBeanModel.getTbpId() != null) {
				tbPaygradeBean = TbPaygradeManager.getInstance().loadByPrimaryKey(tbPaygradeBeanModel.getTbpId());
			}

			if (tbPaygradeBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbPaygradeBean = TbPaygradeManager.getInstance().createTbPaygradeBean();

				tbPaygradeBean = TbPaygradeManager.getInstance().toBean(tbPaygradeBeanModel, tbPaygradeBean);

				TbPaygradeManager.getInstance().save(tbPaygradeBean);

				tbPaygradeBean.setTbpPaygradeId("SAL" + tbPaygradeBean.getTbpId());

				TbPaygradeManager.getInstance().update(tbPaygradeBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbPaygradeBean = TbPaygradeManager.getInstance().toBean(tbPaygradeBeanModel, tbPaygradeBean);

				TbPaygradeManager.getInstance().save(tbPaygradeBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			tbPaygradeBeanModel = TbPaygradeManager.getInstance().toBeanModel(tbPaygradeBean);
			returnValue.set("model", tbPaygradeBeanModel);

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

	public TbPaygradeBeanModel getPaygrade(int tbpId) {
		TbPaygradeBeanModel returnValue = new TbPaygradeBeanModel();

		try {
			TbPaygradeBean TbPaygradeBean = TbPaygradeManager.getInstance().loadByPrimaryKey(tbpId);

			if (TbPaygradeBean != null) {
				returnValue = TbPaygradeManager.getInstance().toBeanModel(TbPaygradeBean);

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

	public ReturnBean deletePaygrade(Integer tbpId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbPaygradeManager.getInstance().deleteByPrimaryKey(tbpId);

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

	public ReturnBean deleteBulkPaygrade(Integer tbpIds[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : tbpIds) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbPaygradeManager.getInstance().deleteByWhere("where tbp_id in (" + strId + ")");

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

	public PagingLoadResult<TbPaygradeBeanModel> getPaygradePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbPaygradeBeanModel> list = new ArrayList<TbPaygradeBeanModel>();

		TbPaygradeBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Salary ID".equals(searchBy)) {
				strWhere = "where tbp_paygrade_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Salary Name".equals(searchBy)) {
				strWhere = "where tbp_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbPaygradeManager.getInstance().countAll();
			} else {
				size = TbPaygradeManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbPaygradeManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbPaygradeBeanModel data = TbPaygradeManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbPaygradeBeanModel data = new TbPaygradeBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbPaygradeBeanModel>(list, config.getOffset(), size);
	}

	public TbCurrencyBeanModel getTbCurrencyAll() {
		TbCurrencyBeanModel returnValue = new TbCurrencyBeanModel();

		try {
			TbCurrencyBean tbCurrencyBeans[] = TbCurrencyManager.getInstance().loadAll();
			TbCurrencyBeanModel tbCurrencyBeanModels[] = TbCurrencyManager.getInstance().toBeanModels(tbCurrencyBeans);

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

	public ReturnBean submitPaygradeCurrency(TbPaygradeCurrencyBeanModel tbPaygradeCurrencyBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbPaygradeCurrencyBean tbPaygradeCurrencyBean = null;

			if (tbPaygradeCurrencyBeanModel.getTbpId() != null) {
				tbPaygradeCurrencyBean = TbPaygradeCurrencyManager.getInstance().loadByPrimaryKey(tbPaygradeCurrencyBeanModel.getTbpId());
			}

			if (tbPaygradeCurrencyBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbPaygradeCurrencyBean = TbPaygradeCurrencyManager.getInstance().createTbPaygradeCurrencyBean();

				tbPaygradeCurrencyBean = TbPaygradeCurrencyManager.getInstance().toBean(tbPaygradeCurrencyBeanModel, tbPaygradeCurrencyBean);

				TbPaygradeCurrencyManager.getInstance().save(tbPaygradeCurrencyBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 10, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbPaygradeCurrencyBean = TbPaygradeCurrencyManager.getInstance().toBean(tbPaygradeCurrencyBeanModel, tbPaygradeCurrencyBean);

				TbPaygradeCurrencyManager.getInstance().save(tbPaygradeCurrencyBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			tbPaygradeCurrencyBeanModel = TbPaygradeCurrencyManager.getInstance().toBeanModel(tbPaygradeCurrencyBean);
			returnValue.set("model", tbPaygradeCurrencyBeanModel);

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
	
	public TbPaygradeCurrencyBeanModel getPaygradeCurrency(int tbpcId) {
		TbPaygradeCurrencyBeanModel returnValue = new TbPaygradeCurrencyBeanModel();

		try {
			TbPaygradeCurrencyBean TbPaygradeCurrencyBean = TbPaygradeCurrencyManager.getInstance().loadByPrimaryKey(tbpcId);

			if (TbPaygradeCurrencyBean != null) {
				returnValue = TbPaygradeCurrencyManager.getInstance().toBeanModel(TbPaygradeCurrencyBean);

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
