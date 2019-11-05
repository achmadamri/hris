package com.gwt.hris.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwt.hris.util.ClassUtil;

public class ResumeUploadServlet extends HttpServlet {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 1809678381772181314L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String strFilename = "";

		if (ServletFileUpload.isMultipartContent(request)) {
			FileItemFactory fileItemFactory = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
			try {
				List<FileItem> list = servletFileUpload.parseRequest(request);
				String folder = getServletContext().getRealPath("upload/resume");
				for (FileItem fileItem : list) {
					if (!fileItem.isFormField()) {
						if (fileItem.getSize() < (1024 * 1000)) {
							String fileName = fileItem.getName();
							String ext = "";
							int mid = fileName.lastIndexOf(".");
							ext = fileName.substring(mid + 1, fileName.length());

							if (ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx") || ext.equalsIgnoreCase("txt") || ext.equalsIgnoreCase("pdf")) {
								File file = new File(folder, generateString(25) + "." + ext.toLowerCase());
								FileOutputStream fileOutputStream = new FileOutputStream(file);
								fileOutputStream.write(fileItem.get());
								fileOutputStream.close();

								strFilename = file.getName();

								out.println("filename:" + strFilename);
							} else {
								out.println("File type not allowed");
							}
						} else {
							out.println("File size should not exceed 1mb");
						}
					}
				}
			} catch (FileUploadException e) {
				ClassUtil.getInstance().logError(log, e);
				e.printStackTrace();
			}
		}

		out.flush();
		out.close();
	}

	public String generateString(int length) {
		String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
		Random rnd = new Random();
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rnd.nextInt(characters.length()));
		}
		return new String(text);
	}
}