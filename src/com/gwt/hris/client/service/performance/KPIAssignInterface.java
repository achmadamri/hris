package com.gwt.hris.client.service.performance;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbKpiAssignBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiAssignBeanModel;

@RemoteServiceRelativePath("kpiassign")
public interface KPIAssignInterface extends RemoteService {
	ReturnBean submitKpiAssign(TbKpiAssignBeanModel TbKpiAssignBeanModel);

	TbKpiAssignBeanModel getKpiAssign(int tbesId);

	ReturnBean deleteKpiAssign(Integer tbesId);

	ReturnBean deleteBulkKpiAssign(Integer tbesIds[]);

	PagingLoadResult<ViewKpiAssignBeanModel> getKpiApprovalAssignPaging(final PagingLoadConfig config, String tbeName, String searchBy, String searchValue);

	PagingLoadResult<ViewKpiAssignBeanModel> getKpiApprovalScoringPaging(final PagingLoadConfig config, String tbeName, String searchBy, String searchValue);

	PagingLoadResult<ViewKpiAssignBeanModel> getKpiAssignPaging(final PagingLoadConfig config, String tbeName, String searchBy, String searchValue);

	TbEmployeeBeanModel getTbEmployeeBeanAssign();

	ReturnBean approveBulkKpiAssign(Integer ids[], Integer status);

	TbEmployeeBeanModel getTbEmployeeBeanApprovalAssign();

	TbEmployeeBeanModel getTbEmployeeBeanApprovalScoring();

	ReturnBean submitKpiScoring(TbKpiAssignBeanModel beanModel);

	ViewKpiAssignBeanModel getViewKpiAssign(int id);
	
	ReturnBean approveKpiAssign(Integer id, Integer status);
}
