package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLicensesBeanModel;
import com.gwt.hris.client.service.bean.TbLicensesBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLicensesBeanModel;
import com.gwt.hris.client.service.pim.AssignedLicensesInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAssignedLicensesBean;
import com.gwt.hris.db.TbAssignedLicensesManager;
import com.gwt.hris.db.TbLicensesBean;
import com.gwt.hris.db.TbLicensesManager;
import com.gwt.hris.db.ViewEmployeeLicensesBean;
import com.gwt.hris.db.ViewEmployeeLicensesManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class AssignedLicensesImpl extends MainRemoteServiceServlet implements AssignedLicensesInterface {
	
	private static final long serialVersionUID = -3373326569009469271L;

	public ReturnBean submitAssignedLicenses(TbAssignedLicensesBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbAssignedLicensesBean bean = null;

			if (beanModel.getTbalId() != null) {
				bean = TbAssignedLicensesManager.getInstance().loadByPrimaryKey(beanModel.getTbalId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbAssignedLicensesManager.getInstance().createTbAssignedLicensesBean();

				bean = TbAssignedLicensesManager.getInstance().toBean(beanModel, bean);

				TbAssignedLicensesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbAssignedLicensesManager.getInstance().toBean(beanModel, bean);

				TbAssignedLicensesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbAssignedLicensesManager.getInstance().toBeanModel(bean);
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

	public PagingLoadResult<ViewEmployeeLicensesBeanModel> getAssignedLicensesPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeLicensesBeanModel> list = new ArrayList<ViewEmployeeLicensesBeanModel>();

		ViewEmployeeLicensesBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			strWhere = "where " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewEmployeeLicensesManager.getInstance().countAll();
			} else {
				size = ViewEmployeeLicensesManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeLicensesManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeLicensesBeanModel data = ViewEmployeeLicensesManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeLicensesBeanModel data = new ViewEmployeeLicensesBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeLicensesBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteAssignedLicenses(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbAssignedLicensesManager.getInstance().deleteByPrimaryKey(id);

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

	public TbAssignedLicensesBeanModel getAssignedLicenses(int id) {
		TbAssignedLicensesBeanModel returnValue = new TbAssignedLicensesBeanModel();

		try {
			TbAssignedLicensesBean bean = TbAssignedLicensesManager.getInstance().loadByPrimaryKey(id);

			if (bean != null) {
				returnValue = TbAssignedLicensesManager.getInstance().toBeanModel(bean);

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

	public TbLicensesBeanModel getTbLicensesAll() {
		TbLicensesBeanModel returnValue = new TbLicensesBeanModel();

		try {
			TbLicensesBean beans[] = TbLicensesManager.getInstance().loadAll();
			TbLicensesBeanModel beanModels[] = TbLicensesManager.getInstance().toBeanModels(beans);

			returnValue.setModels(beanModels);
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
