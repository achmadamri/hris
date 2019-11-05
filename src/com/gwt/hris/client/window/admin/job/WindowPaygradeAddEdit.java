package com.gwt.hris.client.window.admin.job;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.admin.job.PaygradeInterface;
import com.gwt.hris.client.service.admin.job.PaygradeInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewPaygradeCurrencyBeanModel;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowPaygradeAddEdit extends WindowMain {
	private static WindowPaygradeAddEdit instance = new WindowPaygradeAddEdit();

	public static WindowPaygradeAddEdit getInstance() {
		return instance;
	}

	public WindowPaygradeAddEdit() {
		window = new Window();
		window.setId("WindowPaygradeAddEdit");
		window.setSize(550, 440);
		window.setHeading("Job : Salary Grade");
	}

	public String strNav = "";
	public int intTbpId = 0;
	public String strSelectedTab = "";

	public void show(String strNav, int intTbpId) {
		this.strNav = strNav;
		this.intTbpId = intTbpId;

		super.show(true);
	}

	TabPanel tabPanel = new TabPanel();
	TabItem tabItemPayGrade = new TabItem("Salary Grade");
	TabItem tabItemAssignedCurrency = new TabItem("Assigned Currency");

	PaygradeInterfaceAsync paygradeInterfaceAsync = GWT.create(PaygradeInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();

	ComboBox<ComboBoxData> cmbCurrency = new ComboBox<ComboBoxData>();
	ComboBoxData selectedData;
	ListStore<ComboBoxData> cmbCurrencyStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfCurrencyId = new HiddenField<String>();

	TextField<String> txtMin = new TextField<String>();
	TextField<String> txtMax = new TextField<String>();
	TextField<String> txtStep = new TextField<String>();
	TextField<String> txtLate = new TextField<String>();
	TextField<String> txtOvertime = new TextField<String>();

	RpcProxy<PagingLoadResult<ViewPaygradeCurrencyBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewPaygradeCurrencyBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<ViewPaygradeCurrencyBeanModel> grid;

	int deleteID;
	int editID;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				paygradeInterfaceAsync.deletePaygradeCurrency(deleteID, callback);
			}
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
	
	AsyncCallback<TbPaygradeCurrencyBeanModel> callbackEdit = new AsyncCallback<TbPaygradeCurrencyBeanModel>() {
		@Override
		public void onSuccess(TbPaygradeCurrencyBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(window, 0, false);
				doUnlockedComponent(window, 1, true);

				editID = result.getTbpcId();
				
				hfCurrencyId.setValue(result.getTbpId() == null ? "" : result.getTbcId().toString());
				txtMin.setValue(StringUtil.getInstance().toString(result.getTbpcMin()));
				txtMax.setValue(StringUtil.getInstance().toString(result.getTbpcMax()));
				txtStep.setValue(StringUtil.getInstance().toString(result.getTbpcStep()));
				txtLate.setValue(StringUtil.getInstance().toString(result.getTbpcLate()));
				txtOvertime.setValue(StringUtil.getInstance().toString(result.getTbpcOvertime()));

				btnEdit.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
				
				paygradeInterfaceAsync.getTbCurrencyAll(getTbCurrencyAllCallback);
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

	AsyncCallback<TbCurrencyBeanModel> getTbCurrencyAllCallback = new AsyncCallback<TbCurrencyBeanModel>() {
		@Override
		public void onSuccess(TbCurrencyBeanModel result) {
			if (result.getOperationStatus()) {
				cmbCurrencyStore.removeAll();

				for (TbCurrencyBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbcId().toString(), obj.getTbcCurrencyId() + " - " + obj.getTbcName());
					cmbCurrencyStore.add(data);

					if (hfCurrencyId.getValue() != null) {
						if (!"".equals(hfCurrencyId.getValue())) {
							if (obj.getTbcId() == Integer.parseInt(hfCurrencyId.getValue())) {
								selectedData = data;
								cmbCurrency.setValue(selectedData);
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

	AsyncCallback<TbPaygradeBeanModel> getPaygradeCallback = new AsyncCallback<TbPaygradeBeanModel>() {
		@Override
		public void onSuccess(TbPaygradeBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbpId().toString());

				lblCode.setValue(result.getTbpPaygradeId());
				txtName.setValue(result.getTbpName());

				paygradeInterfaceAsync.getTbCurrencyAll(getTbCurrencyAllCallback);
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

				if ("add".equals(strNav)) {
					WindowPaygrade.getInstance().bottomToolBar.refresh();

					hfId = new HiddenField<String>();

					doUnlockedComponent(window, 0, false);
					doUnlockedComponent(window, 1, true);
					doResetComponent(window, 0, false);
					doResetComponent(window, 1, true);

					paygradeInterfaceAsync.getTbCurrencyAll(getTbCurrencyAllCallback);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
				} else {
					if ("Pay Grade".equals(strSelectedTab)) {
						WindowPaygrade.getInstance().bottomToolBar.refresh();

						TbPaygradeBeanModel tbLocationBeanModel = (TbPaygradeBeanModel) result.get("model");
						hfId.setValue(tbLocationBeanModel.getTbpId().toString());
					} else {
						bottomToolBar.refresh();
					}

					doLockedComponent(window, 0, false);
					doLockedComponent(window, 1, true);
					doResetComponent(window, 1, true);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}
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

	FormPanel formPanelTab1 = new FormPanel();
	FormPanel formPanelTab2 = new FormPanel();

	@Override
	public void addComponents() {
		tabItemAssignedCurrency.setLayout(new FitLayout());
		tabItemAssignedCurrency.setStyleAttribute("backgroundColor", "#F2F2F2");

		tabItemPayGrade.setStyleAttribute("backgroundColor", "#F2F2F2");
		tabItemPayGrade.setScrollMode(Scroll.AUTOY);

		tabItemPayGrade.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent e) {
				strSelectedTab = "Salary Grade";
				window.layout(true);
			}
		});

		tabItemAssignedCurrency.addListener(Events.Select, new Listener<ComponentEvent>() {
			public void handleEvent(ComponentEvent e) {
				strSelectedTab = "Assigned Currency";
				window.layout(true);
				bottomToolBar.refresh();
			}
		});

		formPanelTab1.setLabelWidth(100);
		formPanelTab1.setHeaderVisible(false);
		formPanelTab1.setFrame(false);
		formPanelTab1.setStyleAttribute("backgroundColor", "#F2F2F2");

		ContentPanel mainPanel = new ContentPanel();
		mainPanel.setHeaderVisible(false);
		mainPanel.setLayout(new RowLayout(Orientation.VERTICAL));

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

		tabItemPayGrade.add(formPanelTab1);
		tabPanel.add(tabItemPayGrade);

		cmbCurrency.setId("cmbCurrency");
		cmbCurrency.setFieldLabel("Currency *");
		cmbCurrency.setEmptyText("Select");
		cmbCurrency.setDisplayField("value");
		cmbCurrency.setForceSelection(true);
		cmbCurrency.setTypeAhead(true);
		cmbCurrency.setTriggerAction(TriggerAction.ALL);
		cmbCurrency.setStore(cmbCurrencyStore);
		cmbCurrency.setAllowBlank(false);
		formPanelTab2.add(cmbCurrency, formData);

		formPanelTab2.add(hfCurrencyId, formData);

		txtMin.setId("txtMin");
		txtMin.setFieldLabel("Minimum Salary *");
		txtMin.setAllowBlank(false);
		formPanelTab2.add(txtMin, formData);

		txtMax.setId("txtMax");
		txtMax.setFieldLabel("Maximum Salary *");
		txtMax.setAllowBlank(false);
		formPanelTab2.add(txtMax, formData);

		txtStep.setId("txtStep");
		txtStep.setFieldLabel("Increment (%) *");
		txtStep.setAllowBlank(false);
		formPanelTab2.add(txtStep, formData);

		txtLate.setId("txtLate");
		txtLate.setFieldLabel("Late (%)*");
		txtLate.setAllowBlank(false);
		formPanelTab2.add(txtLate, formData);

		txtOvertime.setId("txtOvertime");
		txtOvertime.setFieldLabel("Overtime (%)*");
		txtOvertime.setAllowBlank(false);
		formPanelTab2.add(txtOvertime, formData);

		mainPanel.add(formPanelTab2, new RowData(1, -1));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewPaygradeCurrencyBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewPaygradeCurrencyBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				paygradeInterfaceAsync.getPaygradeCurrencyPaging(pagingLoadConfig, "tbp_id", String.valueOf(intTbpId), new AsyncCallback<PagingLoadResult<ViewPaygradeCurrencyBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewPaygradeCurrencyBeanModel> result) {
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

		pagingStore = new ListStore<ViewPaygradeCurrencyBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbcCurrencyId", "<center><b>Currency ID</b></center>", 100));

		ColumnConfig columnConfig = new ColumnConfig("tbpcMin", "<center><b>Min. Salary</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbpcMin");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbpcMax", "<center><b>Max. Salary</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbpcMax");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbpcStep", "<center><b>Increment (%)</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbpcStep");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbpcLate", "<center><b>Late (%)</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbpcLate");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbpcOvertime", "<center><b>Overtime (%)</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbpcOvertime");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

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
				
				Button btnEdit = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						int id = Integer.parseInt(model.get("tbpcId").toString());
						paygradeInterfaceAsync.getPaygradeCurrency(id, callbackEdit);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit, hBoxLayoutData);

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						deleteID = Integer.parseInt(model.get("tbpcId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete salary currency \"" + model.get("tbcCurrencyId") + "\" ?", deleteListener);
					}
				});
				btnDelete.setToolTip("delete");
				btnDelete.setIcon(Resources.ICONS.delete());
				layoutContainer.add(btnDelete, hBoxLayoutData);

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

		grid = new Grid<ViewPaygradeCurrencyBeanModel>(pagingStore, cm);
		grid.setLoadMask(true);
		grid.setAutoExpandColumn("space");

		panel.add(grid);
		panel.setBottomComponent(bottomToolBar);

		mainPanel.add(panel, new RowData(1, 1));

		tabItemAssignedCurrency.add(mainPanel);
		tabPanel.add(tabItemAssignedCurrency);

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
				if ("Salary Grade".equals(strSelectedTab)) {
					if (formPanelTab1.isValid()) {
						formPanel.mask("Saving...");
						TbPaygradeBeanModel tbPaygradeBeanModel = new TbPaygradeBeanModel();
						tbPaygradeBeanModel.setTbpId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
						tbPaygradeBeanModel.setTbpName(StringUtil.getInstance().getString(txtName.getValue()));

						paygradeInterfaceAsync.submitPaygrade(tbPaygradeBeanModel, submitCallback);
					} else {
						MessageBox.alert("Alert", "Required field is still empty or invalid", null);
					}
				} else {
					if (formPanelTab2.isValid()) {
						TbPaygradeCurrencyBeanModel tbPaygradeCurrencyBeanModel = new TbPaygradeCurrencyBeanModel();
						tbPaygradeCurrencyBeanModel.setTbpcId(editID);
						tbPaygradeCurrencyBeanModel.setTbcId(Integer.parseInt(cmbCurrency.getValue().get("id").toString()));
						tbPaygradeCurrencyBeanModel.setTbpId(intTbpId);
						tbPaygradeCurrencyBeanModel.setTbpcMin(Double.parseDouble(txtMin.getValue()));
						tbPaygradeCurrencyBeanModel.setTbpcMax(Double.parseDouble(txtMax.getValue()));
						tbPaygradeCurrencyBeanModel.setTbpcStep(Double.parseDouble(txtStep.getValue()));
						tbPaygradeCurrencyBeanModel.setTbpcLate(Double.parseDouble(txtLate.getValue()));
						tbPaygradeCurrencyBeanModel.setTbpcOvertime(Double.parseDouble(txtOvertime.getValue()));

						paygradeInterfaceAsync.submitPaygradeCurrency(tbPaygradeCurrencyBeanModel, submitCallback);
					} else {
						MessageBox.alert("Alert", "Required field is still empty or invalid", null);
					}
				}
			}
		});
		window.addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				doUnlockedComponent(window, 0, false);
				doUnlockedComponent(window, 1, true);

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
				
				hfCurrencyId.setValue("");
				
				btnEdit.setEnabled(false);
				btnBack.setEnabled(false);

				doResetComponent(window, 0, false);
				doResetComponent(window, 1, true);

				doLockedComponent(window, 0, false);
				doLockedComponent(window, 1, true);

				paygradeInterfaceAsync.getPaygrade(intTbpId, getPaygradeCallback);

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
				WindowPaygrade window = WindowPaygrade.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		hfCurrencyId.setValue("");

		strSelectedTab = "Pay Grade";
		tabPanel.setSelection(tabItemPayGrade);

		doResetComponent(window, 0, false);
		doResetComponent(window, 1, true);

		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window, 0, false);
			doUnlockedComponent(window, 1, true);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			paygradeInterfaceAsync.getTbCurrencyAll(getTbCurrencyAllCallback);

			tabItemAssignedCurrency.setEnabled(false);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window, 0, false);
			doLockedComponent(window, 1, true);

			paygradeInterfaceAsync.getPaygrade(intTbpId, getPaygradeCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			tabItemAssignedCurrency.setEnabled(true);

			lblCode.setVisible(true);
		}
	}
}
