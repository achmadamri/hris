package com.gwt.hris.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.cron.CronMain;
import com.gwt.hris.db.Manager;

public class LoadOnStartupServlet extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 2273255811036130425L;

	@Override
	public void init() throws ServletException {
		BasicDataSource dataSource = new BasicDataSource();

		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("P@55w0rd");
		dataSource.setUrl("jdbc:mysql://localhost:3306/hris?autoReconnect=true");
		dataSource.setMaxActive(1000);
		dataSource.setMaxIdle(5);
		dataSource.setInitialSize(5);
		dataSource.setValidationQuery("SELECT 1");
		
		Manager.getInstance().setDataSource(dataSource);
//		try {
//			Manager.getInstance().defaultConfigure();
//		} catch (DAOException e) {
//			e.printStackTrace();
//		}
		
		CronMain cronMain = new CronMain();
		cronMain.run();
	}
}