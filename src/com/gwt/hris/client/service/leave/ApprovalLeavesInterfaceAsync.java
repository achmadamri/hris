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

public interface ApprovalLeavesInterfaceAsync {
	void getAssignedLeaves(int tblId, AsyncCallback<TbAssignedLeavesBeanModel> callback);

	void getTbAssignedLeavesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeLeavesBeanModel>> callback);

	void getTbLeaveTypesAll(AsyncCallback<TbLeaveTypesBeanModel> callback);

	void getLeaveCount(int tbeId, Date dateStart, Date dateEnd, int intType, AsyncCallback<BaseTreeModel> callback);

	void approvalAssignedLeaves(int tbaleId, int tbaleStatus, AsyncCallback<ReturnBean> callback);
}
