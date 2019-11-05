package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbJobTitleLeaveBeanModel;
import com.gwt.hris.client.service.bean.ViewJobTitleLeaveBeanModel;

@RemoteServiceRelativePath("jobtitleleaverule")
public interface JobTitleLeaveRuleInterface extends RemoteService {
	ReturnBean submitAssignedEducation(TbJobTitleLeaveBeanModel TbJobTitleLeaveBeanModel);

	PagingLoadResult<ViewJobTitleLeaveBeanModel> getJobTitleLeavePaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteAssignedEducation(Integer id);

	TbJobTitleLeaveBeanModel getJobTitleLeave(int id);
}
