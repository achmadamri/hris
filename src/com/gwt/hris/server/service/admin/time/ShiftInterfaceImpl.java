package com.gwt.hris.server.service.admin.time;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.time.ShiftInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbShiftBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbShiftBean;
import com.gwt.hris.db.TbShiftManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ShiftInterfaceImpl extends MainRemoteServiceServlet implements ShiftInterface {
	
	private static final long serialVersionUID = -7883601706130098064L;

	public ReturnBean submitShift(TbShiftBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbShiftBean bean = null;

			if (beanModel.getTbsId() != null) {
				bean = TbShiftManager.getInstance().loadByPrimaryKey(beanModel.getTbsId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 64, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbShiftManager.getInstance().createTbShiftBean();

				bean = TbShiftManager.getInstance().toBean(beanModel, bean);

				TbShiftManager.getInstance().save(bean);

				bean.setTbsShiftId("SFT" + bean.getTbsId());

				TbShiftManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				bean = TbShiftManager.getInstance().toBean(beanModel, bean);

				TbShiftManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbShiftManager.getInstance().toBeanModel(bean);
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

	public TbShiftBeanModel getShift(int id) {
		TbShiftBeanModel returnValue = new TbShiftBeanModel();

		try {
			TbShiftBean TbShiftBean = TbShiftManager.getInstance().loadByPrimaryKey(id);

			if (TbShiftBean != null) {
				returnValue = TbShiftManager.getInstance().toBeanModel(TbShiftBean);

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

	public ReturnBean deleteShift(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 64, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbShiftBean tbShiftBean = TbShiftManager.getInstance().loadByPrimaryKey(id);
			
			if (tbShiftBean.getTbsDefault() == null) {
				TbShiftManager.getInstance().deleteByPrimaryKey(id);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Deleted");

				commit = true;
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Fail Deleted");
			}
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

	public ReturnBean deleteBulkShift(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 64, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			boolean isValid = true;

			String strId = "";
			for (Integer id : ids) {
				if (id == 1) {
					isValid = false;
				}
				strId = strId + id + ", ";
			}

			if (isValid) {
				strId = strId.substring(0, strId.length() - 2);
				TbShiftManager.getInstance().deleteByWhere("where tbs_id in (" + strId + ")");

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Deleted");

				commit = true;
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Fail Deleted");
			}
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

	public PagingLoadResult<TbShiftBeanModel> getShiftPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbShiftBeanModel> list = new ArrayList<TbShiftBeanModel>();

		TbShiftBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 64, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Shift ID".equals(searchBy)) {
				strWhere = "where tbs_shift_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Shift Name".equals(searchBy)) {
				strWhere = "where tbs_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Start Time".equals(searchBy)) {
				strWhere = "where tbs_start_time like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("End Time".equals(searchBy)) {
				strWhere = "where tbs_end_time like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbShiftManager.getInstance().countAll();
			} else {
				size = TbShiftManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbShiftManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbShiftBeanModel data = TbShiftManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbShiftBeanModel data = new TbShiftBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbShiftBeanModel>(list, config.getOffset(), size);
	}

	public TbShiftBeanModel getTbShiftAll() {
		TbShiftBeanModel returnValue = new TbShiftBeanModel();

		try {
			TbShiftBean tbShiftBeans[] = TbShiftManager.getInstance().loadAll();
			TbShiftBeanModel tbShiftBeanModels[] = TbShiftManager.getInstance().toBeanModels(tbShiftBeans);

			returnValue.setModels(tbShiftBeanModels);
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
