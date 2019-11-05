package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbJobTitleLeaveBeanModel;
import com.gwt.hris.client.service.bean.ViewJobTitleLeaveBeanModel;

public interface JobTitleLeaveRuleInterfaceAsync {
	void submitAssignedEducation(TbJobTitleLeaveBeanModel TbJobTitleLeaveBeanModel, AsyncCallback<ReturnBean> callback);

	void getJobTitleLeavePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewJobTitleLeaveBeanModel>> callback);

	void deleteAssignedEducation(Integer id, AsyncCallback<ReturnBean> callback);

	void getJobTitleLeave(int id, AsyncCallback<TbJobTitleLeaveBeanModel> callback);
}
