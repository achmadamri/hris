package com.gwt.hris.server.service.pim;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.cron.CronAttendance;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbContactDetailsBean;
import com.gwt.hris.db.TbContactDetailsManager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbImmigrationBean;
import com.gwt.hris.db.TbImmigrationManager;
import com.gwt.hris.db.TbJobBean;
import com.gwt.hris.db.TbJobManager;
import com.gwt.hris.db.TbLoginBean;
import com.gwt.hris.db.TbLoginManager;
import com.gwt.hris.db.ViewEmployeeInformationBean;
import com.gwt.hris.db.ViewEmployeeInformationManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.server.service.QueryManager;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.StringUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class EmployeeListImpl extends MainRemoteServiceServlet implements EmployeeListInterface {
	
	private static final long serialVersionUID = 4482937481811019038L;
	
	public ReturnBean submitEmployee(TbEmployeeBeanModel beanModel) {
		return submitEmployee(beanModel, null);
	}

	public ReturnBean submitEmployee(TbEmployeeBeanModel beanModel, HttpServletRequest request) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			boolean restriction = true;

			HttpServletRequest httpServletRequest = this.getThreadLocalRequest() == null ? request : this.getThreadLocalRequest();
			HttpSession httpSession = httpServletRequest.getSession();

			String strKey = (String) httpSession.getAttribute("license_key");
			String strValue = (String) httpSession.getAttribute("license_value");

//			if (strKey.equals("shareware")) {
//				int intEmployeeCount = TbEmployeeManager.getInstance().countAll();
//
//				if (intEmployeeCount >= Integer.parseInt(strValue)) {
//					restriction = true;
//				} else {
//					restriction = false;
//				}
//			} else {
//				restriction = false;
//			}

//			if (restriction == true) {
//				returnValue.setOperationStatus(false);
//				returnValue.setMessage("Demo Application. Only max " + Integer.parseInt(strValue) + " employee allowed to be saved in the system.");
//			} else {
				Manager.getInstance().beginTransaction();

				TbEmployeeBean bean = null;

				if (beanModel.getTbeId() != null) {
					bean = TbEmployeeManager.getInstance().loadByPrimaryKey(beanModel.getTbeId());
				}

				if (bean == null) {
					if (SystemUtil.getInstance().access(httpSession, 90, SystemUtil.ACCESS_INSERT) == false) {
						throw new SystemException("No insert access");
					}

					bean = TbEmployeeManager.getInstance().createTbEmployeeBean();

					bean = TbEmployeeManager.getInstance().toBean(beanModel, bean);

					bean.setTbeName(bean.getTbeFirstName() + " " + bean.getTbeLastName());
					bean.setTbeStatus(0);
					
					bean.setTbeJoinedDate(new Date().getTime());

					TbEmployeeManager.getInstance().save(bean);

					String strTbeId = String.valueOf(bean.getTbeId());
					if (strTbeId.length() == 1) {
						strTbeId = "000" + strTbeId;
					} else if (strTbeId.length() == 2) {
						strTbeId = "00" + strTbeId;
					} else if (strTbeId.length() == 3) {
						strTbeId = "0" + strTbeId;
					}

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMM");

					bean.setTbeEmployeeId("EMP" + simpleDateFormat.format(new Date()) + strTbeId);

					TbEmployeeManager.getInstance().update(bean);

					// Contact Details
					TbContactDetailsBean tbContactDetailsBean = TbContactDetailsManager.getInstance().createTbContactDetailsBean();
					tbContactDetailsBean.setTbeId(bean.getTbeId());
					TbContactDetailsManager.getInstance().save(tbContactDetailsBean);

					// Immigration
					TbImmigrationBean tbImmigrationBean = TbImmigrationManager.getInstance().createTbImmigrationBean();
					tbImmigrationBean.setTbeId(bean.getTbeId());
					TbImmigrationManager.getInstance().save(tbImmigrationBean);

					// Job
					TbJobBean tbJobBean = TbJobManager.getInstance().createTbJobBean();
					tbJobBean.setTbeId(bean.getTbeId());
					TbJobManager.getInstance().save(tbJobBean);

					// TbAssignedLeaves
					QueryManager.getInstance().updateTbAssignedLeaves();
					
					// ESS Users
					TbLoginBean tbLoginBean = TbLoginManager.getInstance().createTbLoginBean();
					tbLoginBean.setTblUsername(bean.getTbeFirstName() + bean.getTbeLastName() + bean.getTbeId());
					tbLoginBean.setTblPassword("123");
					tbLoginBean.setTblStatus(1);
					tbLoginBean.setTbeId(bean.getTbeId());
					TbLoginManager.getInstance().save(tbLoginBean);
					tbLoginBean.setTblLoginId("USR" + tbLoginBean.getTblId());
					TbLoginManager.getInstance().update(tbLoginBean);
					
					CronAttendance cronAttendance = new CronAttendance();
					cronAttendance.createReport(bean);
					cronAttendance.createAttendance(bean);

					returnValue.setOperationStatus(true);
					returnValue.setMessage("Success Saved. Your Employee can login as ESS user. Detault ESS user/password is " + tbLoginBean.getTblUsername().toLowerCase() + "/" + tbLoginBean.getTblPassword());
				} else {
					if (SystemUtil.getInstance().access(httpSession, 90, SystemUtil.ACCESS_UPDATE) == false) {
						throw new SystemException("No update access");
					}

					if (bean.getTbeTaxNoDate() == null) {
						beanModel.setTbeTaxNoDate(new Date().getTime());
					} else {
						beanModel.setTbeTaxNoDate(bean.getTbeTaxNoDate());
					}

					beanModel.setTbeJoinedDate(bean.getTbeJoinedDate());
					bean = TbEmployeeManager.getInstance().toBean(beanModel, bean);

					bean.setTbeName(bean.getTbeFirstName() + " " + bean.getTbeLastName());

					TbEmployeeManager.getInstance().save(bean);
					
					TbContactDetailsBean tbContactDetailsBean = TbContactDetailsManager.getInstance().loadByPrimaryKey(bean.getTbeId());
					tbContactDetailsBean.setTbcdHomePhone(bean.getTbePhone());
					tbContactDetailsBean.setTbcdMobilePhone(bean.getTbeMobile());
					TbContactDetailsManager.getInstance().save(tbContactDetailsBean);

					returnValue.setOperationStatus(true);
					returnValue.setMessage("Success Updated");
				}

				beanModel = TbEmployeeManager.getInstance().toBeanModel(bean);
				returnValue.set("model", beanModel);

				commit = true;
//			}
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
	
	public TbEmployeeBeanModel getEmployee(int id) {
		return getEmployee(id, null);
	}

	public TbEmployeeBeanModel getEmployee(int id, HttpServletRequest request) {
		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			HttpServletRequest httpServletRequest = this.getThreadLocalRequest() == null ? request : this.getThreadLocalRequest();
			HttpSession httpSession = httpServletRequest.getSession();
			
			if (id == 0) {
				TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) httpSession.getAttribute("TbEmployeeBeanModel");
				id = tbEmployeeBeanModel.getTbeId();
			}
			TbEmployeeBean TbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(id);

			if (TbEmployeeBean != null) {
				returnValue = TbEmployeeManager.getInstance().toBeanModel(TbEmployeeBean);

				ViewEmployeeInformationBeanModel viewEmployeeInformationBeanModel = (ViewEmployeeInformationBeanModel) httpSession.getAttribute("ViewEmployeeInformationBeanModel");
				
				TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) httpSession.getAttribute("TbLoginBeanModel");
				tbLoginBeanModel.set("viewEmployeeInformationBeanModel", viewEmployeeInformationBeanModel);
				returnValue.set("tbLoginBeanModel", tbLoginBeanModel);
				
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

	public ReturnBean disableEmployee(Integer id) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
				throw new SystemException("No update access");
			}

			Manager.getInstance().beginTransaction();
			
			TbEmployeeBean tbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(id);
			tbEmployeeBean.setTbeStatus(1);
			TbEmployeeManager.getInstance().save(tbEmployeeBean);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Updated");

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

	public ReturnBean disableBulkEmployee(Integer ids[]) {
		ReturnBean returnValue = new ReturnBean();

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_UPDATE) == false) {
				throw new SystemException("No update access");
			}

			Manager.getInstance().beginTransaction();

			for (Integer id : ids) {
				TbEmployeeBean tbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(id);
				tbEmployeeBean.setTbeStatus(1);
				TbEmployeeManager.getInstance().save(tbEmployeeBean);
			}

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Updated");

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

	public PagingLoadResult<ViewEmployeeInformationBeanModel> getEmployeePaging(final PagingLoadConfig config, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");
		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbLoginBeanModel");
		
		List<ViewEmployeeInformationBeanModel> list = new ArrayList<ViewEmployeeInformationBeanModel>();

		ViewEmployeeInformationBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 90, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			String strWhere = "where 1=1 ";
			
			if (tbLoginBeanModel.getTbaugId() == null) {
				strWhere += "and tbe_id in (select tbe_id from tb_report_to where tbrt_spv = " + tbEmployeeBeanModel.getTbeId() + ") ";
			}
			
			if ("Emp. ID".equals(searchBy)) {
				strWhere += "and tbe_employee_id like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Emp. Name".equals(searchBy)) {
				strWhere += "and tbe_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Job Title".equals(searchBy)) {
				strWhere += "and tbjt_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Organization".equals(searchBy)) {
				strWhere += "and tbo_nama like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Location".equals(searchBy)) {
				strWhere += "and tbl_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Company".equals(searchBy)) {
				strWhere += "and tbp_name like '%" + searchValue.replaceAll("'", "") + "%' ";
			} else if ("Joined Date".equals(searchBy)) {
				strWhere += "and tbe_joined_date like '%" + searchValue.replaceAll("'", "") + "%' ";
			}

			if ("".equals(strWhere)) {
				size = ViewEmployeeInformationManager.getInstance().countAll();
			} else {
				size = ViewEmployeeInformationManager.getInstance().countWhere(strWhere);
			}

			if (config.getSortField() != null) {
				if (!"".equals(config.getSortField()) && !"actions".equals(config.getSortField()) && !"space".equals(config.getSortField())) {
					strWhere = strWhere + " order by " + StringUtil.getInstance().getTableField(config.getSortField()) + " " + config.getSortDir() + " ";
				}
			}

			datas = ViewEmployeeInformationManager.getInstance().loadByWhere(strWhere + "limit " + (config.getOffset() < 0 ? 0 : config.getOffset()) + "," + config.getLimit());

			for (int i = 0; i < datas.length; i++) {
				ViewEmployeeInformationBeanModel data = ViewEmployeeInformationManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewEmployeeInformationBeanModel data = new ViewEmployeeInformationBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewEmployeeInformationBeanModel>(list, config.getOffset(), size);
	}

	public TbEmployeeBeanModel getTbEmployeeAll() {
		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			TbEmployeeBean tbEmployeeBeans[] = TbEmployeeManager.getInstance().loadAll();
			TbEmployeeBeanModel tbEmployeeBeanModels[] = TbEmployeeManager.getInstance().toBeanModels(tbEmployeeBeans);

			returnValue.setModels(tbEmployeeBeanModels);
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
