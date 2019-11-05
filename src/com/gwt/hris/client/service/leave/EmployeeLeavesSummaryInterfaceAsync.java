package com.gwt.hris.client.service.leave;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesSummaryBeanModel;

public interface EmployeeLeavesSummaryInterfaceAsync {
	void getTbAssignedLeavesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>> callback);

	void getTbAssignedLeavesPagingEss(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeLeavesSummaryBeanModel>> callback);
}
