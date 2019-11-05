package com.gwt.hris.client.service.system;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;

@RemoteServiceRelativePath("changepassword")
public interface ChangePasswordInterface extends RemoteService {
	TbLoginBeanModel getLogin();
	
	ReturnBean changePassword(TbLoginBeanModel tbLoginBeanModel);
}
