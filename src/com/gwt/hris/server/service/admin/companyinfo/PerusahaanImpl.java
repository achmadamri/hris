package com.gwt.hris.server.service.admin.companyinfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.companyinfo.PerusahaanInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbPerusahaanBean;
import com.gwt.hris.db.TbPerusahaanManager;
import com.gwt.hris.db.ViewEmployeeInformationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class PerusahaanImpl extends MainRemoteServiceServlet implements PerusahaanInterface {
	private static final long serialVersionUID = -1851427434315494627L;

	public ReturnBean submitPerusahaan(TbPerusahaanBeanModel tbPerusahaanBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbPerusahaanBean tbPerusahaanBean = null;

			if (tbPerusahaanBeanModel.getTbpId() != null) {
				tbPerusahaanBean = TbPerusahaanManager.getInstance().loadByPrimaryKey(tbPerusahaanBeanModel.getTbpId());
			}

			if (tbPerusahaanBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 133, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				tbPerusahaanBean = TbPerusahaanManager.getInstance().createTbPerusahaanBean();

				tbPerusahaanBean = TbPerusahaanManager.getInstance().toBean(tbPerusahaanBeanModel, tbPerusahaanBean);

				TbPerusahaanManager.getInstance().save(tbPerusahaanBean);

				tbPerusahaanBean.setTbpPerusahaanId("COM" + tbPerusahaanBean.getTbpId());

				TbPerusahaanManager.getInstance().update(tbPerusahaanBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 133, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				tbPerusahaanBean = TbPerusahaanManager.getInstance().toBean(tbPerusahaanBeanModel, tbPerusahaanBean);

				TbPerusahaanManager.getInstance().save(tbPerusahaanBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			tbPerusahaanBeanModel = TbPerusahaanManager.getInstance().toBeanModel(tbPerusahaanBean);
			returnValue.set("model", tbPerusahaanBeanModel);

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

	public TbPerusahaanBeanModel getPerusahaan(int tbpId) {
		TbPerusahaanBeanModel returnValue = new TbPerusahaanBeanModel();

		try {
			TbPerusahaanBean tbPerusahaanBean = TbPerusahaanManager.getInstance().loadByPrimaryKey(tbpId);

			if (tbPerusahaanBean != null) {
				returnValue = TbPerusahaanManager.getInstance().toBeanModel(tbPerusahaanBean);
				
				int intNoOfEmployees = ViewEmployeeInformationManager.getInstance().countWhere("where tbp_id = " + tbPerusahaanBean.getTbpId());
				returnValue.set("NoOfEmployees", intNoOfEmployees);

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

	public ReturnBean deletePerusahaan(Integer tbpId) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 133, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbPerusahaanManager.getInstance().deleteByPrimaryKey(tbpId);

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

	public ReturnBean deleteBulkPerusahaan(Integer tbpIds[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 133, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : tbpIds) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbPerusahaanManager.getInstance().deleteByWhere("where tbp_id in (" + strId + ")");

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

	public PagingLoadResult<TbPerusahaanBeanModel> getTbPerusahaanPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbPerusahaanBeanModel> list = new ArrayList<TbPerusahaanBeanModel>();

		TbPerusahaanBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 133, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Company ID".equals(searchBy)) {
				strWhere = "where tbp_Perusahaan_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Company Name".equals(searchBy)) {
				strWhere = "where tbp_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbPerusahaanManager.getInstance().countAll();
			} else {
				size = TbPerusahaanManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbPerusahaanManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbPerusahaanBeanModel data = TbPerusahaanManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbPerusahaanBeanModel data = new TbPerusahaanBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbPerusahaanBeanModel>(list, config.getOffset(), size);
	}
}
