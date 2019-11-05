package com.gwt.hris.client.service.leave;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;

public interface LeaveTypesInterfaceAsync {
	void submitLeaveTypes(TbLeaveTypesBeanModel TbLeaveTypesBeanModel, AsyncCallback<ReturnBean> callback);

	void getLeaveTypes(int tbesId, AsyncCallback<TbLeaveTypesBeanModel> callback);

	void deleteLeaveTypes(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkLeaveTypes(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getLeaveTypesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbLeaveTypesBeanModel>> callback);

	void getTbLeaveTypesAll(AsyncCallback<TbLeaveTypesBeanModel> callback);
}
