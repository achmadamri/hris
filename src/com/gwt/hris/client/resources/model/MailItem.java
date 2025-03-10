/*
 * Ext GWT 2.2.4 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.gwt.hris.client.resources.model;

import com.extjs.gxt.ui.client.data.BaseModel;

public class MailItem extends BaseModel {

	public MailItem() {

	}

	public MailItem(String sender, String email, String subject) {
		setSender(sender);
		setEmail(email);
		setSubject(subject);
	}

	public MailItem(String sender, String email, String subject, String body) {
		this(sender, email, subject);
		set("body", body);
	}

	public String getSender() {
		return (String) get("sender");
	}

	public void setSender(String sender) {
		set("sender", sender);
	}

	public void setEmail(String email) {
		set("email", email);
	}

	public String getEmail() {
		return (String) get("email");
	}

	public String getBody() {
		return (String) get("body");
	}

	public void setSubject(String subject) {
		set("subject", subject);
	}

	public String getSubject() {
		return (String) get("subject");
	}
}
