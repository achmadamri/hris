package com.gwt.hris.server.service.pim;

import java.sql.SQLException;

import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbImmigrationBeanModel;
import com.gwt.hris.client.service.pim.ImmigrationInterface;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbImmigrationBean;
import com.gwt.hris.db.TbImmigrationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class ImmigrationImpl extends MainRemoteServiceServlet implements ImmigrationInterface {
	
	private static final long serialVersionUID = 3428980588188472760L;

	public ReturnBean submitImmigration(TbImmigrationBeanModel beanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbImmigrationBean bean = null;

			if (beanModel.getTbeId() != null) {
				bean = TbImmigrationManager.getInstance().loadByPrimaryKey(beanModel.getTbeId());
			}

			if (bean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				bean = TbImmigrationManager.getInstance().createTbImmigrationBean();

				bean = TbImmigrationManager.getInstance().toBean(beanModel, bean);

				TbImmigrationManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				bean = TbImmigrationManager.getInstance().toBean(beanModel, bean);

				TbImmigrationManager.getInstance().save(bean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

			beanModel = TbImmigrationManager.getInstance().toBeanModel(bean);
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

	public TbImmigrationBeanModel getImmigration(int id) {
		TbImmigrationBeanModel returnValue = new TbImmigrationBeanModel();

		try {
			TbImmigrationBean TbImmigrationBean = TbImmigrationManager.getInstance().loadByPrimaryKey(id);

			if (TbImmigrationBean != null) {
				returnValue = TbImmigrationManager.getInstance().toBeanModel(TbImmigrationBean);

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
