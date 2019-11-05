package com.gwt.hris.client.window.admin.users;

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
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.GroupingStore;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.users.AdminUserGroupsInterface;
import com.gwt.hris.client.service.admin.users.AdminUserGroupsInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAdminUserGroupsBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessAdminBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowAdminUserGroupsAddEdit extends WindowMain {
	private static WindowAdminUserGroupsAddEdit instance = new WindowAdminUserGroupsAddEdit();

	public static WindowAdminUserGroupsAddEdit getInstance() {
		return instance;
	}

	public WindowAdminUserGroupsAddEdit() {
		window = new Window();
		window.setId("WindowAdminUserGroupsAddEdit");
		window.setSize(580, 480);
		window.setHeading("Users : Admin User Groups");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	TabPanel tabPanel;
	TabItem tabItemAdminUserGroup;
	TabItem tabItemMenuAccessAdmin;

	AdminUserGroupsInterfaceAsync interfaceAsync = GWT.create(AdminUserGroupsInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();

	AsyncCallback<TbAdminUserGroupsBeanModel> getCallback = new AsyncCallback<TbAdminUserGroupsBeanModel>() {
		@Override
		public void onSuccess(TbAdminUserGroupsBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbaugId().toString());

				lblCode.setValue(result.getTbaugAdminUserGroupsId());
				txtName.setValue(result.getTbaugName());

				formPanel.unmask();

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

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbAdminUserGroupsBeanModel beanModel = (TbAdminUserGroupsBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbaugId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window, 0, false);
					grid.setEnabled(false);
					
					doResetComponent(window, 0, false);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
				} else {
					doLockedComponent(window, 0, false);
					grid.setEnabled(false);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowAdminUserGroups.getInstance().bottomToolBar.refresh();
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

	FormPanel formPanelTab1;
	FormPanel formPanelTab2;

	RpcProxy<PagingLoadResult<ViewMenuAccessAdminBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	GroupingStore<ViewMenuAccessAdminBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(999999);
	EditorGrid<ViewMenuAccessAdminBeanModel> grid;

	@Override
	public void addComponents() {
		tabItemMenuAccessAdmin = new TabItem("Menu Access");
		tabItemMenuAccessAdmin.setLayout(new FitLayout());
		tabItemMenuAccessAdmin.setStyleAttribute("backgroundColor", "#F2F2F2");

		tabItemAdminUserGroup = new TabItem("Admin User Group");
		tabItemAdminUserGroup.setStyleAttribute("backgroundColor", "#F2F2F2");

		tabItemAdminUserGroup.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent e) {
				window.layout(true);
			}
		});

		tabItemMenuAccessAdmin.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent e) {
				window.layout(true);
				bottomToolBar.refresh();
			}
		});

		formPanelTab1 = new FormPanel();
		formPanelTab1.setLabelWidth(100);
		formPanelTab1.setHeaderVisible(false);
		formPanelTab1.setFrame(false);
		formPanelTab1.setStyleAttribute("backgroundColor", "#F2F2F2");

		ContentPanel mainPanel = new ContentPanel();
		mainPanel.setHeaderVisible(false);
		mainPanel.setLayout(new RowLayout(Orientation.VERTICAL));

		formPanelTab2 = new FormPanel();
		formPanelTab2.setLabelWidth(100);
		formPanelTab2.setHeaderVisible(false);
		formPanelTab2.setFrame(false);
		formPanelTab2.setBodyBorder(false);
		formPanelTab2.setBorders(false);
		formPanelTab2.setStyleAttribute("backgroundColor", "#F2F2F2");
		
		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanelTab1.add(lblCode, formData);

		txtName.setId("txtName");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanelTab1.add(txtName, formData);

		tabItemAdminUserGroup.add(formPanelTab1);
		tabPanel = new TabPanel();
		tabPanel.add(tabItemAdminUserGroup);

		mainPanel.add(formPanelTab2, new RowData(1, -1));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewMenuAccessAdminBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewMenuAccessAdminBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getMenuAccessPaging(pagingLoadConfig, "tbaugId", hfId.getValue(), new AsyncCallback<PagingLoadResult<ViewMenuAccessAdminBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewMenuAccessAdminBeanModel> result) {
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

		pagingStore = new GroupingStore<ViewMenuAccessAdminBeanModel>(pagingLoader);
		pagingStore.groupBy("tbmNamaParent");

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbmNama", "<center><b>Menu</b></center>", 170));

		CheckColumnConfig column = new CheckColumnConfig("tbmaaEnabledBoolean", "<center><b>Enabled</b></center>", 55);
		CellEditor checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);
		
		column = new CheckColumnConfig("tbmaaInsertBoolean", "<center><b>Insert</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		column = new CheckColumnConfig("tbmaaUpdateBoolean", "<center><b>Update</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		column = new CheckColumnConfig("tbmaaDeleteBoolean", "<center><b>Delete</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		column = new CheckColumnConfig("tbmaaViewBoolean", "<center><b>View</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		column = new CheckColumnConfig("tbmaaApproveBoolean", "<center><b>Approve</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		ColumnConfig ccActions = new ColumnConfig();
		ccActions.setId("space");
		ccActions.setHeader("");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return null;
			}
		});
		columns.add(ccActions);

		ColumnModel cm = new ColumnModel(columns);

		GroupingView view = new GroupingView();
		view.setShowGroupedColumn(false);
		view.setGroupRenderer(new GridGroupRenderer() {
			public String render(GroupColumnData data) {
				return "Parent : " + data.group;
			}
		});

		grid = new EditorGrid<ViewMenuAccessAdminBeanModel>(pagingStore, cm);
		grid.setView(view);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewMenuAccessAdminBeanModel>>() {
			public void handleEvent(GridEvent<ViewMenuAccessAdminBeanModel> be) {
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
		grid.addPlugin(column);

		panel.add(grid);
		panel.setBottomComponent(bottomToolBar);

		mainPanel.add(panel, new RowData(1, 1));

		tabItemMenuAccessAdmin.add(mainPanel);
		tabPanel.add(tabItemMenuAccessAdmin);

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
					TbAdminUserGroupsBeanModel beanModel = new TbAdminUserGroupsBeanModel();
					beanModel.setTbaugId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbaugName(StringUtil.getInstance().getString(txtName.getValue()));
					
					List<ViewMenuAccessAdminBeanModel> lstViewMenuAccessAdminBeanModel = grid.getStore().getModels();
					beanModel.set("lstViewMenuAccessAdminBeanModel", lstViewMenuAccessAdminBeanModel);
					
					interfaceAsync.submitAdminUserGroups(beanModel, submitCallback);
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
				grid.setEnabled(true);

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
				grid.setEnabled(false);

				interfaceAsync.getAdminUserGroups(id, getCallback);

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
				WindowAdminUserGroups window = WindowAdminUserGroups.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		grid.setEnabled(false);

		tabPanel.setSelection(tabItemAdminUserGroup);
		
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window, 0, false);
			doResetComponent(window, 0, false);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
			
			tabItemMenuAccessAdmin.setEnabled(false);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");

			doLockedComponent(window, 0, false);

			interfaceAsync.getAdminUserGroups(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
			
			tabItemMenuAccessAdmin.setEnabled(true);
		}
	}
}
