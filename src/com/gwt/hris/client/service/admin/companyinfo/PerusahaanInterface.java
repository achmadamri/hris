package com.gwt.hris.client.service.admin.companyinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;

@RemoteServiceRelativePath("perusahaan")
public interface PerusahaanInterface extends RemoteService {
	ReturnBean submitPerusahaan(TbPerusahaanBeanModel tbPerusahaanBeanModel);

	TbPerusahaanBeanModel getPerusahaan(int tblId);

	ReturnBean deletePerusahaan(Integer tblId);

	ReturnBean deleteBulkPerusahaan(Integer tblIds[]);

	PagingLoadResult<TbPerusahaanBeanModel> getTbPerusahaanPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
