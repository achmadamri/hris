package com.gwt.hris.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.util.ClassUtil;

public class TrackerServlet extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = -5373238476234769590L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = null;
		try {
			Enumeration<String> headerNames = request.getHeaderNames();
			String strTracker = "";
			while (headerNames.hasMoreElements()) {
				String names = headerNames.nextElement();
				strTracker += names + ":" + request.getHeader(names) + "\n";
			}
			log.info("Tracker Servlet :\n" + strTracker);
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