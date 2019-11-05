package com.gwt.hris.client.service.pim;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEeoJobCategoryBeanModel;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.service.bean.TbJobBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbLocationBeanModel;
import com.gwt.hris.client.service.bean.TbOrganizationBeanModel;

@RemoteServiceRelativePath("job")
public interface JobInterface extends RemoteService {
	ReturnBean submitJob(TbJobBeanModel beanModel);

	TbJobBeanModel getJob(int id);

	TbJobTitleBeanModel getTbJobTitleAll();

	TbEmploymentStatusBeanModel getTbEmploymentStatus(int tbjtId);

	TbEeoJobCategoryBeanModel getTbEeoJobCategoryAll();

	TbOrganizationBeanModel getTbOrganizationAll();
	
	TbLocationBeanModel getTbLocationAll();
	
	TbOrganizationBeanModel getTbOrganization(int tbpId);
	
	TbLocationBeanModel getTbLocation(int tbpId);
}
