package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessBeanModel;

@RemoteServiceRelativePath("jobtitle")
public interface JobTitleInterface extends RemoteService {
	ReturnBean submitJobTitle(TbJobTitleBeanModel TbJobTitleBeanModel);

	TbJobTitleBeanModel getJobTitle(int tbjtId);

	ReturnBean deleteJobTitle(Integer tbjtId);

	ReturnBean deleteBulkJobTitle(Integer tbjtIds[]);

	PagingLoadResult<TbJobTitleBeanModel> getJobTitlePaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbJobSpecificationsBeanModel getTbJobSpecificationsAll();

	TbPaygradeBeanModel getTbPaygradeAll();

	TbEmploymentStatusBeanModel getTbEmploymentStatusAll();

	TbEmploymentStatusBeanModel getTbEmploymentStatusByTbjtId(int tbjtId);

	TbEmploymentStatusBeanModel getTbEmploymentStatusByNotTbjtId(int tbjtId);

	TbRenumerationBeanModel getTbRenumerationAll();

	TbRenumerationBeanModel getTbRenumerationByTbjtId(int tbjtId);

	TbRenumerationBeanModel getTbRenumerationByNotTbjtId(int tbjtId);
	
	PagingLoadResult<ViewMenuAccessBeanModel> getMenuAccessPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
