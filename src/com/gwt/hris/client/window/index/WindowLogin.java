package com.gwt.hris.client.window.index;

import java.util.Date;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwt.hris.client.service.MainInterface;
import com.gwt.hris.client.service.MainInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowLogin extends WindowMain {
	private static WindowLogin instance = new WindowLogin();
	
	private static final String STRDBVERSION = "";
	
	private static final String STRAPPVERSION = "";
	

	public static WindowLogin getInstance() {
		return instance;
	}

	MainInterfaceAsync mainInterfaceAsync = GWT.create(MainInterface.class);

	AsyncCallback<ReturnBean> submitLicenseCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				String strLicenseStatus = result.get("license_status");

				if (strLicenseStatus.equals("0")) {
					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
				} else {
					MessageBox.alert("Alert", result.getMessage(), null);

					txtLicense.setVisible(false);
					txtUsername.setVisible(true);
					txtPassword.setVisible(true);

					btnSubmit.setVisible(false);
//					btnLoginHRAdmin.setVisible(true);
					btnReset.setVisible(true);
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

	AsyncCallback<ReturnBean> checkLicenseCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
//			if (result.getOperationStatus()) {
//				String strLicenseStatus = result.get("license_status");
//				
//				String strLicenseString = result.get("license_string");
//				window.setHeading(window.getHeading() + ". " + strLicenseString);
//
//				if (strLicenseStatus.equals("0")) {
//					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
//
//					txtLicense.setVisible(true);
//					txtUsername.setVisible(false);
//					txtPassword.setVisible(false);
//
//					btnSubmit.setVisible(true);
//					btnLogin.setVisible(false);
//					btnReset.setVisible(false);
//				} else {
					txtLicense.setVisible(false);
					txtUsername.setVisible(true);
					txtPassword.setVisible(true);

					btnSubmit.setVisible(false);
//					btnLoginHRAdmin.setVisible(true);
					btnReset.setVisible(true);
//				}
//			} else {
//				MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
//
//				txtLicense.setVisible(true);
//				txtUsername.setVisible(false);
//				txtPassword.setVisible(false);
//
//				btnSubmit.setVisible(true);
//				btnLogin.setVisible(false);
//				btnReset.setVisible(false);
//			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	Status status = new Status();
	
	String strStatusValue = "";

	AsyncCallback<TbLoginBeanModel> getLoginCallback = new AsyncCallback<TbLoginBeanModel>() {
		@Override
		public void onSuccess(TbLoginBeanModel result) {
			if (result.getOperationStatus()) {				
				RootPanel rootPanel = RootPanel.get("main");

				Viewport viewPort = new Viewport();
				viewPort.setLayout(new FitLayout());

				MainTabLayout mainTabLayout = new MainTabLayout(txtUsername.getValue(), txtPassword.getValue());

				viewPort.add(mainTabLayout);

				rootPanel.add(viewPort);

				window.hide();
			} else {
				if (!"Not Login".equals(result.getMessage())) {
					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
					txtUsername.setEnabled(true);
					txtPassword.setEnabled(true);
//					btnLoginHRAdmin.setEnabled(true);
					btnReset.setEnabled(true);
				} else {
					window.show();
				}
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	public WindowLogin() {
		window = new Window();
		window.setId("WindowLogin");
		window.setSize(550, 150);
		window.setHeading("Login");
	}

	public void show() {
		window.setLayout(new FitLayout());
//		window.setClosable(false);
//		window.setResizable(false);
//		window.setMaximizable(false);
//		window.setDraggable(false);

		formPanel.setLabelWidth(100);
		formPanel.setHeaderVisible(false);
		formPanel.setFrame(false);

		addComponents();

		addButtons();

//		window.show();

		init();
		
		mainInterfaceAsync.isLogin(getLoginCallback);
	}

	TextField<String> txtLicense = new TextField<String>();
	TextField<String> txtUsername = new TextField<String>();
	TextField<String> txtPassword = new TextField<String>();

	@Override
	public void addComponents() {
		txtLicense.setId("txtLicense");
		txtLicense.setFieldLabel("License");
		txtLicense.setVisible(false);
		formPanel.add(txtLicense, formData);

		txtUsername.setId("txtUsername");
		txtUsername.setFieldLabel("Username");
		txtUsername.addKeyListener(new KeyListener() {
			public void componentKeyUp(ComponentEvent event) {
				if (event.getKeyCode() == 13) {
//					btnLoginHRAdmin.setEnabled(false);
					btnReset.setEnabled(false);
					mainInterfaceAsync.doLogin(txtUsername.getValue(), txtPassword.getValue(), getLoginCallback);
				}
			}

		});
		formPanel.add(txtUsername, formData);

		txtPassword.setId("txtPassword");
		txtPassword.setFieldLabel("Password");
		txtPassword.setPassword(true);
		txtPassword.addKeyListener(new KeyListener() {
			public void componentKeyUp(ComponentEvent event) {
				if (event.getKeyCode() == 13) {
					txtUsername.setEnabled(false);
					txtPassword.setEnabled(false);
//					btnLoginHRAdmin.setEnabled(false);
					btnReset.setEnabled(false);
					mainInterfaceAsync.doLogin(txtUsername.getValue(), txtPassword.getValue(), getLoginCallback);
				}
			}

		});
		formPanel.add(txtPassword, formData);

		window.add(formPanel);
	}

//	Button btnLoginHRAdmin;
//	Button btnLoginCeo;
//	Button btnLoginDirector;
//	Button btnLoginManager;
//	Button btnLoginStaff;

	Button btnReset;

	Button btnSubmit;

	@Override
	public void addButtons() {
//		btnLoginHRAdmin = new Button("Login HR Admin", new SelectionListener<ButtonEvent>() {
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//				txtUsername.setValue("hradmin1");
//				txtPassword.setValue("hradmin1");
//				txtUsername.setEnabled(false);
//				txtPassword.setEnabled(false);
//				btnLoginHRAdmin.setEnabled(false);
//				btnReset.setEnabled(false);
//				mainInterfaceAsync.doLogin(txtUsername.getValue(), txtPassword.getValue(), getLoginCallback);
//			}
//		});
//		window.addButton(btnLoginHRAdmin);

//		btnLoginCeo = new Button("Login CEO", new SelectionListener<ButtonEvent>() {
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//				txtUsername.setValue("tonystark2");
//				txtPassword.setValue("123");
//				txtUsername.setEnabled(false);
//				txtPassword.setEnabled(false);
//				btnLoginHRAdmin.setEnabled(false);
//				btnReset.setEnabled(false);
//				mainInterfaceAsync.doLogin(txtUsername.getValue(), txtPassword.getValue(), getLoginCallback);
//			}
//		});
//		window.addButton(btnLoginCeo);

//		btnLoginDirector = new Button("Login Director", new SelectionListener<ButtonEvent>() {
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//				txtUsername.setValue("johndoe3");
//				txtPassword.setValue("123");
//				txtUsername.setEnabled(false);
//				txtPassword.setEnabled(false);
//				btnLoginHRAdmin.setEnabled(false);
//				btnReset.setEnabled(false);
//				mainInterfaceAsync.doLogin(txtUsername.getValue(), txtPassword.getValue(), getLoginCallback);
//			}
//		});
//		window.addButton(btnLoginDirector);

//		btnLoginManager = new Button("Login Manager", new SelectionListener<ButtonEvent>() {
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//				txtUsername.setValue("maryjane4");
//				txtPassword.setValue("123");
//				txtUsername.setEnabled(false);
//				txtPassword.setEnabled(false);
//				btnLoginHRAdmin.setEnabled(false);
//				btnReset.setEnabled(false);
//				mainInterfaceAsync.doLogin(txtUsername.getValue(), txtPassword.getValue(), getLoginCallback);
//			}
//		});
//		window.addButton(btnLoginManager);

//		btnLoginStaff = new Button("Login Staff", new SelectionListener<ButtonEvent>() {
//			@Override
//			public void componentSelected(ButtonEvent ce) {
//				txtUsername.setValue("Budi Budiman5");
//				txtPassword.setValue("123");
//				txtUsername.setEnabled(false);
//				txtPassword.setEnabled(false);
//				btnLoginHRAdmin.setEnabled(false);
//				btnReset.setEnabled(false);
//				mainInterfaceAsync.doLogin(txtUsername.getValue(), txtPassword.getValue(), getLoginCallback);
//			}
//		});
//		window.addButton(btnLoginStaff);

		btnReset = new Button("Reset", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				doResetComponent(window);
			}
		});
		window.addButton(btnReset);

		btnSubmit = new Button("Submit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				mainInterfaceAsync.submitLicense(txtLicense.getValue(), submitLicenseCallback);
			}
		});
		btnSubmit.setVisible(false);
		window.addButton(btnSubmit);
	}

	@Override
	public void init() {
//		mainInterfaceAsync.checkLicense(checkLicenseCallback);
//
//		Date data = new Date();
//		DateTimeFormat fmt = DateTimeFormat.getFormat("dd MMMM yyyy");
//		status.setText(fmt.format(data) + ". App version : " + STRAPPVERSION + ". Db version : " + STRDBVERSION + ".");
//		status.setWidth(150);
//
//		ToolBar toolBar = new ToolBar();
//		toolBar.add(status);
//
//		ContentPanel panel = new ContentPanel();
//		panel.setHeaderVisible(false);
//		panel.setBottomComponent(toolBar);
//
//		RootPanel.get("bottom").add(panel);
	}

	Timer timer = new Timer() {
		public void run() {
			Date data = new Date();
			DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm:ss");
			status.setText(fmt.format(data) + ". " + strStatusValue + ". App version : " + STRAPPVERSION + ". Db version : " + STRDBVERSION + ".");
		}
	};
}
