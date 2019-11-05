package com.gwt.hris.server.service.pim;

import java.sql.SQLException;

import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEeoJobCategoryBeanModel;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.service.bean.TbJobBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbLocationBeanModel;
import com.gwt.hris.client.service.bean.TbOrganizationBeanModel;
import com.gwt.hris.client.service.pim.JobInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEeoJobCategoryBean;
import com.gwt.hris.db.TbEeoJobCategoryManager;
import com.gwt.hris.db.TbEmploymentStatusBean;
import com.gwt.hris.db.TbEmploymentStatusManager;
import com.gwt.hris.db.TbJobBean;
import com.gwt.hris.db.TbJobManager;
import com.gwt.hris.db.TbJobTitleBean;
import com.gwt.hris.db.TbJobTitleManager;
import com.gwt.hris.db.TbLocationBean;
import com.gwt.hris.db.TbLocationManager;
import com.gwt.hris.db.TbOrganizationBean;
import com.gwt.hris.db.TbOrganizationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class JobImpl extends MainRemoteServiceServlet implements JobInterface {
	
	private static final long serialVersionUID = 3428980588188472760L;

	public ReturnBean submitJob(TbJobBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbJobBean bean = null;

			if (beanModel.getTbeId() != null) {
				bean = TbJobManager.getInstance().loadByPrimaryKey(beanModel.getTbeId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbJobManager.getInstance().createTbJobBean();

				bean = TbJobManager.getInstance().toBean(beanModel, bean);

				TbJobManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbJobManager.getInstance().toBean(beanModel, bean);

				TbJobManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbJobManager.getInstance().toBeanModel(bean);
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

	public TbJobBeanModel getJob(int id) {
		TbJobBeanModel returnValue = new TbJobBeanModel();

		try {
			TbJobBean bean = TbJobManager.getInstance().loadByPrimaryKey(id);

			if (bean != null) {
				returnValue = TbJobManager.getInstance().toBeanModel(bean);

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

	public TbJobTitleBeanModel getTbJobTitleAll() {
		TbJobTitleBeanModel returnValue = new TbJobTitleBeanModel();

		try {
			TbJobTitleBean tbCurrencyBeans[] = TbJobTitleManager.getInstance().loadAll();
			TbJobTitleBeanModel tbCurrencyBeanModels[] = TbJobTitleManager.getInstance().toBeanModels(tbCurrencyBeans);

			returnValue.setModels(tbCurrencyBeanModels);
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

	public TbEmploymentStatusBeanModel getTbEmploymentStatus(int tbjtId) {
		TbEmploymentStatusBeanModel returnValue = new TbEmploymentStatusBeanModel();

		try {
			TbEmploymentStatusBean tbCurrencyBeans[] = TbEmploymentStatusManager.getInstance().loadByWhere("where tbes_id in (select tbes_id from tb_job_employment_status where tbjt_id = " + tbjtId + ")");
			TbEmploymentStatusBeanModel tbCurrencyBeanModels[] = TbEmploymentStatusManager.getInstance().toBeanModels(tbCurrencyBeans);

			returnValue.setModels(tbCurrencyBeanModels);
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

	public TbOrganizationBeanModel getTbOrganization(int tbpId) {
		TbOrganizationBeanModel returnValue = new TbOrganizationBeanModel();

		try {
			TbOrganizationBean tbCurrencyBeans[] = TbOrganizationManager.getInstance().loadByWhere("where tbp_id = " + tbpId);
			TbOrganizationBeanModel tbCurrencyBeanModels[] = TbOrganizationManager.getInstance().toBeanModels(tbCurrencyBeans);

			returnValue.setModels(tbCurrencyBeanModels);
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

	public TbEeoJobCategoryBeanModel getTbEeoJobCategoryAll() {
		TbEeoJobCategoryBeanModel returnValue = new TbEeoJobCategoryBeanModel();

		try {
			TbEeoJobCategoryBean tbCurrencyBeans[] = TbEeoJobCategoryManager.getInstance().loadAll();
			TbEeoJobCategoryBeanModel tbCurrencyBeanModels[] = TbEeoJobCategoryManager.getInstance().toBeanModels(tbCurrencyBeans);

			returnValue.setModels(tbCurrencyBeanModels);
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

	public TbOrganizationBeanModel getTbOrganizationAll() {
		TbOrganizationBeanModel returnValue = new TbOrganizationBeanModel();

		try {
			TbOrganizationBean tbCurrencyBeans[] = TbOrganizationManager.getInstance().loadAll();
			TbOrganizationBeanModel tbCurrencyBeanModels[] = TbOrganizationManager.getInstance().toBeanModels(tbCurrencyBeans);

			returnValue.setModels(tbCurrencyBeanModels);
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

	public TbLocationBeanModel getTbLocationAll() {
		TbLocationBeanModel returnValue = new TbLocationBeanModel();

		try {
			TbLocationBean tbCurrencyBeans[] = TbLocationManager.getInstance().loadAll();
			TbLocationBeanModel tbLocationBeanModels[] = TbLocationManager.getInstance().toBeanModels(tbCurrencyBeans);

			returnValue.setModels(tbLocationBeanModels);
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

	public TbLocationBeanModel getTbLocation(int tbpId) {
		TbLocationBeanModel returnValue = new TbLocationBeanModel();

		try {
			TbLocationBean tbLocationBeans[] = TbLocationManager.getInstance().loadByWhere("where tbp_id = " + tbpId);
			TbLocationBeanModel tbLocationBeanModels[] = TbLocationManager.getInstance().toBeanModels(tbLocationBeans);

			returnValue.setModels(tbLocationBeanModels);
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
