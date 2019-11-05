package com.gwt.hris.client.service.leave;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;

@RemoteServiceRelativePath("leavetypes")
public interface LeaveTypesInterface extends RemoteService {
	ReturnBean submitLeaveTypes(TbLeaveTypesBeanModel TbLeaveTypesBeanModel);

	TbLeaveTypesBeanModel getLeaveTypes(int tbesId);

	ReturnBean deleteLeaveTypes(Integer tbesId);

	ReturnBean deleteBulkLeaveTypes(Integer tbesIds[]);

	PagingLoadResult<TbLeaveTypesBeanModel> getLeaveTypesPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbLeaveTypesBeanModel getTbLeaveTypesAll();
}
