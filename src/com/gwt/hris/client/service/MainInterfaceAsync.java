package com.gwt.hris.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;

public interface MainInterfaceAsync {
	void doLogin(String strUserName, String strPassword, AsyncCallback<TbLoginBeanModel> callback);

	void isLogin(AsyncCallback<TbLoginBeanModel> callback);

	void doLogout(AsyncCallback<Void> callback);

	void getTbeId(AsyncCallback<Integer> callback);

	void checkLicense(AsyncCallback<ReturnBean> callback);

	void submitLicense(String strLicense, AsyncCallback<ReturnBean> callback);

	void getPopupWindow(AsyncCallback<String> callback);
}
