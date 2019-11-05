package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.service.bean.TbProjectEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewProjectCustomerBeanModel;

public interface ProjectInterfaceAsync {
	void submitProject(TbProjectBeanModel TbProjectBeanModel, AsyncCallback<ReturnBean> callback);

	void getProject(int tbesId, AsyncCallback<TbProjectBeanModel> callback);

	void deleteProject(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkProject(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getProjectPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewProjectCustomerBeanModel>> callback);

	void deleteProjectEmployee(Integer tbeId, Integer tbpId, AsyncCallback<ReturnBean> callback);

	void submitProjectEmployee(TbProjectEmployeeBeanModel beanModel, AsyncCallback<ReturnBean> callback);

	void getTbCustomerAll(AsyncCallback<TbCustomerBeanModel> callback);
}
