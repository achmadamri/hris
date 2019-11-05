package com.gwt.hris.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;

@RemoteServiceRelativePath("main")
public interface MainInterface extends RemoteService {
	TbLoginBeanModel doLogin(String strUserName, String strPassword);

	TbLoginBeanModel isLogin();

	void doLogout();

	Integer getTbeId();
	
	ReturnBean checkLicense();
	
	ReturnBean submitLicense(String strLicense);
	
	String getPopupWindow();
}
