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
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
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
import com.gwt.hris.client.service.bean.TbWorkExperienceBeanModel;
import com.gwt.hris.client.service.pim.WorkExperienceInterface;
import com.gwt.hris.client.service.pim.WorkExperienceInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.MainTabLayout;

public class EssWorkExperience extends EssMainCP {
	FormPanel formPanel;

	public EssWorkExperience(FormData formData, int id_, int callerId_) {
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

	WorkExperienceInterfaceAsync WorkExperienceInterfaceAsync = GWT.create(WorkExperienceInterface.class);

	public int id = 0;
	public int callerId;
	public int deleteID;
	public int editID;

	TextField<String> txtEmployer;
	TextField<String> txtJobTitle;
	TextArea txtaComments;
	DateField dateStartDate;
	DateField dateEndDate;
	Radio rdoYes;
	Radio rdoNo;
	RadioGroup rdgInternal;

	RpcProxy<PagingLoadResult<TbWorkExperienceBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<TbWorkExperienceBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<TbWorkExperienceBeanModel> grid;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				WorkExperienceInterfaceAsync.deleteWorkExperience(deleteID, callback);
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

	AsyncCallback<TbWorkExperienceBeanModel> callbackEdit = new AsyncCallback<TbWorkExperienceBeanModel>() {
		@Override
		public void onSuccess(TbWorkExperienceBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbweId();
				txtEmployer.setValue(result.getTbweEmployer());

				txtJobTitle.setValue(result.getTbweJobTitle());
				txtaComments.setValue(result.getTbweComments());
				dateStartDate.setValue(result.getTbweStartDate());
				dateEndDate.setValue(result.getTbweEndDate());

				if (result.getTbweInternal() == null) {
					rdgInternal.clear();
				} else {
					rdgInternal.setValue(result.getTbweInternal() == 0 ? rdoYes : (result.getTbweInternal() == 1 ? rdoNo : null));
				}

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

		txtEmployer = new TextField<String>();
		txtEmployer.setId("txtEmployer");
		txtEmployer.setFieldLabel("Employer *");
		txtEmployer.setAllowBlank(false);
		formPanel.add(txtEmployer, formData);

		txtJobTitle = new TextField<String>();
		txtJobTitle.setId("txtJobTitle");
		txtJobTitle.setFieldLabel("Job Title *");
		txtJobTitle.setAllowBlank(false);
		formPanel.add(txtJobTitle, formData);

		txtaComments = new TextArea();
		txtaComments.setId("txtaComments");
		txtaComments.setFieldLabel("Comments");
		txtaComments.setAllowBlank(true);
		formPanel.add(txtaComments, formData);

		dateStartDate = new DateField();
		dateStartDate.setFieldLabel("Start Date *");
		dateStartDate.setAllowBlank(false);
		formPanel.add(dateStartDate, formData);

		dateEndDate = new DateField();
		dateEndDate.setFieldLabel("End Date *");
		dateEndDate.setAllowBlank(false);
		formPanel.add(dateEndDate, formData);

		rdoYes = new Radio();
		rdoYes.setBoxLabel("Yes");

		rdoNo = new Radio();
		rdoNo.setBoxLabel("No");

		rdgInternal = new RadioGroup();
		rdgInternal.setFieldLabel("Internal *");
		rdgInternal.add(rdoYes);
		rdgInternal.add(rdoNo);
		formPanel.add(rdgInternal, formData);

		add(formPanel, new RowData(1, -1));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<TbWorkExperienceBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<TbWorkExperienceBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				WorkExperienceInterfaceAsync.getWorkExperiencePaging(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<TbWorkExperienceBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<TbWorkExperienceBeanModel> result) {
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

		pagingStore = new ListStore<TbWorkExperienceBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbweEmployer", "<center><b>Employer</b></center>", 150));
		columns.add(new ColumnConfig("tbweJobTitle", "<center><b>Job Title</b></center>", 150));

		ColumnConfig columnConfig = new ColumnConfig("tbweStartDate", "<center><b>Start Date</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Date data = model.get("tbweStartDate");
				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
				return fmt.format(data);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbweEndDate", "<center><b>End Date</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Date data = model.get("tbweEndDate");
				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
				return fmt.format(data);
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbweInternal", "<center><b>Internal</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbweInternal");
				return (data == 0 ? "Yes" : "No");
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
						int id = Integer.parseInt(model.get("tbweId").toString());
						WorkExperienceInterfaceAsync.getWorkExperience(id, callbackEdit);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit, hBoxLayoutData);

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						deleteID = Integer.parseInt(model.get("tbweId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete your work experience with \"" + model.get("tbweEmployer") + ", " + model.get("tbweJobTitle") + "\" ?", deleteListener);
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

		grid = new Grid<TbWorkExperienceBeanModel>(pagingStore, cm);
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
					TbWorkExperienceBeanModel tbWorkExperienceBeanModel = new TbWorkExperienceBeanModel();
					tbWorkExperienceBeanModel.setTbeId(id);
					if (editID != 0)
						tbWorkExperienceBeanModel.setTbweId(editID);
					tbWorkExperienceBeanModel.setTbweEmployer(StringUtil.getInstance().getString(txtEmployer.getValue()));
					tbWorkExperienceBeanModel.setTbweJobTitle(StringUtil.getInstance().getString(txtJobTitle.getValue()));
					tbWorkExperienceBeanModel.setTbweComments(StringUtil.getInstance().getString(txtaComments.getValue()));
					tbWorkExperienceBeanModel.setTbweStartDate(dateStartDate.getValue().getTime());
					tbWorkExperienceBeanModel.setTbweEndDate(dateEndDate.getValue().getTime());
					tbWorkExperienceBeanModel.setTbweInternal(rdoYes.getValue() == true ? 0 : (rdoNo.getValue() == true ? 1 : null));

					WorkExperienceInterfaceAsync.submitWorkExperience(tbWorkExperienceBeanModel, callback);
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
