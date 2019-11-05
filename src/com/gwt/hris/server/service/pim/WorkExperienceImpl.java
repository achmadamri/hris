package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbWorkExperienceBeanModel;
import com.gwt.hris.client.service.pim.WorkExperienceInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbWorkExperienceBean;
import com.gwt.hris.db.TbWorkExperienceManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class WorkExperienceImpl extends MainRemoteServiceServlet implements WorkExperienceInterface {
	
	private static final long serialVersionUID = -3373326569009469271L;

	public ReturnBean submitWorkExperience(TbWorkExperienceBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbWorkExperienceBean bean = null;

			if (beanModel.getTbweId() != null) {
				bean = TbWorkExperienceManager.getInstance().loadByPrimaryKey(beanModel.getTbweId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbWorkExperienceManager.getInstance().createTbWorkExperienceBean();

				bean = TbWorkExperienceManager.getInstance().toBean(beanModel, bean);

				TbWorkExperienceManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbWorkExperienceManager.getInstance().toBean(beanModel, bean);

				TbWorkExperienceManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbWorkExperienceManager.getInstance().toBeanModel(bean);
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

	public PagingLoadResult<TbWorkExperienceBeanModel> getWorkExperiencePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbWorkExperienceBeanModel> list = new ArrayList<TbWorkExperienceBeanModel>();

		TbWorkExperienceBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			strWhere = "where " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = TbWorkExperienceManager.getInstance().countAll();
			} else {
				size = TbWorkExperienceManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbWorkExperienceManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbWorkExperienceBeanModel data = TbWorkExperienceManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbWorkExperienceBeanModel data = new TbWorkExperienceBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbWorkExperienceBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteWorkExperience(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbWorkExperienceManager.getInstance().deleteByPrimaryKey(id);

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

	public TbWorkExperienceBeanModel getWorkExperience(int id) {
		TbWorkExperienceBeanModel returnValue = new TbWorkExperienceBeanModel();

		try {
			TbWorkExperienceBean TbWorkExperienceBean = TbWorkExperienceManager.getInstance().loadByPrimaryKey(id);

			if (TbWorkExperienceBean != null) {
				returnValue = TbWorkExperienceManager.getInstance().toBeanModel(TbWorkExperienceBean);

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
}
