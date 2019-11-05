package com.gwt.hris.client.service.pim;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbContactDetailsBeanModel;

@RemoteServiceRelativePath("contactdetails")
public interface ContactDetailsInterface extends RemoteService {
	ReturnBean submitContactDetails(TbContactDetailsBeanModel TbContactDetailsBeanModel);

	TbContactDetailsBeanModel getContactDetails(int id);
}
