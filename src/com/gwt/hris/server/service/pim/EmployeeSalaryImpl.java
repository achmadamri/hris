package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeSalaryBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeSalaryBeanModel;
import com.gwt.hris.client.service.pim.EmployeeSalaryInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbCurrencyBean;
import com.gwt.hris.db.TbCurrencyManager;
import com.gwt.hris.db.TbEmployeeSalaryBean;
import com.gwt.hris.db.TbEmployeeSalaryManager;
import com.gwt.hris.db.TbPaygradeBean;
import com.gwt.hris.db.TbPaygradeCurrencyBean;
import com.gwt.hris.db.TbPaygradeCurrencyManager;
import com.gwt.hris.db.TbPaygradeManager;
import com.gwt.hris.db.ViewEmployeeSalaryBean;
import com.gwt.hris.db.ViewEmployeeSalaryManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeeSalaryImpl extends MainRemoteServiceServlet implements EmployeeSalaryInterface {
	
	private static final long serialVersionUID = -3373326569009469271L;

	public ReturnBean submitEmployeeSalary(TbEmployeeSalaryBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbEmployeeSalaryBean bean = null;

			if (beanModel.getTbesId() != null) {
				bean = TbEmployeeSalaryManager.getInstance().loadByPrimaryKey(beanModel.getTbesId());
			}

			int count = TbEmployeeSalaryManager.getInstance().countWhere("where tbe_id = " + beanModel.getTbeId());

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				if (count == 0) {
					bean = TbEmployeeSalaryManager.getInstance().createTbEmployeeSalaryBean();

					bean = TbEmployeeSalaryManager.getInstance().toBean(beanModel, bean);

					TbPaygradeCurrencyBean tbPaygradeCurrencyBean = TbPaygradeCurrencyManager.getInstance().loadByWhere("where tbp_id = " + bean.getTbpId() + " and tbc_id = " + bean.getTbcId())[0];

					if (tbPaygradeCurrencyBean.getTbpcMax() >= bean.getTbesBasicSalary()) {
						TbEmployeeSalaryManager.getInstance().save(bean);

						returnValue.setOperationStatus(true);
						returnValue.setMessage("Success Saved");
					} else {
						returnValue.setOperationStatus(false);
						returnValue.setMessage("Fail Saved");
					}
				} else {
					returnValue.setOperationStatus(false);
					returnValue.setMessage("Fail Saved. Salary is exist");
				}
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbEmployeeSalaryManager.getInstance().toBean(beanModel, bean);

				TbPaygradeCurrencyBean tbPaygradeCurrencyBean = TbPaygradeCurrencyManager.getInstance().loadByWhere("where tbp_id = " + bean.getTbpId() + " and tbc_id = " + bean.getTbcId())[0];

				if (tbPaygradeCurrencyBean.getTbpcMax() >= bean.getTbesBasicSalary()) {
					TbEmployeeSalaryManager.getInstance().save(bean);

					returnValue.setOperationStatus(true);
					returnValue.setMessage("Success Updated");
				} else {
					returnValue.setOperationStatus(false);
					returnValue.setMessage("Fail Updated");
				}
			}

			if (count == 0) {
				beanModel = TbEmployeeSalaryManager.getInstance().toBeanModel(bean);
			}

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

	public PagingLoadResult<ViewEmployeeSalaryBeanModel> getEmployeeSalaryPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeSalaryBeanModel> list = new ArrayList<ViewEmployeeSalaryBeanModel>();

		ViewEmployeeSalaryBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			strWhere = "where " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewEmployeeSalaryManager.getInstance().countAll();
			} else {
				size = ViewEmployeeSalaryManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeSalaryManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeSalaryBeanModel data = ViewEmployeeSalaryManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeSalaryBeanModel data = new ViewEmployeeSalaryBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeSalaryBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteEmployeeSalary(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbEmployeeSalaryManager.getInstance().deleteByPrimaryKey(id);

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

	public TbEmployeeSalaryBeanModel getEmployeeSalary(int id) {
		TbEmployeeSalaryBeanModel returnValue = new TbEmployeeSalaryBeanModel();

		try {
			TbEmployeeSalaryBean TbEmployeeSalaryBean = TbEmployeeSalaryManager.getInstance().loadByPrimaryKey(id);

			if (TbEmployeeSalaryBean != null) {
				returnValue = TbEmployeeSalaryManager.getInstance().toBeanModel(TbEmployeeSalaryBean);

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

	public TbPaygradeBeanModel getTbPaygradeAll() {
		TbPaygradeBeanModel returnValue = new TbPaygradeBeanModel();

		try {
			TbPaygradeBean tbCurrencyBeans[] = TbPaygradeManager.getInstance().loadAll();
			TbPaygradeBeanModel tbCurrencyBeanModels[] = TbPaygradeManager.getInstance().toBeanModels(tbCurrencyBeans);

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

	public TbCurrencyBeanModel getTbCurrencyByPaygrade(int tbpId) {
		TbCurrencyBeanModel returnValue = new TbCurrencyBeanModel();

		try {
			TbCurrencyBean tbCurrencyBeans[] = TbCurrencyManager.getInstance().loadByWhere("where tbc_id in (select tbc_id from tb_paygrade_currency where tbp_id = " + tbpId + ")");
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

	public TbPaygradeCurrencyBeanModel getTbPaygradeCurrency(int tbpId, int tbcId) {
		TbPaygradeCurrencyBeanModel returnValue = new TbPaygradeCurrencyBeanModel();

		try {
			TbPaygradeCurrencyBean TbPaygradeCurrencyBeans[] = TbPaygradeCurrencyManager.getInstance().loadByWhere("where tbp_id = " + tbpId + " and tbc_id = " + tbcId);

			if (TbPaygradeCurrencyBeans.length > 0) {
				returnValue = TbPaygradeCurrencyManager.getInstance().toBeanModel(TbPaygradeCurrencyBeans[0]);

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

	public TbCurrencyBeanModel getTbCurrencyByEmployee(int intTbeId) {
		TbCurrencyBeanModel returnValue = new TbCurrencyBeanModel();

		try {
			TbCurrencyBean tbCurrencyBeans[] = TbCurrencyManager.getInstance().loadByWhere("where tbc_id in (select tbc_id from tb_employee_salary where tbe_id = " + intTbeId + ")");
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
}
