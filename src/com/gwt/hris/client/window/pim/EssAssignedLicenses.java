package com.gwt.hris.client.window.pim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
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
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLicensesBeanModel;
import com.gwt.hris.client.service.bean.TbLicensesBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLicensesBeanModel;
import com.gwt.hris.client.service.pim.AssignedLicensesInterface;
import com.gwt.hris.client.service.pim.AssignedLicensesInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssAssignedLicenses extends EssMainCP {
	FormPanel formPanel;

	public EssAssignedLicenses(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;

		addComponents();

		addButtons();

		doResetComponent(formPanel);
		doLockedComponent(formPanel);

		btnAdd.setVisible(true);
		btnBack.setVisible(true);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);

		bottomToolBar.refresh();
	}

	AssignedLicensesInterfaceAsync assignedLicensesInterfaceAsync = GWT.create(AssignedLicensesInterface.class);

	public int id = 0;
	public int callerId;
	public int deleteID;
	public int editID;

	ComboBox<ComboBoxData> cmbLicenses;
	ComboBoxData cmbLicensesSelectedData;
	ListStore<ComboBoxData> cmbLicensesStore;
	HiddenField<String> hfLicensesId;

	DateField dateStart;
	DateField dateEnd;

	RpcProxy<PagingLoadResult<ViewEmployeeLicensesBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewEmployeeLicensesBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<ViewEmployeeLicensesBeanModel> grid;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				assignedLicensesInterfaceAsync.deleteAssignedLicenses(deleteID, callback);
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

	AsyncCallback<TbAssignedLicensesBeanModel> callbackEdit = new AsyncCallback<TbAssignedLicensesBeanModel>() {
		@Override
		public void onSuccess(TbAssignedLicensesBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbalId();

				hfLicensesId.setValue(result.getTblId() == null ? "" : result.getTblId().toString());
				assignedLicensesInterfaceAsync.getTbLicensesAll(tbLicensesAllCallback);

				dateStart.setValue(result.getTbalStartDate());
				dateEnd.setValue(result.getTbalEndDate());

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

	AsyncCallback<TbLicensesBeanModel> tbLicensesAllCallback = new AsyncCallback<TbLicensesBeanModel>() {
		@Override
		public void onSuccess(TbLicensesBeanModel result) {
			if (result.getOperationStatus()) {
				cmbLicenses.clear();
				cmbLicensesStore.removeAll();

				for (TbLicensesBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTblId().toString(), obj.getTblName());
					cmbLicensesStore.add(data);

					if (hfLicensesId.getValue() != null) {
						if (!"".equals(hfLicensesId.getValue())) {
							if (obj.getTblId() == Integer.parseInt(hfLicensesId.getValue())) {
								cmbLicensesSelectedData = data;
								cmbLicenses.setValue(cmbLicensesSelectedData);
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

	public void addComponents() {
		setLayout(new RowLayout(Orientation.VERTICAL));

		formPanel = new FormPanel();
		formPanel.setLabelWidth(100);
		formPanel.setHeaderVisible(false);
		formPanel.setFrame(false);
		formPanel.setBodyBorder(false);
		formPanel.setBorders(false);
		formPanel.setStyleAttribute("backgroundColor", "#F2F2F2");

		cmbLicenses = new ComboBox<ComboBoxData>();
		cmbLicensesStore = new ListStore<ComboBoxData>();
		cmbLicenses.setId("cmbLicenses");
		cmbLicenses.setFieldLabel("Licenses");
		cmbLicenses.setEmptyText("Select");
		cmbLicenses.setDisplayField("value");
		cmbLicenses.setForceSelection(true);
		cmbLicenses.setTypeAhead(true);
		cmbLicenses.setTriggerAction(TriggerAction.ALL);
		cmbLicenses.setStore(cmbLicensesStore);
		cmbLicenses.setAllowBlank(false);
		formPanel.add(cmbLicenses, formData);
		hfLicensesId = new HiddenField<String>();
		formPanel.add(hfLicensesId, formData);

		dateStart = new DateField();
		dateStart.setFieldLabel("Start Date *");
		dateStart.setAllowBlank(false);
		formPanel.add(dateStart, formData);

		dateEnd = new DateField();
		dateEnd.setFieldLabel("End Date *");
		dateEnd.setAllowBlank(false);
		formPanel.add(dateEnd, formData);

		add(formPanel, new RowData(1, -1));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewEmployeeLicensesBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewEmployeeLicensesBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				assignedLicensesInterfaceAsync.getAssignedLicensesPaging(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<ViewEmployeeLicensesBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewEmployeeLicensesBeanModel> result) {
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

		pagingStore = new ListStore<ViewEmployeeLicensesBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tblName", "<center><b>License Type</b></center>", 150));

		ColumnConfig columnConfig = new ColumnConfig("tbalStartDate", "<center><b>Start Date</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Date data = model.get("tbalStartDate");
				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
				return fmt.format(data);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbalEndDate", "<center><b>End Date</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Date data = model.get("tbalEndDate");
				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
				return fmt.format(data);
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
						int id = Integer.parseInt(model.get("tbalId").toString());
						assignedLicensesInterfaceAsync.getAssignedLicenses(id, callbackEdit);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit, hBoxLayoutData);

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						deleteID = Integer.parseInt(model.get("tbalId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete Licenses \"" + model.get("tblName") + "\" ?", deleteListener);
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

		grid = new Grid<ViewEmployeeLicensesBeanModel>(pagingStore, cm);
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
				if (formPanel.isValid()) {
					TbAssignedLicensesBeanModel tbAssignedLicensesBeanModel = new TbAssignedLicensesBeanModel();
					tbAssignedLicensesBeanModel.setTbeId(id);

					if (editID != 0)
						tbAssignedLicensesBeanModel.setTbalId(editID);

					if (cmbLicenses.getValue() != null)
						tbAssignedLicensesBeanModel.setTblId(Integer.parseInt(cmbLicenses.getValue().get("id").toString()));

					tbAssignedLicensesBeanModel.setTbalStartDate(dateStart.getValue().getTime());
					tbAssignedLicensesBeanModel.setTbalEndDate(dateEnd.getValue().getTime());

					assignedLicensesInterfaceAsync.submitAssignedLicenses(tbAssignedLicensesBeanModel, callback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty", null);
				}
			}
		});
		addButton(btnSave);

		btnAdd = new Button("Add", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				doUnlockedComponent(formPanel);

				assignedLicensesInterfaceAsync.getTbLicensesAll(tbLicensesAllCallback);

				btnAdd.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
			}
		});
		addButton(btnAdd);

		btnCancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				btnAdd.setEnabled(true);
				btnBack.setEnabled(true);

				hfLicensesId.clear();

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
