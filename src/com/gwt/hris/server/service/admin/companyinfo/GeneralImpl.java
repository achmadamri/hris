package com.gwt.hris.server.service.admin.companyinfo;

import java.sql.SQLException;

import com.gwt.hris.client.service.admin.companyinfo.GeneralInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbNegaraBeanModel;
import com.gwt.hris.client.service.bean.TbPerusahaanBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbNegaraBean;
import com.gwt.hris.db.TbNegaraManager;
import com.gwt.hris.db.TbPerusahaanBean;
import com.gwt.hris.db.TbPerusahaanManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class GeneralImpl extends MainRemoteServiceServlet implements GeneralInterface {
	
	private static final long serialVersionUID = -1599528409353892811L;

	public TbNegaraBeanModel getTbNegaraAll() {
		TbNegaraBeanModel returnValue = new TbNegaraBeanModel();

		try {
			TbNegaraBean tbNegaraBeans[] = TbNegaraManager.getInstance().loadAll();
			TbNegaraBeanModel tbNegaraBeanModels[] = TbNegaraManager.getInstance().toBeanModels(tbNegaraBeans);

			returnValue.setModels(tbNegaraBeanModels);
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

	public ReturnBean submitPerusahaan(TbPerusahaanBeanModel tbPerusahaanBeanModel) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();

			TbPerusahaanBean tbPerusahaanBean = TbPerusahaanManager.getInstance().loadByPrimaryKey(1);

			tbPerusahaanBean = TbPerusahaanManager.getInstance().toBean(tbPerusahaanBeanModel, tbPerusahaanBean);

			if (tbPerusahaanBean == null) {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 3, SystemUtil.ACCESS_INSERT) == false) {
					throw new SystemException("No insert access");
				}

				TbPerusahaanManager.getInstance().save(tbPerusahaanBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Saved");
			} else {
				if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 3, SystemUtil.ACCESS_UPDATE) == false) {
					throw new SystemException("No update access");
				}

				TbPerusahaanManager.getInstance().update(tbPerusahaanBean);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Success Updated");
			}

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

	public TbPerusahaanBeanModel getPerusahaan() {
		TbPerusahaanBeanModel returnValue = new TbPerusahaanBeanModel();

		try {
			TbPerusahaanBean tbPerusahaanBean = TbPerusahaanManager.getInstance().loadByPrimaryKey(1);

			if (tbPerusahaanBean != null) {
				returnValue = TbPerusahaanManager.getInstance().toBeanModel(tbPerusahaanBean);

				int intNoOfEmployees = TbEmployeeManager.getInstance().countAll();
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
}
