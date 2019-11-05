package com.gwt.hris.client.service.pim;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbDependentsBeanModel;

@RemoteServiceRelativePath("dependents")
public interface DependentsInterface extends RemoteService {
	ReturnBean submitDependents(TbDependentsBeanModel TbDependentsBeanModel);

	PagingLoadResult<TbDependentsBeanModel> getDependentsPaging(final PagingLoadConfig config, String searchBy, String searchValue);

	ReturnBean deleteDependents(Integer id);

	TbDependentsBeanModel getDependents(int id);
}
