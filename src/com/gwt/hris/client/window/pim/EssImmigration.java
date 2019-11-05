package com.gwt.hris.client.window.pim;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.companyinfo.GeneralInterface;
import com.gwt.hris.client.service.admin.companyinfo.GeneralInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbImmigrationBeanModel;
import com.gwt.hris.client.service.bean.TbNegaraBeanModel;
import com.gwt.hris.client.service.pim.ImmigrationInterface;
import com.gwt.hris.client.service.pim.ImmigrationInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.MainTabLayout;

public class EssImmigration extends EssMainFP {
	EssImmigration formPanel = this;

	public EssImmigration(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;

		addComponents();

		addButtons();

		formPanel.mask("loading");
		btnEdit.setEnabled(false);
		btnBack.setEnabled(false);

		doLockedComponent(formPanel);

		ImmigrationInterfaceAsync.getImmigration(this.id, getCallback);

		btnEdit.setVisible(true);
		btnBack.setVisible(true);
		btnCancel.setVisible(false);
		btnSave.setVisible(false);
	}

	public int id = 0;
	public int callerId;

	ImmigrationInterfaceAsync ImmigrationInterfaceAsync = GWT.create(ImmigrationInterface.class);
	GeneralInterfaceAsync generalInterfaceAsync = GWT.create(GeneralInterface.class);

	AsyncCallback<TbNegaraBeanModel> tbNegaraAllCallback = new AsyncCallback<TbNegaraBeanModel>() {
		@Override
		public void onSuccess(TbNegaraBeanModel result) {
			if (result.getOperationStatus()) {
				cmbNegara.clear();
				cmbNegaraStore.removeAll();

				for (TbNegaraBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbnId().toString(), obj.getTbnNama());
					cmbNegaraStore.add(data);

					if (hfNegaraId.getValue() != null) {
						if (!"".equals(hfNegaraId.getValue())) {
							if (obj.getTbnId() == Integer.parseInt(hfNegaraId.getValue())) {
								cmbNegaraSelectedData = data;
								cmbNegara.setValue(cmbNegaraSelectedData);
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
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				TbImmigrationBeanModel beanModel = (TbImmigrationBeanModel) result.get("model");
				id = beanModel.getTbeId();

				formPanel.mask("loading");
				btnEdit.setEnabled(false);
				btnBack.setEnabled(false);

				doLockedComponent(formPanel);

				ImmigrationInterfaceAsync.getImmigration(id, getCallback);

				btnEdit.setVisible(true);
				btnBack.setVisible(true);
				btnCancel.setVisible(false);
				btnSave.setVisible(false);

				if (callerId == 1) {
					WindowEmployeeList.getInstance().refreshToolBar();
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

	AsyncCallback<TbImmigrationBeanModel> getCallback = new AsyncCallback<TbImmigrationBeanModel>() {
		@Override
		public void onSuccess(TbImmigrationBeanModel result) {
			if (result.getOperationStatus()) {
				if (result.getTbiImmigrationType() == null) {
					rdgImmigrationType.clear();
				} else {
					rdgImmigrationType.setValue(result.getTbiImmigrationType() == 0 ? rdoPassport : (result.getTbiImmigrationType() == 1 ? rdoVisa : null));
				}

				txtImmigrationNo.setValue(result.getTbiImmigrationNo());
				hfNegaraId.setValue(result.getTbnId() == null ? "" : result.getTbnId().toString());
				dateIssuedDate.setValue(result.getTbiIssuedDate());
				dateExpiryDate.setValue(result.getTbiExpiryDate());
				txtI9Status.setValue(result.getTbiL9Status());
				dateI9ReviewDate.setValue(result.getTbiL9ReviewDate());
				txtaComments.setValue(result.getTbiComments());

				generalInterfaceAsync.getTbNegaraAll(tbNegaraAllCallback);

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

	Radio rdoPassport;
	Radio rdoVisa;
	RadioGroup rdgImmigrationType;
	TextField<String> txtImmigrationNo;
	ComboBox<ComboBoxData> cmbNegara;
	ComboBoxData cmbNegaraSelectedData;
	ListStore<ComboBoxData> cmbNegaraStore;
	HiddenField<String> hfNegaraId;
	DateField dateIssuedDate;
	DateField dateExpiryDate;
	TextField<String> txtI9Status;
	DateField dateI9ReviewDate;
	TextArea txtaComments;

	public void addComponents() {
		rdoPassport = new Radio();
		rdoPassport.setBoxLabel("Passport");

		rdoVisa = new Radio();
		rdoVisa.setBoxLabel("Visa");

		rdgImmigrationType = new RadioGroup();
		rdgImmigrationType.setFieldLabel("Immigration Type");
		rdgImmigrationType.add(rdoPassport);
		rdgImmigrationType.add(rdoVisa);
		add(rdgImmigrationType, formData);

		txtImmigrationNo = new TextField<String>();
		txtImmigrationNo.setId("txtImmigrationNo");
		txtImmigrationNo.setFieldLabel("Immigration No");
		txtImmigrationNo.setAllowBlank(true);
		add(txtImmigrationNo, formData);

		cmbNegara = new ComboBox<ComboBoxData>();
		cmbNegaraStore = new ListStore<ComboBoxData>();
		cmbNegara.setId("cmbNegara");
		cmbNegara.setFieldLabel("Country");
		cmbNegara.setEmptyText("Select");
		cmbNegara.setDisplayField("value");
		cmbNegara.setForceSelection(true);
		cmbNegara.setTypeAhead(true);
		cmbNegara.setTriggerAction(TriggerAction.ALL);
		cmbNegara.setStore(cmbNegaraStore);
		cmbNegara.setAllowBlank(true);
		add(cmbNegara, formData);

		hfNegaraId = new HiddenField<String>();
		add(hfNegaraId, formData);

		dateIssuedDate = new DateField();
		dateIssuedDate.setFieldLabel("Issued Date");
		dateIssuedDate.setAllowBlank(true);
		add(dateIssuedDate, formData);

		dateExpiryDate = new DateField();
		dateExpiryDate.setFieldLabel("Expiry Date");
		dateExpiryDate.setAllowBlank(true);
		add(dateExpiryDate, formData);

		txtI9Status = new TextField<String>();
		txtI9Status.setId("txtI9Status");
		txtI9Status.setFieldLabel("I9 Status");
		txtI9Status.setAllowBlank(true);
		add(txtI9Status, formData);

		dateI9ReviewDate = new DateField();
		dateI9ReviewDate.setFieldLabel("I9 Review Date");
		dateI9ReviewDate.setAllowBlank(true);
		add(dateI9ReviewDate, formData);

		txtaComments = new TextArea();
		txtaComments.setId("txtaComments");
		txtaComments.setFieldLabel("Comments");
		txtaComments.setAllowBlank(true);
		add(txtaComments, formData);
	}

	Button btnSave;
	Button btnEdit;
	Button btnCancel;
	Button btnBack;

	public void addButtons() {
		btnSave = new Button("Save", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (formPanel.isValid()) {
					TbImmigrationBeanModel beanModel = new TbImmigrationBeanModel();
					beanModel.setTbeId(id);

					if (rdgImmigrationType.getValue() != null)
						beanModel.setTbiImmigrationType(rdoPassport.getValue() == true ? 0 : (rdoVisa.getValue() == true ? 1 : null));

					beanModel.setTbiImmigrationNo(StringUtil.getInstance().getString(txtImmigrationNo.getValue()));

					if (cmbNegara.getValue() != null)
						beanModel.setTbnId(Integer.parseInt(cmbNegara.getValue().get("id").toString()));

					if (dateIssuedDate.getValue() != null)
						beanModel.setTbiIssuedDate(dateIssuedDate.getValue().getTime());

					if (dateExpiryDate.getValue() != null)
						beanModel.setTbiExpiryDate(dateExpiryDate.getValue().getTime());

					beanModel.setTbiL9Status(StringUtil.getInstance().getString(txtI9Status.getValue()));

					if (dateI9ReviewDate.getValue() != null)
						beanModel.setTbiL9ReviewDate(dateI9ReviewDate.getValue().getTime());

					beanModel.setTbiComments(StringUtil.getInstance().getString(txtaComments.getValue()));

					ImmigrationInterfaceAsync.submitImmigration(beanModel, submitCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty", null);
				}
			}
		});
		addButton(btnSave);

		btnEdit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				doUnlockedComponent(formPanel);

				btnEdit.setVisible(false);
				btnBack.setVisible(false);
				btnCancel.setVisible(true);
				btnSave.setVisible(true);
			}
		});
		addButton(btnEdit);

		btnCancel = new Button("Cancel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				formPanel.mask("loading");

				doLockedComponent(formPanel);

				ImmigrationInterfaceAsync.getImmigration(id, getCallback);

				btnEdit.setVisible(true);
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
