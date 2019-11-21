package com.gwt.hris.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.cron.CronMain;
import com.gwt.hris.db.Manager;
import com.gwt.hris.db.exception.DAOException;

public class LoadOnStartupServlet extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 2273255811036130425L;

	@Override
	public void init() throws ServletException {
		try {
			Manager.getInstance().defaultConfigure();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		CronMain cronMain = new CronMain();
		cronMain.run();
	}
}