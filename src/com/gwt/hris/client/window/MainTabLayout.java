package com.gwt.hris.client.window;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.resources.model.Folder;
import com.gwt.hris.client.service.MainInterface;
import com.gwt.hris.client.service.MainInterfaceAsync;
import com.gwt.hris.client.service.admin.systemsettings.MenuInterface;
import com.gwt.hris.client.service.admin.systemsettings.MenuInterfaceAsync;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbLoginBeanModel;
import com.gwt.hris.client.service.bean.TbMenuBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeInformationBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.window.pim.WindowEss;
import com.gwt.hris.client.window.system.WindowChangePassword;
import com.gwt.hris.client.window.time.attendance.WindowPunchInOutAddEditMinimal;

public class MainTabLayout extends LayoutContainer {
	
	MainInterfaceAsync mainInterfaceAsync = GWT.create(MainInterface.class);

	String strUserName = "";
	String strPassword = "";
	static Map<String, String> tabRegister = new HashMap<String, String>();
	
	ContentPanel westContentPanel = new ContentPanel();
	ContentPanel centerContentPanel = new ContentPanel();
	ContentPanel southContentPanel = new ContentPanel();
	public static TabPanel tabPanel = new TabPanel();
	public static TreePanel<ModelData> treePanel = null;
	
	public static WindowManager windowManager = new WindowManager();
	
	public MainTabLayout(String strUserName, String strPassword) {
		this.strUserName = strUserName;
		this.strPassword = strPassword;
		
		tabItemRegister = new HashMap<String, TabItem>();
	}
	
	public static WindowManager getInstance() {
		return windowManager;
	}

	protected void onRender(Element target, int index) {
		super.onRender(target, index);

		westContentPanel.setLayout(new FitLayout());
		westContentPanel.setHeading("Menu");

		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.setStyleAttribute("padding", "10px");
		this.setStyleAttribute("padding-bottom", "30px");

		centerContentPanel.setBodyBorder(false);
		centerContentPanel.setHeaderVisible(false);
		centerContentPanel.setScrollMode(Scroll.AUTOX);
		centerContentPanel.setLayout(new FitLayout());

		BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 200);
		westData.setSplit(true);
		westData.setCollapsible(true);
		westData.setMargins(new Margins(0, 5, 0, 0));

		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setMargins(new Margins(0));
		
//		BorderLayoutData southData = new BorderLayoutData(LayoutRegion.SOUTH, 25);
//		southData.setSplit(true);
//		southData.setCollapsible(false);
//		southData.setMargins(new Margins(0, 5, 0, 0));

		this.add(westContentPanel, westData);
		this.add(centerContentPanel, centerData);
//		this.add(south, southData);

		tabPanel = new TabPanel();
		tabPanel.setAnimScroll(true);  
		tabPanel.setTabScroll(true);

		centerContentPanel.add(tabPanel);

		TabItem tabItem = new TabItem();
		tabItem.setText("");
		tabItem.addText("");
		tabItem.addStyleName("pad-text");
		tabItem.setClosable(false);
		tabPanel.add(tabItem);
		tabPanel.setSelection(tabItem);

		MainInterfaceAsync mainInterfaceAsync = GWT.create(MainInterface.class);
		mainInterfaceAsync.doLogin(strUserName, strPassword, getLoginCallback);
	}

	AsyncCallback<TbLoginBeanModel> getLoginCallback = new AsyncCallback<TbLoginBeanModel>() {
		@Override
		public void onSuccess(TbLoginBeanModel result) {
			MenuInterfaceAsync menuInterfaceAsync = GWT.create(MenuInterface.class);
			menuInterfaceAsync.getMenuTree(menuInterfaceCallback);
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	AsyncCallback<TbMenuBeanModel> menuInterfaceCallback = new AsyncCallback<TbMenuBeanModel>() {
		@Override
		public void onSuccess(TbMenuBeanModel result) {
			Folder model = (Folder) result.get("root");

			TreeStore<ModelData> store = new TreeStore<ModelData>();
			store.add(model.getChildren(), true);

			treePanel = new TreePanel<ModelData>(store);
			treePanel.setDisplayProperty("name");
			treePanel.getStyle().setLeafIcon(Resources.ICONS.form());
			treePanel.addListener(Events.OnClick, new Listener<TreePanelEvent<ModelData>>() {
				public void handleEvent(TreePanelEvent<ModelData> be) {
					ModelData m = be.getItem();
					windowManager.showWindow((String) m.get("name"));
				};
			});
			
			ContentPanel contentPanel = new ContentPanel();
			contentPanel = new ContentPanel();
			contentPanel.setBodyBorder(false);
			contentPanel.setHeaderVisible(false);
			contentPanel.setLayout(new RowLayout(Orientation.VERTICAL));
			contentPanel.setScrollMode(Scroll.AUTO);
			
			FormData formData = new FormData("100%");
			
			FormPanel formPanel = new FormPanel();
			formPanel.setLabelWidth(100);
			formPanel.setHeaderVisible(false);
			formPanel.setFrame(false);
			formPanel.setStyleAttribute("backgroundColor", "#F2F2F2");
			formPanel.setScrollMode(Scroll.AUTOY);
			
			lblName = new LabelField();
			lblName.setId("lblName");
			lblName.setFieldLabel("Name");
			formPanel.add(lblName, formData);
			
			lblJobTitle = new LabelField();
			lblJobTitle.setId("lblJobStatus");
			lblJobTitle.setFieldLabel("Job Status");
			formPanel.add(lblJobTitle, formData);
			
			btnChangePassword = new Button("", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					WindowChangePassword window = WindowChangePassword.getInstance();
					window.show(false);
				}
			});
			btnChangePassword.setWidth("25px");
			btnChangePassword.setIcon(Resources.ICONS.form());
			btnChangePassword.setToolTip("Change Password");
			formPanel.addButton(btnChangePassword);
			
			btnLogout = new Button("", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					MessageBox.confirm("Confirmation", "Are you sure you want to logut ?", logoutListener);
				}
			});
			btnLogout.setWidth("25px");
			btnLogout.setIcon(Resources.ICONS.cancel());
			btnLogout.setToolTip("Logout");
			formPanel.addButton(btnLogout);

			contentPanel.add(formPanel);
			contentPanel.add(treePanel);
			
			westContentPanel.add(contentPanel);
			
//			south.add(new LabelField("Copyright @ 2018 PT. Dafba Informatika Indonesia"));
			
			employeeListInterfaceAsync.getEmployee(0, getCallback);
			
			windowManager.showWindow("Attendance Minimal");
			
			layout();
		}
		
		public TbLoginBeanModel tbLoginBeanModel = null;
		public ViewEmployeeInformationBeanModel viewEmployeeInformationBeanModel = null;
		LabelField lblName = null;
		LabelField lblJobTitle = null;
		Button btnChangePassword = null;
		Button btnLogout = null;
		
		EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);

		AsyncCallback<TbEmployeeBeanModel> getCallback = new AsyncCallback<TbEmployeeBeanModel>() {
			@Override
			public void onSuccess(TbEmployeeBeanModel result) {
				if (result.getOperationStatus()) {
					tbLoginBeanModel = result.get("tbLoginBeanModel");
					viewEmployeeInformationBeanModel = tbLoginBeanModel.get("viewEmployeeInformationBeanModel");

					lblName.setText(viewEmployeeInformationBeanModel.getTbeName());
					lblJobTitle.setText(viewEmployeeInformationBeanModel.getTbjtName());
					
					mainInterfaceAsync.getPopupWindow(new AsyncCallback<String>() {
						@Override
						public void onSuccess(String result) {
							if (!"".equals(result)) {
								windowManager.showWindow(result);								
							} else {
								mainInterfaceAsync.getTbeId(new AsyncCallback<Integer>() {
									@Override
									public void onSuccess(Integer result) {
										WindowEss window = WindowEss.getInstance();
										window.show(result, 0);
									}

									@Override
									public void onFailure(Throwable caught) {
										MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

										caught.printStackTrace();
									}
								});
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

							caught.printStackTrace();
						}
					});
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

		AsyncCallback<Void> logoutCallback = new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Window.Location.reload();
			}

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

				caught.printStackTrace();
			}
		};

		Listener<MessageBoxEvent> logoutListener = new Listener<MessageBoxEvent>() {
			public void handleEvent(MessageBoxEvent ce) {
				String strBtnText = ce.getButtonClicked().getText();

				if ("Yes".equals(strBtnText)) {
					mainInterfaceAsync.doLogout(logoutCallback);
				}
			}
		};

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};
	
	static HashMap<String, TabItem> tabItemRegister;
	
	public static void closeTab(String window) {
		tabRegister.remove(window);
		MainTabLayout.tabPanel.getSelectedItem().close();
	}

	public static void addTab(ContentPanel window, boolean parent) {
		TabItem tabItem = null;
		
		if (parent) {
			tabItem = tabPanel.getSelectedItem();
			tabItem.removeAll();
			tabItem.setLayout(new FillLayout());
			tabItem.add(window);
			tabItem.setItemId(window.getHeading());
			tabPanel.setSelection(tabItem);
			tabItem.layout();
		} else {
			if (!window.getHeading().equals(tabRegister.get(window.getHeading()))) {
				tabItem = new TabItem(window.getHeading());
				tabItem.setClosable(true);
				tabItem.addListener(Events.BeforeClose, new Listener<TabPanelEvent>() {
					@Override
					public void handleEvent(TabPanelEvent be) {
						tabRegister.remove(be.getItem().getHeader().getText());
					}
				});
				tabItem.setLayout(new FillLayout());
				tabItem.add(window);
				tabItem.setItemId(window.getHeading());
				tabPanel.add(tabItem);
				tabPanel.setSelection(tabItem);
				tabItem.layout();
				
				tabRegister.put(window.getHeading(), window.getHeading());
			}
		}
		tabPanel.setSelection(tabPanel.getItemByItemId(window.getHeading()));
	}
}
