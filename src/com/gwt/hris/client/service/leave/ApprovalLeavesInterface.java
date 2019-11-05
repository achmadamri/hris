package com.gwt.hris.client.service.leave;

import java.util.Date;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLeavesBeanModel;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLeavesBeanModel;

@RemoteServiceRelativePath("approvalleaves")
public interface ApprovalLeavesInterface extends RemoteService {
	TbAssignedLeavesBeanModel getAssignedLeaves(int tblId);

	PagingLoadResult<ViewEmployeeLeavesBeanModel> getTbAssignedLeavesPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbLeaveTypesBeanModel getTbLeaveTypesAll();

	BaseTreeModel getLeaveCount(int tbeId, Date dateStart, Date dateEnd, int intType);

	public ReturnBean approvalAssignedLeaves(int tbaleId, int tbaleStatus);
}
