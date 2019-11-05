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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedEducationBeanModel;
import com.gwt.hris.client.service.bean.TbEducationBeanModel;
import com.gwt.hris.client.service.bean.ViewEmployeeEducationBeanModel;
import com.gwt.hris.client.service.pim.AssignedEducationInterface;
import com.gwt.hris.client.service.pim.AssignedEducationInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssAssignedEducation extends EssMainCP {
	FormPanel formPanel;

	public EssAssignedEducation(FormData formData, int id_, int callerId_) {
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

	AssignedEducationInterfaceAsync AssignedEducationInterfaceAsync = GWT.create(AssignedEducationInterface.class);

	public int id = 0;
	public int callerId;
	public int deleteID;
	public int editID;

	ComboBox<ComboBoxData> cmbEducation;
	ComboBoxData cmbEducationSelectedData;
	ListStore<ComboBoxData> cmbEducationStore;
	HiddenField<String> hfPaygradeId;

	TextField<String> txtMajor;
	TextField<String> txtGpa;

	DateField dateStart;
	DateField dateEnd;

	RpcProxy<PagingLoadResult<ViewEmployeeEducationBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewEmployeeEducationBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<ViewEmployeeEducationBeanModel> grid;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				AssignedEducationInterfaceAsync.deleteAssignedEducation(deleteID, callback);
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

	AsyncCallback<TbAssignedEducationBeanModel> callbackEdit = new AsyncCallback<TbAssignedEducationBeanModel>() {
		@Override
		public void onSuccess(TbAssignedEducationBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbaeId();
				txtMajor.setValue(result.getTbaeMajor());
				txtGpa.setValue(result.getTbaeGpa().toString());
				dateStart.setValue(result.getTbaeStartDate());
				dateEnd.setValue(result.getTbaeEndDate());

				hfPaygradeId.setValue(result.getTbeduId() == null ? "" : result.getTbeduId().toString());
				AssignedEducationInterfaceAsync.getTbEducationAll(tbPaygradeAllCallback);

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

	AsyncCallback<TbEducationBeanModel> tbPaygradeAllCallback = new AsyncCallback<TbEducationBeanModel>() {
		@Override
		public void onSuccess(TbEducationBeanModel result) {
			if (result.getOperationStatus()) {
				cmbEducation.clear();
				cmbEducationStore.removeAll();

				for (TbEducationBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbeId().toString(), obj.getTbeInstitute());
					cmbEducationStore.add(data);

					if (hfPaygradeId.getValue() != null) {
						if (!"".equals(hfPaygradeId.getValue())) {
							if (obj.getTbeId() == Integer.parseInt(hfPaygradeId.getValue())) {
								cmbEducationSelectedData = data;
								cmbEducation.setValue(cmbEducationSelectedData);
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

		cmbEducation = new ComboBox<ComboBoxData>();
		cmbEducationStore = new ListStore<ComboBoxData>();
		cmbEducation.setId("cmbEducation");
		cmbEducation.setFieldLabel("Education *");
		cmbEducation.setEmptyText("Select");
		cmbEducation.setDisplayField("value");
		cmbEducation.setForceSelection(true);
		cmbEducation.setTypeAhead(true);
		cmbEducation.setTriggerAction(TriggerAction.ALL);
		cmbEducation.setStore(cmbEducationStore);
		cmbEducation.setAllowBlank(false);
		formPanel.add(cmbEducation, formData);
		hfPaygradeId = new HiddenField<String>();
		formPanel.add(hfPaygradeId, formData);

		txtMajor = new TextField<String>();
		txtMajor.setId("txtMajor");
		txtMajor.setFieldLabel("Major *");
		txtMajor.setAllowBlank(false);
		formPanel.add(txtMajor, formData);

		txtGpa = new TextField<String>();
		txtGpa.setId("txtGpa");
		txtGpa.setFieldLabel("Gpa *");
		txtGpa.setAllowBlank(false);
		formPanel.add(txtGpa, formData);

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

		proxy = new RpcProxy<PagingLoadResult<ViewEmployeeEducationBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewEmployeeEducationBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				AssignedEducationInterfaceAsync.getAssignedEducationPaging(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<ViewEmployeeEducationBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewEmployeeEducationBeanModel> result) {
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

		pagingStore = new ListStore<ViewEmployeeEducationBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbeInstitute", "<center><b>Education</b></center>", 150));
		columns.add(new ColumnConfig("tbaeMajor", "<center><b>Major/Specialization</b></center>", 150));
		columns.add(new ColumnConfig("tbaeGpa", "<center><b>GPA</b></center>", 100));

		ColumnConfig columnConfig = new ColumnConfig("tbaeStartDate", "<center><b>Start Date</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Date data = model.get("tbaeStartDate");
				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
				return fmt.format(data);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaeEndDate", "<center><b>End Date</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Date data = model.get("tbaeEndDate");
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
						int id = Integer.parseInt(model.get("tbaeId").toString());
						AssignedEducationInterfaceAsync.getAssignedEducation(id, callbackEdit);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit, hBoxLayoutData);

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						deleteID = Integer.parseInt(model.get("tbaeId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete major / specialization \"" + model.get("tbaeMajor") + "\" ?", deleteListener);
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

		grid = new Grid<ViewEmployeeEducationBeanModel>(pagingStore, cm);
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
					TbAssignedEducationBeanModel tbAssignedEducationBeanModel = new TbAssignedEducationBeanModel();
					tbAssignedEducationBeanModel.setTbempId(id);

					if (editID != 0)
						tbAssignedEducationBeanModel.setTbaeId(editID);

					tbAssignedEducationBeanModel.setTbaeMajor(txtMajor.getValue());
					tbAssignedEducationBeanModel.setTbaeGpa(Double.valueOf(txtGpa.getValue()));
					tbAssignedEducationBeanModel.setTbaeStartDate(dateStart.getValue().getTime());
					tbAssignedEducationBeanModel.setTbaeEndDate(dateEnd.getValue().getTime());

					if (cmbEducation.getValue() != null)
						tbAssignedEducationBeanModel.setTbeduId(Integer.parseInt(cmbEducation.getValue().get("id").toString()));

					AssignedEducationInterfaceAsync.submitAssignedEducation(tbAssignedEducationBeanModel, callback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty", null);
				}
			}
		});
		addButton(btnSave);

		btnAdd = new Button("Add", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				hfPaygradeId.setValue("");
				
				doUnlockedComponent(formPanel);

				AssignedEducationInterfaceAsync.getTbEducationAll(tbPaygradeAllCallback);

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

				hfPaygradeId.clear();

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
