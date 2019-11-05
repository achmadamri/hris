package com.gwt.hris.server.service.pim;

import java.sql.SQLException;

import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbContactDetailsBeanModel;
import com.gwt.hris.client.service.pim.ContactDetailsInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbContactDetailsBean;
import com.gwt.hris.db.TbContactDetailsManager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ContactDetailsImpl extends MainRemoteServiceServlet implements ContactDetailsInterface {
	
	private static final long serialVersionUID = 3428980588188472760L;

	public ReturnBean submitContactDetails(TbContactDetailsBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbContactDetailsBean bean = null;

			if (beanModel.getTbeId() != null) {
				bean = TbContactDetailsManager.getInstance().loadByPrimaryKey(beanModel.getTbeId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbContactDetailsManager.getInstance().createTbContactDetailsBean();

				bean = TbContactDetailsManager.getInstance().toBean(beanModel, bean);

				TbContactDetailsManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbContactDetailsManager.getInstance().toBean(beanModel, bean);

				TbContactDetailsManager.getInstance().save(bean);
				
				TbEmployeeBean tbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(bean.getTbeId());
				tbEmployeeBean.setTbePhone(bean.getTbcdHomePhone());
				tbEmployeeBean.setTbeMobile(bean.getTbcdMobilePhone());
				TbEmployeeManager.getInstance().save(tbEmployeeBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbContactDetailsManager.getInstance().toBeanModel(bean);
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

	public TbContactDetailsBeanModel getContactDetails(int id) {
		TbContactDetailsBeanModel returnValue = new TbContactDetailsBeanModel();

		try {
			TbContactDetailsBean TbContactDetailsBean = TbContactDetailsManager.getInstance().loadByPrimaryKey(id);

			if (TbContactDetailsBean != null) {
				returnValue = TbContactDetailsManager.getInstance().toBeanModel(TbContactDetailsBean);

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
