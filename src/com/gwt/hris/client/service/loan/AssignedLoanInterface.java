package com.gwt.hris.client.service.loan;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLoanBeanModel;
import com.gwt.hris.client.service.bean.ViewAssignedLoanBeanModel;

@RemoteServiceRelativePath("assignedloan")
public interface AssignedLoanInterface extends RemoteService {
	ReturnBean submitAssignedLoan(TbAssignedLoanBeanModel TbAssignedLoanBeanModel);

	ViewAssignedLoanBeanModel getAssignedLoan(int tbesId);

	ReturnBean deleteAssignedLoan(Integer tbesId);

	ReturnBean deleteBulkAssignedLoan(Integer tbesIds[]);

	PagingLoadResult<ViewAssignedLoanBeanModel> getAssignedLoanPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbAssignedLoanBeanModel getTbAssignedLoanAll();

	PagingLoadResult<ViewAssignedLoanBeanModel> getApprovalLoanPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean approvalAssignedLoan(int tbaloId, int tbaleStatus);
	
	PagingLoadResult<ViewAssignedLoanBeanModel> getAssignedLoanPagingEss(final PagingLoadConfig config, String searchBy, String searchValue);
}
