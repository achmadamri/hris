package com.gwt.hris.server.service.leave;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;
import com.gwt.hris.client.service.leave.LeaveTypesInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbLeaveTypesBean;
import com.gwt.hris.db.TbLeaveTypesManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.server.service.QueryManager;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class LeaveTypesImpl extends MainRemoteServiceServlet implements LeaveTypesInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitLeaveTypes(TbLeaveTypesBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbLeaveTypesBean bean = null;

			if (beanModel.getTbltId() != null) {
				bean = TbLeaveTypesManager.getInstance().loadByPrimaryKey(beanModel.getTbltId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 51, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbLeaveTypesManager.getInstance().createTbLeaveTypesBean();

				bean = TbLeaveTypesManager.getInstance().toBean(beanModel, bean);

				TbLeaveTypesManager.getInstance().save(bean);

				bean.setTbltLeaveTypesId("LVT" + bean.getTbltId());

				TbLeaveTypesManager.getInstance().update(bean);

				// TbAssignedLeaves
				QueryManager.getInstance().updateTbAssignedLeaves();

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				bean = TbLeaveTypesManager.getInstance().toBean(beanModel, bean);

				TbLeaveTypesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbLeaveTypesManager.getInstance().toBeanModel(bean);
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

	public TbLeaveTypesBeanModel getLeaveTypes(int id) {
		TbLeaveTypesBeanModel returnValue = new TbLeaveTypesBeanModel();

		try {
			TbLeaveTypesBean TbLeaveTypesBean = TbLeaveTypesManager.getInstance().loadByPrimaryKey(id);

			if (TbLeaveTypesBean != null) {
				returnValue = TbLeaveTypesManager.getInstance().toBeanModel(TbLeaveTypesBean);

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

	public ReturnBean deleteLeaveTypes(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 51, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbLeaveTypesManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkLeaveTypes(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 51, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbLeaveTypesManager.getInstance().deleteByWhere("where tbh_id in (" + strId + ")");

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

	public PagingLoadResult<TbLeaveTypesBeanModel> getLeaveTypesPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbLeaveTypesBeanModel> list = new ArrayList<TbLeaveTypesBeanModel>();

		TbLeaveTypesBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 51, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Leave Types ID".equals(searchBy)) {
				strWhere = "where tblt_leave_types_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Leave Types".equals(searchBy)) {
				strWhere = "where tblt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbLeaveTypesManager.getInstance().countAll();
			} else {
				size = TbLeaveTypesManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbLeaveTypesManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbLeaveTypesBeanModel data = TbLeaveTypesManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbLeaveTypesBeanModel data = new TbLeaveTypesBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbLeaveTypesBeanModel>(list, config.getOffset(), size);
	}

	public TbLeaveTypesBeanModel getTbLeaveTypesAll() {
		TbLeaveTypesBeanModel returnValue = new TbLeaveTypesBeanModel();

		try {
			TbLeaveTypesBean tbLeaveTypesBeans[] = TbLeaveTypesManager.getInstance().loadAll();
			TbLeaveTypesBeanModel tbLeaveTypesBeanModels[] = TbLeaveTypesManager.getInstance().toBeanModels(tbLeaveTypesBeans);

			returnValue.setModels(tbLeaveTypesBeanModels);
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
