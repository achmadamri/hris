package com.gwt.hris.util;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessAdminBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessBeanModel;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbNotificationBean;
import com.gwt.hris.db.TbNotificationManager;
import com.gwt.hris.db.TbNotificationTemplateBean;
import com.gwt.hris.db.TbNotificationTemplateManager;
import com.gwt.hris.db.ViewEmployeeReportToBean;
import com.gwt.hris.db.ViewEmployeeReportToManager;
import com.gwt.hris.db.ViewMenuAccessAdminBean;
import com.gwt.hris.db.ViewMenuAccessAdminManager;
import com.gwt.hris.db.ViewMenuAccessBean;
import com.gwt.hris.db.ViewMenuAccessManager;
import com.gwt.hris.db.exception.DAOException;

public class SystemUtil {
	public Logger log = LoggerFactory.getLogger(this.getClass());

	public static String ACCESS_ENABLED = "ENABLED";
	public static String ACCESS_INSERT = "INSERT";
	public static String ACCESS_UPDATE = "UPDATE";
	public static String ACCESS_DELETE = "DELETE";
	public static String ACCESS_VIEW = "VIEW";
	public static String ACCESS_APPROVE = "APPROVE";
	
	public static int CHANNEL_EMAIL = 1;
	public static int CHANNEL_SMS = 2;
	
	public static int UI_ACTIVITIES = 1;
	public static int UI_ACTIVITY_GROUPS = 2;
	public static int UI_ADD_EMPLOYEE = 3;
	public static int UI_ADD_PAY_PERIOD = 4;
	public static int UI_ADMIN = 5;
	public static int UI_ADMIN_USER_GROUPS = 6;
	public static int UI_APPLICANTS = 7;
	public static int UI_APPROVAL_LEAVE = 8;
	public static int UI_APPROVAL_LOAN = 9;
	public static int UI_APPROVAL_TIMESHEET = 10;
	public static int UI_ATTENDANCE = 11;
	//public static int UI_ATTENDANCE = 12;
	public static int UI_BEGINNING_OF_THE_WEEK = 13;
	public static int UI_BENEFITS = 14;
	public static int UI_BUDGET_RESOURCES_GROUP = 15;
	public static int UI_CHANGE_PASSWORD = 16;
	public static int UI_COMPANY = 17;
	public static int UI_COMPANY_INFO = 18;
	public static int UI_COMPANY_PROPERTY = 19;
	public static int UI_COMPANY_STRUCTURE = 20;
	public static int UI_CONFIGURATION = 21;
	public static int UI_CRON_JOB = 22;
	public static int UI_CURRENCY = 23;
	public static int UI_CUSTOM_FIELDS = 24;
	public static int UI_CUSTOMERS = 25;
	public static int UI_DATA_IMPORT_EXPORT = 26;
	public static int UI_DAYS_OFF = 27;
	public static int UI_DEFINE_CUSTOME_EXPORT = 28;
	public static int UI_DEFINE_CUSTOME_IMPORT = 29;
	public static int UI_DEFINE_DAYS_OFF = 30;
	public static int UI_DEFINE_HSP = 31;
	public static int UI_DEFINE_REPORTS = 32;
	public static int UI_EDUCATION = 33;
	public static int UI_EEO_JOB_CATEGORIES = 34;
	public static int UI_EMAIL_NOTIFICATIONS = 35;
	public static int UI_EMPLOYEE_ATTENDANCE = 36;
	public static int UI_EMPLOYEE_HSP_SUMMARY = 37;
	public static int UI_EMPLOYEE_INFORMATION = 38;
	public static int UI_EMPLOYEE_LEAVE_SUMMARY = 39;
	public static int UI_EMPLOYEE_LIST = 40;
	public static int UI_EMPLOYEE_PAYROLL = 41;
	public static int UI_EMPLOYEE_PPH = 42;
	public static int UI_EMPLOYEE_PTT = 43;
	public static int UI_EMPLOYEE_REPORTS = 44;
	public static int UI_EMPLOYEE_SHIFT = 45;
	public static int UI_EMPLOYEE_TIMESHEET = 46;
	public static int UI_EMPLOYEE_TIMESHEETS = 47;
	public static int UI_EMPLOYMENT_STATUS = 48;
	public static int UI_ESS = 49;
	//public static int UI_ESS = 50;
	public static int UI_ESS_USERS = 51;
	public static int UI_ETHNIC_RACES = 52;
	public static int UI_EXPORT = 53;
	public static int UI_GENERAL = 54;
	public static int UI_HEALTH_SAVING_PLAN = 55;
	public static int UI_HOLIDAYS = 56;
	public static int UI_HR_ADMIN_USERS = 57;
	public static int UI_HSP_EXPENDITURES = 58;
	public static int UI_HSP_PAYMENTS_DUE = 59;
	public static int UI_HSP_USED = 60;
	public static int UI_IMPORT = 61;
	public static int UI_JOB = 62;
	public static int UI_JOB_SPECIFICATIONS = 63;
	public static int UI_JOB_TITLES = 64;
	public static int UI_KPI = 65;
	public static int UI_KPI_APPROVAL_ASSIGN = 66;
	public static int UI_KPI_ASSIGN = 67;
	public static int UI_KPI_EMPLOYEE = 68;
	public static int UI_KPI_GROUPS = 69;
	public static int UI_LANGUAGES = 70;
	public static int UI_LEAVE = 71;
	//public static int UI_LEAVE = 72;
	public static int UI_LEAVE_LIST = 73;
	public static int UI_LEAVE_SUMMARY = 74;
	public static int UI_LEAVE_TYPES = 75;
	public static int UI_LICENSES = 76;
	public static int UI_LOAN = 77;
	public static int UI_LOAN_LIST = 78;
	public static int UI_LOCATIONS = 79;
	public static int UI_LOGOUT = 80;
	public static int UI_MEMBERSHIP_TYPES = 81;
	public static int UI_MEMBERSHIPS = 82;
	//public static int UI_MEMBERSHIPS = 83;
	public static int UI_MENU = 84;
	public static int UI_NATIONALITY = 85;
	public static int UI_NATIONALITY_RACE = 86;
	public static int UI_PAYROLL = 87;
	public static int UI_PAYROLL_SCHEDULE = 88;
	public static int UI_PERFORMANCE = 89;
	//public static int UI_PERFORMANCE = 90;
	public static int UI_PIM = 91;
	public static int UI_PRINT_TIMESHEETS = 92;
	public static int UI_PROJECT_INFO = 93;
	public static int UI_PROJECT_REPORTS = 94;
	public static int UI_PROJECT_TIMESHEET = 95;
	public static int UI_PROJECTS = 96;
	public static int UI_PTKP = 97;
	public static int UI_PTT = 98;
	public static int UI_PUNCH_IN_OUT = 99;
	public static int UI_QUALIFICATION = 100;
	public static int UI_RECRUITMENT = 101;
	public static int UI_RENUMERATION = 102;
	public static int UI_REPORTS = 103;
	public static int UI_SALARY_GRADES = 104;
	public static int UI_SHIFTS = 105;
	public static int UI_SKILLS = 106;
	//public static int UI_SKILLS = 107;
	public static int UI_SUBSCRIBE = 108;
	public static int UI_SYSTEM = 109;
	public static int UI_SYSTEM_SETTINGS = 110;
	public static int UI_TAX = 111;
	public static int UI_TAX_RATE = 112;
	public static int UI_TIME = 113;
	//public static int UI_TIME = 114;
	public static int UI_TIMESHEET = 115;
	public static int UI_TIMESHEETS = 116;
	public static int UI_USERS = 117;
	public static int UI_VACANCIES = 118;
	public static int UI_VIEW_PAYROLL_SCHEDULE = 119;
	public static int UI_VIEW_REPORTS = 120;
	public static int UI_KPI_APPROVAL_SCORING = 121;
	
	private static SystemUtil instance = new SystemUtil();

	public static SystemUtil getInstance() {
		return instance;
	}

	public boolean access(HttpSession httpSession, Integer intTbmId, String strAccess) throws DAOException {
		boolean returnValue = false;
		
		log.debug("access : " + strAccess);

		TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) httpSession.getAttribute("TbLoginBeanModel");

		ViewEmployeeInformationBeanModel viewEmployeeInformationBeanModel = (ViewEmployeeInformationBeanModel) httpSession.getAttribute("ViewEmployeeInformationBeanModel");
		
		if (tbLoginBeanModel != null) {
			if (tbLoginBeanModel.getTbaugId() == null) {
				ViewMenuAccessBean viewMenuAccessBean = ViewMenuAccessManager.getInstance().loadByWhere("where tbm_id = " + intTbmId + " and tbjt_id = " + viewEmployeeInformationBeanModel.getTbjtId())[0];
				ViewMenuAccessBeanModel viewMenuAccessBeanModel = ViewMenuAccessManager.getInstance().toBeanModel(viewMenuAccessBean);

				if (strAccess.equals(SystemUtil.ACCESS_INSERT)) {
					if (viewMenuAccessBeanModel.getTbmaInsert() == 1 && viewMenuAccessBeanModel.getTbmaEnabled() == 1) {
						returnValue = true;
					}
				} else if (strAccess.equals(SystemUtil.ACCESS_UPDATE)) {
					if (viewMenuAccessBeanModel.getTbmaUpdate() == 1 && viewMenuAccessBeanModel.getTbmaEnabled() == 1) {
						returnValue = true;
					}
				} else if (strAccess.equals(SystemUtil.ACCESS_DELETE)) {
					if (viewMenuAccessBeanModel.getTbmaDelete() == 1 && viewMenuAccessBeanModel.getTbmaEnabled() == 1) {
						returnValue = true;
					}
				} else if (strAccess.equals(SystemUtil.ACCESS_VIEW)) {
					if (viewMenuAccessBeanModel.getTbmaView() == 1 && viewMenuAccessBeanModel.getTbmaEnabled() == 1) {
						returnValue = true;
					}
				} else if (strAccess.equals(SystemUtil.ACCESS_APPROVE)) {
					if (viewMenuAccessBeanModel.getTbmaApprove() == 1 && viewMenuAccessBeanModel.getTbmaEnabled() == 1) {
						returnValue = true;
					}
				}
			} else {
				ViewMenuAccessAdminBean viewMenuAccessAdminBean = ViewMenuAccessAdminManager.getInstance().loadByWhere("where tbm_id = " + intTbmId + " and tbaug_id = " + tbLoginBeanModel.getTbaugId())[0];
				ViewMenuAccessAdminBeanModel viewMenuAccessAdminBeanModel = ViewMenuAccessAdminManager.getInstance().toBeanModel(viewMenuAccessAdminBean);

				if (strAccess.equals(SystemUtil.ACCESS_INSERT)) {
					if (viewMenuAccessAdminBeanModel.getTbmaaInsert() == 1 && viewMenuAccessAdminBeanModel.getTbmaaEnabled() == 1) {
						returnValue = true;
					}
				} else if (strAccess.equals(SystemUtil.ACCESS_UPDATE)) {
					if (viewMenuAccessAdminBeanModel.getTbmaaUpdate() == 1 && viewMenuAccessAdminBeanModel.getTbmaaEnabled() == 1) {
						returnValue = true;
					}
				} else if (strAccess.equals(SystemUtil.ACCESS_DELETE)) {
					if (viewMenuAccessAdminBeanModel.getTbmaaDelete() == 1 && viewMenuAccessAdminBeanModel.getTbmaaEnabled() == 1) {
						returnValue = true;
					}
				} else if (strAccess.equals(SystemUtil.ACCESS_VIEW)) {
					if (viewMenuAccessAdminBeanModel.getTbmaaView() == 1 && viewMenuAccessAdminBeanModel.getTbmaaEnabled() == 1) {
						returnValue = true;
					}
				} else if (strAccess.equals(SystemUtil.ACCESS_APPROVE)) {
					if (viewMenuAccessAdminBeanModel.getTbmaaApprove() == 1 && viewMenuAccessAdminBeanModel.getTbmaaEnabled() == 1) {
						returnValue = true;
					}
				}
			}
		}

		return returnValue;
	}

	public boolean notification(int tbncId, int tbntId, int tbnuId, int tbeIdFrom, int tbeIdTo, String strData[]) {
		boolean returnValue = false;

		boolean commit = false;
		try {
			Manager.getInstance().beginTransaction();
			
			TbNotificationTemplateBean tbNotificationTemplateBean = TbNotificationTemplateManager.getInstance().loadByPrimaryKey(tbntId);
			String strTbntmData = new String(tbNotificationTemplateBean.getTbntData());
			
			int i = 1;
			for (String strData_ : strData) {
				strTbntmData = strTbntmData.replace("[FIELD_" + i + "]", strData_);
				i = i + 1;
			}
			
			if (tbeIdTo > 0) {
				TbNotificationBean tbNotificationBean = TbNotificationManager.getInstance().createTbNotificationBean();
				tbNotificationBean.setTbncId(tbncId);
				tbNotificationBean.setTbntId(tbntId);
				tbNotificationBean.setTbnuId(tbnuId);
				tbNotificationBean.setTbeIdFrom(tbeIdFrom);
				tbNotificationBean.setTbeIdTo(tbeIdTo);
				tbNotificationBean.setTbnData(strTbntmData);
				tbNotificationBean.setTbnCreateDate(new Date().getTime());
				TbNotificationManager.getInstance().save(tbNotificationBean);
			} else {
				ViewEmployeeReportToBean beans[] = ViewEmployeeReportToManager.getInstance().loadByWhere("where tbe_id = " + tbeIdFrom + " and tbrt_reporting_method = 0");
				
				for (ViewEmployeeReportToBean beans_ : beans) {
					TbNotificationBean tbNotificationBean = TbNotificationManager.getInstance().createTbNotificationBean();
					tbNotificationBean.setTbncId(tbncId);
					tbNotificationBean.setTbntId(tbntId);
					tbNotificationBean.setTbnuId(tbnuId);
					tbNotificationBean.setTbeIdFrom(tbeIdFrom);
					tbNotificationBean.setTbeIdTo(beans_.getTbeIdSpv());
					tbNotificationBean.setTbnData(strTbntmData);
					tbNotificationBean.setTbnCreateDate(new Date().getTime());
					TbNotificationManager.getInstance().save(tbNotificationBean);
				}
			}
			
			/*
ComplexEmail complexEmail = new ComplexEmail();

		String strRecipients[] = new String[] { "jualan.com.2010@gmail.com" };
		String strSubject = "Subject Testing Saja";
		String strAttachements[] = null;
		String strContent = "<html>testing saja</html>";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		System.out.println(simpleDateFormat.format(new Date()) + " " + "Recipients Count : " + strRecipients.length);
		int i = 1;
		for (String strRecipient : strRecipients) {
			System.out.println(simpleDateFormat.format(new Date()) + " " + i + ". Sending : " + strRecipient);
			complexEmail.mail(strSubject, strRecipient, strAttachements, strContent);
			System.out.println(simpleDateFormat.format(new Date()) + " " + i + ". Sending Succes");
			Thread.sleep(1000);
			i++;
		}
			 */
			
			returnValue = true;
			commit = true;
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			try {
				Manager.getInstance().endTransaction(commit);
			} catch (SQLException e) {
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}
	
	public static void main (String args[]) {
		SystemUtil systemUtil = new SystemUtil();
		
		String strData[] = new String[]{"Achmad Amri", "Nurul Ana", "Miykaal Abrar"};
		
		systemUtil.notification(1, 1, 1, 4, 2, strData);
		
//		try {
//			ViewNotificationBean viewNotificationBeans[] = ViewNotificationManager.getInstance().loadAll();
//			for (ViewNotificationBean viewNotificationBeans_ : viewNotificationBeans) {
//				System.out.println(viewNotificationBeans_);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
