package com.gwt.hris.client.window.pim;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
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
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbCurrencyBeanModel;
import com.gwt.hris.client.service.bean.TbEmployeeSalaryBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeBeanModel;
import com.gwt.hris.client.service.bean.TbPaygradeCurrencyBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeSalaryBeanModel;
import com.gwt.hris.client.service.pim.EmployeeSalaryInterface;
import com.gwt.hris.client.service.pim.EmployeeSalaryInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssEmployeeSalary extends EssMainCP {
	FormPanel formPanel;

	public EssEmployeeSalary(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;
		
		if (this.callerId == 0) {
			tbAugId = WindowEss.getInstance().tbLoginBeanModel.getTbaugId();
		} else {
			tbAugId = WindowEmployeeListEss.getInstance().tbLoginBeanModel.getTbaugId();
		}

		addComponents();

		addButtons();

		doResetComponent(formPanel);
		doLockedComponent(formPanel);

		if (tbAugId != null) {
			btnAdd.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		} else {
			btnAdd.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}

		bottomToolBar.refresh();
	}

	EmployeeSalaryInterfaceAsync employeeSalaryInterfaceAsync = GWT.create(EmployeeSalaryInterface.class);

	public int id = 0;
	public int callerId;
	public int deleteID;
	public int editID;
	public Integer tbAugId;

	ComboBox<ComboBoxData> cmbPaygrade;
	ComboBoxData cmbPaygradeSelectedData;
	ListStore<ComboBoxData> cmbPaygradeStore;
	HiddenField<String> hfPaygradeId;

	ComboBox<ComboBoxData> cmbCurrency;
	ComboBoxData cmbCurrencySelectedData;
	ListStore<ComboBoxData> cmbCurrencyStore;
	HiddenField<String> hfCurrencyId;

	TextField<String> txtBasicSalary;
	TextField<String> txtCogs;

	LabelField lblMinimumSalary;
	LabelField lblMaximumSalary;

	Radio rdoHourly;
	Radio rdoWeekly;
	Radio rdoBiWeekly;
	Radio rdoMonthly;
	RadioGroup rdgPayFrequency;

	RpcProxy<PagingLoadResult<ViewEmployeeSalaryBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewEmployeeSalaryBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<ViewEmployeeSalaryBeanModel> grid;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				employeeSalaryInterfaceAsync.deleteEmployeeSalary(deleteID, callback);
			}
		}
	};

	AsyncCallback<ReturnBean> callback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				editID = 0;

				doResetComponent(formPanel);
				doLockedComponent(formPanel);

				btnAdd.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);

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

	AsyncCallback<TbEmployeeSalaryBeanModel> callbackEdit = new AsyncCallback<TbEmployeeSalaryBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeSalaryBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbesId();
				txtBasicSalary.setValue(result.getTbesBasicSalary().toString());
				txtCogs.setValue(result.getTbesCogs().toString());

				hfPaygradeId.setValue(result.getTbpId() == null ? "" : result.getTbpId().toString());
				employeeSalaryInterfaceAsync.getTbPaygradeAll(tbPaygradeAllCallback);

				hfCurrencyId.setValue(result.getTbcId() == null ? "" : result.getTbcId().toString());
				employeeSalaryInterfaceAsync.getTbCurrencyByPaygrade(result.getTbpId() == null ? 0 : result.getTbpId(), tbCurrencyAllCallback);

				employeeSalaryInterfaceAsync.getTbPaygradeCurrency(result.getTbpId() == null ? 0 : result.getTbpId(), result.getTbcId() == null ? 0 : result.getTbcId(), getTbPaygradeCurrencyCallback);

				if (result.getTbesPayFrequency() == null) {
					rdgPayFrequency.clear();
				} else {
					rdgPayFrequency.setValue(result.getTbesPayFrequency() == 0 ? rdoHourly : (result.getTbesPayFrequency() == 1 ? rdoWeekly : (result.getTbesPayFrequency() == 2 ? rdoBiWeekly : result.getTbesPayFrequency() == 3 ? rdoMonthly : null)));
				}

				btnAdd.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
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

	AsyncCallback<TbPaygradeBeanModel> tbPaygradeAllCallback = new AsyncCallback<TbPaygradeBeanModel>() {
		@Override
		public void onSuccess(TbPaygradeBeanModel result) {
			if (result.getOperationStatus()) {
				cmbPaygrade.clear();
				cmbPaygradeStore.removeAll();

				for (TbPaygradeBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbpId().toString(), obj.getTbpName());
					cmbPaygradeStore.add(data);

					if (hfPaygradeId.getValue() != null) {
						if (!"".equals(hfPaygradeId.getValue())) {
							if (obj.getTbpId() == Integer.parseInt(hfPaygradeId.getValue())) {
								cmbPaygradeSelectedData = data;
								cmbPaygrade.setValue(cmbPaygradeSelectedData);
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

	AsyncCallback<TbCurrencyBeanModel> tbCurrencyAllCallback = new AsyncCallback<TbCurrencyBeanModel>() {
		@Override
		public void onSuccess(TbCurrencyBeanModel result) {
			if (result.getOperationStatus()) {
				cmbCurrency.clear();
				cmbCurrencyStore.removeAll();

				for (TbCurrencyBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbcId().toString(), obj.getTbcName() + " (" + obj.getTbcCurrencyId() + ")");
					cmbCurrencyStore.add(data);

					if (hfCurrencyId.getValue() != null) {
						if (!"".equals(hfCurrencyId.getValue())) {
							if (obj.getTbcId() == Integer.parseInt(hfCurrencyId.getValue())) {
								cmbCurrencySelectedData = data;
								cmbCurrency.setValue(cmbCurrencySelectedData);
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

	AsyncCallback<TbPaygradeCurrencyBeanModel> getTbPaygradeCurrencyCallback = new AsyncCallback<TbPaygradeCurrencyBeanModel>() {
		@Override
		public void onSuccess(TbPaygradeCurrencyBeanModel result) {
			if (result.getOperationStatus()) {
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				lblMinimumSalary.setValue(fmt.format(result.getTbpcMin()));
				lblMaximumSalary.setValue(fmt.format(result.getTbpcMax()));
			} else {
				if (!"Data Not Found".equals(result.getMessage())) {
					MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.getMessage(), null);
				}
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};

	public void addComponents() {
		setLayout(new RowLayout(Orientation.VERTICAL));

		formPanel = new FormPanel();
		formPanel.setLabelWidth(100);
		formPanel.setHeaderVisible(false);
		formPanel.setFrame(false);
		formPanel.setBodyBorder(false);
		formPanel.setBorders(false);
		formPanel.setStyleAttribute("backgroundColor", "#F2F2F2");

		if (tbAugId != null) {
			cmbPaygrade = new ComboBox<ComboBoxData>();
			cmbPaygradeStore = new ListStore<ComboBoxData>();
			cmbPaygrade.setId("cmbPaygrade");
			cmbPaygrade.setFieldLabel("Salary Grade *");
			cmbPaygrade.setEmptyText("Select");
			cmbPaygrade.setDisplayField("value");
			cmbPaygrade.setForceSelection(true);
			cmbPaygrade.setTypeAhead(true);
			cmbPaygrade.setTriggerAction(TriggerAction.ALL);
			cmbPaygrade.setStore(cmbPaygradeStore);
			cmbPaygrade.setAllowBlank(false);
			cmbPaygrade.addListener(Events.Select, new Listener<BaseEvent>() {
				public void handleEvent(BaseEvent be) {
					lblMinimumSalary.clear();
					lblMaximumSalary.clear();
					employeeSalaryInterfaceAsync.getTbCurrencyByPaygrade(Integer.parseInt(cmbPaygrade.getValue().get("id").toString()), tbCurrencyAllCallback);
				}
			});
			formPanel.add(cmbPaygrade, formData);
			hfPaygradeId = new HiddenField<String>();
			formPanel.add(hfPaygradeId, formData);

			cmbCurrency = new ComboBox<ComboBoxData>();
			cmbCurrencyStore = new ListStore<ComboBoxData>();
			cmbCurrency.setId("cmbCurrency");
			cmbCurrency.setFieldLabel("Currency *");
			cmbCurrency.setEmptyText("Select");
			cmbCurrency.setDisplayField("value");
			cmbCurrency.setForceSelection(true);
			cmbCurrency.setTypeAhead(true);
			cmbCurrency.setTriggerAction(TriggerAction.ALL);
			cmbCurrency.setStore(cmbCurrencyStore);
			cmbCurrency.setAllowBlank(false);
			cmbCurrency.addListener(Events.Select, new Listener<BaseEvent>() {
				public void handleEvent(BaseEvent be) {
					employeeSalaryInterfaceAsync.getTbPaygradeCurrency(Integer.parseInt(cmbPaygrade.getValue().get("id").toString()), Integer.parseInt(cmbCurrency.getValue().get("id").toString()), getTbPaygradeCurrencyCallback);
				}
			});
			formPanel.add(cmbCurrency, formData);
			hfCurrencyId = new HiddenField<String>();
			formPanel.add(hfCurrencyId, formData);

			lblMinimumSalary = new LabelField();
			lblMinimumSalary.setId("lblMinimumSalary");
			lblMinimumSalary.setFieldLabel("Minimum Salary");
			formPanel.add(lblMinimumSalary, formData);

			lblMaximumSalary = new LabelField();
			lblMaximumSalary.setId("lblMaximumSalary");
			lblMaximumSalary.setFieldLabel("Maximum Salary");
			formPanel.add(lblMaximumSalary, formData);

			txtBasicSalary = new TextField<String>();
			txtBasicSalary.setId("txtName");
			txtBasicSalary.setFieldLabel("Basic Salary *");
			txtBasicSalary.setAllowBlank(false);
			formPanel.add(txtBasicSalary, formData);

			txtCogs = new TextField<String>();
			txtCogs.setId("txtCogs");
			txtCogs.setFieldLabel("COGS *");
			txtCogs.setAllowBlank(false);
			formPanel.add(txtCogs, formData);

			rdoHourly = new Radio();
			rdoHourly.setBoxLabel("Hourly");

			rdoWeekly = new Radio();
			rdoWeekly.setBoxLabel("Weekly");

			rdoBiWeekly = new Radio();
			rdoBiWeekly.setBoxLabel("Bi Weekly");

			rdoMonthly = new Radio();
			rdoMonthly.setBoxLabel("Monthly");

			rdgPayFrequency = new RadioGroup();
			rdgPayFrequency.setFieldLabel("Pay Frequency *");
			rdgPayFrequency.add(rdoHourly);
			rdgPayFrequency.add(rdoWeekly);
			rdgPayFrequency.add(rdoBiWeekly);
			rdgPayFrequency.add(rdoMonthly);
			formPanel.add(rdgPayFrequency, formData);
			
			add(formPanel, new RowData(1, -1));
		}

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewEmployeeSalaryBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewEmployeeSalaryBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				employeeSalaryInterfaceAsync.getEmployeeSalaryPaging(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<ViewEmployeeSalaryBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewEmployeeSalaryBeanModel> result) {
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

		pagingStore = new ListStore<ViewEmployeeSalaryBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbpName", "<center><b>Salary Grade</b></center>", 100));
		columns.add(new ColumnConfig("tbcCurrencyId", "<center><b>Currency ID</b></center>", 100));

		ColumnConfig columnConfig = new ColumnConfig("tbesBasicSalary", "<center><b>Basic Salary</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbesBasicSalary");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbesCogs", "<center><b>COGS</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				double data1 = model.get("tbesCogs");
				NumberFormat fmt = NumberFormat.getFormat("#,###,###");
				return fmt.format(data1);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbesPayFrequency", "<center><b>Pay Frequency</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				int data = model.get("tbesPayFrequency");
				return data == 0 ? "Hourly" : (data == 1 ? "Weekly" : (data == 2 ? "Bi Weekly" : data == 3 ? "Monthly" : null));
			}
		});
		columns.add(columnConfig);columnConfig = new ColumnConfig("tbpcStep", "<center><b>Increment (%)</b></center>", 100);
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
		if (tbAugId != null) {
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
							int id = Integer.parseInt(model.get("tbesId").toString());
							employeeSalaryInterfaceAsync.getEmployeeSalary(id, callbackEdit);
						}
					});
					btnEdit.setToolTip("edit");
					btnEdit.setIcon(Resources.ICONS.form());
					layoutContainer.add(btnEdit, hBoxLayoutData);

					Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							deleteID = Integer.parseInt(model.get("tbesId").toString());

							MessageBox.confirm("Confirmation", "Are you sure you want to delete employee salary \"" + model.get("tbesCurrencyName") + "\" ?", deleteListener);
						}
					});
					btnDelete.setToolTip("delete");
					btnDelete.setIcon(Resources.ICONS.delete());
					layoutContainer.add(btnDelete, hBoxLayoutData);

					return layoutContainer;
				}
			});
			columns.add(ccActions);
		}

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

		grid = new Grid<ViewEmployeeSalaryBeanModel>(pagingStore, cm);
		grid.setLoadMask(true);
		grid.setAutoExpandColumn("space");

		panel.add(grid);
		panel.setBottomComponent(bottomToolBar);

		add(panel, new RowData(1, 1));
	}

	Button btnSave;
	Button btnAdd;
	Button btnCancel;
	Button btnBack;

	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (tbAugId != null) {
					if (formPanel.isValid() && (rdoBiWeekly.getValue() == true || rdoHourly.getValue() == true || rdoMonthly.getValue() == true || rdoWeekly.getValue() == true)) {
						TbEmployeeSalaryBeanModel tbEmployeeSalaryBeanModel = new TbEmployeeSalaryBeanModel();
						tbEmployeeSalaryBeanModel.setTbeId(id);

						if (editID != 0)
							tbEmployeeSalaryBeanModel.setTbesId(editID);

						tbEmployeeSalaryBeanModel.setTbesBasicSalary(Double.parseDouble(txtBasicSalary.getValue()));
						tbEmployeeSalaryBeanModel.setTbesCogs(Double.parseDouble(txtCogs.getValue()));

						if (cmbPaygrade.getValue() != null)
							tbEmployeeSalaryBeanModel.setTbpId(Integer.parseInt(cmbPaygrade.getValue().get("id").toString()));

						if (cmbCurrency.getValue() != null)
							tbEmployeeSalaryBeanModel.setTbcId(Integer.parseInt(cmbCurrency.getValue().get("id").toString()));

						tbEmployeeSalaryBeanModel.setTbesCurrencyName(cmbCurrency.getValue().get("value").toString());

						if (rdgPayFrequency.getValue() != null)
							tbEmployeeSalaryBeanModel.setTbesPayFrequency(rdoHourly.getValue() == true ? 0 : (rdoWeekly.getValue() == true ? 1 : (rdoBiWeekly.getValue() == true ? 2 : (rdoMonthly.getValue() == true ? 3 : null))));

						employeeSalaryInterfaceAsync.submitEmployeeSalary(tbEmployeeSalaryBeanModel, callback);
					} else {
						MessageBox.alert("Alert", "Required field is still empty", null);
					}
				}
			}
		});
		addButton(btnSave);

		btnAdd = new Button("Add", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (tbAugId != null) {
					doUnlockedComponent(formPanel);

					employeeSalaryInterfaceAsync.getTbPaygradeAll(tbPaygradeAllCallback);

					btnAdd.setVisible(false);
					btnBack.setVisible(false);
					btnCancel.setVisible(true);
					btnSave.setVisible(true);
				}
			}
		});
		addButton(btnAdd);

		btnCancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				btnAdd.setEnabled(true);
				btnBack.setEnabled(true);

				hfPaygradeId.clear();
				hfCurrencyId.clear();

				editID = 0;

				doResetComponent(formPanel);
				doLockedComponent(formPanel);

				btnAdd.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		addButton(btnCancel);

		if (this.callerId == 0) {
			btnBack = new Button("Close", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					MainTabLayout.closeTab(WindowEss.getInstance().window.getHeading());
				}
			});
		} else {
			btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					WindowEmployeeList window = WindowEmployeeList.getInstance();
					window.show(true);
				}
			});
		}
		addButton(btnBack);
	}
}
