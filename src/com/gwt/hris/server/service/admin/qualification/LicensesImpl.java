package com.gwt.hris.server.service.admin.qualification;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.qualification.LicensesInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLicensesBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbLicensesBean;
import com.gwt.hris.db.TbLicensesManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class LicensesImpl extends MainRemoteServiceServlet implements LicensesInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitLicenses(TbLicensesBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbLicensesBean bean = null;

			if (beanModel.getTblId() != null) {
				bean = TbLicensesManager.getInstance().loadByPrimaryKey(beanModel.getTblId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 15, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbLicensesManager.getInstance().createTbLicensesBean();

				bean = TbLicensesManager.getInstance().toBean(beanModel, bean);

				TbLicensesManager.getInstance().save(bean);

				bean.setTblLicensesId("LIC" + bean.getTblId());

				TbLicensesManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 15, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbLicensesManager.getInstance().toBean(beanModel, bean);

				TbLicensesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbLicensesManager.getInstance().toBeanModel(bean);
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

	public TbLicensesBeanModel getLicenses(int id) {
		TbLicensesBeanModel returnValue = new TbLicensesBeanModel();

		try {
			TbLicensesBean TbLicensesBean = TbLicensesManager.getInstance().loadByPrimaryKey(id);

			if (TbLicensesBean != null) {
				returnValue = TbLicensesManager.getInstance().toBeanModel(TbLicensesBean);

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

	public ReturnBean deleteLicenses(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 15, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbLicensesManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkLicenses(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 15, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbLicensesManager.getInstance().deleteByWhere("where tbl_id in (" + strId + ")");

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

	public PagingLoadResult<TbLicensesBeanModel> getLicensesPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbLicensesBeanModel> list = new ArrayList<TbLicensesBeanModel>();

		TbLicensesBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 15, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Licenses ID".equals(searchBy)) {
				strWhere = "where tbl_licenses_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Description".equals(searchBy)) {
				strWhere = "where tbl_description like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbLicensesManager.getInstance().countAll();
			} else {
				size = TbLicensesManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbLicensesManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbLicensesBeanModel data = TbLicensesManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbLicensesBeanModel data = new TbLicensesBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbLicensesBeanModel>(list, config.getOffset(), size);
	}
}
