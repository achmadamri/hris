package com.gwt.hris.client.service.recruitment;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbVacancyBeanModel;
import com.gwt.hris.client.service.bean.ViewVacancyBeanModel;

@RemoteServiceRelativePath("vacancies")
public interface VacanciesInterface extends RemoteService {
	ReturnBean submitVacancy(TbVacancyBeanModel TbVacancyBeanModel);

	TbVacancyBeanModel getVacancy(int tbesId);

	ReturnBean deleteVacancy(Integer tbesId);

	ReturnBean deleteBulkVacancy(Integer tbesIds[]);

	PagingLoadResult<ViewVacancyBeanModel> getVacancyPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbEmployeeBeanModel getTbEmployeeById(int id);
	
	TbJobTitleBeanModel getTbJobTitleAll();
}
