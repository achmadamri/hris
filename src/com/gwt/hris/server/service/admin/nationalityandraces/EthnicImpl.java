package com.gwt.hris.server.service.admin.nationalityandraces;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.nationalityandraces.EthnicInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEthnicRacesBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEthnicRacesBean;
import com.gwt.hris.db.TbEthnicRacesManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EthnicImpl extends MainRemoteServiceServlet implements EthnicInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitEthnic(TbEthnicRacesBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbEthnicRacesBean bean = null;

			if (beanModel.getTberId() != null) {
				bean = TbEthnicRacesManager.getInstance().loadByPrimaryKey(beanModel.getTberId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 24, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbEthnicRacesManager.getInstance().createTbEthnicRacesBean();

				bean = TbEthnicRacesManager.getInstance().toBean(beanModel, bean);

				TbEthnicRacesManager.getInstance().save(bean);

				bean.setTberEthnicRacesId("ETH" + bean.getTberId());

				TbEthnicRacesManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 24, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbEthnicRacesManager.getInstance().toBean(beanModel, bean);

				TbEthnicRacesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbEthnicRacesManager.getInstance().toBeanModel(bean);
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

	public TbEthnicRacesBeanModel getEthnic(int id) {
		TbEthnicRacesBeanModel returnValue = new TbEthnicRacesBeanModel();

		try {
			TbEthnicRacesBean TbEthnicRacesBean = TbEthnicRacesManager.getInstance().loadByPrimaryKey(id);

			if (TbEthnicRacesBean != null) {
				returnValue = TbEthnicRacesManager.getInstance().toBeanModel(TbEthnicRacesBean);

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

	public ReturnBean deleteEthnic(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 24, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbEthnicRacesManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkEthnic(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 24, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbEthnicRacesManager.getInstance().deleteByWhere("where tber_id in (" + strId + ")");

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

	public PagingLoadResult<TbEthnicRacesBeanModel> getEthnicPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbEthnicRacesBeanModel> list = new ArrayList<TbEthnicRacesBeanModel>();

		TbEthnicRacesBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 24, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Ethnic Race ID".equals(searchBy)) {
				strWhere = "where tber_ethnic_races_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Ethnic Race Name".equals(searchBy)) {
				strWhere = "where tber_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbEthnicRacesManager.getInstance().countAll();
			} else {
				size = TbEthnicRacesManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbEthnicRacesManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbEthnicRacesBeanModel data = TbEthnicRacesManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbEthnicRacesBeanModel data = new TbEthnicRacesBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbEthnicRacesBeanModel>(list, config.getOffset(), size);
	}

	public TbEthnicRacesBeanModel getTbEthnicRacesAll() {
		TbEthnicRacesBeanModel returnValue = new TbEthnicRacesBeanModel();

		try {
			TbEthnicRacesBean tbEthnicRacesBeans[] = TbEthnicRacesManager.getInstance().loadAll();
			TbEthnicRacesBeanModel tbEthnicRacesBeanModels[] = TbEthnicRacesManager.getInstance().toBeanModels(tbEthnicRacesBeans);

			returnValue.setModels(tbEthnicRacesBeanModels);
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
