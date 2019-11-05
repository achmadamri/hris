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
import com.gwt.hris.client.service.bean.TbAssignedLeavesBeanModel;
import com.gwt.hris.client.service.bean.TbLeaveTypesBeanModel;
import com.gwt.hris.client.service.leave.ApprovalLeavesInterface;
import com.gwt.hris.client.service.leave.ApprovalLeavesInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowApprovalLeavesView extends WindowMain {
	private static WindowApprovalLeavesView instance = new WindowApprovalLeavesView();

	public static WindowApprovalLeavesView getInstance() {
		return instance;
	}

	public WindowApprovalLeavesView() {
		window = new Window();
		window.setId("WindowApprovalLeavesView");
		window.setSize(400, 310);
		window.setHeading("Leave : Approval Leave");
	}

	public int intTbaleId = 0;

	public void show(int intTbaleId) {
		this.intTbaleId = intTbaleId;

		super.show(true);
	}

	ApprovalLeavesInterfaceAsync approvalLeavesInterfaceAsync = GWT.create(ApprovalLeavesInterface.class);

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
							approvalLeavesInterfaceAsync.getLeaveCount(0, null, null, 1, leaveCountCallback);
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
					approvalLeavesInterfaceAsync.getLeaveCount(0, null, null, 1, leaveCountCallback);
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

	AsyncCallback<TbAssignedLeavesBeanModel> getApprovalLeavesCallback = new AsyncCallback<TbAssignedLeavesBeanModel>() {
		@Override
		public void onSuccess(TbAssignedLeavesBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbaleId().toString());

				hfLeaveTypesId.setValue(result.getTbltId().toString());

				dateStart.setValue(result.getTbaleStartDate());
				dateEnd.setValue(result.getTbaleEndDate());
				txtaComments.setValue(result.getTbaleComments());

				approvalLeavesInterfaceAsync.getTbLeaveTypesAll(tbLeaveTypesAllCallback);

				approvalLeavesInterfaceAsync.getLeaveCount(Integer.parseInt(hfId.getValue()), result.getTbaleStartDate(), result.getTbaleEndDate(), 0, leaveCountCallback);

				formPanel.unmask();
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

	@Override
	public void addComponents() {
		cmbLeaveTypes.setId("cmbCountry");
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
					approvalLeavesInterfaceAsync.getLeaveCount(Integer.parseInt(hfId.getValue()), dateStart.getValue(), dateEnd.getValue(), 1, leaveCountCallback);
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
					approvalLeavesInterfaceAsync.getLeaveCount(Integer.parseInt(hfId.getValue()), dateStart.getValue(), dateEnd.getValue(), 1, leaveCountCallback);
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

	Button btnBack;

	@Override
	public void addButtons() {
		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowApprovalLeaves window = WindowApprovalLeaves.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	int isLocked = 1;

	@Override
	public void init() {
		hfLeaveTypesId.setValue("");

		isLocked = 1;

		formPanel.mask("loading");
		btnBack.setEnabled(false);

		doLockedComponent(window);

		approvalLeavesInterfaceAsync.getAssignedLeaves(intTbaleId, getApprovalLeavesCallback);

		btnBack.setVisible(true);
	}
}
