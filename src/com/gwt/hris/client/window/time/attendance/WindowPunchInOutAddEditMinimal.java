package com.gwt.hris.client.window.time.attendance;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAttendanceBeanModel;
import com.gwt.hris.client.service.time.attendance.AttendanceInterface;
import com.gwt.hris.client.service.time.attendance.AttendanceInterfaceAsync;
import com.gwt.hris.client.window.WindowMain;

public class WindowPunchInOutAddEditMinimal extends WindowMain {
	private static WindowPunchInOutAddEditMinimal instance = new WindowPunchInOutAddEditMinimal();

	public static WindowPunchInOutAddEditMinimal getInstance() {
		return instance;
	}

	public WindowPunchInOutAddEditMinimal() {
		window = new Window();
		window.setId("WindowPunchInOutAddEditMinimal");
		window.setSize(500, 280);
		window.setHeading("Attendance Minimal : Punch");
	}

	public String strNav = "";

	LabelField lblDate = new LabelField();

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
					btnPunchInOut.setText("Punch In");
				} else {
					strNav = "out";
					btnPunchInOut.setText("Punch Out");
				}

				lblDate.setValue(result.get("date"));
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

		window.add(formPanel);
	}

	Button btnPunchInOut;
	Button btnDetail;
	
	public static native float getLat() /*-{
	  return $wnd.lat;
	}-*/;
	
	public static native float getLon() /*-{
	  return $wnd.lon;
	}-*/;
	
	public static native void getLatVoid() /*-{
	  $wnd.getLat();
	}-*/;
	
	public static native void getLonVoid() /*-{
	  $wnd.getLon();
	}-*/;

	double lat = 0;
	double lon = 0;
	
	@Override
	public void addButtons() {
		btnPunchInOut = new Button("Punch XXX", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
//					if (lat == 0 || lon == 0) {
//						MessageBox.alert("Alert", "Waiting for geolocation", null);
//						getLat();
//						getLon();
//					} else {
						formPanel.mask("Saving...");
						TbAttendanceBeanModel beanModel = new TbAttendanceBeanModel();

						if ("in".equals(strNav)) {
							beanModel.setTbaInLatitude(String.valueOf(lat));
							beanModel.setTbaInLongitude(String.valueOf(lon));

							beanModel.set("strNav", "in");
						} else {
							beanModel.setTbaOutLatitude(String.valueOf(lat));
							beanModel.setTbaOutLongitude(String.valueOf(lon));
							
							beanModel.set("strNav", "out");
						}
						interfaceAsync.submitAttendance(beanModel, submitCallback);
//					}
				} else {
					MessageBox.alert("Alert", "Required field is still empty or invalid", null);
				}
			}
		});
		window.addButton(btnPunchInOut);

		btnDetail = new Button("Detail", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowAttendanceMinimal window = WindowAttendanceMinimal.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnDetail);
	}
	
	// Create a new timer that calls Window.alert().
    Timer t = new Timer() {
      @Override
      public void run() {
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
		getLatVoid();
		getLonVoid();
		
		interfaceAsync.getPunchStatus("", getPunchStatusCallback);

	    // Schedule the timer to run once in 5 seconds.
	    t.scheduleRepeating(1000);
	}
}
