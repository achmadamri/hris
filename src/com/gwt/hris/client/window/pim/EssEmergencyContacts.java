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
import com.extjs.gxt.ui.client.widget.form.FormPanel;
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
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmergencyContactBeanModel;
import com.gwt.hris.client.service.pim.EmergencyContactsInterface;
import com.gwt.hris.client.service.pim.EmergencyContactsInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.MainTabLayout;

public class EssEmergencyContacts extends EssMainCP {
	FormPanel formPanel;

	public EssEmergencyContacts(FormData formData, int id_, int callerId_) {
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

	EmergencyContactsInterfaceAsync emergencyContactsInterfaceAsync = GWT.create(EmergencyContactsInterface.class);

	public int id = 0;
	public int callerId;
	public int deleteID;
	public int editID;

	TextField<String> txtName;
	TextField<String> txtRelationship;
	TextField<String> txtHomePhone;
	TextField<String> txtMobilePhone;
	TextField<String> txtWorkPhone;

	RpcProxy<PagingLoadResult<TbEmergencyContactBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<TbEmergencyContactBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<TbEmergencyContactBeanModel> grid;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				emergencyContactsInterfaceAsync.deleteEmergencyContact(deleteID, callback);
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

	AsyncCallback<TbEmergencyContactBeanModel> callbackEdit = new AsyncCallback<TbEmergencyContactBeanModel>() {
		@Override
		public void onSuccess(TbEmergencyContactBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbecId();
				txtName.setValue(result.getTbecName());
				txtRelationship.setValue(result.getTbecRelationship());
				txtHomePhone.setValue(result.getTbecHomePhone());
				txtMobilePhone.setValue(result.getTbecMobilePhone());
				txtWorkPhone.setValue(result.getTbecWorkPhone());

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

		txtName = new TextField<String>();
		txtName.setId("txtName");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanel.add(txtName, formData);

		txtRelationship = new TextField<String>();
		txtRelationship.setId("txtRelationship");
		txtRelationship.setFieldLabel("Relationship *");
		txtRelationship.setAllowBlank(false);
		formPanel.add(txtRelationship, formData);

		txtHomePhone = new TextField<String>();
		txtHomePhone.setId("txtHomePhone");
		txtHomePhone.setFieldLabel("Home Phone");
		txtHomePhone.setAllowBlank(true);
		formPanel.add(txtHomePhone, formData);

		txtMobilePhone = new TextField<String>();
		txtMobilePhone.setId("txtMobilePhone");
		txtMobilePhone.setFieldLabel("Mobile Phone");
		txtMobilePhone.setAllowBlank(true);
		formPanel.add(txtMobilePhone, formData);

		txtWorkPhone = new TextField<String>();
		txtWorkPhone.setId("txtWorkPhone");
		txtWorkPhone.setFieldLabel("Work Phone");
		txtWorkPhone.setAllowBlank(true);
		formPanel.add(txtWorkPhone, formData);

		add(formPanel, new RowData(1, -1));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<TbEmergencyContactBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<TbEmergencyContactBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				emergencyContactsInterfaceAsync.getEmergencyContactPaging(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<TbEmergencyContactBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<TbEmergencyContactBeanModel> result) {
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

		pagingStore = new ListStore<TbEmergencyContactBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbecName", "<center><b>Name</b></center>", 150));
		columns.add(new ColumnConfig("tbecRelationship", "<center><b>Relationship</b></center>", 100));
		columns.add(new ColumnConfig("tbecHomePhone", "<center><b>Home Telephone</b></center>", 100));
		columns.add(new ColumnConfig("tbecMobilePhone", "<center><b>Mobile</b></center>", 100));
		columns.add(new ColumnConfig("tbecWorkPhone", "<center><b>Work Telephone</b></center>", 100));

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
						int id = Integer.parseInt(model.get("tbecId").toString());
						emergencyContactsInterfaceAsync.getEmergencyContact(id, callbackEdit);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit, hBoxLayoutData);

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						deleteID = Integer.parseInt(model.get("tbecId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete emergency contacts \"" + model.get("tbecName") + "\" ?", deleteListener);
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

		grid = new Grid<TbEmergencyContactBeanModel>(pagingStore, cm);
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
					TbEmergencyContactBeanModel tbEmergencyContactBeanModel = new TbEmergencyContactBeanModel();
					tbEmergencyContactBeanModel.setTbeId(id);
					if (editID != 0)
						tbEmergencyContactBeanModel.setTbecId(editID);
					tbEmergencyContactBeanModel.setTbecName(StringUtil.getInstance().getString(txtName.getValue()));
					tbEmergencyContactBeanModel.setTbecRelationship(StringUtil.getInstance().getString(txtRelationship.getValue()));
					tbEmergencyContactBeanModel.setTbecHomePhone(StringUtil.getInstance().getString(txtHomePhone.getValue()));
					tbEmergencyContactBeanModel.setTbecMobilePhone(StringUtil.getInstance().getString(txtMobilePhone.getValue()));
					tbEmergencyContactBeanModel.setTbecWorkPhone(StringUtil.getInstance().getString(txtWorkPhone.getValue()));

					emergencyContactsInterfaceAsync.submitEmergencyContact(tbEmergencyContactBeanModel, callback);
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
