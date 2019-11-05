package com.gwt.hris.client.window.admin.job;

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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.admin.job.JobTitleLeaveRuleInterface;
import com.gwt.hris.client.service.admin.job.JobTitleLeaveRuleInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbJobTitleLeaveBeanModel;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;
import com.gwt.hris.client.service.bean.ViewJobTitleLeaveBeanModel;
import com.gwt.hris.client.service.leave.AssignedLeavesInterface;
import com.gwt.hris.client.service.leave.AssignedLeavesInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.pim.EssMainCP;

public class JobTitleLeaveRule extends EssMainCP {
	FormPanel formPanel;

	public JobTitleLeaveRule(FormData formData, int id_, int callerId_) {
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

	JobTitleLeaveRuleInterfaceAsync interfaceAsync = GWT.create(JobTitleLeaveRuleInterface.class);
	AssignedLeavesInterfaceAsync assignedLeavesInterfaceAsync = GWT.create(AssignedLeavesInterface.class);

	public int id = 0;
	public int callerId = 0;
	public int deleteID;
	public int editID;

	TextField<String> txtMinLeave;
	TextField<String> txtPriorDate;
	
	ComboBox<ComboBoxData> cmbLeaveTypes;
	ComboBoxData cmbLeaveTypesSelectedData;
	ListStore<ComboBoxData> cmbLeaveTypesStore;
	HiddenField<String> hfLeaveTypesId;

	RpcProxy<PagingLoadResult<ViewJobTitleLeaveBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewJobTitleLeaveBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<ViewJobTitleLeaveBeanModel> grid;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				interfaceAsync.deleteAssignedEducation(deleteID, callback);
			}
		}
	};

	AsyncCallback<TbLeaveTypesBeanModel> tbLeaveTypesAllCallback = new AsyncCallback<TbLeaveTypesBeanModel>() {
		@Override
		public void onSuccess(TbLeaveTypesBeanModel result) {
			if (result.getOperationStatus()) {
				cmbLeaveTypesStore.removeAll();

				for (TbLeaveTypesBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbltId().toString(), obj.getTbltName());
					cmbLeaveTypesStore.add(data);

					if (hfLeaveTypesId.getValue() != null) {
						if (!"".equals(hfLeaveTypesId.getValue())) {
							if (obj.getTbltId() == Integer.parseInt(hfLeaveTypesId.getValue())) {
								cmbLeaveTypesSelectedData = data;
								cmbLeaveTypes.setValue(cmbLeaveTypesSelectedData);
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

	AsyncCallback<TbJobTitleLeaveBeanModel> callbackEdit = new AsyncCallback<TbJobTitleLeaveBeanModel>() {
		@Override
		public void onSuccess(TbJobTitleLeaveBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbjtlId();
				txtMinLeave.setValue(result.getTbjtlMin().toString());
				txtPriorDate.setValue(result.getTbjtlPriorDate().toString());

				hfLeaveTypesId.setValue(result.getTbltId().toString());
				
				assignedLeavesInterfaceAsync.getTbLeaveTypesAll(tbLeaveTypesAllCallback);

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

		formPanel = new FormPanel();
		formPanel.setLabelWidth(100);
		formPanel.setHeaderVisible(false);
		formPanel.setFrame(false);
		formPanel.setBodyBorder(false);
		formPanel.setBorders(false);
		formPanel.setStyleAttribute("backgroundColor", "#F2F2F2");
		
		cmbLeaveTypes = new ComboBox<ComboBoxData>();
		cmbLeaveTypesStore = new ListStore<ComboBoxData>();
		cmbLeaveTypes.setId("cmbLeaveTypes");
		cmbLeaveTypes.setFieldLabel("Leave Type *");
		cmbLeaveTypes.setEmptyText("Select");
		cmbLeaveTypes.setDisplayField("value");
		cmbLeaveTypes.setForceSelection(true);
		cmbLeaveTypes.setTypeAhead(true);
		cmbLeaveTypes.setTriggerAction(TriggerAction.ALL);
		cmbLeaveTypes.setStore(cmbLeaveTypesStore);
		cmbLeaveTypes.setAllowBlank(false);
		formPanel.add(cmbLeaveTypes, formData);
		hfLeaveTypesId = new HiddenField<String>();
		formPanel.add(hfLeaveTypesId, formData);

		txtMinLeave = new TextField<String>();
		txtMinLeave.setId("txtMinLeave");
		txtMinLeave.setFieldLabel("Min Leave *");
		txtMinLeave.setAllowBlank(false);
		formPanel.add(txtMinLeave, formData);

		txtPriorDate = new TextField<String>();
		txtPriorDate.setId("txtPriorDate");
		txtPriorDate.setFieldLabel("Prior Date *");
		txtPriorDate.setAllowBlank(false);
		formPanel.add(txtPriorDate, formData);

		add(formPanel, new RowData(1, -1));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<ViewJobTitleLeaveBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewJobTitleLeaveBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getJobTitleLeavePaging(pagingLoadConfig, "tbjt_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<ViewJobTitleLeaveBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewJobTitleLeaveBeanModel> result) {
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

		pagingStore = new ListStore<ViewJobTitleLeaveBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbltName", "<center><b>Leave Name</b></center>", 150));
		columns.add(new ColumnConfig("tbjtlMin", "<center><b>Min Leave</b></center>", 75));
		columns.add(new ColumnConfig("tbjtlPriorDate", "<center><b>Prior Date</b></center>", 75));

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
						int id = Integer.parseInt(model.get("tbjtlId").toString());
						interfaceAsync.getJobTitleLeave(id, callbackEdit);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit, hBoxLayoutData);

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						deleteID = Integer.parseInt(model.get("tbjtlId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete leave rule \"" + model.get("tbltName") + "\" ?", deleteListener);
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

		grid = new Grid<ViewJobTitleLeaveBeanModel>(pagingStore, cm);
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
					TbJobTitleLeaveBeanModel tbAssignedEducationBeanModel = new TbJobTitleLeaveBeanModel();
					tbAssignedEducationBeanModel.setTbjtId(id);

					if (editID != 0)
						tbAssignedEducationBeanModel.setTbjtlId(editID);

					tbAssignedEducationBeanModel.setTbjtlMin(Integer.valueOf(txtMinLeave.getValue()));
					tbAssignedEducationBeanModel.setTbjtlPriorDate(Integer.valueOf(txtPriorDate.getValue()));

					if (cmbLeaveTypes.getValue() != null)
						tbAssignedEducationBeanModel.setTbltId(Integer.parseInt(cmbLeaveTypes.getValue().get("id").toString()));
					
					interfaceAsync.submitAssignedEducation(tbAssignedEducationBeanModel, callback);
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
				
				assignedLeavesInterfaceAsync.getTbLeaveTypesAll(tbLeaveTypesAllCallback);

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

				editID = 0;
				
				hfLeaveTypesId.clear();

				doResetComponent(formPanel);
				doLockedComponent(formPanel);

				btnAdd.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);
			}
		});
		addButton(btnCancel);

		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowJobTitle window = WindowJobTitle.getInstance();
				window.show(true);
			}
		});
		addButton(btnBack);
	}
}
