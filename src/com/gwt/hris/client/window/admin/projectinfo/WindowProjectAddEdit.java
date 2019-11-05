package com.gwt.hris.client.window.admin.projectinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.admin.companyinfo.CompanyPropertyInterface;
import com.gwt.hris.client.service.admin.companyinfo.CompanyPropertyInterfaceAsync;
import com.gwt.hris.client.service.admin.projectinfo.ProjectInterface;
import com.gwt.hris.client.service.admin.projectinfo.ProjectInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCustomerBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbProjectBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowProjectAddEdit extends WindowMain {
	private static WindowProjectAddEdit instance = new WindowProjectAddEdit();

	public static WindowProjectAddEdit getInstance() {
		return instance;
	}

	public WindowProjectAddEdit() {
		window = new Window();
		window.setId("WindowProjectAddEdit");
		window.setSize(550, 400);
		window.setHeading("Project Info : Project");
	}

	public String strNav = "";
	public int id = 0;
	public String strSelectedTab = "";

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	ProjectInterfaceAsync interfaceAsync = GWT.create(ProjectInterface.class);
	CompanyPropertyInterfaceAsync companyPropertyInterfaceAsync = GWT.create(CompanyPropertyInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();
	TextArea txtaDescription = new TextArea();

	TabPanel tabPanel;
	TabItem tabItemProject;
	TabItem tabItemProjectAdministrators;

	RpcProxy<PagingLoadResult<TbEmployeeBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<TbEmployeeBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<TbEmployeeBeanModel> grid;

	Button btnAssignEmployee;

	public int intTbeId = 0;

	ComboBox<ComboBoxData> cmbCustomer = new ComboBox<ComboBoxData>();
	ComboBoxData cmbCustomerSelectedData;
	ListStore<ComboBoxData> cmbCustomerStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfCustomerId = new HiddenField<String>();

	AsyncCallback<TbCustomerBeanModel> tbCustomerAllCallback = new AsyncCallback<TbCustomerBeanModel>() {
		@Override
		public void onSuccess(TbCustomerBeanModel result) {
			if (result.getOperationStatus()) {
				cmbCustomerStore.removeAll();

				for (TbCustomerBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbcId().toString(), obj.getTbcName());
					cmbCustomerStore.add(data);

					if (hfCustomerId.getValue() != null) {
						if (!"".equals(hfCustomerId.getValue())) {
							if (obj.getTbcId() == Integer.parseInt(hfCustomerId.getValue())) {
								cmbCustomerSelectedData = data;
								cmbCustomer.setValue(cmbCustomerSelectedData);
							}
						}
					}
				}

				formPanel.unmask();
				btnEdit.setEnabled(true);
				btnBack.setEnabled(true);
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

	AsyncCallback<ReturnBean> callback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);
				bottomToolBar.refresh();
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

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				interfaceAsync.deleteProjectEmployee(intTbeId, id, callback);
			}
		}
	};

	AsyncCallback<TbProjectBeanModel> getCallback = new AsyncCallback<TbProjectBeanModel>() {
		@Override
		public void onSuccess(TbProjectBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbpId().toString());

				lblCode.setValue(result.getTbpProjectId());
				txtName.setValue(result.getTbpName());
				txtaDescription.setValue(result.getTbpDescription());

				hfCustomerId.setValue(result.getTbcId().toString());

				interfaceAsync.getTbCustomerAll(tbCustomerAllCallback);
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

				TbProjectBeanModel beanModel = (TbProjectBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbpId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window, 0, false);
					doResetComponent(window, 0, false);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);

					interfaceAsync.getTbCustomerAll(tbCustomerAllCallback);
				} else {
					doLockedComponent(window, 0, false);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowProject.getInstance().bottomToolBar.refresh();
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
		tabPanel = new TabPanel();
		tabItemProject = new TabItem("Project");
		tabItemProjectAdministrators = new TabItem("Project Administrators");
		
		tabItemProjectAdministrators.setLayout(new FitLayout());
		tabItemProjectAdministrators.setStyleAttribute("backgroundColor", "#F2F2F2");

		tabItemProject.setStyleAttribute("backgroundColor", "#F2F2F2");

		tabItemProject.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent e) {
				strSelectedTab = "Project";
				window.layout(true);
			}
		});

		tabItemProjectAdministrators.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent e) {
				strSelectedTab = "Project Administrators";
				window.layout(true);
			}
		});

		FormPanel formPanelTab1 = new FormPanel();
		formPanelTab1.setLabelWidth(100);
		formPanelTab1.setHeaderVisible(false);
		formPanelTab1.setFrame(false);
		formPanelTab1.setStyleAttribute("backgroundColor", "#F2F2F2");

		ContentPanel mainPanel = new ContentPanel();
		mainPanel.setHeaderVisible(false);
		mainPanel.setLayout(new RowLayout(Orientation.VERTICAL));

		FormPanel formPanelTab2 = new FormPanel();
		formPanelTab2.setLabelWidth(100);
		formPanelTab2.setHeaderVisible(false);
		formPanelTab2.setFrame(false);
		formPanelTab2.setStyleAttribute("backgroundColor", "#F2F2F2");

		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanelTab1.add(lblCode, formData);

		cmbCustomer.setId("cmbCustomer");
		cmbCustomer.setFieldLabel("Customer *");
		cmbCustomer.setEmptyText("Select");
		cmbCustomer.setDisplayField("value");
		cmbCustomer.setForceSelection(true);
		cmbCustomer.setTypeAhead(true);
		cmbCustomer.setTriggerAction(TriggerAction.ALL);
		cmbCustomer.setStore(cmbCustomerStore);
		cmbCustomer.setAllowBlank(false);
		formPanelTab1.add(cmbCustomer, formData);

		formPanelTab1.add(hfCustomerId, formData);

		txtName.setId("txtTitle");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanelTab1.add(txtName, formData);

		txtaDescription.setId("txtCourse");
		txtaDescription.setFieldLabel("Description");
		txtaDescription.setAllowBlank(true);
		formPanelTab1.add(txtaDescription, formData);

		tabItemProject.add(formPanelTab1);
		tabPanel.add(tabItemProject);

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());
		panel.setFrame(false);
		panel.setBodyBorder(false);
		panel.setBorders(false);
		panel.setStyleAttribute("backgroundColor", "#F2F2F2");

		ToolBar toolBar = new ToolBar();
		bottomToolBar = new PagingToolBar(20);

		btnAssignEmployee = new Button("Assign Employee", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowProjectAssign window = WindowProjectAssign.getInstance();
				window.show("WindowProjectAddEdit", id);
			}
		});
		toolBar.add(btnAssignEmployee);

		panel.setTopComponent(toolBar);

		proxy = new RpcProxy<PagingLoadResult<TbEmployeeBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<TbEmployeeBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				companyPropertyInterfaceAsync.getEmployeePaging(pagingLoadConfig, "tbpId", String.valueOf(id), new AsyncCallback<PagingLoadResult<TbEmployeeBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<TbEmployeeBeanModel> result) {
						if (result.getData().size() > 0) {
							if (result.getData().get(0).get("messages") == null) {
								callback.onSuccess(result);
							} else {
								MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getData().get(0).get("messages").toString(), null);
								callback.onSuccess(null);
							}
						} else {
							callback.onSuccess(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

						caught.printStackTrace();

						callback.onFailure(caught);
					}
				});
			}
		};

		pagingLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
		pagingLoader.setRemoteSort(true);

		pagingStore = new ListStore<TbEmployeeBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbeFirstName", "<center><b>First Name</b></center>", 100));
		columns.add(new ColumnConfig("tbeLastName", "<center><b>Last Name</b></center>", 100));
		columns.add(new ColumnConfig("tbeNickName", "<center><b>Nick Name</b></center>", 100));

		ColumnConfig ccActions = new ColumnConfig();
		ccActions.setId("actions");
		ccActions.setWidth(120);
		ccActions.setHeader("<center><b>Actions</b></center>");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				LayoutContainer layoutContainer = new LayoutContainer();
				HBoxLayout hBoxLayout = new HBoxLayout();
				hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
				hBoxLayout.setPack(BoxLayoutPack.CENTER);
				layoutContainer.setLayout(hBoxLayout);

				HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

				Button btnRemoveAssign = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						intTbeId = Integer.parseInt(model.get("tbeId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete user \"" + model.get("tbeFirstName") + " " + model.get("tbeLastName") + "\" from assigned project administrator ?", deleteListener);
					}
				});
				btnRemoveAssign.setToolTip("assign user");
				btnRemoveAssign.setIcon(Resources.ICONS.user_delete());
				layoutContainer.add(btnRemoveAssign, hBoxLayoutData);

				return layoutContainer;
			}
		});
		columns.add(ccActions);

		ccActions = new ColumnConfig();
		ccActions.setId("space");
		ccActions.setHeader("");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return null;
			}
		});
		columns.add(ccActions);

		ColumnModel cm = new ColumnModel(columns);

		grid = new Grid<TbEmployeeBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<TbEmployeeBeanModel>>() {
			public void handleEvent(GridEvent<TbEmployeeBeanModel> be) {
				PagingLoadConfig config = new BasePagingLoadConfig();
				config.setOffset(0);
				config.setLimit(50);

				Map<String, Object> state = grid.getState();
				if (state.containsKey("offset")) {
					int offset = (Integer) state.get("offset");
					int limit = (Integer) state.get("limit");
					config.setOffset(offset);
					config.setLimit(limit);
				}
				if (state.containsKey("sortField")) {
					config.setSortField((String) state.get("sortField"));
					config.setSortDir(SortDir.valueOf((String) state.get("sortDir")));
				}
				pagingLoader.load(config);
			}
		});
		grid.setLoadMask(true);
		grid.setAutoExpandColumn("space");

		panel.add(grid);

		panel.setBottomComponent(bottomToolBar);

		mainPanel.add(panel, new RowData(1, 1));
		tabItemProjectAdministrators.add(mainPanel);
		tabPanel.add(tabItemProjectAdministrators);

		window.add(tabPanel);
	}

	Button btnSave;
	Button btnEdit;
	Button btnCancel;
	Button btnBack;

	@Override
	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					formPanel.mask("Saving...");
					TbProjectBeanModel beanModel = new TbProjectBeanModel();
					beanModel.setTbpId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbcId(Integer.parseInt(cmbCustomer.getValue().get("id").toString()));
					beanModel.setTbpName(StringUtil.getInstance().getString(txtName.getValue()));
					beanModel.setTbpDescription(StringUtil.getInstance().getString(txtaDescription.getValue()));
					interfaceAsync.submitProject(beanModel, submitCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty or invalid", null);
				}
			}
		});
		window.addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				doUnlockedComponent(window, 0, false);

				btnEdit.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
			}
		});
		window.addButton(btnEdit);

		btnCancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				formPanel.mask("loading");
				btnEdit.setEnabled(false);
				btnBack.setEnabled(false);

				doLockedComponent(window, 0, false);

				interfaceAsync.getProject(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		window.addButton(btnCancel);

		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowProject window = WindowProject.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		strSelectedTab = "Project";
		tabPanel.setSelection(tabItemProject);

		hfCustomerId.setValue("");

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window, 0, false);
			doResetComponent(window, 0, false);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);

			tabItemProjectAdministrators.setEnabled(false);

			interfaceAsync.getTbCustomerAll(tbCustomerAllCallback);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window, 0, false);

			interfaceAsync.getProject(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);

			tabItemProjectAdministrators.setEnabled(true);
		}
	}
}
