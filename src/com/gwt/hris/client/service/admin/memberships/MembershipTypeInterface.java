package com.gwt.hris.client.service.admin.memberships;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbMembershipTypesBeanModel;

@RemoteServiceRelativePath("membershiptype")
public interface MembershipTypeInterface extends RemoteService {
	ReturnBean submitMembershipTypes(TbMembershipTypesBeanModel TbMembershipTypesBeanModel);

	TbMembershipTypesBeanModel getMembershipTypes(int tbesId);

	ReturnBean deleteMembershipTypes(Integer tbesId);

	ReturnBean deleteBulkMembershipTypes(Integer tbesIds[]);

	PagingLoadResult<TbMembershipTypesBeanModel> getMembershipTypesPaging(final PagingLoadConfig config, String searchBy, String searchValue);
}
