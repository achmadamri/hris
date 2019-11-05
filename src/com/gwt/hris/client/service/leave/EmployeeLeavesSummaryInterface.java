package com.gwt.hris.client.service.leave;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesSummaryBeanModel;

@RemoteServiceRelativePath("employeeleavessummary")
public interface EmployeeLeavesSummaryInterface extends RemoteService {
	PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel> getTbAssignedLeavesPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel> getTbAssignedLeavesPagingEss(final PagingLoadConfig config, String searchBy, String searchValue);
}
