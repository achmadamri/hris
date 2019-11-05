package com.gwt.hris.client.service.recruitment;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbVacancyBeanModel;
import com.gwt.hris.client.service.bean.ViewVacancyBeanModel;

public interface VacanciesInterfaceAsync {
	void submitVacancy(TbVacancyBeanModel TbVacancyBeanModel, AsyncCallback<ReturnBean> callback);

	void getVacancy(int tbesId, AsyncCallback<TbVacancyBeanModel> callback);

	void deleteVacancy(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkVacancy(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getVacancyPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewVacancyBeanModel>> callback);

	void getTbEmployeeById(int id, AsyncCallback<TbEmployeeBeanModel> callback);

	void getTbJobTitleAll(AsyncCallback<TbJobTitleBeanModel> callback);
}
