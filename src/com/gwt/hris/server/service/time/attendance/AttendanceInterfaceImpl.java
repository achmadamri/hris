package com.gwt.hris.server.service.time.attendance;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAttendanceBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;
import com.gwt.hris.client.service.time.attendance.AttendanceInterface;
import com.gwt.hris.cron.CronAttendance;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbAttendanceBean;
import com.gwt.hris.db.TbAttendanceManager;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbEmployeeShiftBean;
import com.gwt.hris.db.TbEmployeeShiftManager;
import com.gwt.hris.db.TbShiftBean;
import com.gwt.hris.db.TbShiftManager;
import com.gwt.hris.db.ViewAttendanceBean;
import com.gwt.hris.db.ViewAttendanceCustomManager;
import com.gwt.hris.db.ViewAttendanceManager;
import com.gwt.hris.db.ViewEmployeeShiftBean;
import com.gwt.hris.db.ViewEmployeeShiftManager;
import com.gwt.hris.server.service.MainRemoteServiceServlet;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.SystemException;
import com.gwt.hris.util.SystemUtil;

public class AttendanceInterfaceImpl extends MainRemoteServiceServlet implements AttendanceInterface {
	
	private static final long serialVersionUID = 1147380468542423014L;
	
	public TbEmployeeBeanModel getEmployeeAttendance(int id, String month) {
		return getEmployeeAttendance(id, null);
	}

	public TbEmployeeBeanModel getEmployeeAttendance(int id, String month, HttpServletRequest request) {
		TbEmployeeBeanModel returnValue = new TbEmployeeBeanModel();

		try {
			if (SystemUtil.getInstance().access((this.getThreadLocalRequest() == null ? request : this.getThreadLocalRequest()).getSession(), 98, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No insert access");
			}
			
			HttpServletRequest httpServletRequest = this.getThreadLocalRequest() == null ? request : this.getThreadLocalRequest();
			HttpSession httpSession = httpServletRequest.getSession();
			
			TbEmployeeBean tbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(id);

			if (tbEmployeeBean != null) {
				returnValue = TbEmployeeManager.getInstance().toBeanModel(tbEmployeeBean);

				ViewEmployeeInformationBeanModel viewEmployeeInformationBeanModel = (ViewEmployeeInformationBeanModel) httpSession.getAttribute("ViewEmployeeInformationBeanModel");
				
				ViewAttendanceBean viewAttendanceBeans[] = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(tbEmployeeBean.getTbeId(), month);
				ViewAttendanceBeanModel viewAttendanceBeanModels[] = ViewAttendanceCustomManager.getInstance().toBeanModels(viewAttendanceBeans);
				
				TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) httpSession.getAttribute("TbLoginBeanModel");
				tbLoginBeanModel.set("viewEmployeeInformationBeanModel", viewEmployeeInformationBeanModel);
				tbLoginBeanModel.set("viewAttendanceBeanModels", viewAttendanceBeanModels);
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
	
	public ReturnBean submitAttendance(TbAttendanceBeanModel beanModel) {
		return submitAttendance(beanModel, null);
	}

	public ReturnBean submitAttendance(TbAttendanceBeanModel beanModel, HttpServletRequest request) {
		ReturnBean returnValue = new ReturnBean();
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) (this.getThreadLocalRequest() == null ? request : this.getThreadLocalRequest()).getSession().getAttribute("TbEmployeeBeanModel");

		boolean commit = false;
		try {
			if (SystemUtil.getInstance().access((this.getThreadLocalRequest() == null ? request : this.getThreadLocalRequest()).getSession(), 98, SystemUtil.ACCESS_INSERT) == false) {
				throw new SystemException("No insert access");
			}

			Manager.getInstance().beginTransaction();

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			TbAttendanceBean beans[] = TbAttendanceManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBeanModel.getTbeId() + " and tba_date = '" + simpleDateFormat.format(cal.getTime()) + "' and tba_out_time is null");
			TbAttendanceBean bean = null;
			if (beans.length > 0) {
				ViewAttendanceBean viewAttendanceBeansPrevious[] = ViewAttendanceManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBeanModel.getTbeId() + " order by tba_date desc, tba_in_time desc, tba_out_time desc limit 10");
				
				if (viewAttendanceBeansPrevious[0].getTbaInTime() == null && viewAttendanceBeansPrevious[1].getTbaOutTime() == null) {
					bean = TbAttendanceManager.getInstance().createTbAttendanceBean();
				} else {
					bean = beans[0];
				}
			} else {
				bean = TbAttendanceManager.getInstance().createTbAttendanceBean();
			}
			
			bean.setTbeId(tbEmployeeBeanModel.getTbeId());
			bean.setTbaDate(simpleDateFormat.format(cal.getTime()));
			
			if ("in".equals(beanModel.get("strNav"))) {
				simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
				bean.setTbaInTime(simpleDateFormat.format(cal.getTime()));

				bean.setTbaInNote(beanModel.getTbaInNote());
				bean.setTbaInLongitude(beanModel.getTbaInLongitude());
				bean.setTbaInLatitude(beanModel.getTbaInLatitude());
				bean.setTbaInPhoto(beanModel.getTbaInPhoto());
				
				TbEmployeeShiftBean tbEmployeeShiftBean = TbEmployeeShiftManager.getInstance().createTbEmployeeShiftBean();
				tbEmployeeShiftBean.setTbeId(bean.getTbeId());
				tbEmployeeShiftBean.setTbsId(3);
				tbEmployeeShiftBean.setTbesDate(bean.getTbaDate());
				TbEmployeeShiftManager.getInstance().save(tbEmployeeShiftBean);
			} else {
				simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
				bean.setTbaOutTime(simpleDateFormat.format(cal.getTime()));

				bean.setTbaOutNote(beanModel.getTbaOutNote());
				bean.setTbaOutLongitude(beanModel.getTbaOutLongitude());
				bean.setTbaOutLatitude(beanModel.getTbaOutLatitude());
				bean.setTbaOutPhoto(beanModel.getTbaOutPhoto());
			}

			TbAttendanceManager.getInstance().save(bean);
			
			TbEmployeeBean tbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(tbEmployeeBeanModel.getTbeId());
			CronAttendance cronAttendance = new CronAttendance();
			cronAttendance.createReport(tbEmployeeBean);
			cronAttendance.createAttendance(tbEmployeeBean);

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Success Saved");

			beanModel = TbAttendanceManager.getInstance().toBeanModel(bean);

			if ("in".equals(beanModel.get("strNav"))) {
				beanModel.set("in", "false");
			} else {
				beanModel.set("in", "true");
			}

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

	public ReturnBean getPunchStatus(String strDate) {
		return getPunchStatus(strDate, null);
	}

	public ReturnBean getPunchStatus(String strDate, HttpServletRequest request) {
		ReturnBean returnValue = new ReturnBean();

		try {
			TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) (this.getThreadLocalRequest() == null ? request : this.getThreadLocalRequest()).getSession().getAttribute("TbEmployeeBeanModel");

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			System.out.println(cal.getTime());
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			ViewAttendanceBean viewAttendanceBeans[] = ViewAttendanceManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBeanModel.getTbeId() + " and tba_date = '" + simpleDateFormat.format(cal.getTime()) + "' order by tba_date desc, tba_in_time desc, tba_out_time desc");
			ViewAttendanceBean viewAttendanceBeansPrevious[] = ViewAttendanceManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBeanModel.getTbeId() + " order by tba_date desc, tba_in_time desc, tba_out_time desc limit 10");

			if (viewAttendanceBeans.length == 0) {
				if (viewAttendanceBeansPrevious.length == 0) {
					returnValue.set("in", "true");
				} else if (viewAttendanceBeansPrevious[0].getTbaOutTime() == null) {
					returnValue.set("in", "false");
				} else {
					returnValue.set("in", "true");
				}
			} else {
				if (viewAttendanceBeans[0].getTbaOutTime() == null) {
					returnValue.set("in", "false");					
				} else {
					returnValue.set("in", "true");
				}
			}
			

//			ViewAttendanceBean viewAttendanceBean = null;
//			
//			if ("".equals(strDate)) {
//				ViewAttendanceBean viewAttendanceBeans[] = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(tbEmployeeBeanModel.getTbeId(), simpleDateFormat.format(cal.getTime()));
//				
//				if (viewAttendanceBeans.length > 0) {
//					viewAttendanceBean = viewAttendanceBeans[viewAttendanceBeans.length - 1];
//				}
//			} else {
//				ViewAttendanceBean viewAttendanceBeans[] = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(tbEmployeeBeanModel.getTbeId(), strDate);
//				
//				if (viewAttendanceBeans.length > 0) {
//					viewAttendanceBean = viewAttendanceBeans[viewAttendanceBeans.length - 1];
//				}
//			}
//			
//			if (viewAttendanceBean == null) {
//				returnValue.set("in", "true");
//			} else {
//				if (viewAttendanceBean.getTbaInTime() == null) {
//					returnValue.set("in", "true");
//				} else {
//					if (viewAttendanceBean.getTbaOutTime() == null) {
//						returnValue.set("in", "false");
//					} else {
//						SimpleDateFormat simpleDateFormatPunch = new SimpleDateFormat("HH:mm:ss");
//						Date dateInTime = simpleDateFormatPunch.parse(viewAttendanceBean.getTbaInTime());
//						Date dateOutTime = simpleDateFormatPunch.parse(viewAttendanceBean.getTbaOutTime());
//						
//						if (dateInTime.after(dateOutTime)) {
//							returnValue.set("in", "false");
//						} else {
//							returnValue.set("in", "true");
//						}
//					}
//				}
//			}
//
//			returnValue.set("tbsName", viewAttendanceBean.getTbsName());
//			returnValue.set("tbsInTime", viewAttendanceBean.getTbsInTime());
//			returnValue.set("tbsOutTime", viewAttendanceBean.getTbsOutTime());
//
//			returnValue.set("tbaInTime", viewAttendanceBean.getTbaInTime());
//			returnValue.set("tbaOutTime", viewAttendanceBean.getTbaOutTime());
//
//			returnValue.set("tbaInNote", viewAttendanceBean.getTbaInNote());
//			returnValue.set("tbaOutNote", viewAttendanceBean.getTbaOutNote());

			if ("".equals(strDate)) {
				returnValue.set("date", simpleDateFormat.format(cal.getTime()));				
			} else {
				returnValue.set("date", strDate);
			}
			
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

	public ReturnBean getMonth() {
		ReturnBean returnValue = new ReturnBean();

		ReturnBean datas[] = new ReturnBean[12];
		datas[0] = new ReturnBean();
		datas[0].set("month", "01");

		datas[1] = new ReturnBean();
		datas[1].set("month", "02");

		datas[2] = new ReturnBean();
		datas[2].set("month", "03");

		datas[3] = new ReturnBean();
		datas[3].set("month", "04");

		datas[4] = new ReturnBean();
		datas[4].set("month", "05");

		datas[5] = new ReturnBean();
		datas[5].set("month", "06");

		datas[6] = new ReturnBean();
		datas[6].set("month", "07");

		datas[7] = new ReturnBean();
		datas[7].set("month", "08");

		datas[8] = new ReturnBean();
		datas[8].set("month", "09");

		datas[9] = new ReturnBean();
		datas[9].set("month", "10");

		datas[10] = new ReturnBean();
		datas[10].set("month", "11");

		datas[11] = new ReturnBean();
		datas[11].set("month", "12");

		returnValue.set("datas", datas);

		returnValue.setOperationStatus(true);

		return returnValue;
	}

	public PagingLoadResult<ViewAttendanceBeanModel> getTimesheetPaging(final PagingLoadConfig config, String month, String searchBy, String searchValue) {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		List<ViewAttendanceBeanModel> list = new ArrayList<ViewAttendanceBeanModel>();

		ViewAttendanceBean datas[] = null;
		int size = 0;
		try {
			if (SystemUtil.getInstance().access(this.getThreadLocalRequest().getSession(), 98, SystemUtil.ACCESS_VIEW) == false) {
				throw new SystemException("No view access");
			}

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

			if ("".equals(month)) {
				simpleDateFormat = new SimpleDateFormat("yyyy-MM");
				month = simpleDateFormat.format(cal.getTime());
			} else {
				simpleDateFormat = new SimpleDateFormat("yyyy-");
				month = simpleDateFormat.format(cal.getTime()) + month;
			}

			size = ViewAttendanceCustomManager.getInstance().countWhereUnion(tbEmployeeBeanModel.getTbeId(), month);

			datas = ViewAttendanceCustomManager.getInstance().loadByWhereUnion(tbEmployeeBeanModel.getTbeId(), month);

			for (int i = 0; i < datas.length; i++) {
				ViewAttendanceBeanModel data = ViewAttendanceManager.getInstance().toBeanModel(datas[i]);
				list.add(data);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
			ViewAttendanceBeanModel data = new ViewAttendanceBeanModel();
			data.set("messages", e.getMessage());
			list.add(data);
		}

		return new BasePagingLoadResult<ViewAttendanceBeanModel>(list, config.getOffset(), size);
	}
}
