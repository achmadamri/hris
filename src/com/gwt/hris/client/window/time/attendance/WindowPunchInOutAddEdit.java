package com.gwt.hris.client.window.time.attendance;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
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
	TextArea txtaNote = new TextArea();
	LabelField lblDate = new LabelField();
	LabelField lblShiftName = new LabelField();
	LabelField lblShiftInTime = new LabelField();
	LabelField lblShiftOutTime = new LabelField();
	LabelField lblPunchTime = new LabelField();
	LabelField lblLatitude = new LabelField();
	LabelField lblLongitude = new LabelField();

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
					lblLatitude.setFieldLabel("Latitude In Time");
					lblLongitude.setFieldLabel("Longitude In Time");
				} else {
					strNav = "out";
					window.setHeading("Attendance : Punch Out");
					btnPunchInOut.setText("Punch Out");
					lblPunchTime.setFieldLabel("Punch Out Time");
					lblLatitude.setFieldLabel("Latitude Out Time");
					lblLongitude.setFieldLabel("Longitude Out Time");
				}

				lblDate.setValue(result.get("date"));

				lblShiftName.setValue(result.get("tbsName"));
				lblShiftInTime.setValue(result.get("tbsInTime"));
				lblShiftOutTime.setValue(result.get("tbsOutTime"));
				
				lblLatitude.setValue(lat);
				lblLongitude.setValue(lon);

				lblPunchTime.setValue(result.get("time"));

				txtaComments.clear();
				txtaNote.clear();
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

		lblLatitude.setId("lblLatitude");
		lblLatitude.setFieldLabel("Latitude In Time");
		formPanel.add(lblLatitude, formData);

		lblLongitude.setId("lblLongitude");
		lblLongitude.setFieldLabel("Longitude In Time");
		formPanel.add(lblLongitude, formData);

		txtaComments.setId("txtaComments");
		txtaComments.setFieldLabel("Comments");
		txtaComments.setAllowBlank(true);
		formPanel.add(txtaComments, formData);

		txtaNote.setId("txtaNote");
		txtaNote.setFieldLabel("Note");
		txtaNote.setAllowBlank(true);
		formPanel.add(txtaNote, formData);

		window.add(formPanel);
	}

	Button btnPunchInOut;
	Button btnBack;
	
	public static native float getLat() /*-{
	  return $wnd.lat;
	}-*/;
	
	public static native float getLon() /*-{
	  return $wnd.lon;
	}-*/;

	double lat = 0;
	double lon = 0;
	
	@Override
	public void addButtons() {
		btnPunchInOut = new Button("Punch XXX", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					if (lat == 0 || lon == 0) {
						MessageBox.alert("Alert", "Waiting for geolocation", null);
					} else {
						formPanel.mask("Saving...");
						TbAttendanceBeanModel beanModel = new TbAttendanceBeanModel();

						if ("in".equals(strNav)) {
							beanModel.setTbaInNote(StringUtil.getInstance().getString(txtaComments.getValue()));
							beanModel.setTbaInPhoto(StringUtil.getInstance().getString(txtaNote.getValue()));
							beanModel.setTbaInLatitude(lblLatitude.getText());
							beanModel.setTbaInLongitude(lblLongitude.getText());

							beanModel.set("strNav", "in");
						} else {
							beanModel.setTbaOutNote(StringUtil.getInstance().getString(txtaComments.getValue()));
							beanModel.setTbaOutPhoto(StringUtil.getInstance().getString(txtaNote.getValue()));
							beanModel.setTbaOutLatitude(lblLatitude.getText());
							beanModel.setTbaOutLongitude(lblLongitude.getText());
							
							beanModel.set("strNav", "out");
						}
						interfaceAsync.submitAttendance(beanModel, submitCallback);
					}
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
	
	// Create a new timer that calls Window.alert().
    Timer t = new Timer() {
      @Override
      public void run() {
    	  lblLatitude.setValue(lat);
    	  lblLongitude.setValue(lon);
    	  
    	  if (lat == 0) {
    		  lat = getLat();
    	  }
    	  if (lon == 0) {
    		  lon = getLon();
    	  }
      }
    };
	
	@Override
	public void init() {
		lat = getLat();
		lon = getLon();
		
		interfaceAsync.getPunchStatus("", getPunchStatusCallback);

	    // Schedule the timer to run once in 5 seconds.
	    t.scheduleRepeating(1000);
	}
}
