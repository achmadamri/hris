package com.gwt.hris.client.service.admin.skills;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbSkillsBeanModel;

public interface SkillsInterfaceAsync {
	void submitSkills(TbSkillsBeanModel TbSkillsBeanModel, AsyncCallback<ReturnBean> callback);

	void getSkills(int tbesId, AsyncCallback<TbSkillsBeanModel> callback);

	void deleteSkills(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkSkills(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getSkillsPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbSkillsBeanModel>> callback);
}
