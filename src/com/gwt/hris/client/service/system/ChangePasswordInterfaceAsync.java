package com.gwt.hris.client.service.system;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;

public interface ChangePasswordInterfaceAsync {
	void changePassword(TbLoginBeanModel tbLoginBeanModel, AsyncCallback<ReturnBean> callback);

	void getLogin(AsyncCallback<TbLoginBeanModel> callback);
}
