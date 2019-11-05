package com.gwt.hris.client.service.loan;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLoanBeanModel;
import com.gwt.hris.client.service.bean.ViewAssignedLoanBeanModel;

public interface AssignedLoanInterfaceAsync {
	void submitAssignedLoan(TbAssignedLoanBeanModel TbAssignedLoanBeanModel, AsyncCallback<ReturnBean> callback);

	void getAssignedLoan(int tbesId, AsyncCallback<ViewAssignedLoanBeanModel> callback);

	void deleteAssignedLoan(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkAssignedLoan(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getAssignedLoanPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewAssignedLoanBeanModel>> callback);

	void getTbAssignedLoanAll(AsyncCallback<TbAssignedLoanBeanModel> callback);

	void getApprovalLoanPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewAssignedLoanBeanModel>> callback);

	void approvalAssignedLoan(int tbaloId, int tbaleStatus, AsyncCallback<ReturnBean> callback);

	void getAssignedLoanPagingEss(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewAssignedLoanBeanModel>> callback);
}
