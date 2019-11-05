package com.gwt.hris.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.db.Manager;
import com.gwt.hris.db.TbPerusahaanBean;
import com.gwt.hris.db.TbPerusahaanManager;
import com.gwt.hris.util.ClassUtil;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ReportServlet extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = -5373286375558819590L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strName = request.getParameter("name");
		
		OutputStream out = response.getOutputStream();
		Connection connection = null;

		try {
			connection = Manager.getInstance().getConnection();

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
			String strFileName = strName + "_" + df.format(new Date());
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "filename=" + strFileName + ".pdf");

			String reportFileName = this.getServletConfig().getServletContext().getRealPath("/reports/" + strName + ".jasper");
			
			@SuppressWarnings("unchecked")
			Map<String, Object> requestParameter = request.getParameterMap();
			Set<String> key = requestParameter.keySet();
			Iterator<String> itr = key.iterator();
			Map<String, Object> parameters = new HashMap<String, Object>();
			while (itr.hasNext()) {
				String strKey = itr.next();
				String data = request.getParameter(strKey);
				parameters.put(strKey, data);
			}
			
			TbPerusahaanBean tbPerusahaanBean = TbPerusahaanManager.getInstance().loadByPrimaryKey(1);
			parameters.put("tbpName", tbPerusahaanBean.getTbpName().toUpperCase());
			
			String SUBREPORT_DIR = this.getServletConfig().getServletContext().getRealPath("/reports");
			parameters.put("SUBREPORT_DIR", SUBREPORT_DIR);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, connection);

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

			exporter.exportReport();
		} catch (Exception e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}

			Manager.getInstance().releaseConnection(connection);
		}
	}
}