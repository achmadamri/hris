package com.gwt.hris.client.window.admin.time;

import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.time.EmployeeShiftInterface;
import com.gwt.hris.client.service.admin.time.EmployeeShiftInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbShiftBeanModel;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowEmployeeShiftSet extends WindowMain {
	private static WindowEmployeeShiftSet instance = new WindowEmployeeShiftSet();

	public static WindowEmployeeShiftSet getInstance() {
		return instance;
	}

	public WindowEmployeeShiftSet() {
		window = new Window();
		window.setId("WindowEmployeeShiftSet");
		window.setSize(500, 125);
		window.setHeading("Time : Employee Shift Set");
	}

	public int tbeId = 0;

	public void show(int tbeId) {
		this.tbeId = tbeId;

		super.show(true);
	}

	EmployeeShiftInterfaceAsync interfaceAsync = GWT.create(EmployeeShiftInterface.class);

	ComboBox<ComboBoxData> cmbShift = new ComboBox<ComboBoxData>();
	ComboBoxData cmbShiftSelectedData;
	ListStore<ComboBoxData> cmbShiftStore = new ListStore<ComboBoxData>();
	HiddenField<String> hfShiftId = new HiddenField<String>();

	AsyncCallback<TbShiftBeanModel> tbShiftAllCallback = new AsyncCallback<TbShiftBeanModel>() {
		@Override
		public void onSuccess(TbShiftBeanModel result) {
			if (result.getOperationStatus()) {
				cmbShiftStore.removeAll();

				for (TbShiftBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbsId().toString(), obj.getTbsName());
					cmbShiftStore.add(data);

					if (hfShiftId.getValue() != null) {
						if (!"".equals(hfShiftId.getValue())) {
							if (obj.getTbsId() == Integer.parseInt(hfShiftId.getValue())) {
								cmbShiftSelectedData = data;
								cmbShift.setValue(cmbShiftSelectedData);
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

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				WindowEmployeeShiftList window = WindowEmployeeShiftList.getInstance();
				window.show(true);
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
		cmbShift.setId("cmbCountry");
		cmbShift.setFieldLabel("Leave Type *");
		cmbShift.setEmptyText("Select");
		cmbShift.setDisplayField("value");
		cmbShift.setForceSelection(true);
		cmbShift.setTypeAhead(true);
		cmbShift.setTriggerAction(TriggerAction.ALL);
		cmbShift.setStore(cmbShiftStore);
		cmbShift.setAllowBlank(false);
		formPanel.add(cmbShift, formData);
		formPanel.add(hfShiftId, formData);

		window.add(formPanel);
	}

	Button btnSave;
	Button btnBack;

	@Override
	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					formPanel.mask("Saving...");
					List<ViewAttendanceBeanModel> lst = WindowEmployeeShiftList.getInstance().grid.getSelectionModel().getSelectedItems();

					String strDates[] = new String[lst.size()];
					Iterator<ViewAttendanceBeanModel> itr = lst.iterator();
					int i = 0;
					while (itr.hasNext()) {
						ViewAttendanceBeanModel data = (ViewAttendanceBeanModel) itr.next();
						strDates[i] = data.getTbaDate();
						i++;
					}

					int tbsId = Integer.parseInt(cmbShift.getValue().get("id").toString());

					interfaceAsync.submitEmployeeShifts(tbeId, tbsId, strDates, submitCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty or invalid", null);
				}
			}
		});
		window.addButton(btnSave);

		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowEmployeeShiftList window = WindowEmployeeShiftList.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		interfaceAsync.getTbShiftAll(tbShiftAllCallback);

		doUnlockedComponent(window);
		doResetComponent(window);

		btnBack.setVisible(true);
		btnSave.setVisible(true);
	}
}
