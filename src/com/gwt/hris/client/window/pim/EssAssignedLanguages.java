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
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLanguagesBeanModel;
import com.gwt.hris.client.service.bean.TbLanguageBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeLanguagesBeanModel;
import com.gwt.hris.client.service.pim.AssignedLanguagesInterface;
import com.gwt.hris.client.service.pim.AssignedLanguagesInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssAssignedLanguages extends EssMainCP {
	FormPanel formPanel;

	public EssAssignedLanguages(FormData formData, int id_, int callerId_) {
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

	AssignedLanguagesInterfaceAsync assignedLanguagesInterfaceAsync = GWT.create(AssignedLanguagesInterface.class);

	public int id = 0;
	public int callerId;
	public int deleteID;
	public int editID;

	ComboBox<ComboBoxData> cmbLanguages;
	ComboBoxData cmbLanguagesSelectedData;
	ListStore<ComboBoxData> cmbLanguagesStore;
	HiddenField<String> hfLanguagesId;

	ComboBox<ComboBoxData> cmbFluency;
	ComboBoxData cmbFluencySelectedData;
	ListStore<ComboBoxData> cmbFluencyStore;
	HiddenField<String> hfFluencyId;

	ComboBox<ComboBoxData> cmbCompetency;
	ComboBoxData cmbCompetencySelectedData;
	ListStore<ComboBoxData> cmbCompetencyStore;
	HiddenField<String> hfCompetencyId;

	RpcProxy<PagingLoadResult<ViewEmployeeLanguagesBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewEmployeeLanguagesBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<ViewEmployeeLanguagesBeanModel> grid;

	public void fillCmbCompetency() {
		cmbCompetencyStore.removeAll();

		cmbCompetencyStore.add(new ComboBoxData("0", "Poor"));
		cmbCompetencyStore.add(new ComboBoxData("1", "Basic"));
		cmbCompetencyStore.add(new ComboBoxData("2", "Good"));
		cmbCompetencyStore.add(new ComboBoxData("3", "Native"));

		if (hfCompetencyId.getValue() != null) {
			if (!"".equals(hfCompetencyId.getValue())) {
				if (0 == Integer.parseInt(hfCompetencyId.getValue())) {
					cmbCompetencySelectedData = new ComboBoxData("0", "Poor");
					cmbCompetency.setValue(cmbCompetencySelectedData);
				} else if (1 == Integer.parseInt(hfCompetencyId.getValue())) {
					cmbCompetencySelectedData = new ComboBoxData("1", "Basic");
					cmbCompetency.setValue(cmbCompetencySelectedData);
				} else if (2 == Integer.parseInt(hfCompetencyId.getValue())) {
					cmbCompetencySelectedData = new ComboBoxData("1", "Good");
					cmbCompetency.setValue(cmbCompetencySelectedData);
				} else {
					cmbCompetencySelectedData = new ComboBoxData("1", "Native");
					cmbCompetency.setValue(cmbCompetencySelectedData);
				}
			}
		}
	}

	public void fillCmbFluency() {
		cmbFluencyStore.removeAll();

		cmbFluencyStore.add(new ComboBoxData("0", "Writing"));
		cmbFluencyStore.add(new ComboBoxData("1", "Speaking"));
		cmbFluencyStore.add(new ComboBoxData("2", "Reading"));

		if (hfFluencyId.getValue() != null) {
			if (!"".equals(hfFluencyId.getValue())) {
				if (0 == Integer.parseInt(hfFluencyId.getValue())) {
					cmbFluencySelectedData = new ComboBoxData("0", "Writing");
					cmbFluency.setValue(cmbFluencySelectedData);
				} else if (1 == Integer.parseInt(hfFluencyId.getValue())) {
					cmbFluencySelectedData = new ComboBoxData("1", "Speaking");
					cmbFluency.setValue(cmbFluencySelectedData);
				} else {
					cmbFluencySelectedData = new ComboBoxData("1", "Reading");
					cmbFluency.setValue(cmbFluencySelectedData);
				}
			}
		}
	}

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				assignedLanguagesInterfaceAsync.deleteAssignedLanguages(deleteID, callback);
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

	AsyncCallback<TbAssignedLanguagesBeanModel> callbackEdit = new AsyncCallback<TbAssignedLanguagesBeanModel>() {
		@Override
		public void onSuccess(TbAssignedLanguagesBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbalId();

				hfLanguagesId.setValue(result.getTblId() == null ? "" : result.getTblId().toString());
				assignedLanguagesInterfaceAsync.getTbLanguageAll(tbPaygradeAllCallback);

				hfFluencyId.setValue(result.getTbalFluency().toString());
				fillCmbFluency();

				hfCompetencyId.setValue(result.getTbalCompetency().toString());
				fillCmbCompetency();

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

	AsyncCallback<TbLanguageBeanModel> tbPaygradeAllCallback = new AsyncCallback<TbLanguageBeanModel>() {
		@Override
		public void onSuccess(TbLanguageBeanModel result) {
			if (result.getOperationStatus()) {
				cmbLanguages.clear();
				cmbLanguagesStore.removeAll();

				for (TbLanguageBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTblId().toString(), obj.getTblName());
					cmbLanguagesStore.add(data);

					if (hfLanguagesId.getValue() != null) {
						if (!"".equals(hfLanguagesId.getValue())) {
							if (obj.getTblId() == Integer.parseInt(hfLanguagesId.getValue())) {
								cmbLanguagesSelectedData = data;
								cmbLanguages.setValue(cmbLanguagesSelectedData);
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

		cmbLanguages = new ComboBox<ComboBoxData>();
		cmbLanguagesStore = new ListStore<ComboBoxData>();
		cmbLanguages.setId("cmbLanguages");
		cmbLanguages.setFieldLabel("Language *");
		cmbLanguages.setEmptyText("Select");
		cmbLanguages.setDisplayField("value");
		cmbLanguages.setForceSelection(true);
		cmbLanguages.setTypeAhead(true);
		cmbLanguages.setTriggerAction(TriggerAction.ALL);
		cmbLanguages.setStore(cmbLanguagesStore);
		cmbLanguages.setAllowBlank(false);
		formPanel.add(cmbLanguages, formData);
		hfLanguagesId = new HiddenField<String>();
		formPanel.add(hfLanguagesId, formData);

		cmbFluencyStore = new ListStore<ComboBoxData>();
		cmbFluency = new ComboBox<ComboBoxData>();
		cmbFluency.setId("cmbFluency");
		cmbFluency.setFieldLabel("Fluency *");
		cmbFluency.setEmptyText("Select");
		cmbFluency.setDisplayField("value");
		cmbFluency.setForceSelection(true);
		cmbFluency.setTypeAhead(true);
		cmbFluency.setTriggerAction(TriggerAction.ALL);
		cmbFluency.setStore(cmbFluencyStore);
		cmbFluency.setAllowBlank(false);
		formPanel.add(cmbFluency, formData);
		hfFluencyId = new HiddenField<String>();
		formPanel.add(hfFluencyId, formData);

		cmbCompetencyStore = new ListStore<ComboBoxData>();
		cmbCompetency = new ComboBox<ComboBoxData>();
		cmbCompetency.setId("cmbReportingMethod");
		cmbCompetency.setFieldLabel("Competency *");
		cmbCompetency.setEmptyText("Select");
		cmbCompetency.setDisplayField("value");
		cmbCompetency.setForceSelection(true);
		cmbCompetency.setTypeAhead(true);
		cmbCompetency.setTriggerAction(TriggerAction.ALL);
		cmbCompetency.setStore(cmbCompetencyStore);
		cmbCompetency.setAllowBlank(false);
		formPanel.add(cmbCompetency, formData);
		hfCompetencyId = new HiddenField<String>();
		formPanel.add(hfCompetencyId, formData);

		add(formPanel, new RowData(1, -1));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewEmployeeLanguagesBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewEmployeeLanguagesBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				assignedLanguagesInterfaceAsync.getAssignedLanguagesPaging(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<ViewEmployeeLanguagesBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewEmployeeLanguagesBeanModel> result) {
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

		pagingStore = new ListStore<ViewEmployeeLanguagesBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tblName", "<center><b>Language</b></center>", 150));

		ColumnConfig columnConfig = new ColumnConfig("tbalFluency", "<center><b>Fluency</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbalFluency");
				return (data == 0 ? "Writing" : (data == 1 ? "Speaking" : "Reading"));
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbalCompetency", "<center><b>Competency</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbalCompetency");
				return (data == 0 ? "Poor" : (data == 1 ? "Basic" : (data == 2 ? "Good" : "Native")));
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
						assignedLanguagesInterfaceAsync.getAssignedLanguages(id, callbackEdit);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit, hBoxLayoutData);

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						deleteID = Integer.parseInt(model.get("tbalId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete language \"" + model.get("tblName") + "\" ?", deleteListener);
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

		grid = new Grid<ViewEmployeeLanguagesBeanModel>(pagingStore, cm);
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
					TbAssignedLanguagesBeanModel tbAssignedLanguagesBeanModel = new TbAssignedLanguagesBeanModel();
					tbAssignedLanguagesBeanModel.setTbeId(id);

					if (editID != 0)
						tbAssignedLanguagesBeanModel.setTbalId(editID);

					if (cmbLanguages.getValue() != null)
						tbAssignedLanguagesBeanModel.setTblId(Integer.parseInt(cmbLanguages.getValue().get("id").toString()));

					tbAssignedLanguagesBeanModel.setTbalFluency(Integer.parseInt(cmbFluency.getValue().get("id").toString()));

					tbAssignedLanguagesBeanModel.setTbalCompetency(Integer.parseInt(cmbCompetency.getValue().get("id").toString()));

					assignedLanguagesInterfaceAsync.submitAssignedLanguages(tbAssignedLanguagesBeanModel, callback);
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

				assignedLanguagesInterfaceAsync.getTbLanguageAll(tbPaygradeAllCallback);

				fillCmbFluency();

				fillCmbCompetency();

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

				hfFluencyId.clear();
				hfCompetencyId.clear();

				hfLanguagesId.clear();

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
