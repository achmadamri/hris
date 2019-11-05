package com.gwt.hris.server.service.admin.skills;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.skills.LanguageInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLanguageBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbLanguageBean;
import com.gwt.hris.db.TbLanguageManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class LanguageImpl extends MainRemoteServiceServlet implements LanguageInterface {
	
	private static final long serialVersionUID = 3114255624923747555L;

	public ReturnBean submitLanguage(TbLanguageBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbLanguageBean bean = null;

			if (beanModel.getTblId() != null) {
				bean = TbLanguageManager.getInstance().loadByPrimaryKey(beanModel.getTblId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 18, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbLanguageManager.getInstance().createTbLanguageBean();

				bean = TbLanguageManager.getInstance().toBean(beanModel, bean);

				TbLanguageManager.getInstance().save(bean);

				bean.setTblLanguageId("LAN" + bean.getTblId());

				TbLanguageManager.getInstance().update(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 18, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbLanguageManager.getInstance().toBean(beanModel, bean);

				TbLanguageManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbLanguageManager.getInstance().toBeanModel(bean);
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

	public TbLanguageBeanModel getLanguage(int id) {
		TbLanguageBeanModel returnValue = new TbLanguageBeanModel();

		try {
			TbLanguageBean TbLanguageBean = TbLanguageManager.getInstance().loadByPrimaryKey(id);

			if (TbLanguageBean != null) {
				returnValue = TbLanguageManager.getInstance().toBeanModel(TbLanguageBean);

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

	public ReturnBean deleteLanguage(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 18, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbLanguageManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkLanguage(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 18, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbLanguageManager.getInstance().deleteByWhere("where tbl_id in (" + strId + ")");

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

	public PagingLoadResult<TbLanguageBeanModel> getLanguagePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbLanguageBeanModel> list = new ArrayList<TbLanguageBeanModel>();

		TbLanguageBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 18, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Language ID".equals(searchBy)) {
				strWhere = "where tbl_language_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Language Name".equals(searchBy)) {
				strWhere = "where tbl_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbLanguageManager.getInstance().countAll();
			} else {
				size = TbLanguageManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbLanguageManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbLanguageBeanModel data = TbLanguageManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbLanguageBeanModel data = new TbLanguageBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbLanguageBeanModel>(list, config.getOffset(), size);
	}
}
