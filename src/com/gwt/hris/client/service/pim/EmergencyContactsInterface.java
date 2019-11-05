package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmergencyContactBeanModel;

@RemoteServiceRelativePath("emergencycontacts")
public interface EmergencyContactsInterface extends RemoteService {
	ReturnBean submitEmergencyContact(TbEmergencyContactBeanModel TbEmergencyContactBeanModel);

	PagingLoadResult<TbEmergencyContactBeanModel> getEmergencyContactPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteEmergencyContact(Integer id);

	TbEmergencyContactBeanModel getEmergencyContact(int id);
}
