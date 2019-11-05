package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;

@RemoteServiceRelativePath("employmentstatus")
public interface EmploymentStatusInterface extends RemoteService {
	ReturnBean submitEmploymentStatus(TbEmploymentStatusBeanModel TbEmploymentStatusBeanModel);

	TbEmploymentStatusBeanModel getEmploymentStatus(int tbesId);

	ReturnBean deleteEmploymentStatus(Integer tbesId);

	ReturnBean deleteBulkEmploymentStatus(Integer tbesIds[]);

	PagingLoadResult<TbEmploymentStatusBeanModel> getEmploymentStatusPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
