package com.gwt.hris.client.service.recruitment;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbApplicantsBeanModel;
import com.gwt.hris.client.service.bean.TbVacancyBeanModel;
import com.gwt.hris.client.service.bean.ViewApplicantsBeanModel;

public interface ApplicantsInterfaceAsync {
	void submitApplicants(TbApplicantsBeanModel TbApplicantsBeanModel, AsyncCallback<ReturnBean> callback);

	void getApplicants(int tbesId, AsyncCallback<TbApplicantsBeanModel> callback);

	void deleteApplicants(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkApplicants(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getApplicantsPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewApplicantsBeanModel>> callback);

	void getTbVacancyAll(AsyncCallback<TbVacancyBeanModel> callback);
}
