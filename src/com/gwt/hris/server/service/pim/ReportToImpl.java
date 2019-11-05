package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbReportToBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeReportToBeanModel;
import com.gwt.hris.client.service.pim.ReportToInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbReportToBean;
import com.gwt.hris.db.TbReportToManager;
import com.gwt.hris.db.ViewEmployeeReportToBean;
import com.gwt.hris.db.ViewEmployeeReportToManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ReportToImpl extends MainRemoteServiceServlet implements ReportToInterface {
	
	private static final long serialVersionUID = -3373326569009469271L;

	public ReturnBean submitReportTo(TbReportToBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbReportToBean bean = null;

			if (beanModel.getTbrtId() != null) {
				bean = TbReportToManager.getInstance().loadByPrimaryKey(beanModel.getTbrtId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbReportToManager.getInstance().createTbReportToBean();

				bean = TbReportToManager.getInstance().toBean(beanModel, bean);

				TbReportToManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbReportToManager.getInstance().toBean(beanModel, bean);

				TbReportToManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbReportToManager.getInstance().toBeanModel(bean);
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

	public PagingLoadResult<ViewEmployeeReportToBeanModel> getReportToPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeReportToBeanModel> list = new ArrayList<ViewEmployeeReportToBeanModel>();

		ViewEmployeeReportToBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			strWhere = "WHERE tbrt_spv IS NOT NULL AND (tbe_id = " + searchValue.replaceAll("'", "") + " OR tbrt_spv = " + searchValue.replaceAll("'", "") + ")";

			if ("".equals(strWhere)) {
				size = ViewEmployeeReportToManager.getInstance().countAll();
			} else {
				size = ViewEmployeeReportToManager.getInstance().countWhere(strWhere);
			}

			datas = ViewEmployeeReportToManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeReportToBeanModel data = ViewEmployeeReportToManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeReportToBeanModel data = new ViewEmployeeReportToBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeReportToBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteReportTo(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbReportToManager.getInstance().deleteByPrimaryKey(id);

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

	public ViewEmployeeReportToBeanModel getReportTo(int id) {
		ViewEmployeeReportToBeanModel returnValue = new ViewEmployeeReportToBeanModel();

		try {
			ViewEmployeeReportToBean beans[] = ViewEmployeeReportToManager.getInstance().loadByWhere("where tbrt_id = " + id);

			if (beans.length > 0) {
				returnValue = ViewEmployeeReportToManager.getInstance().toBeanModel(beans[0]);

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
