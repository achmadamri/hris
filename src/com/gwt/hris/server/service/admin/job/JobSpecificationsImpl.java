package com.gwt.hris.server.service.admin.job;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.job.JobSpecificationsInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbJobSpecificationsBean;
import com.gwt.hris.db.TbJobSpecificationsManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class JobSpecificationsImpl extends MainRemoteServiceServlet implements JobSpecificationsInterface {
	
	private static final long serialVersionUID = -9130232253110656682L;

	public ReturnBean submitJobSpecifications(TbJobSpecificationsBeanModel TbJobSpecificationsBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbJobSpecificationsBean bean = null;

			if (TbJobSpecificationsBeanModel.getTbjsId() != null) {
				bean = TbJobSpecificationsManager.getInstance().loadByPrimaryKey(TbJobSpecificationsBeanModel.getTbjsId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 9, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbJobSpecificationsManager.getInstance().createTbJobSpecificationsBean();

				bean = TbJobSpecificationsManager.getInstance().toBean(TbJobSpecificationsBeanModel, bean);

				TbJobSpecificationsManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 9, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbJobSpecificationsManager.getInstance().toBean(TbJobSpecificationsBeanModel, bean);

				TbJobSpecificationsManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			TbJobSpecificationsBeanModel = TbJobSpecificationsManager.getInstance().toBeanModel(bean);
			returnValue.set("model", TbJobSpecificationsBeanModel);

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

	public TbJobSpecificationsBeanModel getJobSpecifications(int tbjsId) {
		TbJobSpecificationsBeanModel returnValue = new TbJobSpecificationsBeanModel();

		try {
			TbJobSpecificationsBean bean = TbJobSpecificationsManager.getInstance().loadByPrimaryKey(tbjsId);

			if (bean != null) {
				returnValue = TbJobSpecificationsManager.getInstance().toBeanModel(bean);

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

	public ReturnBean deleteJobSpecifications(Integer tbjsId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 9, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbJobSpecificationsManager.getInstance().deleteByPrimaryKey(tbjsId);

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

	public ReturnBean deleteBulkJobSpecifications(Integer tbjsIds[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 9, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : tbjsIds) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbJobSpecificationsManager.getInstance().deleteByWhere("where tbjs_id in (" + strId + ")");

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

	public PagingLoadResult<TbJobSpecificationsBeanModel> getJobSpecifications(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbJobSpecificationsBeanModel> list = new ArrayList<TbJobSpecificationsBeanModel>();

		TbJobSpecificationsBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 9, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Job Spec Name".equals(searchBy)) {
				strWhere = "where tbjs_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Description".equals(searchBy)) {
				strWhere = "where tbjs_description like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbJobSpecificationsManager.getInstance().countAll();
			} else {
				size = TbJobSpecificationsManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbJobSpecificationsManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbJobSpecificationsBeanModel data = TbJobSpecificationsManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbJobSpecificationsBeanModel data = new TbJobSpecificationsBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbJobSpecificationsBeanModel>(list, config.getOffset(), size);
	}

	public TbJobSpecificationsBeanModel getJobSpecificationsByJobTitle(int tbjtId) {
		TbJobSpecificationsBeanModel returnValue = new TbJobSpecificationsBeanModel();

		try {
			TbJobSpecificationsBean beans[] = TbJobSpecificationsManager.getInstance().loadByWhere("where tbjs_id in (select tbjs_id from tb_job_title where tbjt_id =" + tbjtId + ")");
			if (beans.length > 0) {
				returnValue = TbJobSpecificationsManager.getInstance().toBeanModel(beans[0]);

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
