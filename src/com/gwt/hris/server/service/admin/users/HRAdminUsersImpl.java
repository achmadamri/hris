package com.gwt.hris.server.service.admin.users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.users.HRAdminUsersInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAdminUserGroupsBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAdminUserGroupsBean;
import com.gwt.hris.db.TbAdminUserGroupsManager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbLoginBean;
import com.gwt.hris.db.TbLoginManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class HRAdminUsersImpl extends MainRemoteServiceServlet implements HRAdminUsersInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitLogin(TbLoginBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbLoginBean bean = null;

			if (beanModel.getTblId() != null) {
				bean = TbLoginManager.getInstance().loadByPrimaryKey(beanModel.getTblId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 26, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbLoginManager.getInstance().createTbLoginBean();

				bean = TbLoginManager.getInstance().toBean(beanModel, bean);

				TbLoginManager.getInstance().save(bean);

				bean.setTblLoginId("USR" + bean.getTblId());

				TbLoginManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 26, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbLoginManager.getInstance().toBean(beanModel, bean);

				TbLoginManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbLoginManager.getInstance().toBeanModel(bean);
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

	public TbLoginBeanModel getLogin(int id) {
		TbLoginBeanModel returnValue = new TbLoginBeanModel();

		try {
			TbLoginBean TbLoginBean = TbLoginManager.getInstance().loadByPrimaryKey(id);

			if (TbLoginBean != null) {
				returnValue = TbLoginManager.getInstance().toBeanModel(TbLoginBean);

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

	public ReturnBean deleteLogin(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 26, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbLoginManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkLogin(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 26, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbLoginManager.getInstance().deleteByWhere("where tbl_id in (" + strId + ")");

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

	public PagingLoadResult<TbLoginBeanModel> getLoginPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbLoginBeanModel> list = new ArrayList<TbLoginBeanModel>();

		TbLoginBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 26, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("ESS".equals(config.get("type"))) {
				strWhere = "where tbaug_id is null ";
			} else if ("HR".equals(config.get("type"))) {
				strWhere = "where tbaug_id is not null ";
			}

			if ("User ID".equals(searchBy)) {
				strWhere = "and tbl_login_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("User Name".equals(searchBy)) {
				strWhere = "and tbl_username like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbLoginManager.getInstance().countAll();
			} else {
				size = TbLoginManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbLoginManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbLoginBeanModel data = TbLoginManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbLoginBeanModel data = new TbLoginBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbLoginBeanModel>(list, config.getOffset(), size);
	}

	public TbAdminUserGroupsBeanModel getTbAdminUserGroupsAll() {
		TbAdminUserGroupsBeanModel returnValue = new TbAdminUserGroupsBeanModel();

		try {
			TbAdminUserGroupsBean tbNegaraBeans[] = TbAdminUserGroupsManager.getInstance().loadAll();
			TbAdminUserGroupsBeanModel tbNegaraBeanModels[] = TbAdminUserGroupsManager.getInstance().toBeanModels(tbNegaraBeans);

			returnValue.setModels(tbNegaraBeanModels);
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

	public TbEmployeeBeanModel getTbEmployeeAll() {
		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			TbEmployeeBean beans[] = TbEmployeeManager.getInstance().loadAll();
			TbEmployeeBeanModel beanModels[] = TbEmployeeManager.getInstance().toBeanModels(beans);

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

	public TbEmployeeBeanModel getTbEmployeeById(int id) {
		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			TbEmployeeBean bean = TbEmployeeManager.getInstance().loadByPrimaryKey(id);
			returnValue = TbEmployeeManager.getInstance().toBeanModel(bean);

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
