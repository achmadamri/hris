package com.gwt.hris.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.servlet.bo.excel.EmployeeInformation;
import com.gwt.hris.servlet.bo.excel.EmployeeReportAttendance;
import com.gwt.hris.servlet.bo.excel.EmployeeReportPayroll;
import com.gwt.hris.servlet.bo.excel.EmployeeReportPph;
import com.gwt.hris.servlet.bo.excel.EmployeeTimesheet;
import com.gwt.hris.util.ClassUtil;

public class ExcelServlet extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = -5373286375558819590L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = null;
		try {
			String strName = request.getParameter("name");

			String searchBy = request.getParameter("searchkey");
			String searchValue = request.getParameter("searchvalue");

			if ("employeeinformation".equals(strName)) {
				EmployeeInformation bo = new EmployeeInformation();
				bo.process(request, response, searchBy, searchValue);
			} else if ("employeereportattendance".equals(strName)) {
				EmployeeReportAttendance bo = new EmployeeReportAttendance();
				bo.process(request, response, searchBy, searchValue);
			} else if ("employeereportpph".equals(strName)) {
				EmployeeReportPph bo = new EmployeeReportPph();
				bo.process(request, response, searchBy, searchValue);
			} else if ("employeereportpayroll".equals(strName)) {
				EmployeeReportPayroll bo = new EmployeeReportPayroll();
				bo.process(request, response, searchBy, searchValue);
			} else if ("employeetimesheet".equals(strName)) {
				EmployeeTimesheet bo = new EmployeeTimesheet();
				bo.process(request, response, searchBy, searchValue);
			}
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}