package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessBeanModel;

public interface JobTitleInterfaceAsync {
	void submitJobTitle(TbJobTitleBeanModel TbJobTitleBeanModel, AsyncCallback<ReturnBean> callback);

	void getJobTitle(int tbjtId, AsyncCallback<TbJobTitleBeanModel> callback);

	void deleteJobTitle(Integer tbjtId, AsyncCallback<ReturnBean> callback);

	void deleteBulkJobTitle(Integer tbjtIds[], AsyncCallback<ReturnBean> callback);

	void getJobTitlePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbJobTitleBeanModel>> callback);

	void getTbJobSpecificationsAll(AsyncCallback<TbJobSpecificationsBeanModel> callback);

	void getTbPaygradeAll(AsyncCallback<TbPaygradeBeanModel> callback);

	void getTbEmploymentStatusAll(AsyncCallback<TbEmploymentStatusBeanModel> callback);

	void getTbEmploymentStatusByTbjtId(int tbjtId, AsyncCallback<TbEmploymentStatusBeanModel> callback);

	void getTbEmploymentStatusByNotTbjtId(int tbjtId, AsyncCallback<TbEmploymentStatusBeanModel> callback);

	void getTbRenumerationAll(AsyncCallback<TbRenumerationBeanModel> callback);

	void getTbRenumerationByTbjtId(int tbjtId, AsyncCallback<TbRenumerationBeanModel> callback);

	void getTbRenumerationByNotTbjtId(int tbjtId, AsyncCallback<TbRenumerationBeanModel> callback);

	void getMenuAccessPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewMenuAccessBeanModel>> callback);
}
