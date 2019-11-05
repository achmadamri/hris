package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;

@RemoteServiceRelativePath("employeelist")
public interface EmployeeListInterface extends RemoteService {
	ReturnBean submitEmployee(TbEmployeeBeanModel TbEmployeeBeanModel);

	TbEmployeeBeanModel getEmployee(int tbesId);

	ReturnBean disableEmployee(Integer tbesId);

	ReturnBean disableBulkEmployee(Integer tbesIds[]);

	PagingLoadResult<ViewEmployeeInformationBeanModel> getEmployeePaging(final PagingLoadConfig config, String searchBy, String searchValue);
	
	TbEmployeeBeanModel getTbEmployeeAll();
}
