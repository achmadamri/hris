package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;

public interface JobSpecificationsInterfaceAsync {
	void submitJobSpecifications(TbJobSpecificationsBeanModel TbJobSpecificationsBeanModel, AsyncCallback<ReturnBean> callback);

	void getJobSpecifications(int tblId, AsyncCallback<TbJobSpecificationsBeanModel> callback);

	void deleteJobSpecifications(Integer tblId, AsyncCallback<ReturnBean> callback);

	void deleteBulkJobSpecifications(Integer tblIds[], AsyncCallback<ReturnBean> callback);

	void getJobSpecifications(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbJobSpecificationsBeanModel>> callback);

	void getJobSpecificationsByJobTitle(int tbjtId, AsyncCallback<TbJobSpecificationsBeanModel> callback);
}
