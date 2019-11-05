package com.gwt.hris.server.service.admin.companyinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.companyinfo.CompanyPropertyInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCompanyPropertyBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewPropertyEmployeeBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbCompanyPropertyBean;
import com.gwt.hris.db.TbCompanyPropertyManager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.ViewPropertyEmployeeBean;
import com.gwt.hris.db.ViewPropertyEmployeeManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class CompanyPropertyImpl extends MainRemoteServiceServlet implements CompanyPropertyInterface {
	
	private static final long serialVersionUID = -691939291296534124L;

	public ReturnBean removeAssignCompanyProperty(int tbcpId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 6, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbCompanyPropertyBean tbCompanyPropertyBean = TbCompanyPropertyManager.getInstance().loadByPrimaryKey(tbcpId);
			tbCompanyPropertyBean.setTbeId(null);
			TbCompanyPropertyManager.getInstance().save(tbCompanyPropertyBean);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Remove Assign");

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

	public ReturnBean assignCompanyProperty(int tbcpId, int tbeId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 6, SystemUtil.ACCESS_INSERT) == false) {
				throw new SystemException("No insert access");
			}

			Manager.getInstance().beginTransaction();

			if (TbCompanyPropertyManager.getInstance().countWhere("where tbcp_id = " + tbcpId + " and tbe_id is null") > 0) {
				if (TbEmployeeManager.getInstance().countWhere("where tbe_id = " + tbeId) > 0) {
					TbEmployeeBean tbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(tbeId);

					TbCompanyPropertyBean tbCompanyPropertyBean = TbCompanyPropertyManager.getInstance().loadByPrimaryKey(tbcpId);
					tbCompanyPropertyBean.setTbeId(tbEmployeeBean.getTbeId());
					TbCompanyPropertyManager.getInstance().save(tbCompanyPropertyBean);

					returnValue.setOperationStatus(true);
					returnValue.setMessage("Success Assign");
				} else {
					returnValue.setOperationStatus(false);
					returnValue.setMessage("Fail Assign. Employee not found.");
				}
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Fail Assign. Company Property is already assigned.");
			}

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

	public ReturnBean submitCompanyProperty(TbCompanyPropertyBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbCompanyPropertyBean bean = null;

			if (beanModel.getTbcpId() != null) {
				bean = TbCompanyPropertyManager.getInstance().loadByPrimaryKey(beanModel.getTbcpId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 6, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbCompanyPropertyManager.getInstance().createTbCompanyPropertyBean();

				bean = TbCompanyPropertyManager.getInstance().toBean(beanModel, bean);

				TbCompanyPropertyManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 6, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbCompanyPropertyManager.getInstance().toBean(beanModel, bean);

				TbCompanyPropertyManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbCompanyPropertyManager.getInstance().toBeanModel(bean);
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

	public TbCompanyPropertyBeanModel getCompanyProperty(int id) {
		TbCompanyPropertyBeanModel returnValue = new TbCompanyPropertyBeanModel();

		try {
			TbCompanyPropertyBean bean = TbCompanyPropertyManager.getInstance().loadByPrimaryKey(id);

			if (bean != null) {
				returnValue = TbCompanyPropertyManager.getInstance().toBeanModel(bean);

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

	public ReturnBean deleteCompanyProperty(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 6, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbCompanyPropertyManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkCompanyProperty(Integer tblIds[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 6, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : tblIds) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbCompanyPropertyManager.getInstance().deleteByWhere("where tbcp_id in (" + strId + ")");

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

	public PagingLoadResult<ViewPropertyEmployeeBeanModel> getCompanyPropertyPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewPropertyEmployeeBeanModel> list = new ArrayList<ViewPropertyEmployeeBeanModel>();

		ViewPropertyEmployeeBean beans[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 6, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Property Name".equals(searchBy)) {
				strWhere = "where tbcp_nama like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Employee Name".equals(searchBy)) {
				strWhere = "where name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewPropertyEmployeeManager.getInstance().countAll();
			} else {
				size = ViewPropertyEmployeeManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			beans = ViewPropertyEmployeeManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < beans.length; i++) {
				ViewPropertyEmployeeBeanModel data = ViewPropertyEmployeeManager.getInstance().toBeanModel(beans[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewPropertyEmployeeBeanModel data = new ViewPropertyEmployeeBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewPropertyEmployeeBeanModel>(list, config.getOffset(), size);
	}

	public PagingLoadResult<TbEmployeeBeanModel> getEmployeePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbEmployeeBeanModel> list = new ArrayList<TbEmployeeBeanModel>();

		TbEmployeeBean beans[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 6, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("First Name".equals(searchBy)) {
				strWhere = "where tbe_first_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Last Name".equals(searchBy)) {
				strWhere = "where tbe_last_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Nick Name".equals(searchBy)) {
				strWhere = "where tbe_nick_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("tbpId".equals(searchBy)) {
				strWhere = "where tbe_id in (select tbe_id from tb_project_employee where tbp_id = " + searchValue.replaceAll("'", "") + ")";
			}

			if ("".equals(strWhere)) {
				size = TbEmployeeManager.getInstance().countAll();
			} else {
				size = TbEmployeeManager.getInstance().countWhere(strWhere);
			}

			if ("tbeFirstName".equals(config.getSortField())) {
				strWhere = strWhere + "order by tbe_first_name " + config.getSortDir() + " ";
			} else if ("tbeLastName".equals(config.getSortField())) {
				strWhere = strWhere + "order by tbe_last_name " + config.getSortDir() + " ";
			} else if ("tbeNickName".equals(config.getSortField())) {
				strWhere = strWhere + "order by tbe_nick_name " + config.getSortDir() + " ";
			}

			beans = TbEmployeeManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < beans.length; i++) {
				TbEmployeeBeanModel data = TbEmployeeManager.getInstance().toBeanModel(beans[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbEmployeeBeanModel data = new TbEmployeeBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbEmployeeBeanModel>(list, config.getOffset(), size);
	}
}
