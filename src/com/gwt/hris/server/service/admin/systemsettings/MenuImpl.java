package com.gwt.hris.server.service.admin.systemsettings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.resources.model.Folder;
import com.gwt.hris.client.service.admin.systemsettings.MenuInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.TbMenuBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAdminUserGroupsBean;
import com.gwt.hris.db.TbAdminUserGroupsManager;
import com.gwt.hris.db.TbJobTitleBean;
import com.gwt.hris.db.TbJobTitleManager;
import com.gwt.hris.db.TbMenuAccessAdminBean;
import com.gwt.hris.db.TbMenuAccessAdminManager;
import com.gwt.hris.db.TbMenuAccessBean;
import com.gwt.hris.db.TbMenuAccessManager;
import com.gwt.hris.db.TbMenuBean;
import com.gwt.hris.db.TbMenuManager;
import com.gwt.hris.db.ViewMenuBean;
import com.gwt.hris.db.ViewMenuManager;
import com.gwt.hris.db.exception.DAOException;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class MenuImpl extends MainRemoteServiceServlet implements MenuInterface {

	private static final long serialVersionUID = -1085917040233716837L;

	private int countById(TbMenuBean tbMenuBeans[], int intId) {
		int intReturnValue = 0;

		for (TbMenuBean tbMenuBean : tbMenuBeans) {
			if (intId == 0) {
				if (tbMenuBean.getTbmParentId() == null) {
					intReturnValue++;
				}
			} else {
				if (tbMenuBean.getTbmParentId() != null) {
					if (tbMenuBean.getTbmParentId() == intId) {
						intReturnValue++;
					}
				}
			}
		}

		return intReturnValue;
	}

	private TbMenuBean[] getTbMenuBeanById(TbMenuBean tbMenuBeans[], int intId) {
		List<TbMenuBean> lstTbMenuBean = new ArrayList<TbMenuBean>();

		for (TbMenuBean tbMenuBean : tbMenuBeans) {
			if (intId == 0) {
				if (tbMenuBean.getTbmParentId() == null) {
					lstTbMenuBean.add(tbMenuBean);
				}
			} else {
				if (tbMenuBean.getTbmParentId() != null) {
					if (tbMenuBean.getTbmParentId() == intId) {
						lstTbMenuBean.add(tbMenuBean);
					}
				}
			}
		}
		Object objs[] = lstTbMenuBean.toArray();
		TbMenuBean tbMenuBeanReturns[] = new TbMenuBean[objs.length];
		for (int i = 0; i < tbMenuBeanReturns.length; i++) {
			tbMenuBeanReturns[i] = (TbMenuBean) objs[i];
		}

		return tbMenuBeanReturns;
	}

	@Override
	public TbMenuBeanModel getMenu() {
		TbMenuBeanModel returnValue = new TbMenuBeanModel();

		try {
			TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
			ViewEmployeeInformationBeanModel viewEmployeeInformationBeanModel = (ViewEmployeeInformationBeanModel) this.getThreadLocalRequest().getSession()
					.getAttribute("ViewEmployeeInformationBeanModel");

			// format key
			// id + "_" + parent
			String strWhere = "where tbm_disabled = 0 ";
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and tbm_id in (select tbm_id from view_menu_access where tbjt_id = " + viewEmployeeInformationBeanModel.getTbjtId() + " and tbma_enabled = 1) ";
			} else {
				strWhere += "and tbm_id in (select tbm_id from view_menu_access_admin where tbaug_id = " + tbLoginBeanModel.getTbaugId() + " and tbmaa_enabled = 1) ";
			}
			strWhere += "order by tbm_index";

			TbMenuBean tbMenuBeans[] = (TbMenuBean[]) TbMenuManager.getInstance().loadByWhere(strWhere);

			// Load Level 1
			TbMenuBean tbMenuBean_1s[] = getTbMenuBeanById(tbMenuBeans, 0);
			TbMenuBeanModel TbMenuBeanModel_1s[] = TbMenuManager.getInstance().toBeanModels(tbMenuBean_1s);
			
			List<ModelData> lst_1s_ = new ArrayList<ModelData>();
			for (TbMenuBeanModel TbMenuBeanModel_1s_ : TbMenuBeanModel_1s) {
				lst_1s_.add(new Folder(TbMenuBeanModel_1s_.getTbmNama()));
			}
			returnValue.setChildren(lst_1s_);

			// Load Level 2
			for (int x = 0; x < TbMenuBeanModel_1s.length; x++) {
				int intCountx = countById(tbMenuBeans, TbMenuBeanModel_1s[x].getTbmId());
				if (intCountx > 0) {
					TbMenuBean tbMenuBean_2s[] = getTbMenuBeanById(tbMenuBeans, TbMenuBeanModel_1s[x].getTbmId());
					TbMenuBeanModel TbMenuBeanModel_2s[] = TbMenuManager.getInstance().toBeanModels(tbMenuBean_2s);
					TbMenuBeanModel_1s[x].setModels(TbMenuBeanModel_2s);
					
					List<ModelData> lst_2s_ = new ArrayList<ModelData>();
					for (TbMenuBeanModel TbMenuBeanModel_2s_ : TbMenuBeanModel_2s) {
						lst_2s_.add(new Folder(TbMenuBeanModel_2s_.getTbmNama()));
					}
					TbMenuBeanModel_1s[x].setChildren(lst_2s_);

					// Load Level 3
					for (int xx = 0; xx < TbMenuBeanModel_2s.length; xx++) {
						int intCountxx = countById(tbMenuBeans, TbMenuBeanModel_2s[xx].getTbmId());
						if (intCountxx > 0) {
							TbMenuBean tbMenuBean_3s[] = getTbMenuBeanById(tbMenuBeans, TbMenuBeanModel_2s[xx].getTbmId());
							TbMenuBeanModel TbMenuBeanModel_3s[] = TbMenuManager.getInstance().toBeanModels(tbMenuBean_3s);
							TbMenuBeanModel_2s[xx].setModels(TbMenuBeanModel_3s);

							// Load Level 4
							for (int xxx = 0; xxx < TbMenuBeanModel_3s.length; xxx++) {
								int intCountxxx = countById(tbMenuBeans, TbMenuBeanModel_3s[xxx].getTbmId());
								if (intCountxxx > 0) {
									TbMenuBean tbMenuBean_4s[] = getTbMenuBeanById(tbMenuBeans, TbMenuBeanModel_3s[xxx].getTbmId());
									TbMenuBeanModel TbMenuBeanModel_4s[] = TbMenuManager.getInstance().toBeanModels(tbMenuBean_4s);
									TbMenuBeanModel_3s[xxx].setModels(TbMenuBeanModel_4s);

									// Load Level 5
									for (int xxxx = 0; xxxx < TbMenuBeanModel_3s.length; xxxx++) {
										int intCountxxxx = countById(tbMenuBeans, TbMenuBeanModel_4s[xxxx].getTbmId());
										if (intCountxxxx > 0) {
											TbMenuBean tbMenuBean_5s[] = getTbMenuBeanById(tbMenuBeans, TbMenuBeanModel_4s[xxxx].getTbmId());
											TbMenuBeanModel TbMenuBeanModel_5s[] = TbMenuManager.getInstance().toBeanModels(tbMenuBean_5s);
											TbMenuBeanModel_4s[xxxx].setModels(TbMenuBeanModel_5s);
										}
									}
								}
							}
						}
					}
				}
			}

			returnValue.setOperationStatus(true);
			returnValue.setMessage("success");

			returnValue.setModels(TbMenuBeanModel_1s);
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}

	public ReturnBean submitMenu(TbMenuBeanModel tbMenuBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbMenuBean tbMenuBean = null;

			if (tbMenuBeanModel.getTbmId() != null) {
				tbMenuBean = TbMenuManager.getInstance().loadByPrimaryKey(tbMenuBeanModel.getTbmId());
			}

			if (tbMenuBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 91, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbMenuBean = TbMenuManager.getInstance().createTbMenuBean();

				tbMenuBean = TbMenuManager.getInstance().toBean(tbMenuBeanModel, tbMenuBean);

				TbMenuManager.getInstance().save(tbMenuBean);

				TbJobTitleBean tbJobTitleBeans[] = TbJobTitleManager.getInstance().loadAll();
				TbMenuAccessBean tbMenuAccessBeans[] = new TbMenuAccessBean[tbJobTitleBeans.length];
				for (int i = 0; i < tbJobTitleBeans.length; i++) {
					tbMenuAccessBeans[i] = TbMenuAccessManager.getInstance().createTbMenuAccessBean();
					tbMenuAccessBeans[i].setTbmId(tbMenuBean.getTbmId());
					tbMenuAccessBeans[i].setTbjtId(tbJobTitleBeans[i].getTbjtId());
					tbMenuAccessBeans[i].setTbmaEnabled(0);
					tbMenuAccessBeans[i].setTbmaInsert(0);
					tbMenuAccessBeans[i].setTbmaUpdate(0);
					tbMenuAccessBeans[i].setTbmaDelete(0);
					tbMenuAccessBeans[i].setTbmaView(0);
					tbMenuAccessBeans[i].setTbmaApprove(0);
				}
				TbMenuAccessManager.getInstance().save(tbMenuAccessBeans);

				TbAdminUserGroupsBean tbAdminUserGroupsBeans[] = TbAdminUserGroupsManager.getInstance().loadAll();
				TbMenuAccessAdminBean tbMenuAccessAdminBeans[] = new TbMenuAccessAdminBean[tbAdminUserGroupsBeans.length];
				for (int i = 0; i < tbAdminUserGroupsBeans.length; i++) {
					tbMenuAccessAdminBeans[i] = TbMenuAccessAdminManager.getInstance().createTbMenuAccessAdminBean();
					tbMenuAccessAdminBeans[i].setTbmId(tbMenuBean.getTbmId());
					tbMenuAccessAdminBeans[i].setTbaugId(tbAdminUserGroupsBeans[i].getTbaugId());
					tbMenuAccessAdminBeans[i].setTbmaaEnabled(0);
					tbMenuAccessAdminBeans[i].setTbmaaInsert(0);
					tbMenuAccessAdminBeans[i].setTbmaaUpdate(0);
					tbMenuAccessAdminBeans[i].setTbmaaDelete(0);
					tbMenuAccessAdminBeans[i].setTbmaaView(0);
					tbMenuAccessAdminBeans[i].setTbmaaApprove(0);
				}
				TbMenuAccessAdminManager.getInstance().save(tbMenuAccessAdminBeans);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 91, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbMenuBean = TbMenuManager.getInstance().toBean(tbMenuBeanModel, tbMenuBean);

				if (tbMenuBean.getTbmParentId() == 0) {
					tbMenuBean.setTbmParentId(null);
				}

				TbMenuManager.getInstance().save(tbMenuBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			tbMenuBeanModel = TbMenuManager.getInstance().toBeanModel(tbMenuBean);
			returnValue.set("model", tbMenuBeanModel);

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public TbMenuBeanModel getMenu(int id) {
		TbMenuBeanModel returnValue = new TbMenuBeanModel();

		try {
			TbMenuBean bean = TbMenuManager.getInstance().loadByPrimaryKey(id);

			if (bean != null) {
				returnValue = TbMenuManager.getInstance().toBeanModel(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("");
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Data Not Found");
			}
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}

	public ReturnBean deleteMenu(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 91, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbMenuAccessManager.getInstance().deleteBytbma_fk_1(id);

			TbMenuAccessAdminManager.getInstance().deleteBytbmaa_fk_1(id);

			TbMenuManager.getInstance().deleteByPrimaryKey(id);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Deleted");

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public ReturnBean deleteBulkMenu(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 91, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);

			TbMenuAccessManager.getInstance().deleteByWhere("where tbm_id in (" + strId + ")");

			TbMenuAccessAdminManager.getInstance().deleteByWhere("where tbm_id in (" + strId + ")");

			TbMenuManager.getInstance().deleteByWhere("where tbm_id in (" + strId + ")");

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Deleted");

			commit = true;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				returnValue.setOperationStatus(false);
				returnValue.setMessage(e.getMessage());
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	public PagingLoadResult<ViewMenuBeanModel> getTbMenuPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewMenuBeanModel> list = new ArrayList<ViewMenuBeanModel>();

		ViewMenuBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 91, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Parent".equals(searchBy)) {
				strWhere = "where tbm_nama_parent like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Menu".equals(searchBy)) {
				strWhere = "where tbm_nama like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Enabled".equals(searchBy)) {
				strWhere = "where tbm_disabled like '%" + ("yes".equalsIgnoreCase(searchValue) ? "0" : "1") + "%' ";
			} else if ("Index".equals(searchBy)) {
				strWhere = "where tbm_index like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewMenuManager.getInstance().countAll();
			} else {
				size = ViewMenuManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewMenuManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewMenuBeanModel data = ViewMenuManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewMenuBeanModel data = new ViewMenuBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewMenuBeanModel>(list, config.getOffset(), size);
	}

	public TbMenuBeanModel getTbMenuAll() {
		TbMenuBeanModel returnValue = new TbMenuBeanModel();

		try {
			TbMenuBean beans[] = TbMenuManager.getInstance().loadAll();
			TbMenuBeanModel beanModels[] = TbMenuManager.getInstance().toBeanModels(beans);

			returnValue.setModels(beanModels);
			returnValue.setOperationStatus(true);
			returnValue.setMessage("");
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}
	
//	public static void main(String args[]) {
//		MenuImpl menuImpl = new MenuImpl();
//		try {
//			Folder objData = menuImpl.getMenuTree(0,  0, new Folder("root"));
//			System.out.println(objData);
//		} catch (DAOException e) {
//			e.printStackTrace();
//		}
//	}

	public TbMenuBeanModel getMenuTree() {
		TbMenuBeanModel returnValue = new TbMenuBeanModel();

		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
		ViewEmployeeInformationBeanModel viewEmployeeInformationBeanModel = (ViewEmployeeInformationBeanModel) this.getThreadLocalRequest().getSession()
				.getAttribute("ViewEmployeeInformationBeanModel");
		
		try {
			Folder objData = getMenuTree(0,  0, viewEmployeeInformationBeanModel.getTbjtId() == null ? 0 : viewEmployeeInformationBeanModel.getTbjtId(), 
												tbLoginBeanModel.getTbaugId() == null ? 0 : tbLoginBeanModel.getTbaugId(), new Folder("root", 0));

			returnValue.set("root", objData);
			returnValue.setOperationStatus(true);
			returnValue.setMessage("");
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}
	
	public Folder getMenuTree(int intTbmParentId, int intIndent, int intTbjtId, int intTbaugId, Folder objParent) throws DAOException {
		String strWhere = "where tbm_disabled = 0 ";
		if (intTbmParentId == 0) {
			strWhere += "and tbm_parent_id is null ";
		} else {
			strWhere += "and tbm_parent_id = " + intTbmParentId + " ";
		}
		
		if (intTbaugId == 0) {
			strWhere += "and tbm_id in (select tbm_id from view_menu_access where tbjt_id = " + intTbjtId + " and tbma_enabled = 1) ";
		} else {
			strWhere += "and tbm_id in (select tbm_id from view_menu_access_admin where tbaug_id = " + intTbaugId + " and tbmaa_enabled = 1) ";
		}
		strWhere += "order by tbm_index";
		
		List<ModelData> lst = new ArrayList<ModelData>();
		
		TbMenuBeanModel objChildrens[] = TbMenuManager.getInstance().toBeanModels((TbMenuBean[]) TbMenuManager.getInstance().loadByWhere(strWhere));
		for (TbMenuBeanModel objChildrens_ : objChildrens) {
			Folder children = new Folder(objChildrens_.getTbmNama(), objChildrens_.getTbmId());
			
			strWhere = "where tbm_parent_id = " + objChildrens_.getTbmId();
			int childCount = TbMenuManager.getInstance().countWhere(strWhere);
			if (childCount > 0) {
				getMenuTree(objChildrens_.getTbmId(), (intIndent + 1), intTbjtId, intTbaugId, children);
			}
			
			lst.add(children);
		}
		
		objParent.setChildren(lst);
		
		return objParent;
	}
}
