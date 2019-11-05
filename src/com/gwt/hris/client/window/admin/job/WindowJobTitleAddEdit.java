package com.gwt.hris.client.window.admin.job;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.Scroll;
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
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DualListField;
import com.extjs.gxt.ui.client.widget.form.DualListField.Mode;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
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
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.job.JobTitleInterface;
import com.gwt.hris.client.service.admin.job.JobTitleInterfaceAsync;
import com.gwt.hris.client.service.admin.job.PaygradeInterface;
import com.gwt.hris.client.service.admin.job.PaygradeInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmploymentStatusBeanModel;
import com.gwt.hris.client.service.bean.TbJobSpecificationsBeanModel;
import com.gwt.hris.client.service.bean.TbJobTitleBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbRenumerationBeanModel;
import com.gwt.hris.client.service.bean.ViewMenuAccessBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowJobTitleAddEdit extends WindowMain {
	private static WindowJobTitleAddEdit instance = new WindowJobTitleAddEdit();

	public static WindowJobTitleAddEdit getInstance() {
		return instance;
	}

	public WindowJobTitleAddEdit() {
		window = new Window();
		window.setId("WindowJobTitleAddEdit");
		window.setSize(580, 480);
		window.setHeading("Job : Job Title");
	}

	public String strNav = "";
	public int intTbjtId = 0;

	public void show(String strNav, int intTbesId) {
		this.strNav = strNav;
		this.intTbjtId = intTbesId;

		super.show(true);
	}

	TabPanel tabPanel = new TabPanel();
	TabItem tabItemJobTitle = new TabItem("Job Title");
	TabItem tabItemMenuAccess = new TabItem("Menu Access");
	TabItem tabItemJobTitleAddEditLeaveRule = new TabItem("Leave Rule");

	PaygradeInterfaceAsync paygradeInterfaceAsync = GWT.create(PaygradeInterface.class);

	JobTitleInterfaceAsync jobTitleInterfaceAsync = GWT.create(JobTitleInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();
	TextArea txtaDescription = new TextArea();
	TextArea txtaComments = new TextArea();

	ComboBox<ComboBoxData> cmbJobSpecification = new ComboBox<ComboBoxData>();
	ComboBoxData selectedDataJobSpecification;
	ListStore<ComboBoxData> cmbJobSpecificationStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfJobSpecificationId = new HiddenField<String>();

	ComboBox<ComboBoxData> cmbPaygrade = new ComboBox<ComboBoxData>();
	ComboBoxData selectedDataPaygrade;
	ListStore<ComboBoxData> cmbPaygradeStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfPaygradeId = new HiddenField<String>();

	DualListField<ComboBoxData> listsEmployeeStatus = new DualListField<ComboBoxData>();
	ListField<ComboBoxData> fromEmployeeStatus = new ListField<ComboBoxData>();
	ListStore<ComboBoxData> storeFromEmployeeStatus = new ListStore<ComboBoxData>();
	ListField<ComboBoxData> toEmployeeStatus = new ListField<ComboBoxData>();
	ListStore<ComboBoxData> storeToEmployeeStatus = new ListStore<ComboBoxData>();

	DualListField<ComboBoxData> listsRenumeration = new DualListField<ComboBoxData>();
	ListField<ComboBoxData> fromRenumeration = new ListField<ComboBoxData>();
	ListStore<ComboBoxData> storeFromRenumeration = new ListStore<ComboBoxData>();
	ListField<ComboBoxData> toRenumeration = new ListField<ComboBoxData>();
	ListStore<ComboBoxData> storeToRenumeration = new ListStore<ComboBoxData>();

	TextField<String> txtLeaveEntitled = new TextField<String>();

	AsyncCallback<TbEmploymentStatusBeanModel> getTbEmploymentStatusAllCallbackStoreFrom = new AsyncCallback<TbEmploymentStatusBeanModel>() {
		@Override
		public void onSuccess(TbEmploymentStatusBeanModel result) {
			if (result.getOperationStatus()) {
				storeFromEmployeeStatus.removeAll();

				for (TbEmploymentStatusBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbesId().toString(), obj.getTbesName());
					storeFromEmployeeStatus.add(data);
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

	AsyncCallback<TbRenumerationBeanModel> getTbRenumerationAllCallbackStoreFrom = new AsyncCallback<TbRenumerationBeanModel>() {
		@Override
		public void onSuccess(TbRenumerationBeanModel result) {
			if (result.getOperationStatus()) {
				storeFromRenumeration.removeAll();

				for (TbRenumerationBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbrId().toString(), obj.getTbrName());
					storeFromRenumeration.add(data);
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

	AsyncCallback<TbEmploymentStatusBeanModel> getTbEmploymentStatusAllCallbackStoreTo = new AsyncCallback<TbEmploymentStatusBeanModel>() {
		@Override
		public void onSuccess(TbEmploymentStatusBeanModel result) {
			if (result.getOperationStatus()) {
				storeToEmployeeStatus.removeAll();

				for (TbEmploymentStatusBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbesId().toString(), obj.getTbesName());
					storeToEmployeeStatus.add(data);
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

	AsyncCallback<TbRenumerationBeanModel> getTbRenumerationAllCallbackStoreTo = new AsyncCallback<TbRenumerationBeanModel>() {
		@Override
		public void onSuccess(TbRenumerationBeanModel result) {
			if (result.getOperationStatus()) {
				storeToRenumeration.removeAll();

				for (TbRenumerationBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbrId().toString(), obj.getTbrName());
					storeToRenumeration.add(data);
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

	AsyncCallback<TbJobSpecificationsBeanModel> getTbJobSpecificationsAllCallback = new AsyncCallback<TbJobSpecificationsBeanModel>() {
		@Override
		public void onSuccess(TbJobSpecificationsBeanModel result) {
			if (result.getOperationStatus()) {
				cmbJobSpecificationStore.removeAll();

				for (TbJobSpecificationsBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbjsId().toString(), obj.getTbjsName());
					cmbJobSpecificationStore.add(data);

					if (hfJobSpecificationId.getValue() != null) {
						if (!"".equals(hfJobSpecificationId.getValue())) {
							if (obj.getTbjsId() == Integer.parseInt(hfJobSpecificationId.getValue())) {
								selectedDataJobSpecification = data;
								cmbJobSpecification.setValue(selectedDataJobSpecification);
							}
						}
					}
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

	AsyncCallback<TbPaygradeBeanModel> getTbPaygradeAllCallback = new AsyncCallback<TbPaygradeBeanModel>() {
		@Override
		public void onSuccess(TbPaygradeBeanModel result) {
			if (result.getOperationStatus()) {
				cmbPaygradeStore.removeAll();

				for (TbPaygradeBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpId().toString(), obj.getTbpName());
					cmbPaygradeStore.add(data);

					if (hfPaygradeId.getValue() != null) {
						if (!"".equals(hfPaygradeId.getValue())) {
							if (obj.getTbpId() == Integer.parseInt(hfPaygradeId.getValue())) {
								selectedDataPaygrade = data;
								cmbPaygrade.setValue(selectedDataPaygrade);
							}
						}
					}
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

	AsyncCallback<TbJobTitleBeanModel> getJobTitleCallback = new AsyncCallback<TbJobTitleBeanModel>() {
		@Override
		public void onSuccess(TbJobTitleBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbjtId().toString());

				lblCode.setValue(result.getTbjtJobTitleId());
				txtName.setValue(result.getTbjtName());
				txtaDescription.setValue(result.getTbjtDescription());
				txtaComments.setValue(result.getTbjtComments());

				if (result.getTbjsId() != null)
					hfJobSpecificationId.setValue(result.getTbjsId().toString());

				if (result.getTbpId() != null)
					hfPaygradeId.setValue(result.getTbpId().toString());

				jobTitleInterfaceAsync.getTbJobSpecificationsAll(getTbJobSpecificationsAllCallback);
				jobTitleInterfaceAsync.getTbPaygradeAll(getTbPaygradeAllCallback);

				jobTitleInterfaceAsync.getTbEmploymentStatusByNotTbjtId(result.getTbjtId(), getTbEmploymentStatusAllCallbackStoreFrom);
				jobTitleInterfaceAsync.getTbEmploymentStatusByTbjtId(result.getTbjtId(), getTbEmploymentStatusAllCallbackStoreTo);

				jobTitleInterfaceAsync.getTbRenumerationByNotTbjtId(result.getTbjtId(), getTbRenumerationAllCallbackStoreFrom);
				jobTitleInterfaceAsync.getTbRenumerationByTbjtId(result.getTbjtId(), getTbRenumerationAllCallbackStoreTo);

				txtLeaveEntitled.setValue(result.getTbjtLeaveEntitled().toString());

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

	AsyncCallback<ReturnBean> submitJobTitleCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbJobTitleBeanModel tbJobTitleBeanModel = (TbJobTitleBeanModel) result.get("model");
				hfId.setValue(tbJobTitleBeanModel.getTbjtId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window, 0, false);
					grid.setEnabled(false);

					doResetComponent(window, 0, false);

					storeFromEmployeeStatus.removeAll();
					storeToEmployeeStatus.removeAll();

					storeFromRenumeration.removeAll();
					storeToRenumeration.removeAll();

					jobTitleInterfaceAsync.getTbJobSpecificationsAll(getTbJobSpecificationsAllCallback);
					jobTitleInterfaceAsync.getTbPaygradeAll(getTbPaygradeAllCallback);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);

					jobTitleInterfaceAsync.getTbEmploymentStatusAll(getTbEmploymentStatusAllCallbackStoreFrom);

					jobTitleInterfaceAsync.getTbRenumerationAll(getTbRenumerationAllCallbackStoreFrom);
				} else {
					doLockedComponent(window, 0, false);
					
					grid.setEnabled(false);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowJobTitle.getInstance().bottomToolBar.refresh();
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

	RpcProxy<PagingLoadResult<ViewMenuAccessBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	GroupingStore<ViewMenuAccessBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(999999);
	EditorGrid<ViewMenuAccessBeanModel> grid;

	@Override
	public void addComponents() {
		tabItemMenuAccess = new TabItem("Menu Access");
		tabItemMenuAccess.setLayout(new FitLayout());
		tabItemMenuAccess.setStyleAttribute("backgroundColor", "#F2F2F2");

		tabItemJobTitle = new TabItem("Job Title");
		tabItemJobTitle.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemJobTitle.setScrollMode(Scroll.AUTOY);

		tabItemJobTitle.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent e) {
				window.layout(true);
				
				if (intTbjtId == 0) {
					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
				} else {
					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);					
				}
			}
		});

		tabItemMenuAccess.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent e) {
				window.layout(true);
				bottomToolBar.refresh();
				
				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
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

		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanelTab1.add(lblCode, formData);

		txtName.setId("txtName");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanelTab1.add(txtName, formData);

		txtaDescription.setId("txtaDescription");
		txtaDescription.setFieldLabel("Description *");
		txtaDescription.setAllowBlank(false);
		formPanelTab1.add(txtaDescription, formData);

		txtaComments.setId("txtaComments");
		txtaComments.setFieldLabel("Comments");
		txtaComments.setAllowBlank(true);
		formPanelTab1.add(txtaComments, formData);

		cmbJobSpecification.setId("cmbJobSpecification");
		cmbJobSpecification.setFieldLabel("Job Spec. *");
		cmbJobSpecification.setEmptyText("Select");
		cmbJobSpecification.setDisplayField("value");
		cmbJobSpecification.setForceSelection(true);
		cmbJobSpecification.setTypeAhead(true);
		cmbJobSpecification.setTriggerAction(TriggerAction.ALL);
		cmbJobSpecification.setStore(cmbJobSpecificationStore);
		cmbJobSpecification.setAllowBlank(false);
		formPanelTab1.add(cmbJobSpecification, formData);

		cmbPaygrade.setId("cmbPaygrade");
		cmbPaygrade.setFieldLabel("Salary Grade *");
		cmbPaygrade.setEmptyText("Select");
		cmbPaygrade.setDisplayField("value");
		cmbPaygrade.setForceSelection(true);
		cmbPaygrade.setTypeAhead(true);
		cmbPaygrade.setTriggerAction(TriggerAction.ALL);
		cmbPaygrade.setStore(cmbPaygradeStore);
		cmbPaygrade.setAllowBlank(false);
		formPanelTab1.add(cmbPaygrade, formData);

		txtLeaveEntitled.setId("txtLeaveEntitled");
		txtLeaveEntitled.setFieldLabel("Leave Entitled *");
		txtLeaveEntitled.setAllowBlank(false);
		formPanelTab1.add(txtLeaveEntitled, formData);

		listsEmployeeStatus.setMode(Mode.INSERT);
		listsEmployeeStatus.setFieldLabel("Employment Status");
		fromEmployeeStatus = listsEmployeeStatus.getFromList();
		fromEmployeeStatus.setDisplayField("value");
		fromEmployeeStatus.setStore(storeFromEmployeeStatus);
		toEmployeeStatus = listsEmployeeStatus.getToList();
		toEmployeeStatus.setDisplayField("value");
		toEmployeeStatus.setStore(storeToEmployeeStatus);
		formPanelTab1.add(listsEmployeeStatus, formData);

		listsRenumeration.setMode(Mode.INSERT);
		listsRenumeration.setFieldLabel("Renumeration");
		fromRenumeration = listsRenumeration.getFromList();
		fromRenumeration.setDisplayField("value");
		fromRenumeration.setStore(storeFromRenumeration);
		toRenumeration = listsRenumeration.getToList();
		toRenumeration.setDisplayField("value");
		toRenumeration.setStore(storeToRenumeration);
		formPanelTab1.add(listsRenumeration, formData);

		formPanelTab1.add(hfJobSpecificationId, formData);

		tabItemJobTitle.add(formPanelTab1);
		tabPanel = new TabPanel();
		tabPanel.add(tabItemJobTitle);

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewMenuAccessBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewMenuAccessBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				jobTitleInterfaceAsync.getMenuAccessPaging(pagingLoadConfig, "tbjtId", hfId.getValue(), new AsyncCallback<PagingLoadResult<ViewMenuAccessBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewMenuAccessBeanModel> result) {
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

		pagingStore = new GroupingStore<ViewMenuAccessBeanModel>(pagingLoader);
		pagingStore.groupBy("tbmNamaParent");

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbmNama", "<center><b>Menu</b></center>", 170));

		CheckColumnConfig column = new CheckColumnConfig("tbmaEnabledBoolean", "<center><b>Enabled</b></center>", 55);
		CellEditor checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);
		
		column = new CheckColumnConfig("tbmaInsertBoolean", "<center><b>Insert</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		column = new CheckColumnConfig("tbmaUpdateBoolean", "<center><b>Update</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		column = new CheckColumnConfig("tbmaDeleteBoolean", "<center><b>Delete</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		column = new CheckColumnConfig("tbmaViewBoolean", "<center><b>View</b></center>", 55);
		checkBoxEditor = new CellEditor(new CheckBox());
		column.setEditor(checkBoxEditor);
		columns.add(column);

		column = new CheckColumnConfig("tbmaApproveBoolean", "<center><b>Approve</b></center>", 55);
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

		grid = new EditorGrid<ViewMenuAccessBeanModel>(pagingStore, cm);
		grid.setView(view);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewMenuAccessBeanModel>>() {
			public void handleEvent(GridEvent<ViewMenuAccessBeanModel> be) {
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

		tabItemMenuAccess.add(mainPanel);
		tabPanel.add(tabItemMenuAccess);
		
		tabItemJobTitleAddEditLeaveRule = new TabItem("Leave Rule");
		tabItemJobTitleAddEditLeaveRule.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemJobTitleAddEditLeaveRule.setLayout(new FillLayout());
		tabItemJobTitleAddEditLeaveRule.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent be) {
				tabItemJobTitleAddEditLeaveRule.removeAll();
				tabItemJobTitleAddEditLeaveRule.add(new JobTitleLeaveRule(formData, intTbjtId, 0));
				
				btnEdit.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
				
				window.layout(true);
			}
		});
		tabPanel.add(tabItemJobTitleAddEditLeaveRule);

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
					TbJobTitleBeanModel tbJobTitleBeanModel = new TbJobTitleBeanModel();
					tbJobTitleBeanModel.setTbjtId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					tbJobTitleBeanModel.setTbjtName(StringUtil.getInstance().getString(txtName.getValue()));
					tbJobTitleBeanModel.setTbjtDescription(StringUtil.getInstance().getString(txtaDescription.getValue()));
					tbJobTitleBeanModel.setTbjtComments(StringUtil.getInstance().getString(txtaComments.getValue()));

					tbJobTitleBeanModel.setTbjsId(Integer.parseInt(cmbJobSpecification.getValue().get("id").toString()));

					tbJobTitleBeanModel.setTbpId(Integer.parseInt(cmbPaygrade.getValue().get("id").toString()));

					List<ComboBoxData> lstEmployeeStatus = storeToEmployeeStatus.getModels();
					Iterator<ComboBoxData> itrEmployeeStatus = lstEmployeeStatus.iterator();
					String tbesIds[] = new String[lstEmployeeStatus.size()];
					int i = 0;
					while (itrEmployeeStatus.hasNext()) {
						tbesIds[i] = itrEmployeeStatus.next().get("id");
						i++;
					}
					tbJobTitleBeanModel.set("tbesIds", tbesIds);

					List<ComboBoxData> lstRenumeration = storeToRenumeration.getModels();
					Iterator<ComboBoxData> itrRenumeration = lstRenumeration.iterator();
					String tbrIds[] = new String[lstRenumeration.size()];
					int ii = 0;
					while (itrRenumeration.hasNext()) {
						tbrIds[ii] = itrRenumeration.next().get("id");
						ii++;
					}
					tbJobTitleBeanModel.set("tbrIds", tbrIds);

					tbJobTitleBeanModel.setTbjtLeaveEntitled(txtLeaveEntitled.getValue() == null ? 0 : Integer.parseInt(txtLeaveEntitled.getValue()));

					List<ViewMenuAccessBeanModel> lstViewMenuAccessBeanModel = grid.getStore().getModels();
					tbJobTitleBeanModel.set("lstViewMenuAccessBeanModel", lstViewMenuAccessBeanModel);

					jobTitleInterfaceAsync.submitJobTitle(tbJobTitleBeanModel, submitJobTitleCallback);
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

				jobTitleInterfaceAsync.getJobTitle(intTbjtId, getJobTitleCallback);

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
				WindowJobTitle window = WindowJobTitle.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		grid.setEnabled(false);

		tabPanel.setSelection(tabItemJobTitle);

		hfJobSpecificationId.setValue("");
		hfPaygradeId.setValue("");

		storeFromEmployeeStatus.removeAll();
		storeToEmployeeStatus.removeAll();

		storeFromRenumeration.removeAll();
		storeToRenumeration.removeAll();

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window, 0, false);
			doResetComponent(window, 0, false);

			jobTitleInterfaceAsync.getTbJobSpecificationsAll(getTbJobSpecificationsAllCallback);
			jobTitleInterfaceAsync.getTbPaygradeAll(getTbPaygradeAllCallback);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);

			jobTitleInterfaceAsync.getTbEmploymentStatusAll(getTbEmploymentStatusAllCallbackStoreFrom);

			jobTitleInterfaceAsync.getTbRenumerationAll(getTbRenumerationAllCallbackStoreFrom);
			
			tabItemMenuAccess.setEnabled(false);
			tabItemJobTitleAddEditLeaveRule.setEnabled(false);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window, 0, false);

			jobTitleInterfaceAsync.getJobTitle(intTbjtId, getJobTitleCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
			
			tabItemMenuAccess.setEnabled(true);
			tabItemJobTitleAddEditLeaveRule.setEnabled(true);
		}
	}
}
