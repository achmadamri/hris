package com.gwt.hris.server.service.admin.companyinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gwt.hris.client.service.admin.companyinfo.CompanyStructureInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbOrganizationBeanModel;
import com.gwt.hris.client.service.bean.ViewCompanyOrganizationBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbOrganizationBean;
import com.gwt.hris.db.TbOrganizationManager;
import com.gwt.hris.db.ViewCompanyOrganizationBean;
import com.gwt.hris.db.ViewCompanyOrganizationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class CompanyStructureImpl extends MainRemoteServiceServlet implements CompanyStructureInterface {
	
	private static final long serialVersionUID = -3168920845536464411L;

	public List<ViewCompanyOrganizationBeanModel> getViewCompanyOrganizationsList(ViewCompanyOrganizationBeanModel tbOrganizationBeanModel) {
		List<ViewCompanyOrganizationBeanModel> returnValues = new ArrayList<ViewCompanyOrganizationBeanModel>();

		try {
			ViewCompanyOrganizationBean tbOrganizationBeans[] = null;
			if (tbOrganizationBeanModel == null) {
				tbOrganizationBeans = ViewCompanyOrganizationManager.getInstance().loadByWhere("where tbo_parent_id = 0 order by tbo_nama");
			} else {
				tbOrganizationBeans = ViewCompanyOrganizationManager.getInstance().loadByWhere("where tbo_parent_id = " + tbOrganizationBeanModel.getTboId() + " order by tbo_nama");
			}

			ViewCompanyOrganizationBeanModel ViewCompanyOrganizationBeanModels[] = ViewCompanyOrganizationManager.getInstance().toBeanModels(tbOrganizationBeans);

			for (ViewCompanyOrganizationBeanModel ViewCompanyOrganizationBeanModel : ViewCompanyOrganizationBeanModels) {
				returnValues.add(ViewCompanyOrganizationBeanModel);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValues;
	}

	public TbOrganizationBeanModel getTbOrganizations(TbOrganizationBeanModel tbOrganizationBeanModel) {
		TbOrganizationBeanModel returnValue = new TbOrganizationBeanModel();

		try {
			TbOrganizationBean tbOrganizationBeans[] = TbOrganizationManager.getInstance().loadAll();
			TbOrganizationBeanModel tbNegaraBeanModels[] = TbOrganizationManager.getInstance().toBeanModels(tbOrganizationBeans);

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

	public TbOrganizationBeanModel getTbOrganization(int tblId) {
		TbOrganizationBeanModel returnValue = new TbOrganizationBeanModel();

		try {
			TbOrganizationBean TbOrganizationBean = TbOrganizationManager.getInstance().loadByPrimaryKey(tblId);

			if (TbOrganizationBean != null) {
				returnValue = TbOrganizationManager.getInstance().toBeanModel(TbOrganizationBean);

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

	public ReturnBean submitTbOrganization(TbOrganizationBeanModel TbOrganizationBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbOrganizationBean TbOrganizationBean = null;

			if (TbOrganizationBeanModel.getTboId() != null) {
				TbOrganizationBean = TbOrganizationManager.getInstance().loadByPrimaryKey(TbOrganizationBeanModel.getTboId());
			}

			if (TbOrganizationBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 5, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				TbOrganizationBean = TbOrganizationManager.getInstance().createTbOrganizationBean();

				TbOrganizationBean = TbOrganizationManager.getInstance().toBean(TbOrganizationBeanModel, TbOrganizationBean);

				TbOrganizationManager.getInstance().save(TbOrganizationBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 5, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				TbOrganizationBean = TbOrganizationManager.getInstance().toBean(TbOrganizationBeanModel, TbOrganizationBean);

				TbOrganizationManager.getInstance().save(TbOrganizationBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			TbOrganizationBeanModel = TbOrganizationManager.getInstance().toBeanModel(TbOrganizationBean);
			returnValue.set("model", TbOrganizationBeanModel);

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
	
	public ReturnBean deleteTbOrganization(Integer tblId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 5, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();
			
			TbOrganizationBean tbOrganizationBeans[] = TbOrganizationManager.getInstance().loadByWhere("where tbo_parent_id = " + tblId);
			if (tbOrganizationBeans.length > 0) {
				for (TbOrganizationBean tbOrganizationBean : tbOrganizationBeans) {
					deleteTbOrganization(tbOrganizationBean.getTboId());					
				}
			}

			TbOrganizationManager.getInstance().deleteByPrimaryKey(tblId);

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
}
