package com.gwt.hris.server.service;

//import java.io.IOException;

//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
//import com.gxt.hris.util.ClassUtil;

public class MainRemoteServiceServlet extends RemoteServiceServlet {
	public Logger log = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = -5434912561704021989L;

//	@Override
//	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
//		HttpServletRequest httpServletRequest = (HttpServletRequest) arg0;
//		ClassUtil.getInstance().logMethod(log, httpServletRequest.getServletPath());
//		
//		super.service(arg0, arg1);
//	}
}
