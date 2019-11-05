package com.gwt.hris.client.service.recruitment;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbApplicantsBeanModel;
import com.gwt.hris.client.service.bean.TbVacancyBeanModel;
import com.gwt.hris.client.service.bean.ViewApplicantsBeanModel;

@RemoteServiceRelativePath("applicants")
public interface ApplicantsInterface extends RemoteService {
	ReturnBean submitApplicants(TbApplicantsBeanModel TbApplicantsBeanModel);

	TbApplicantsBeanModel getApplicants(int tbesId);

	ReturnBean deleteApplicants(Integer tbesId);

	ReturnBean deleteBulkApplicants(Integer tbesIds[]);

	PagingLoadResult<ViewApplicantsBeanModel> getApplicantsPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	TbVacancyBeanModel getTbVacancyAll();
}
