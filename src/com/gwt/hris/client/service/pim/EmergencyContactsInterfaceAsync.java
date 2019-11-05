package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmergencyContactBeanModel;

public interface EmergencyContactsInterfaceAsync {
	void submitEmergencyContact(TbEmergencyContactBeanModel TbEmergencyContactBeanModel, AsyncCallback<ReturnBean> callback);

	void getEmergencyContactPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbEmergencyContactBeanModel>> callback);

	void deleteEmergencyContact(Integer id, AsyncCallback<ReturnBean> callback);

	void getEmergencyContact(int id, AsyncCallback<TbEmergencyContactBeanModel> callback);
}
