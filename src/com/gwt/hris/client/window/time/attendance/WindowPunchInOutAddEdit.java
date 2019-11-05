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
import com.gwt.hris.client.service.bean.TbAttendanceBeanModel;
import com.gwt.hris.client.service.time.attendance.AttendanceInterface;
import com.gwt.hris.client.service.time.attendance.AttendanceInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowPunchInOutAddEdit extends WindowMain {
	private static WindowPunchInOutAddEdit instance = new WindowPunchInOutAddEdit();

	public static WindowPunchInOutAddEdit getInstance() {
		return instance;
	}

	public WindowPunchInOutAddEdit() {
		window = new Window();
		window.setId("WindowPunchInAddEdit");
		window.setSize(500, 280);
		window.setHeading("Attendance : Punch XXX");
	}

	public String strNav = "";

	TextArea txtaComments = new TextArea();

	LabelField lblDate = new LabelField();
	LabelField lblShiftName = new LabelField();
	LabelField lblShiftInTime = new LabelField();
	LabelField lblShiftOutTime = new LabelField();
	LabelField lblPunchTime = new LabelField();

	public void show() {
		super.show(true);
	}

	AttendanceInterfaceAsync interfaceAsync = GWT.create(AttendanceInterface.class);

	AsyncCallback<ReturnBean> getPunchStatusCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				if ("true".equals(result.get("in"))) {
					strNav = "in";
					window.setHeading("Attendance : Punch In");
					btnPunchInOut.setText("Punch In");
					lblPunchTime.setFieldLabel("Punch In Time");
				} else {
					strNav = "out";
					window.setHeading("Attendance : Punch Out");
					btnPunchInOut.setText("Punch Out");
					lblPunchTime.setFieldLabel("Punch Out Time");
				}

				lblDate.setValue(result.get("date"));

				lblShiftName.setValue(result.get("tbsName"));
				lblShiftInTime.setValue(result.get("tbsInTime"));
				lblShiftOutTime.setValue(result.get("tbsOutTime"));

				lblPunchTime.setValue(result.get("time"));

				txtaComments.clear();
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

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				formPanel.unmask();
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				interfaceAsync.getPunchStatus("", getPunchStatusCallback);

				WindowAttendance.getInstance().btnPunchInOut.setText("Punch Out");

				WindowAttendance.getInstance().bottomToolBar.refresh();
			} else {
				MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			formPanel.unmask();
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
		formPanel.add(txtaComments, formData);

		window.add(formPanel);
	}

	Button btnPunchInOut;
	Button btnBack;

	@Override
	public void addButtons() {
		btnPunchInOut = new Button("Punch XXX", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					formPanel.mask("Saving...");
					TbAttendanceBeanModel beanModel = new TbAttendanceBeanModel();

					if ("in".equals(strNav)) {
						beanModel.setTbaInNote(StringUtil.getInstance().getString(txtaComments.getValue()));

						beanModel.set("strNav", "in");
					} else {
						beanModel.setTbaOutNote(StringUtil.getInstance().getString(txtaComments.getValue()));

						beanModel.set("strNav", "out");
					}
					interfaceAsync.submitAttendance(beanModel, submitCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty or invalid", null);
				}
			}
		});
		window.addButton(btnPunchInOut);

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
		interfaceAsync.getPunchStatus("", getPunchStatusCallback);
	}
}
