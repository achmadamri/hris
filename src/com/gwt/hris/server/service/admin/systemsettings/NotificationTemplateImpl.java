package com.gwt.hris.server.service.admin.systemsettings;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.admin.systemsettings.NotificationTemplateInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbNotificationTemplateBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbNotificationTemplateBean;
import com.gwt.hris.db.TbNotificationTemplateManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class NotificationTemplateImpl extends MainRemoteServiceServlet implements NotificationTemplateInterface {
	
	private static final long serialVersionUID = -8224239844874364345L;

	public ReturnBean submitNotificationTemplate(TbNotificationTemplateBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbNotificationTemplateBean bean = null;

			if (beanModel.getTbntId() != null) {
				bean = TbNotificationTemplateManager.getInstance().loadByPrimaryKey(beanModel.getTbntId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 138, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbNotificationTemplateManager.getInstance().createTbNotificationTemplateBean();

				bean = TbNotificationTemplateManager.getInstance().toBean(beanModel, bean);

				TbNotificationTemplateManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				bean = TbNotificationTemplateManager.getInstance().toBean(beanModel, bean);

				TbNotificationTemplateManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbNotificationTemplateManager.getInstance().toBeanModel(bean);
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

	public TbNotificationTemplateBeanModel getNotificationTemplate(int id) {
		TbNotificationTemplateBeanModel returnValue = new TbNotificationTemplateBeanModel();

		try {
			TbNotificationTemplateBean TbNotificationTemplateBean = TbNotificationTemplateManager.getInstance().loadByPrimaryKey(id);

			if (TbNotificationTemplateBean != null) {
				returnValue = TbNotificationTemplateManager.getInstance().toBeanModel(TbNotificationTemplateBean);

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

	public ReturnBean deleteNotificationTemplate(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 138, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			TbNotificationTemplateManager.getInstance().deleteByPrimaryKey(id);

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

	public ReturnBean deleteBulkNotificationTemplate(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 138, SystemUtil.ACCESS_DELETE) == false) {
				throw new SystemException("No delete access");
			}

			Manager.getInstance().beginTransaction();

			String strId = "";
			for (Integer id : ids) {
				strId = strId + id + ", ";
			}
			strId = strId.substring(0, strId.length() - 2);
			TbNotificationTemplateManager.getInstance().deleteByWhere("where tbc_id in (" + strId + ")");

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

	public PagingLoadResult<TbNotificationTemplateBeanModel> getNotificationTemplatePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		List<TbNotificationTemplateBeanModel> list = new ArrayList<TbNotificationTemplateBeanModel>();

		TbNotificationTemplateBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 138, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "";
			if ("Subject".equals(searchBy)) {
				strWhere = "where tbnt_subject like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = TbNotificationTemplateManager.getInstance().countAll();
			} else {
				size = TbNotificationTemplateManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = TbNotificationTemplateManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				TbNotificationTemplateBeanModel data = TbNotificationTemplateManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			TbNotificationTemplateBeanModel data = new TbNotificationTemplateBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<TbNotificationTemplateBeanModel>(list, config.getOffset(), size);
	}

	public TbNotificationTemplateBeanModel getTbNotificationTemplateAll() {
		TbNotificationTemplateBeanModel returnValue = new TbNotificationTemplateBeanModel();

		try {
			TbNotificationTemplateBean tbNotificationTemplateBeans[] = TbNotificationTemplateManager.getInstance().loadAll();
			TbNotificationTemplateBeanModel tbNotificationTemplateBeanModels[] = TbNotificationTemplateManager.getInstance().toBeanModels(tbNotificationTemplateBeans);

			returnValue.setModels(tbNotificationTemplateBeanModels);
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
