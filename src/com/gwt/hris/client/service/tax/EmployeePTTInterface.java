package com.gwt.hris.client.service.tax;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeePttBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeePttBeanModel;

@RemoteServiceRelativePath("employeeptt")
public interface EmployeePTTInterface extends RemoteService {
	ReturnBean submitPtt(TbEmployeePttBeanModel TbEmployeePttBeanModel);

	TbEmployeePttBeanModel getPtt(int tbesId);

	ReturnBean deletePtt(Integer tbesId);

	ReturnBean deleteBulkPtt(Integer tbesIds[]);

	PagingLoadResult<ViewEmployeePttBeanModel> getPttPaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	ViewEmployeePttBeanModel getViewEmployeePttAll();
	
	PagingLoadResult<ViewEmployeePttBeanModel> getPttPagingESS(final PagingLoadConfig config, String searchBy, String searchValue);
}
