package com.gwt.hris.client.service.admin.skills;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbSkillsBeanModel;

@RemoteServiceRelativePath("skills")
public interface SkillsInterface extends RemoteService {
	ReturnBean submitSkills(TbSkillsBeanModel TbSkillsBeanModel);

	TbSkillsBeanModel getSkills(int tbesId);

	ReturnBean deleteSkills(Integer tbesId);

	ReturnBean deleteBulkSkills(Integer tbesIds[]);

	PagingLoadResult<TbSkillsBeanModel> getSkillsPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
