package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedSkillsBeanModel;
import com.gwt.hris.client.service.bean.TbSkillsBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeSkillsBeanModel;

public interface AssignedSkillsInterfaceAsync {
	void submitAssignedSkills(TbAssignedSkillsBeanModel TbAssignedSkillsBeanModel, AsyncCallback<ReturnBean> callback);

	void getAssignedSkillsPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewEmployeeSkillsBeanModel>> callback);

	void deleteAssignedSkills(Integer id, AsyncCallback<ReturnBean> callback);

	void getAssignedSkills(int id, AsyncCallback<TbAssignedSkillsBeanModel> callback);

	void getTbSkillsAll(AsyncCallback<TbSkillsBeanModel> callback);
}
