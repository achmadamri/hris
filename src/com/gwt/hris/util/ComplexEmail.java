package com.gwt.hris.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComplexEmail {
	public Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static ComplexEmail instance = new ComplexEmail();

	public static ComplexEmail getInstance() {
		return instance;
	}
	
	public static void main(String[] args) throws Exception {
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
	}

	public void mail(String strSubject, String strTo, String[] strFileAttachments, String strContent) {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", Messages.getString("mail.smtp.host"));
		props.put("mail.smtp.auth", Messages.getString("mail.smtp.auth"));
		props.put("mail.debug", Messages.getString("mail.debug"));
		props.put("mail.smtp.port", Messages.getString("mail.smtp.port"));
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Messages.getString("mail.user"), Messages.getString("mail.password"));
			}
		});

		MimeMessage message = new MimeMessage(session);
		try {
			message.setSentDate(new java.util.Date());

			message.setFrom(new InternetAddress(Messages.getString("From")));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(strTo));
			
			message.setSubject(strSubject);

			Multipart mixedMultiPart = new MimeMultipart("mixed");

			BodyPart htmlBodyPart = new MimeBodyPart();
			
			htmlBodyPart.setContent(strContent, "text/html");

			mixedMultiPart.addBodyPart(htmlBodyPart);

			MimeBodyPart mimeBodyPart;

			if (strFileAttachments != null) {
				for (String strFileAttachment : strFileAttachments) {
					mimeBodyPart = new MimeBodyPart();
					FileDataSource fileDataSource = new FileDataSource(strFileAttachment);
					mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
					mimeBodyPart.setFileName(fileDataSource.getName());
					mixedMultiPart.addBodyPart(mimeBodyPart);
				}
			}

			message.setContent(mixedMultiPart);

			Transport.send(message);
		} catch (MessagingException e) {
			ClassUtil.getInstance().logError(log, e);
			e.printStackTrace();
		}
	}
}
