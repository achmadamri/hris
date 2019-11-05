package com.gwt.hris.client.service.pim;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbContactDetailsBeanModel;

public interface ContactDetailsInterfaceAsync {
	void submitContactDetails(TbContactDetailsBeanModel TbContactDetailsBeanModel, AsyncCallback<ReturnBean> callback);

	void getContactDetails(int id, AsyncCallback<TbContactDetailsBeanModel> callback);
}
