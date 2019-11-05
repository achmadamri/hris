package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedEducationBeanModel;
import com.gwt.hris.client.service.bean.TbEducationBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeEducationBeanModel;

public interface AssignedEducationInterfaceAsync {
	void submitAssignedEducation(TbAssignedEducationBeanModel TbAssignedEducationBeanModel, AsyncCallback<ReturnBean> callback);

	void getAssignedEducationPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeEducationBeanModel>> callback);

	void deleteAssignedEducation(Integer id, AsyncCallback<ReturnBean> callback);

	void getAssignedEducation(int id, AsyncCallback<TbAssignedEducationBeanModel> callback);

	void getTbEducationAll(AsyncCallback<TbEducationBeanModel> callback);
}
