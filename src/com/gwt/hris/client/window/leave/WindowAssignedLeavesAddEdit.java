package com.gwt.hris.client.window.leave;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbAssignedLeavesBeanModel;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;
import com.gwt.hris.client.service.leave.AssignedLeavesInterface;
import com.gwt.hris.client.service.leave.AssignedLeavesInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowAssignedLeavesAddEdit extends WindowMain {
	private static WindowAssignedLeavesAddEdit instance = new WindowAssignedLeavesAddEdit();

	public static WindowAssignedLeavesAddEdit getInstance() {
		return instance;
	}

	public WindowAssignedLeavesAddEdit() {
		window = new Window();
		window.setId("WindowAssignedLeavesAddEdit");
		window.setSize(400, 310);
		window.setHeading("Leave : Assigned Leaves");
	}

	public String strNav = "";
	public int intTbaleId = 0;

	public void show(String strNav, int intTbaleId) {
		this.strNav = strNav;
		this.intTbaleId = intTbaleId;

		super.show(true);
	}

	AssignedLeavesInterfaceAsync assignedLeavesInterfaceAsync = GWT.create(AssignedLeavesInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();

	ComboBox<ComboBoxData> cmbLeaveTypes = new ComboBox<ComboBoxData>();
	ComboBoxData cmbLeaveTypesSelectedData;
	ListStore<ComboBoxData> cmbLeaveTypesStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfLeaveTypesId = new HiddenField<String>();

	DateField dateStart;
	DateField dateEnd;
	TextArea txtaComments = new TextArea();

	LabelField lblLeaveEntitled;
	LabelField lblLeaveAvailable;
	LabelField lblLeaveTaken;

	AsyncCallback<BaseTreeModel> leaveCountCallback = new AsyncCallback<BaseTreeModel>() {
		@Override
		public void onSuccess(BaseTreeModel result) {
			if (result != null) {
				if (((Integer) result.get("leaveAvailable")) >= 0) {
					if (result.get("leaveTaken") != null) {
						if (((Integer) result.get("leaveTaken")) >= 0) {
							lblLeaveEntitled.setValue(result.get("leaveEntitled"));
							lblLeaveAvailable.setValue(result.get("leaveAvailable"));
							lblLeaveTaken.setValue(result.get("leaveTaken"));
						} else {
							MessageBox.alert("Alert", "Leave is not valid", null);
							dateStart.clear();
							dateEnd.clear();
							assignedLeavesInterfaceAsync.getLeaveCount(null, null, 1, hfId.getValue() == null ? 0 : Integer.parseInt(hfId.getValue()), leaveCountCallback);
						}
					} else {
						lblLeaveEntitled.setValue(result.get("leaveEntitled"));
						lblLeaveAvailable.setValue(result.get("leaveAvailable"));
						lblLeaveTaken.setValue(result.get("leaveTaken"));
					}
				} else {
					MessageBox.alert("Alert", "Leave over quota", null);
					dateStart.clear();
					dateEnd.clear();
					assignedLeavesInterfaceAsync.getLeaveCount(null, null, 1, hfId.getValue() == null ? 0 : Integer.parseInt(hfId.getValue()), leaveCountCallback);
				}
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
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

	AsyncCallback<TbAssignedLeavesBeanModel> getAssignedLeavesCallback = new AsyncCallback<TbAssignedLeavesBeanModel>() {
		@Override
		public void onSuccess(TbAssignedLeavesBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbaleId().toString());

				hfLeaveTypesId.setValue(result.getTbltId().toString());

				dateStart.setValue(result.getTbaleStartDate());
				dateEnd.setValue(result.getTbaleEndDate());
				txtaComments.setValue(result.getTbaleComments());

				assignedLeavesInterfaceAsync.getTbLeaveTypesAll(tbLeaveTypesAllCallback);

				assignedLeavesInterfaceAsync.getLeaveCount(result.getTbaleStartDate(), result.getTbaleEndDate(), 0, hfId.getValue() == null ? 0 : Integer.parseInt(hfId.getValue()), leaveCountCallback);

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

	AsyncCallback<ReturnBean> submitAssignedLeavesCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbAssignedLeavesBeanModel tbAssignedLeavesBeanModel = (TbAssignedLeavesBeanModel) result.get("model");
				hfId.setValue(tbAssignedLeavesBeanModel.getTbaleId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

					assignedLeavesInterfaceAsync.getTbLeaveTypesAll(tbLeaveTypesAllCallback);

					btnEdit.setVisible(false);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(true);
				} else {
					doLockedComponent(window);

					btnEdit.setVisible(true);
					btnBack.setVisible(true);
					btnCancel.setVisible(false);
					btnSave.setVisible(false);
				}

				WindowAssignedLeaves.getInstance().bottomToolBar.refresh();
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

	@Override
	public void addComponents() {
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
		formPanel.add(hfLeaveTypesId, formData);

		dateStart = new DateField();
		dateStart.setFieldLabel("Date Start *");
		dateStart.setAllowBlank(false);
		dateStart.addListener(Events.Valid, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				if (isLocked == 0) {
					assignedLeavesInterfaceAsync.getLeaveCount(dateStart.getValue(), dateEnd.getValue(), 1, hfId.getValue() == null ? 0 : Integer.parseInt(hfId.getValue()), leaveCountCallback);
				}
			}
		});
		formPanel.add(dateStart, formData);

		dateEnd = new DateField();
		dateEnd.setFieldLabel("Date End *");
		dateEnd.setAllowBlank(false);
		dateEnd.addListener(Events.Valid, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				if (isLocked == 0) {
					assignedLeavesInterfaceAsync.getLeaveCount(dateStart.getValue(), dateEnd.getValue(), 1, hfId.getValue() == null ? 0 : Integer.parseInt(hfId.getValue()), leaveCountCallback);
				}
			}
		});
		formPanel.add(dateEnd, formData);

		lblLeaveEntitled = new LabelField();
		lblLeaveEntitled.setId("lblLeaveEntitled");
		lblLeaveEntitled.setFieldLabel("Leave Entitled");
		formPanel.add(lblLeaveEntitled, formData);

		lblLeaveAvailable = new LabelField();
		lblLeaveAvailable.setId("lblLeaveAvailable");
		lblLeaveAvailable.setFieldLabel("Leave Available");
		formPanel.add(lblLeaveAvailable, formData);

		lblLeaveTaken = new LabelField();
		lblLeaveTaken.setId("lblLeaveTaken");
		lblLeaveTaken.setFieldLabel("Leave Taken");
		formPanel.add(lblLeaveTaken, formData);

		txtaComments.setId("txtaComments");
		txtaComments.setFieldLabel("Comments");
		txtaComments.setAllowBlank(true);
		formPanel.add(txtaComments, formData);

		window.add(formPanel);
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
					TbAssignedLeavesBeanModel tbAssignedLeavesBeanModel = new TbAssignedLeavesBeanModel();
					tbAssignedLeavesBeanModel.setTbaleId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					tbAssignedLeavesBeanModel.setTbltId(Integer.parseInt(cmbLeaveTypes.getValue().get("id").toString()));
					tbAssignedLeavesBeanModel.setTbaleStartDate(dateStart.getValue().getTime());
					tbAssignedLeavesBeanModel.setTbaleEndDate(dateEnd.getValue().getTime());
					tbAssignedLeavesBeanModel.setTbaleComments(StringUtil.getInstance().getString(txtaComments.getValue()));
					assignedLeavesInterfaceAsync.submitAssignedLeaves(tbAssignedLeavesBeanModel, submitAssignedLeavesCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty or invalid", null);
				}
			}
		});
		window.addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				isLocked = 0;

				doUnlockedComponent(window);

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

				doLockedComponent(window);
				assignedLeavesInterfaceAsync.getAssignedLeaves(intTbaleId, getAssignedLeavesCallback);

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
				WindowAssignedLeaves window = WindowAssignedLeaves.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	int isLocked = 1;

	@Override
	public void init() {
		hfLeaveTypesId.setValue("");

		if ("add".equals(strNav)) {
			isLocked = 0;

			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			assignedLeavesInterfaceAsync.getTbLeaveTypesAll(tbLeaveTypesAllCallback);

			assignedLeavesInterfaceAsync.getLeaveCount(null, null, 1, hfId.getValue() == null ? 0 : Integer.parseInt(hfId.getValue()), leaveCountCallback);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);
		} else if ("edit".equals(strNav)) {
			isLocked = 1;

			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			assignedLeavesInterfaceAsync.getAssignedLeaves(intTbaleId, getAssignedLeavesCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}
	}
}
