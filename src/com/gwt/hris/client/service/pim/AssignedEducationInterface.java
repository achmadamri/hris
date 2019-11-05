package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedEducationBeanModel;
import com.gwt.hris.client.service.bean.TbEducationBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeEducationBeanModel;

@RemoteServiceRelativePath("assignededucation")
public interface AssignedEducationInterface extends RemoteService {
	ReturnBean submitAssignedEducation(TbAssignedEducationBeanModel TbAssignedEducationBeanModel);

	PagingLoadResult<ViewEmployeeEducationBeanModel> getAssignedEducationPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteAssignedEducation(Integer id);

	TbAssignedEducationBeanModel getAssignedEducation(int id);

	TbEducationBeanModel getTbEducationAll();
}
