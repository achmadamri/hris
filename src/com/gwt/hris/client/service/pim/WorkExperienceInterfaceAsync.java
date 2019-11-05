package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbWorkExperienceBeanModel;

public interface WorkExperienceInterfaceAsync {
	void submitWorkExperience(TbWorkExperienceBeanModel TbWorkExperienceBeanModel, AsyncCallback<ReturnBean> callback);

	void getWorkExperiencePaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbWorkExperienceBeanModel>> callback);

	void deleteWorkExperience(Integer id, AsyncCallback<ReturnBean> callback);

	void getWorkExperience(int id, AsyncCallback<TbWorkExperienceBeanModel> callback);
}
