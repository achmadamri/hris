package com.gwt.hris.client.service.admin.memberships;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMembershipTypesBeanModel;

public interface MembershipTypeInterfaceAsync {
	void submitMembershipTypes(TbMembershipTypesBeanModel TbMembershipTypesBeanModel, AsyncCallback<ReturnBean> callback);

	void getMembershipTypes(int tbesId, AsyncCallback<TbMembershipTypesBeanModel> callback);

	void deleteMembershipTypes(Integer tbesId, AsyncCallback<ReturnBean> callback);

	void deleteBulkMembershipTypes(Integer tbesIds[], AsyncCallback<ReturnBean> callback);

	void getMembershipTypesPaging(PagingLoadConfig config, String searchBy, String searchValue, AsyncCallback<PagingLoadResult<TbMembershipTypesBeanModel>> callback);
}
