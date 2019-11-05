package com.gwt.hris.client.service.pim;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEeoJobCategoryBeanModel;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.service.bean.TbJobBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbLocationBeanModel;
import com.gwt.hris.client.service.bean.TbOrganizationBeanModel;

public interface JobInterfaceAsync {
	void submitJob(TbJobBeanModel beanModel, AsyncCallback<ReturnBean> callback);

	void getJob(int id, AsyncCallback<TbJobBeanModel> callback);

	void getTbJobTitleAll(AsyncCallback<TbJobTitleBeanModel> callback);

	void getTbEmploymentStatus(int tbjtId, AsyncCallback<TbEmploymentStatusBeanModel> callback);

	void getTbEeoJobCategoryAll(AsyncCallback<TbEeoJobCategoryBeanModel> callback);

	void getTbOrganizationAll(AsyncCallback<TbOrganizationBeanModel> callback);

	void getTbLocationAll(AsyncCallback<TbLocationBeanModel> callback);

	void getTbOrganization(int tbpId, AsyncCallback<TbOrganizationBeanModel> callback);

	void getTbLocation(int tbpId, AsyncCallback<TbLocationBeanModel> callback);
}
