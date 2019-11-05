package com.gwt.hris.client.service.admin.memberships;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMembershipBeanModel;
import com.gwt.hris.client.service.bean.TbMembershipTypesBeanModel;
import com.gwt.hris.client.service.bean.ViewMembershipTypesBeanModel;

public interface MembershipInterfaceAsync {
	void submitMembership(TbMembershipBeanModel TbMembershipBeanModel, AsyncCallback<ReturnBean> callback);

	void getMembership(int tbesId, AsyncCallback<TbMembershipBeanModel> callback);

	void deleteMembership(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkMembership(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getMembershipPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<ViewMembershipTypesBeanModel>> callback);

	void getTbMembershipTypesAll(AsyncCallback<TbMembershipTypesBeanModel> callback);
}
