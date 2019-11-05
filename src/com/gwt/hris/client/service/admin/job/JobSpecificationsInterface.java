package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;

@RemoteServiceRelativePath("jobspecifications")
public interface JobSpecificationsInterface extends RemoteService {
	ReturnBean submitJobSpecifications(TbJobSpecificationsBeanModel TbJobSpecificationsBeanModel);

	TbJobSpecificationsBeanModel getJobSpecifications(int tblId);

	ReturnBean deleteJobSpecifications(Integer tblId);

	ReturnBean deleteBulkJobSpecifications(Integer tblIds[]);

	PagingLoadResult<TbJobSpecificationsBeanModel> getJobSpecifications(final PagingLoadConfig config, String searchBy, String searchValue);

	TbJobSpecificationsBeanModel getJobSpecificationsByJobTitle(int tbjtId);
}
