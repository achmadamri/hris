package com.gwt.hris.client.service.admin.qualification;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEducationBeanModel;

public interface EducationInterfaceAsync {
	void submitEducation(TbEducationBeanModel TbEducationBeanModel, AsyncCallback<ReturnBean> callback);

	void getEducation(int tbesId, AsyncCallback<TbEducationBeanModel> callback);

	void deleteEducation(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkEducation(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getEducationPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbEducationBeanModel>> callback);
}
