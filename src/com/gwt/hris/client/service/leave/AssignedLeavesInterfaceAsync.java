package com.gwt.hris.client.service.leave;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLeavesBeanModel;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesBeanModel;

public interface AssignedLeavesInterfaceAsync {
	void submitAssignedLeaves(TbAssignedLeavesBeanModel tbAssignedLeavesBeanModel, AsyncCallback<ReturnBean> callback);

	void getAssignedLeaves(int tbaleId, AsyncCallback<TbAssignedLeavesBeanModel> callback);

	void deleteAssignedLeaves(Integer tbaleId, AsyncCallback<ReturnBean> callback);

	void getTbAssignedLeavesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeLeavesBeanModel>> callback);

	void getTbLeaveTypesAll(AsyncCallback<TbLeaveTypesBeanModel> callback);

	void getLeaveCount(Date dateStart, Date dateEnd, int intType, int intTbaleId, AsyncCallback<BaseTreeModel> callback);

	void deleteBulkAssignedLeaves(Integer tbaleIds[], AsyncCallback<ReturnBean> callback);
}
