package com.gwt.hris.server.service.admin.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.job.EmploymentStatusInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEmploymentStatusBean;
import com.gwt.hris.db.TbEmploymentStatusManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmploymentStatusImpl extends MainRemoteServiceServlet implements EmploymentStatusInterface {
	
	private static final long serialVersionUID = -9130232253110656682L;

	public ReturnBean submitEmploymentStatus(TbEmploymentStatusBeanModel tbEmploymentStatusBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbEmploymentStatusBean tbEmploymentStatusBean = null;

			if (tbEmploymentStatusBeanModel.getTbesId() != null) {
				tbEmploymentStatusBean = TbEmploymentStatusManager.getInstance().loadByPrimaryKey(tbEmploymentStatusBeanModel.getTbesId());
			}

			if (tbEmploymentStatusBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 11, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbEmploymentStatusBean = TbEmploymentStatusManager.getInstance().createTbEmploymentStatusBean();

				tbEmploymentStatusBean = TbEmploymentStatusManager.getInstance().toBean(tbEmploymentStatusBeanModel, tbEmploymentStatusBean);

				TbEmploymentStatusManager.getInstance().save(tbEmploymentStatusBean);

				tbEmploymentStatusBean.setTbesEmploymentStatusId("EST" + tbEmploymentStatusBean.getTbesId());

				TbEmploymentStatusManager.getInstance().update(tbEmploymentStatusBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 11, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbEmploymentStatusBean = TbEmploymentStatusManager.getInstance().toBean(tbEmploymentStatusBeanModel, tbEmploymentStatusBean);

				TbEmploymentStatusManager.getInstance().save(tbEmploymentStatusBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			tbEmploymentStatusBeanModel = TbEmploymentStatusManager.getInstance().toBeanModel(tbEmploymentStatusBean);
			returnValue.set("model", tbEmploymentStatusBeanModel);

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

	public TbEmploymentStatusBeanModel getEmploymentStatus(int tbesId) {
		TbEmploymentStatusBeanModel returnValue = new TbEmploymentStatusBeanModel();

		try {
			TbEmploymentStatusBean TbEmploymentStatusBean = TbEmploymentStatusManager.getInstance().loadByPrimaryKey(tbesId);

			if (TbEmploymentStatusBean != null) {
				returnValue = TbEmploymentStatusManager.getInstance().toBeanModel(TbEmploymentStatusBean);

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

	public ReturnBean deleteEmploymentStatus(Integer tbesId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 11, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbEmploymentStatusManager.getInstance().deleteByPrimaryKey(tbesId);

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

	public ReturnBean deleteBulkEmploymentStatus(Integer tbesIds[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 11, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : tbesIds) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbEmploymentStatusManager.getInstance().deleteByWhere("where tbes_id in (" + strId + ")");

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

	public PagingLoadResult<TbEmploymentStatusBeanModel> getEmploymentStatusPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbEmploymentStatusBeanModel> list = new ArrayList<TbEmploymentStatusBeanModel>();

		TbEmploymentStatusBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 11, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Emp. Stat. ID".equals(searchBy)) {
				strWhere = "where tbes_employment_status_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Emp. Stat. Name".equals(searchBy)) {
				strWhere = "where tbes_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbEmploymentStatusManager.getInstance().countAll();
			} else {
				size = TbEmploymentStatusManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbEmploymentStatusManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbEmploymentStatusBeanModel data = TbEmploymentStatusManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbEmploymentStatusBeanModel data = new TbEmploymentStatusBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbEmploymentStatusBeanModel>(list, config.getOffset(), size);
	}
}
