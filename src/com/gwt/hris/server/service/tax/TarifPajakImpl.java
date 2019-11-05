package com.gwt.hris.server.service.tax;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbTarifPajakBeanModel;
import com.gwt.hris.client.service.tax.TarifPajakInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbTarifPajakBean;
import com.gwt.hris.db.TbTarifPajakManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class TarifPajakImpl extends MainRemoteServiceServlet implements TarifPajakInterface {
	
	private static final long serialVersionUID = -7961569030655255347L;

	public ReturnBean submitTarifPajak(TbTarifPajakBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbTarifPajakBean bean = null;

			if (beanModel.getTbtpId() != null) {
				bean = TbTarifPajakManager.getInstance().loadByPrimaryKey(beanModel.getTbtpId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 123, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbTarifPajakManager.getInstance().createTbTarifPajakBean();

				bean = TbTarifPajakManager.getInstance().toBean(beanModel, bean);

				TbTarifPajakManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				bean = TbTarifPajakManager.getInstance().toBean(beanModel, bean);

				TbTarifPajakManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbTarifPajakManager.getInstance().toBeanModel(bean);
			returnValue.set("model", beanModel);

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

	public TbTarifPajakBeanModel getTarifPajak(int id) {
		TbTarifPajakBeanModel returnValue = new TbTarifPajakBeanModel();

		try {
			TbTarifPajakBean TbTarifPajakBean = TbTarifPajakManager.getInstance().loadByPrimaryKey(id);

			if (TbTarifPajakBean != null) {
				returnValue = TbTarifPajakManager.getInstance().toBeanModel(TbTarifPajakBean);

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

	public ReturnBean deleteTarifPajak(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 123, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbTarifPajakManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkTarifPajak(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 123, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbTarifPajakManager.getInstance().deleteByWhere("where tbtp_id in (" + strId + ")");

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

	public PagingLoadResult<TbTarifPajakBeanModel> getTarifPajakPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbTarifPajakBeanModel> list = new ArrayList<TbTarifPajakBeanModel>();

		TbTarifPajakBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 123, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Dari".equals(searchBy)) {
				strWhere = "where tbtp_pkp_dari like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Sampai".equals(searchBy)) {
				strWhere = "where tbtp_pkp_sampai like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbTarifPajakManager.getInstance().countAll();
			} else {
				size = TbTarifPajakManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbTarifPajakManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbTarifPajakBeanModel data = TbTarifPajakManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbTarifPajakBeanModel data = new TbTarifPajakBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbTarifPajakBeanModel>(list, config.getOffset(), size);
	}

	public TbTarifPajakBeanModel getTbTarifPajakAll() {
		TbTarifPajakBeanModel returnValue = new TbTarifPajakBeanModel();

		try {
			TbTarifPajakBean tbTarifPajakBeans[] = TbTarifPajakManager.getInstance().loadAll();
			TbTarifPajakBeanModel tbTarifPajakBeanModels[] = TbTarifPajakManager.getInstance().toBeanModels(tbTarifPajakBeans);

			returnValue.setModels(tbTarifPajakBeanModels);
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
