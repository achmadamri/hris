package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEeoJobCategoryBeanModel;

public interface EeoJobCategoryInterfaceAsync {
	void submitEeoJobCategory(TbEeoJobCategoryBeanModel TbEeoJobCategoryBeanModel, AsyncCallback<ReturnBean> callback);

	void getEeoJobCategory(int tbesId, AsyncCallback<TbEeoJobCategoryBeanModel> callback);

	void deleteEeoJobCategory(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkEeoJobCategory(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getEeoJobCategoryPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbEeoJobCategoryBeanModel>> callback);
}
