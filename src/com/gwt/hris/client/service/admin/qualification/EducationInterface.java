package com.gwt.hris.client.service.admin.qualification;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEducationBeanModel;

@RemoteServiceRelativePath("education")
public interface EducationInterface extends RemoteService {
	ReturnBean submitEducation(TbEducationBeanModel TbEducationBeanModel);

	TbEducationBeanModel getEducation(int tbesId);

	ReturnBean deleteEducation(Integer tbesId);

	ReturnBean deleteBulkEducation(Integer tbesIds[]);

	PagingLoadResult<TbEducationBeanModel> getEducationPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
