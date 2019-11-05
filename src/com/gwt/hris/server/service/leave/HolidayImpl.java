package com.gwt.hris.server.service.leave;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbHolidayBeanModel;
import com.gwt.hris.client.service.leave.HolidayInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbHolidayBean;
import com.gwt.hris.db.TbHolidayManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class HolidayImpl extends MainRemoteServiceServlet implements HolidayInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitHoliday(TbHolidayBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbHolidayBean bean = null;

			if (beanModel.getTbhId() != null) {
				bean = TbHolidayManager.getInstance().loadByPrimaryKey(beanModel.getTbhId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 50, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbHolidayManager.getInstance().createTbHolidayBean();

				bean = TbHolidayManager.getInstance().toBean(beanModel, bean);

				TbHolidayManager.getInstance().save(bean);

				bean.setTbhHolidayId("HOL" + bean.getTbhId());

				TbHolidayManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				bean = TbHolidayManager.getInstance().toBean(beanModel, bean);

				TbHolidayManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbHolidayManager.getInstance().toBeanModel(bean);
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

	public TbHolidayBeanModel getHoliday(int id) {
		TbHolidayBeanModel returnValue = new TbHolidayBeanModel();

		try {
			TbHolidayBean TbHolidayBean = TbHolidayManager.getInstance().loadByPrimaryKey(id);

			if (TbHolidayBean != null) {
				returnValue = TbHolidayManager.getInstance().toBeanModel(TbHolidayBean);

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

	public ReturnBean deleteHoliday(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 50, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbHolidayManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkHoliday(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 50, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbHolidayManager.getInstance().deleteByWhere("where tbh_id in (" + strId + ")");

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

	public PagingLoadResult<TbHolidayBeanModel> getHolidayPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbHolidayBeanModel> list = new ArrayList<TbHolidayBeanModel>();

		TbHolidayBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 50, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Name of Holiday".equals(searchBy)) {
				strWhere = "where tbh_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Date".equals(searchBy)) {
				strWhere = "where tbh_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Repeats Annually".equals(searchBy)) {
				strWhere = "where tbh_repeat_annually like '%" + ("yes".equalsIgnoreCase(searchValue) ? "0" : "1") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbHolidayManager.getInstance().countAll();
			} else {
				size = TbHolidayManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbHolidayManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbHolidayBeanModel data = TbHolidayManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbHolidayBeanModel data = new TbHolidayBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbHolidayBeanModel>(list, config.getOffset(), size);
	}

	public TbHolidayBeanModel getTbHolidayAll() {
		TbHolidayBeanModel returnValue = new TbHolidayBeanModel();

		try {
			TbHolidayBean tbHolidayBeans[] = TbHolidayManager.getInstance().loadAll();
			TbHolidayBeanModel tbHolidayBeanModels[] = TbHolidayManager.getInstance().toBeanModels(tbHolidayBeans);

			returnValue.setModels(tbHolidayBeanModels);
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
