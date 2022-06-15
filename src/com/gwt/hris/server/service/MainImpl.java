package com.gwt.hris.server.service;

import java.io.File;
import java.util.Calendar;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import com.gwt.hris.client.service.MainInterface;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;
import com.gwt.hris.db.TbEmployeeBean;
import com.gwt.hris.db.TbEmployeeManager;
import com.gwt.hris.db.TbLoginBean;
import com.gwt.hris.db.TbLoginManager;
import com.gwt.hris.db.ViewEmployeeInformationBean;
import com.gwt.hris.db.ViewEmployeeInformationManager;
import com.gwt.hris.util.ClassUtil;
import com.gwt.hris.util.CryptoMessage;
import com.gwt.hris.util.MD5;

public class MainImpl extends MainRemoteServiceServlet implements MainInterface {
	
	private static final long serialVersionUID = 9190512122764832834L;

	public TbLoginBeanModel doLogin(String strUserName, String strPassword) {
		return doLogin(strUserName, strPassword, null);
	}

	public TbLoginBeanModel doLogin(String strUserName, String strPassword, HttpServletRequest request) {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest() == null ? request : this.getThreadLocalRequest();
		HttpSession httpSession = httpServletRequest.getSession();

		TbLoginBeanModel returnValue = new TbLoginBeanModel();
		//TbLoginBeanModel tbLoginBeanModel = (TbLoginBeanModel) httpSession.getAttribute("TbLoginBeanModel");
		TbLoginBeanModel tbLoginBeanModel = null;

//		String strWhere = "";
		String strWhere = "where tbl_username = '" + strUserName.replaceAll("'", "") + "' and tbl_password = '" + strPassword.replaceAll("'", "") + "' and tbe_id in (select tbe_id from tb_employee where tbe_status = 0)";
		
//		if (tbLoginBeanModel == null) {
//			strWhere = "where tbl_username = '" + strUserName.replaceAll("'", "") + "' and tbl_password = '" + strPassword.replaceAll("'", "") + "' and tbe_id in (select tbe_id from tb_employee where tbe_status = 0)";
//		} else {
//			if (strUserName == null && strPassword == null) {
//				strWhere = "where tbl_username = '" + tbLoginBeanModel.getTblUsername() + "' and tbl_password = '" + tbLoginBeanModel.getTblPassword() + "' and tbe_id in (select tbe_id from tb_employee where tbe_status = 0)";				
//			} else {
//				strWhere = "where tbl_username = '" + strUserName.replaceAll("'", "") + "' and tbl_password = '" + strPassword.replaceAll("'", "") + "' and tbe_id in (select tbe_id from tb_employee where tbe_status = 0)";				
//			}
//		}
		
		try {
			TbLoginBean tbLoginBeans[] = TbLoginManager.getInstance().loadByWhere(strWhere);

			if (tbLoginBeans.length > 0) {
				returnValue = TbLoginManager.getInstance().toBeanModel(tbLoginBeans[0]);

				returnValue.setOperationStatus(true);
				returnValue.setMessage("Login Success");

				httpSession.setAttribute("TbLoginBeanModel", returnValue);

				TbEmployeeBean tbEmployeeBean = TbEmployeeManager.getInstance().loadByPrimaryKey(tbLoginBeans[0].getTbeId());
				TbEmployeeBeanModel tbEmployeeBeanModel = TbEmployeeManager.getInstance().toBeanModel(tbEmployeeBean);
				httpSession.setAttribute("TbEmployeeBeanModel", tbEmployeeBeanModel);

				ViewEmployeeInformationBean viewEmployeeInformationBean = ViewEmployeeInformationManager.getInstance().loadByWhere("where tbe_id = " + tbEmployeeBean.getTbeId())[0];
				ViewEmployeeInformationBeanModel viewEmployeeInformationBeanModel = ViewEmployeeInformationManager.getInstance().toBeanModel(viewEmployeeInformationBean);
				httpSession.setAttribute("ViewEmployeeInformationBeanModel", viewEmployeeInformationBeanModel);

				returnValue.set("tbeName", tbEmployeeBean.getTbeName());
				
				httpSession.setMaxInactiveInterval(24 * 60 * 60); // in seconds
			} else {
				returnValue.setOperationStatus(false);
				returnValue.setMessage("Login Failed");
			}

			return returnValue;
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage(e.getMessage());
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}

	@Override
	public TbLoginBeanModel isLogin() {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession httpSession = httpServletRequest.getSession();
		
		TbLoginBeanModel returnValue = new TbLoginBeanModel();

		if (httpSession.getAttribute("TbLoginBeanModel") != null) {
			returnValue = (TbLoginBeanModel) httpSession.getAttribute("TbLoginBeanModel");

			returnValue.setOperationStatus(true);
			returnValue.setMessage("Login Success");
		} else {
			returnValue.setOperationStatus(false);
			returnValue.setMessage("Not Login");
		}
		
		return returnValue;
	}

	@Override
	public void doLogout() {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.removeAttribute("TbLoginBeanModel");
		httpSession.removeAttribute("TbEmployeeBeanModel");
		httpSession.removeAttribute("ViewEmployeeInformationBeanModel");
		httpSession.removeAttribute("license_key");
		httpSession.removeAttribute("license_value");
		
		httpSession.invalidate();
	}

	@Override
	public Integer getTbeId() {
		TbEmployeeBeanModel tbEmployeeBeanModel = (TbEmployeeBeanModel) this.getThreadLocalRequest().getSession().getAttribute("TbEmployeeBeanModel");

		return tbEmployeeBeanModel.getTbeId();
	}

	@Override
	public ReturnBean checkLicense() {
		ReturnBean returnValue = new ReturnBean();

		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession httpSession = httpServletRequest.getSession();

		try {
			String strLicensePath = this.getServletConfig().getServletContext().getRealPath("/license") + File.separator + "license.dat";

			File f = new File(strLicensePath);
//			if (f.exists()) {
//				String strData = FileUtils.readFileToString(f);
//
//				CryptoMessage cryptoMessage = new CryptoMessage();
//				strData = cryptoMessage.decrypt(strData);
				String strData = "shareware|100";

				StringTokenizer stringTokenizer = new StringTokenizer(strData, "|");

				String strKey = stringTokenizer.nextToken();
				String strValue = stringTokenizer.nextToken();

//				boolean parseLicense = parseLicense(strKey, strValue);
//
//				if (parseLicense) {
//					if (strKey.equals("shareware")) {
						httpSession.setAttribute("license_key", strKey);
						httpSession.setAttribute("license_value", strValue);

						returnValue.setOperationStatus(true);
						returnValue.setMessage("License valid");
						returnValue.set("license_status", "1");
						returnValue.set("license_string", strData);
//					} else {
//						Calendar cal = Calendar.getInstance();
//						if ((cal.get(Calendar.MONTH) + 1) <= Integer.valueOf(strValue)) {
//							returnValue.setOperationStatus(true);
//							returnValue.setMessage("License valid");
//							returnValue.set("license_status", "1");
//							returnValue.set("license_string", strData);
//						} else {
//							returnValue.setOperationStatus(true);
//							returnValue.setMessage("License expired");
//							returnValue.set("license_status", "0");
//							returnValue.set("license_string", strData);
//						}
//					}
//				} else {
//					returnValue.setOperationStatus(true);
//					returnValue.setMessage("License error");
//					returnValue.set("license_status", "0");
//				}
//			} else {
//				returnValue.setOperationStatus(true);
//				returnValue.setMessage("License not found");
//				returnValue.set("license_status", "0");
//			}
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage("License error");
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}

	@Override
	public ReturnBean submitLicense(String strLicense) {
		ReturnBean returnValue = new ReturnBean();

		try {
			String strData = "";

			CryptoMessage cryptoMessage = new CryptoMessage();
//			strData = cryptoMessage.decrypt(strLicense);

			StringTokenizer stringTokenizer = new StringTokenizer(strData, "|");

			String strKey = stringTokenizer.nextToken();
			String strValue = stringTokenizer.nextToken();

			boolean parseLicense = parseLicense(strKey, strValue);

			if (parseLicense) {
				String strLicensePath = this.getServletConfig().getServletContext().getRealPath("/license") + File.separator + "license.dat";
				FileUtils.writeStringToFile(new File(strLicensePath), strLicense);

				if (strKey.equals("shareware")) {
					returnValue.setOperationStatus(true);
					returnValue.setMessage("License valid");
					returnValue.set("license_status", "1");
				} else {
					Calendar cal = Calendar.getInstance();
					if ((cal.get(Calendar.MONTH) + 1) <= Integer.valueOf(strValue)) {
						returnValue.setOperationStatus(true);
						returnValue.setMessage("License valid");
						returnValue.set("license_status", "1");
					} else {
						returnValue.setOperationStatus(true);
						returnValue.setMessage("License expired");
						returnValue.set("license_status", "0");
					}
				}
			} else {
				returnValue.setOperationStatus(true);
				returnValue.setMessage("License error");
				returnValue.set("license_status", "0");
			}
		} catch (Exception e) {
			returnValue.setOperationStatus(false);
			returnValue.setMessage("License error");
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}

		return returnValue;
	}

	public boolean parseLicense(String strKey, String strValue) {
		boolean returnValue = false;

		if (strKey.equals("shareware") || strKey.equals("demo")) {
			try {
				Integer.parseInt(strValue);
				returnValue = true;
			} catch (Exception e) {
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		return returnValue;
	}
	
	public String getPopupWindow() {
		String returnValue = "";
		
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession httpSession = httpServletRequest.getSession();
		returnValue = httpSession.getAttribute("windowName") == null ? "" : (String) httpSession.getAttribute("windowName");
		
		return returnValue;
	}
}
