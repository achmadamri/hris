package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLanguagesBeanModel;
import com.gwt.hris.client.service.bean.TbLanguageBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLanguagesBeanModel;
import com.gwt.hris.client.service.pim.AssignedLanguagesInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAssignedLanguagesBean;
import com.gwt.hris.db.TbAssignedLanguagesManager;
import com.gwt.hris.db.TbLanguageBean;
import com.gwt.hris.db.TbLanguageManager;
import com.gwt.hris.db.ViewEmployeeLanguagesBean;
import com.gwt.hris.db.ViewEmployeeLanguagesManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class AssignedLanguagesImpl extends MainRemoteServiceServlet implements AssignedLanguagesInterface {
	
	private static final long serialVersionUID = -3373326569009469271L;

	public ReturnBean submitAssignedLanguages(TbAssignedLanguagesBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbAssignedLanguagesBean bean = null;

			if (beanModel.getTbalId() != null) {
				bean = TbAssignedLanguagesManager.getInstance().loadByPrimaryKey(beanModel.getTbalId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbAssignedLanguagesManager.getInstance().createTbAssignedLanguagesBean();

				bean = TbAssignedLanguagesManager.getInstance().toBean(beanModel, bean);

				TbAssignedLanguagesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbAssignedLanguagesManager.getInstance().toBean(beanModel, bean);

				TbAssignedLanguagesManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbAssignedLanguagesManager.getInstance().toBeanModel(bean);
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

	public PagingLoadResult<ViewEmployeeLanguagesBeanModel> getAssignedLanguagesPaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<ViewEmployeeLanguagesBeanModel> list = new ArrayList<ViewEmployeeLanguagesBeanModel>();

		ViewEmployeeLanguagesBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			strWhere = "where " + searchBy + "=" + searchValue.replaceAll("'", "") + " ";

			if ("".equals(strWhere)) {
				size = ViewEmployeeLanguagesManager.getInstance().countAll();
			} else {
				size = ViewEmployeeLanguagesManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeLanguagesManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeLanguagesBeanModel data = ViewEmployeeLanguagesManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeLanguagesBeanModel data = new ViewEmployeeLanguagesBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeLanguagesBeanModel>(list, config.getOffset(), size);
	}

	public ReturnBean deleteAssignedLanguages(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbAssignedLanguagesManager.getInstance().deleteByPrimaryKey(id);

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

	public TbAssignedLanguagesBeanModel getAssignedLanguages(int id) {
		TbAssignedLanguagesBeanModel returnValue = new TbAssignedLanguagesBeanModel();

		try {
			TbAssignedLanguagesBean bean = TbAssignedLanguagesManager.getInstance().loadByPrimaryKey(id);

			if (bean != null) {
				returnValue = TbAssignedLanguagesManager.getInstance().toBeanModel(bean);

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

	public TbLanguageBeanModel getTbLanguageAll() {
		TbLanguageBeanModel returnValue = new TbLanguageBeanModel();

		try {
			TbLanguageBean beans[] = TbLanguageManager.getInstance().loadAll();
			TbLanguageBeanModel beanModels[] = TbLanguageManager.getInstance().toBeanModels(beans);

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
}
