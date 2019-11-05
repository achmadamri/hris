package com.gwt.hris.client.service.admin.projectinfo;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;

@RemoteServiceRelativePath("customers")
public interface CustomersInterface extends RemoteService {
	ReturnBean submitCustomer(TbCustomerBeanModel TbCustomerBeanModel);

	TbCustomerBeanModel getCustomer(int tbesId);

	ReturnBean deleteCustomer(Integer tbesId);

	ReturnBean deleteBulkCustomer(Integer tbesIds[]);

	PagingLoadResult<TbCustomerBeanModel> getCustomerPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
