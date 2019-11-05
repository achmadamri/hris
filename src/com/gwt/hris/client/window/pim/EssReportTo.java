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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.admin.users.HRAdminUsersInterface;
import com.gwt.hris.client.service.admin.users.HRAdminUsersInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbReportToBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeReportToBeanModel;
import com.gwt.hris.client.service.pim.EmployeeListInterface;
import com.gwt.hris.client.service.pim.EmployeeListInterfaceAsync;
import com.gwt.hris.client.service.pim.ReportToInterface;
import com.gwt.hris.client.service.pim.ReportToInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssReportTo extends EssMainFP {
	FormPanel formPanel;

	public EssReportTo(FormData formData, int id_, int callerId_) {
		super(formData);

		formPanel = new FormPanel();
		formPanel.setLabelWidth(100);
		formPanel.setHeaderVisible(false);
		formPanel.setFrame(false);
		formPanel.setBodyBorder(false);
		formPanel.setBorders(false);
		formPanel.setStyleAttribute("backgroundColor", "#F2F2F2");
		formPanel.setLabelWidth(150);

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
		
		if (tbAugId != null)
			employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);

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

	ReportToInterfaceAsync reportToInterfaceAsync = GWT.create(ReportToInterface.class);
	HRAdminUsersInterfaceAsync hrAdminUsersInterfaceAsync = GWT.create(HRAdminUsersInterface.class);
	EmployeeListInterfaceAsync employeeListInterfaceAsync = GWT.create(EmployeeListInterface.class);

	public int id = 0;
	public int callerId;
	public int deleteID;
	public int editID;
	public Integer tbAugId;

	ComboBox<ComboBoxData> cmbStatus;
	ComboBoxData cmbStatusSelectedData;
	ListStore<ComboBoxData> cmbStatusStore;
	HiddenField<String> hfStatusId;

	ComboBox<ComboBoxData> cmbReportingMethod;
	ComboBoxData cmbReportingMethodSelectedData;
	ListStore<ComboBoxData> cmbReportingMethodStore;
	HiddenField<String> hfReportingMethodId;

	RpcProxy<PagingLoadResult<ViewEmployeeReportToBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewEmployeeReportToBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<ViewEmployeeReportToBeanModel> grid;

	ComboBox<ComboBoxData> cmbEmployee;
	ComboBoxData cmbEmployeeSelectedData;
	ListStore<ComboBoxData> cmbEmployeeStore;
	HiddenField<String> hfEmployeeId;

	AsyncCallback<TbEmployeeBeanModel> tbEmployeeAllCallback = new AsyncCallback<TbEmployeeBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeBeanModel result) {
			if (result.getOperationStatus()) {
				cmbEmployee.clear();
				cmbEmployeeStore.removeAll();

				for (TbEmployeeBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbeId().toString(), obj.getTbeName() + " (" + obj.getTbeEmployeeId() + ")");
					cmbEmployeeStore.add(data);

					if (hfEmployeeId.getValue() != null) {
						if (!"".equals(hfEmployeeId.getValue())) {
							if (obj.getTbeId() == Integer.parseInt(hfEmployeeId.getValue())) {
								cmbEmployeeSelectedData = data;
								cmbEmployee.setValue(cmbEmployeeSelectedData);
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

	public void fillCmbStatus() {
		cmbStatusStore.removeAll();

		cmbStatusStore.add(new ComboBoxData("0", "Supervisor"));
		cmbStatusStore.add(new ComboBoxData("1", "Subordinate"));

		if (hfStatusId.getValue() != null) {
			if (!"".equals(hfStatusId.getValue())) {
				if (0 == Integer.parseInt(hfStatusId.getValue())) {
					cmbStatusSelectedData = new ComboBoxData("0", "Supervisor");
					cmbStatus.setValue(cmbStatusSelectedData);
				} else {
					cmbStatusSelectedData = new ComboBoxData("1", "Subordinate");
					cmbStatus.setValue(cmbStatusSelectedData);
				}
			}
		}
	}

	public void fillCmbReportingMethod() {
		cmbReportingMethodStore.removeAll();

		cmbReportingMethodStore.add(new ComboBoxData("0", "Direct"));
		cmbReportingMethodStore.add(new ComboBoxData("1", "Indirect"));

		if (hfReportingMethodId.getValue() != null) {
			if (!"".equals(hfReportingMethodId.getValue())) {
				if (0 == Integer.parseInt(hfReportingMethodId.getValue())) {
					cmbReportingMethodSelectedData = new ComboBoxData("0", "Direct");
					cmbReportingMethod.setValue(cmbReportingMethodSelectedData);
				} else {
					cmbReportingMethodSelectedData = new ComboBoxData("1", "Indirect");
					cmbReportingMethod.setValue(cmbReportingMethodSelectedData);
				}
			}
		}
	}

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				reportToInterfaceAsync.deleteReportTo(deleteID, callback);
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

	AsyncCallback<ViewEmployeeReportToBeanModel> callbackEdit = new AsyncCallback<ViewEmployeeReportToBeanModel>() {
		@Override
		public void onSuccess(ViewEmployeeReportToBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbrtId();

				hfStatusId.setValue(result.getTbrtSpv().toString());
				fillCmbStatus();

				hfReportingMethodId.setValue(result.getTbrtReportingMethod().toString());
				fillCmbReportingMethod();
				
				hfEmployeeId.setValue(result.getTbrtSpv() == null ? "" : result.getTbrtSpv().toString());
				
				if (tbAugId != null)
					employeeListInterfaceAsync.getTbEmployeeAll(tbEmployeeAllCallback);

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

	public void addComponents() {
		setLayout(new RowLayout(Orientation.VERTICAL));

		if (tbAugId != null) {
			cmbStatusStore = new ListStore<ComboBoxData>();
			cmbStatus = new ComboBox<ComboBoxData>();
			cmbStatus.setId("cmbStatus");
			cmbStatus.setFieldLabel("Supervisor / Subordinate *");
			cmbStatus.setEmptyText("Select");
			cmbStatus.setDisplayField("value");
			cmbStatus.setForceSelection(true);
			cmbStatus.setTypeAhead(true);
			cmbStatus.setTriggerAction(TriggerAction.ALL);
			cmbStatus.setStore(cmbStatusStore);
			cmbStatus.setAllowBlank(false);
			formPanel.add(cmbStatus, formData);
			hfStatusId = new HiddenField<String>();
			formPanel.add(hfStatusId, formData);

			cmbReportingMethodStore = new ListStore<ComboBoxData>();
			cmbReportingMethod = new ComboBox<ComboBoxData>();
			cmbReportingMethod.setId("cmbReportingMethod");
			cmbReportingMethod.setFieldLabel("Reporting Method *");
			cmbReportingMethod.setEmptyText("Select");
			cmbReportingMethod.setDisplayField("value");
			cmbReportingMethod.setForceSelection(true);
			cmbReportingMethod.setTypeAhead(true);
			cmbReportingMethod.setTriggerAction(TriggerAction.ALL);
			cmbReportingMethod.setStore(cmbReportingMethodStore);
			cmbReportingMethod.setAllowBlank(false);
			formPanel.add(cmbReportingMethod, formData);
			hfReportingMethodId = new HiddenField<String>();
			formPanel.add(hfReportingMethodId, formData);

			cmbEmployee = new ComboBox<ComboBoxData>();
			cmbEmployeeStore = new ListStore<ComboBoxData>();
			cmbEmployee.setId("cmbEmployee");
			cmbEmployee.setFieldLabel("Employee *");
			cmbEmployee.setEmptyText("Select");
			cmbEmployee.setDisplayField("value");
			cmbEmployee.setForceSelection(true);
			cmbEmployee.setTypeAhead(true);
			cmbEmployee.setTriggerAction(TriggerAction.ALL);
			cmbEmployee.setStore(cmbEmployeeStore);
			cmbEmployee.setAllowBlank(false);
			formPanel.add(cmbEmployee, formData);
			hfEmployeeId = new HiddenField<String>();
			formPanel.add(hfEmployeeId, formData);

			add(formPanel, new RowData(1, -1));
		}

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewEmployeeReportToBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewEmployeeReportToBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				reportToInterfaceAsync.getReportToPaging(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<ViewEmployeeReportToBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewEmployeeReportToBeanModel> result) {
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

		pagingStore = new ListStore<ViewEmployeeReportToBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

		ColumnConfig columnConfig = new ColumnConfig("tbeEmployeeId", "<center><b>Supervisor / Subordinate</b></center>", 150);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbeId").toString();
				if (id == Integer.parseInt(data)) {
					return "Supervisor";
				} else {
					return "Subordinate";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbeEmployeeIdSpv", "<center><b>Id</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbeId").toString();
				if (id == Integer.parseInt(data)) {
					return model.get("tbeEmployeeIdSpv");
				} else {
					return model.get("tbeEmployeeId");
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbeNameSpv", "<center><b>Name</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbeId").toString();
				if (id == Integer.parseInt(data)) {
					return model.get("tbeNameSpv");
				} else {
					return model.get("tbeName");
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbrtReportingMethod", "<center><b>Reporting Method</b></center>", 120);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbrtReportingMethod").toString();
				if (0 == Integer.parseInt(data)) {
					return "Direct";
				} else {
					return "Indirect";
				}
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
							int id = Integer.parseInt(model.get("tbrtId").toString());
							reportToInterfaceAsync.getReportTo(id, callbackEdit);
						}
					});
					btnEdit.setToolTip("edit");
					btnEdit.setIcon(Resources.ICONS.form());
					layoutContainer.add(btnEdit, hBoxLayoutData);

					if (tbAugId != null) {
						Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
							@Override
							public void componentSelected(ButtonEvent ce) {
								deleteID = Integer.parseInt(model.get("tbrtId").toString());

								MessageBox.confirm("Confirmation", "Are you sure you want to delete supervisor/subordinate \"" + model.get("tbeEmployeeId") + "\" ?", deleteListener);
							}
						});
						btnDelete.setToolTip("delete");
						btnDelete.setIcon(Resources.ICONS.delete());
						layoutContainer.add(btnDelete, hBoxLayoutData);
					}

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

		grid = new Grid<ViewEmployeeReportToBeanModel>(pagingStore, cm);
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
					if (formPanel.isValid()) {
						TbReportToBeanModel beanModel = new TbReportToBeanModel();
						beanModel.setTbeId(id);

						if (editID != 0)
							beanModel.setTbrtId(editID);

						int spv = Integer.parseInt(cmbStatus.getValue().get("id").toString());
						if (spv == 0) {
							beanModel.setTbeId(id);
							if (cmbEmployee.getValue() != null)
								beanModel.setTbrtSpv(Integer.parseInt(cmbEmployee.getValue().get("id").toString()));
						} else {
							if (cmbEmployee.getValue() != null)
								beanModel.setTbrtSpv(Integer.parseInt(cmbEmployee.getValue().get("id").toString()));
							beanModel.setTbrtSpv(id);
						}

						beanModel.setTbrtReportingMethod(Integer.parseInt(cmbReportingMethod.getValue().get("id").toString()));

						reportToInterfaceAsync.submitReportTo(beanModel, callback);
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

					fillCmbStatus();
					fillCmbReportingMethod();

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

				hfStatusId.clear();
				hfReportingMethodId.clear();

				doResetComponent(formPanel);
				doLockedComponent(formPanel);

				editID = 0;

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
