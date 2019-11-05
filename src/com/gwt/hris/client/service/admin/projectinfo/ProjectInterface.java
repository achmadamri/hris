package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.service.bean.TbProjectEmployeeBeanModel;
import com.gwt.hris.client.service.bean.ViewProjectCustomerBeanModel;

@RemoteServiceRelativePath("project")
public interface ProjectInterface extends RemoteService {
	ReturnBean submitProject(TbProjectBeanModel TbProjectBeanModel);

	TbProjectBeanModel getProject(int tbesId);

	ReturnBean deleteProject(Integer tbesId);

	ReturnBean deleteBulkProject(Integer tbesIds[]);

	PagingLoadResult<ViewProjectCustomerBeanModel> getProjectPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteProjectEmployee(Integer tbeId, Integer tbpId);

	ReturnBean submitProjectEmployee(TbProjectEmployeeBeanModel beanModel);

	TbCustomerBeanModel getTbCustomerAll();
}
