package com.gwt.hris.client.window.time.attendance;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.time.attendance.AttendanceInterface;
import com.gwt.hris.client.service.time.attendance.AttendanceInterfaceAsync;
import com.gwt.hris.client.window.WindowMain;

public class WindowPunchDetail extends WindowMain {
	private static WindowPunchDetail instance = new WindowPunchDetail();

	public static WindowPunchDetail getInstance() {
		return instance;
	}

	public WindowPunchDetail() {
		window = new Window();
		window.setId("WindowPunchDetail");
		window.setSize(500, 280);
		window.setHeading("Attendance : Punch XXX");
	}

	public String strNav = "";
	public String strDate = "";

	TextArea txtaComments = new TextArea();

	LabelField lblDate = new LabelField();
	LabelField lblShiftName = new LabelField();
	LabelField lblShiftInTime = new LabelField();
	LabelField lblShiftOutTime = new LabelField();
	LabelField lblPunchTime = new LabelField();

	public void show(String strNav, String strDate) {
		this.strNav = strNav;
		this.strDate = strDate;

		super.show(true);
	}

	AttendanceInterfaceAsync interfaceAsync = GWT.create(AttendanceInterface.class);

	AsyncCallback<ReturnBean> getPunchStatusCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				lblDate.setValue(result.get("date"));
				lblShiftName.setValue(result.get("tbsName"));
				lblShiftInTime.setValue(result.get("tbsInTime"));
				lblShiftOutTime.setValue(result.get("tbsOutTime"));

				if ("inDetail".equals(strNav)) {
					window.setHeading("Attendance : Punch In");
					lblPunchTime.setFieldLabel("Punch In Time");
					lblPunchTime.setValue(result.get("tbaInTime"));
					txtaComments.setValue(result.get("tbaInNote") == null ? "" : result.get("tbaInNote").toString());
				} else {
					window.setHeading("Attendance : Punch Out");
					lblPunchTime.setFieldLabel("Punch Out Time");
					lblPunchTime.setValue(result.get("tbaOutTime"));
					txtaComments.setValue(result.get("tbaOutNote") == null ? "" : result.get("tbaOutNote").toString());
				}
			} else {
				MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	@Override
	public void addComponents() {
		formPanel.setLabelWidth(115);

		lblDate.setId("lblDate");
		lblDate.setFieldLabel("Date");
		formPanel.add(lblDate, formData);

		lblShiftName.setId("lblShiftName");
		lblShiftName.setFieldLabel("Shift Name");
		formPanel.add(lblShiftName, formData);

		lblShiftInTime.setId("lblShiftInTime");
		lblShiftInTime.setFieldLabel("Shift In Time");
		formPanel.add(lblShiftInTime, formData);

		lblShiftOutTime.setId("lblShiftOutTime");
		lblShiftOutTime.setFieldLabel("Shift Out Time");
		formPanel.add(lblShiftOutTime, formData);

		lblPunchTime.setId("lblTime");
		lblPunchTime.setFieldLabel("Punch In Time");
		formPanel.add(lblPunchTime, formData);

		txtaComments.setId("txtaComments");
		txtaComments.setFieldLabel("Comments");
		txtaComments.setAllowBlank(true);
		txtaComments.setReadOnly(true);
		formPanel.add(txtaComments, formData);

		window.add(formPanel);
	}

	Button btnBack;

	@Override
	public void addButtons() {
		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowAttendance window = WindowAttendance.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		interfaceAsync.getPunchStatus(strDate, getPunchStatusCallback);
	}
}
