package com.gwt.hris.client.service.performance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbKpiAssignBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiAssignBeanModel;

public interface KPIAssignInterfaceAsync {
	void submitKpiAssign(TbKpiAssignBeanModel TbKpiAssignBeanModel, AsyncCallback<ReturnBean> callback);

	void getKpiAssign(int tbesId, AsyncCallback<TbKpiAssignBeanModel> callback);

	void deleteKpiAssign(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkKpiAssign(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getKpiApprovalAssignPaging(PagingLoadConfig config, String tbeName, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewKpiAssignBeanModel>> callback);

	void getKpiApprovalScoringPaging(PagingLoadConfig config, String tbeName, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewKpiAssignBeanModel>> callback);

	void getKpiAssignPaging(PagingLoadConfig config, String tbeName, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewKpiAssignBeanModel>> callback);

	void getTbEmployeeBeanAssign(AsyncCallback<TbEmployeeBeanModel> callback);

	void approveBulkKpiAssign(Integer ids[], Integer status, AsyncCallback<ReturnBean> callback);

	void getTbEmployeeBeanApprovalAssign(AsyncCallback<TbEmployeeBeanModel> callback);

	void getTbEmployeeBeanApprovalScoring(AsyncCallback<TbEmployeeBeanModel> callback);

	void submitKpiScoring(TbKpiAssignBeanModel beanModel, AsyncCallback<ReturnBean> callback);

	void getViewKpiAssign(int id, AsyncCallback<ViewKpiAssignBeanModel> callback);

	void approveKpiAssign(Integer id, Integer status, AsyncCallback<ReturnBean> callback);
}
