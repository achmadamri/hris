package com.gwt.hris.client.service.admin.companyinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLocationBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.client.service.bean.ViewLocationNegaraBeanModel;

public interface LocationsInterfaceAsync {
	void submitLocations(TbLocationBeanModel tbLocationBeanModel, AsyncCallback<ReturnBean> callback);

	void getLocation(int tblId, AsyncCallback<TbLocationBeanModel> callback);

	void deleteLocations(Integer tblId, AsyncCallback<ReturnBean> callback);

	void deleteBulkLocations(Integer tblIds[], AsyncCallback<ReturnBean> callback);

	void getTbLocationPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewLocationNegaraBeanModel>> callback);

	void getTbPerusahaanAll(AsyncCallback<TbPerusahaanBeanModel> callback);
}
