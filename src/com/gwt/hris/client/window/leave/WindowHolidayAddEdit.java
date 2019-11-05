package com.gwt.hris.client.window.leave;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbHolidayBeanModel;
import com.gwt.hris.client.service.leave.HolidayInterface;
import com.gwt.hris.client.service.leave.HolidayInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.WindowMain;

public class WindowHolidayAddEdit extends WindowMain {
	private static WindowHolidayAddEdit instance = new WindowHolidayAddEdit();

	public static WindowHolidayAddEdit getInstance() {
		return instance;
	}

	public WindowHolidayAddEdit() {
		window = new Window();
		window.setId("WindowHolidayAddEdit");
		window.setSize(500, 195);
		window.setHeading("Leave : Holiday");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	HolidayInterfaceAsync interfaceAsync = GWT.create(HolidayInterface.class);

	HiddenField<String> hfId = new HiddenField<String>();
	LabelField lblCode = new LabelField();
	TextField<String> txtName = new TextField<String>();
	DateField date;

	Radio rdoRepeatAnnuallyYes;
	Radio rdoRepeatAnnuallyNo;
	RadioGroup rdgRepeatAnnually;

	AsyncCallback<TbHolidayBeanModel> getCallback = new AsyncCallback<TbHolidayBeanModel>() {
		@Override
		public void onSuccess(TbHolidayBeanModel result) {
			if (result.getOperationStatus()) {
				hfId.setValue(result.getTbhId().toString());

				lblCode.setValue(result.getTbhHolidayId());
				txtName.setValue(result.getTbhName());
				date.setValue(result.getTbhDate());

				if (result.getTbhRepeatAnnually() == null) {
					rdgRepeatAnnually.clear();
				} else {
					rdgRepeatAnnually.setValue(result.getTbhRepeatAnnually() == 0 ? rdoRepeatAnnuallyYes : (result.getTbhRepeatAnnually() == 1 ? rdoRepeatAnnuallyNo : null));
				}

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

	AsyncCallback<ReturnBean> submitCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			formPanel.unmask();
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbHolidayBeanModel beanModel = (TbHolidayBeanModel) result.get("model");
				hfId.setValue(beanModel.getTbhId().toString());

				if ("add".equals(strNav)) {
					hfId = new HiddenField<String>();

					doUnlockedComponent(window);
					doResetComponent(window);

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

				WindowHoliday.getInstance().bottomToolBar.refresh();
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
		formPanel.setLabelWidth(115);

		lblCode.setId("lblCode");
		lblCode.setFieldLabel("Code");
		formPanel.add(lblCode, formData);

		txtName.setId("txtName");
		txtName.setFieldLabel("Name *");
		txtName.setAllowBlank(false);
		formPanel.add(txtName, formData);

		date = new DateField();
		date.setFieldLabel("Date *");
		date.setAllowBlank(false);
		formPanel.add(date, formData);

		rdoRepeatAnnuallyYes = new Radio();
		rdoRepeatAnnuallyYes.setBoxLabel("Yes");

		rdoRepeatAnnuallyNo = new Radio();
		rdoRepeatAnnuallyNo.setBoxLabel("No");

		rdgRepeatAnnually = new RadioGroup();
		rdgRepeatAnnually.setFieldLabel("Repeat Annually *");
		rdgRepeatAnnually.add(rdoRepeatAnnuallyYes);
		rdgRepeatAnnually.add(rdoRepeatAnnuallyNo);
		formPanel.add(rdgRepeatAnnually, formData);

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
					TbHolidayBeanModel beanModel = new TbHolidayBeanModel();
					beanModel.setTbhId(hfId.getValue() == null ? null : Integer.parseInt(hfId.getValue()));
					beanModel.setTbhName(StringUtil.getInstance().getString(txtName.getValue()));
					beanModel.setTbhDate(date.getValue().getTime());
					beanModel.setTbhRepeatAnnually(rdoRepeatAnnuallyYes.getValue() == true ? 0 : (rdoRepeatAnnuallyNo.getValue() == true ? 1 : null));
					interfaceAsync.submitHoliday(beanModel, submitCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty or invalid", null);
				}
			}
		});
		window.addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
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

				interfaceAsync.getHoliday(id, getCallback);

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
				WindowHoliday window = WindowHoliday.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		if ("add".equals(strNav)) {
			hfId = new HiddenField<String>();

			doUnlockedComponent(window);
			doResetComponent(window);

			btnEdit.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(true);

			lblCode.setVisible(false);
		} else if ("edit".equals(strNav)) {
			formPanel.mask("loading");
			btnEdit.setEnabled(false);
			btnBack.setEnabled(false);

			doLockedComponent(window);

			interfaceAsync.getHoliday(id, getCallback);

			btnEdit.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);

			lblCode.setVisible(true);
		}
	}
}
