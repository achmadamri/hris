package com.gwt.hris.client.service.admin.job;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEeoJobCategoryBeanModel;

@RemoteServiceRelativePath("eeojobcategory")
public interface EeoJobCategoryInterface extends RemoteService {
	ReturnBean submitEeoJobCategory(TbEeoJobCategoryBeanModel TbEeoJobCategoryBeanModel);

	TbEeoJobCategoryBeanModel getEeoJobCategory(int tbesId);

	ReturnBean deleteEeoJobCategory(Integer tbesId);

	ReturnBean deleteBulkEeoJobCategory(Integer tbesIds[]);

	PagingLoadResult<TbEeoJobCategoryBeanModel> getEeoJobCategoryPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
