package com.gwt.hris.client.service.admin.companyinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;

public interface PerusahaanInterfaceAsync {
	void submitPerusahaan(TbPerusahaanBeanModel tbPerusahaanBeanModel, AsyncCallback<ReturnBean> callback);

	void getPerusahaan(int tblId, AsyncCallback<TbPerusahaanBeanModel> callback);

	void deletePerusahaan(Integer tblId, AsyncCallback<ReturnBean> callback);

	void deleteBulkPerusahaan(Integer tblIds[], AsyncCallback<ReturnBean> callback);

	void getTbPerusahaanPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbPerusahaanBeanModel>> callback);
}
