package com.gwt.hris.server.service.system;

import java.sql.SQLException;

import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.system.ChangePasswordInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbLoginBean;
import com.gwt.hris.db.TbLoginManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ChangePasswordImpl extends MainRemoteServiceServlet implements ChangePasswordInterface {
	
	
	private static final long serialVersionUID = 943538312024436544L;

	public TbLoginBeanModel getLogin() {
		TbLoginBeanModel returnValue = new TbLoginBeanModel();
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");

		try {
			TbLoginBean TbLoginBean = TbLoginManager.getInstance().loadByPrimaryKey(tbLoginBeanModel.getTblId());

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

	public ReturnBean changePassword(TbLoginBeanModel tbLoginBeanModel_) {
		ReturnBean returnValue = new ReturnBean();
		
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbLoginBean tbLoginBean = null;

			if (tbLoginBeanModel.getTblId() != null) {
				tbLoginBean = TbLoginManager.getInstance().loadByPrimaryKey(tbLoginBeanModel.getTblId());
			}

			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 129, SystemUtil.ACCESS_UPDATE) == false) {
				throw new SystemException("No update access");
			}

			tbLoginBean = TbLoginManager.getInstance().toBean(tbLoginBeanModel, tbLoginBean);
			tbLoginBean.setTblPassword(tbLoginBeanModel_.getTblPassword());

//			TbLoginManager.getInstance().save(tbLoginBean);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Updated");

			tbLoginBeanModel = TbLoginManager.getInstance().toBeanModel(tbLoginBean);
			returnValue.set("model", tbLoginBeanModel);

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
