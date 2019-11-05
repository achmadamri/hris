package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedSkillsBeanModel;
import com.gwt.hris.client.service.bean.TbSkillsBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeSkillsBeanModel;

@RemoteServiceRelativePath("assignedskills")
public interface AssignedSkillsInterface extends RemoteService {
	ReturnBean submitAssignedSkills(TbAssignedSkillsBeanModel TbAssignedSkillsBeanModel);

	PagingLoadResult<ViewEmployeeSkillsBeanModel> getAssignedSkillsPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteAssignedSkills(Integer id);

	TbAssignedSkillsBeanModel getAssignedSkills(int id);

	TbSkillsBeanModel getTbSkillsAll();
}
