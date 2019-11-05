package com.gwt.hris.server.service.admin.companyinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.companyinfo.LocationsInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLocationBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.client.service.bean.ViewLocationNegaraBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbLocationBean;
import com.gwt.hris.db.TbLocationManager;
import com.gwt.hris.db.TbPerusahaanBean;
import com.gwt.hris.db.TbPerusahaanManager;
import com.gwt.hris.db.ViewLocationNegaraBean;
import com.gwt.hris.db.ViewLocationNegaraManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class LocationsImpl extends MainRemoteServiceServlet implements LocationsInterface {
	
	private static final long serialVersionUID = -691939291296534124L;

	public ReturnBean submitLocations(TbLocationBeanModel tbLocationBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbLocationBean tbLocationBean = null;

			if (tbLocationBeanModel.getTblId() != null) {
				tbLocationBean = TbLocationManager.getInstance().loadByPrimaryKey(tbLocationBeanModel.getTblId());
			}

			if (tbLocationBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 4, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbLocationBean = TbLocationManager.getInstance().createTbLocationBean();

				tbLocationBean = TbLocationManager.getInstance().toBean(tbLocationBeanModel, tbLocationBean);

				TbLocationManager.getInstance().save(tbLocationBean);

				tbLocationBean.setTblLocationId("LOC" + tbLocationBean.getTblId());

				TbLocationManager.getInstance().update(tbLocationBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 4, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbLocationBean = TbLocationManager.getInstance().toBean(tbLocationBeanModel, tbLocationBean);

				TbLocationManager.getInstance().save(tbLocationBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			tbLocationBeanModel = TbLocationManager.getInstance().toBeanModel(tbLocationBean);
			returnValue.set("model", tbLocationBeanModel);

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

	public TbLocationBeanModel getLocation(int tblId) {
		TbLocationBeanModel returnValue = new TbLocationBeanModel();

		try {
			TbLocationBean tbLocationBean = TbLocationManager.getInstance().loadByPrimaryKey(tblId);

			if (tbLocationBean != null) {
				returnValue = TbLocationManager.getInstance().toBeanModel(tbLocationBean);

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

	public ReturnBean deleteLocations(Integer tblId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 4, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbLocationManager.getInstance().deleteByPrimaryKey(tblId);

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

	public ReturnBean deleteBulkLocations(Integer tblIds[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 4, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : tblIds) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbLocationManager.getInstance().deleteByWhere("where tbl_id in (" + strId + ")");

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

	public PagingLoadResult<ViewLocationNegaraBeanModel> getTbLocationPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewLocationNegaraBeanModel> list = new ArrayList<ViewLocationNegaraBeanModel>();

		ViewLocationNegaraBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 4, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Location ID".equals(searchBy)) {
				strWhere = "where tbl_location_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Location Name".equals(searchBy)) {
				strWhere = "where tbl_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("City Name".equals(searchBy)) {
				strWhere = "where tbn_nama like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewLocationNegaraManager.getInstance().countAll();
			} else {
				size = ViewLocationNegaraManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewLocationNegaraManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewLocationNegaraBeanModel data = ViewLocationNegaraManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewLocationNegaraBeanModel data = new ViewLocationNegaraBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewLocationNegaraBeanModel>(list, config.getOffset(), size);
	}
	
	public TbPerusahaanBeanModel getTbPerusahaanAll() {
		TbPerusahaanBeanModel returnValue = new TbPerusahaanBeanModel();

		try {
			TbPerusahaanBean tbPerusahaanBeans[] = TbPerusahaanManager.getInstance().loadAll();
			TbPerusahaanBeanModel tbPerusahaanBeanModels[] = TbPerusahaanManager.getInstance().toBeanModels(tbPerusahaanBeans);

			returnValue.setModels(tbPerusahaanBeanModels);
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
}
