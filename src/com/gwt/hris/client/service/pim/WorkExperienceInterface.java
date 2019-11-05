package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbWorkExperienceBeanModel;

@RemoteServiceRelativePath("workexperience")
public interface WorkExperienceInterface extends RemoteService {
	ReturnBean submitWorkExperience(TbWorkExperienceBeanModel TbWorkExperienceBeanModel);

	PagingLoadResult<TbWorkExperienceBeanModel> getWorkExperiencePaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteWorkExperience(Integer id);

	TbWorkExperienceBeanModel getWorkExperience(int id);
}
