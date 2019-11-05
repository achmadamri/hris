package com.gwt.hris.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PopupWindowServlet extends HttpServlet {
	private static final long serialVersionUID = -2711767323546519436L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("windowName") != null) {
			String strPopupWindow = request.getParameter("windowName");
			request.getSession().setAttribute("windowName", strPopupWindow);
		}
		
		response.sendRedirect("GXT_HRIS.html?gwt.codesvr=127.0.0.1:9997");
	}
}