package com.gwt.hris.server.service.admin.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.job.EeoJobCategoryInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEeoJobCategoryBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEeoJobCategoryBean;
import com.gwt.hris.db.TbEeoJobCategoryManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EeoJobCategoryImpl extends MainRemoteServiceServlet implements EeoJobCategoryInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitEeoJobCategory(TbEeoJobCategoryBeanModel tbEeoJobCategoryBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbEeoJobCategoryBean tbEeoJobCategoryBean = null;

			if (tbEeoJobCategoryBeanModel.getTbejcId() != null) {
				tbEeoJobCategoryBean = TbEeoJobCategoryManager.getInstance().loadByPrimaryKey(tbEeoJobCategoryBeanModel.getTbejcId());
			}

			if (tbEeoJobCategoryBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbEeoJobCategoryBean = TbEeoJobCategoryManager.getInstance().createTbEeoJobCategoryBean();

				tbEeoJobCategoryBean = TbEeoJobCategoryManager.getInstance().toBean(tbEeoJobCategoryBeanModel, tbEeoJobCategoryBean);

				TbEeoJobCategoryManager.getInstance().save(tbEeoJobCategoryBean);

				tbEeoJobCategoryBean.setTbejcEeoJobCategoryId("EEC" + tbEeoJobCategoryBean.getTbejcId());

				TbEeoJobCategoryManager.getInstance().update(tbEeoJobCategoryBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbEeoJobCategoryBean = TbEeoJobCategoryManager.getInstance().toBean(tbEeoJobCategoryBeanModel, tbEeoJobCategoryBean);

				TbEeoJobCategoryManager.getInstance().save(tbEeoJobCategoryBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			tbEeoJobCategoryBeanModel = TbEeoJobCategoryManager.getInstance().toBeanModel(tbEeoJobCategoryBean);
			returnValue.set("model", tbEeoJobCategoryBeanModel);

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

	public TbEeoJobCategoryBeanModel getEeoJobCategory(int tbejcId) {
		TbEeoJobCategoryBeanModel returnValue = new TbEeoJobCategoryBeanModel();

		try {
			TbEeoJobCategoryBean TbEeoJobCategoryBean = TbEeoJobCategoryManager.getInstance().loadByPrimaryKey(tbejcId);

			if (TbEeoJobCategoryBean != null) {
				returnValue = TbEeoJobCategoryManager.getInstance().toBeanModel(TbEeoJobCategoryBean);

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

	public ReturnBean deleteEeoJobCategory(Integer tbejcId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbEeoJobCategoryManager.getInstance().deleteByPrimaryKey(tbejcId);

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

	public ReturnBean deleteBulkEeoJobCategory(Integer tbejcIds[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : tbejcIds) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbEeoJobCategoryManager.getInstance().deleteByWhere("where tbejc_id in (" + strId + ")");

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

	public PagingLoadResult<TbEeoJobCategoryBeanModel> getEeoJobCategoryPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbEeoJobCategoryBeanModel> list = new ArrayList<TbEeoJobCategoryBeanModel>();

		TbEeoJobCategoryBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("EEO Job Cat. Id".equals(searchBy)) {
				strWhere = "where tbejc_eeo_job_category_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("EEO Job Cat. Title".equals(searchBy)) {
				strWhere = "where tbejc_title like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbEeoJobCategoryManager.getInstance().countAll();
			} else {
				size = TbEeoJobCategoryManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbEeoJobCategoryManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbEeoJobCategoryBeanModel data = TbEeoJobCategoryManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbEeoJobCategoryBeanModel data = new TbEeoJobCategoryBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbEeoJobCategoryBeanModel>(list, config.getOffset(), size);
	}
}
