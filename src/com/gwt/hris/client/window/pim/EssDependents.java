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
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
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
import com.gwt.hris.client.service.bean.TbDependentsBeanModel;
import com.gwt.hris.client.service.pim.DependentsInterface;
import com.gwt.hris.client.service.pim.DependentsInterfaceAsync;
import com.gwt.hris.client.util.StringUtil;
import com.gwt.hris.client.window.MainTabLayout;

public class EssDependents extends EssMainCP {
	FormPanel formPanel;

	public EssDependents(FormData formData, int id_, int callerId_) {
		super(formData);

		id = id_;
		callerId = callerId_;
		
		if (this.callerId == 0) {
			tbAugId = WindowEss.getInstance().tbLoginBeanModel.getTbaugId();
		} else {
			tbAugId = WindowEmployeeListEss.getInstance().tbLoginBeanModel.getTbaugId();
		}

		addComponents();

		addButtons();

		doResetComponent(formPanel);
		doLockedComponent(formPanel);

		if (tbAugId != null) {
			btnAdd.setVisible(true);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		} else {
			btnAdd.setVisible(false);
			btnBack.setVisible(true);
			btnCancel.setVisible(false);
			btnSave.setVisible(false);
		}

		bottomToolBar.refresh();
	}

	DependentsInterfaceAsync DependentsInterfaceAsync = GWT.create(DependentsInterface.class);

	public int id = 0;
	public int callerId;
	public int deleteID;
	public int editID;
	public Integer tbAugId;

	TextField<String> txtName;
	DateField dateDateOfBirth;
	TextField<String> txtRelationship;

	Radio rdoDependents;
	Radio rdoChildren;
	RadioGroup rdgDependents;

	RpcProxy<PagingLoadResult<TbDependentsBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<TbDependentsBeanModel> pagingStore;
	PagingToolBar bottomToolBar;
	Grid<TbDependentsBeanModel> grid;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				DependentsInterfaceAsync.deleteDependents(deleteID, callback);
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

	AsyncCallback<TbDependentsBeanModel> callbackEdit = new AsyncCallback<TbDependentsBeanModel>() {
		@Override
		public void onSuccess(TbDependentsBeanModel result) {
			if (result.getOperationStatus()) {
				doUnlockedComponent(formPanel);

				editID = result.getTbdId();
				txtName.setValue(result.getTbdName());
				dateDateOfBirth.setValue(result.getTbdDob());
				txtRelationship.setValue(result.getTbdRelationship());

				if (result.getTbdDependentsType() == null) {
					rdgDependents.clear();
				} else {
					rdgDependents.setValue(result.getTbdDependentsType() == 0 ? rdoDependents : (result.getTbdDependentsType() == 1 ? rdoChildren : null));
				}
				
				if (result.getTbdDependentsType() != null) {
					if (result.getTbdDependentsType() == 0) {
						txtRelationship.setEnabled(true);
					} else {
						txtRelationship.setEnabled(false);
					}
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

		if (tbAugId != null) {
			txtName = new TextField<String>();
			txtName.setId("txtName");
			txtName.setFieldLabel("Name *");
			txtName.setAllowBlank(false);
			formPanel.add(txtName, formData);

			dateDateOfBirth = new DateField();
			dateDateOfBirth.setFieldLabel("Date Of Birth *");
			dateDateOfBirth.setAllowBlank(false);
			formPanel.add(dateDateOfBirth, formData);

			rdoDependents = new Radio();
			rdoDependents.setBoxLabel("Dependents");
			rdoDependents.addListener(Events.OnClick, new Listener<BaseEvent>() {
				public void handleEvent(BaseEvent be) {
					txtRelationship.setEnabled(true);
				}
			});

			rdoChildren = new Radio();
			rdoChildren.setBoxLabel("Children");
			rdoChildren.addListener(Events.OnClick, new Listener<BaseEvent>() {
				public void handleEvent(BaseEvent be) {
					txtRelationship.setValue("Children");
					txtRelationship.setEnabled(false);
				}
			});

			rdgDependents = new RadioGroup();
			rdgDependents.setFieldLabel("Dependents *");
			rdgDependents.add(rdoDependents);
			rdgDependents.add(rdoChildren);
			formPanel.add(rdgDependents, formData);

			txtRelationship = new TextField<String>();
			txtRelationship.setId("txtRelationship");
			txtRelationship.setFieldLabel("Relationship *");
			txtRelationship.setAllowBlank(false);
			formPanel.add(txtRelationship, formData);

			add(formPanel, new RowData(1, -1));
		}

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		proxy = new RpcProxy<PagingLoadResult<TbDependentsBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<TbDependentsBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				DependentsInterfaceAsync.getDependentsPaging(pagingLoadConfig, "tbe_id", String.valueOf(id), new AsyncCallback<PagingLoadResult<TbDependentsBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<TbDependentsBeanModel> result) {
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

		pagingStore = new ListStore<TbDependentsBeanModel>(pagingLoader);

		bottomToolBar = new PagingToolBar(20);
		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbdName", "<center><b>Name</b></center>", 150));
		columns.add(new ColumnConfig("tbdRelationship", "<center><b>Relationship</b></center>", 100));

		ColumnConfig columnConfig = new ColumnConfig("tbdDob", "<center><b>Date of Birth</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Date data = model.get("tbdDob");
				DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd");
				return fmt.format(data);
			}
		});
		columns.add(columnConfig);

		ColumnConfig ccActions = new ColumnConfig();
		if (tbAugId != null) {
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
							int id = Integer.parseInt(model.get("tbdId").toString());
							DependentsInterfaceAsync.getDependents(id, callbackEdit);
						}
					});
					btnEdit.setToolTip("edit");
					btnEdit.setIcon(Resources.ICONS.form());
					layoutContainer.add(btnEdit, hBoxLayoutData);

					Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							deleteID = Integer.parseInt(model.get("tbdId").toString());

							MessageBox.confirm("Confirmation", "Are you sure you want to delete dependents \"" + model.get("tbdName") + "\" ?", deleteListener);
						}
					});
					btnDelete.setToolTip("delete");
					btnDelete.setIcon(Resources.ICONS.delete());
					layoutContainer.add(btnDelete, hBoxLayoutData);

					return layoutContainer;
				}
			});
			columns.add(ccActions);
		}

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

		grid = new Grid<TbDependentsBeanModel>(pagingStore, cm);
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
				if (tbAugId != null) {
					if (formPanel.isValid()) {
						if (rdgDependents.getValue() != null) {
							TbDependentsBeanModel tbDependentsBeanModel = new TbDependentsBeanModel();
							tbDependentsBeanModel.setTbeId(id);
							if (editID != 0)
								tbDependentsBeanModel.setTbdId(editID);
							tbDependentsBeanModel.setTbdName(StringUtil.getInstance().getString(txtName.getValue()));
							tbDependentsBeanModel.setTbdDob(dateDateOfBirth.getValue().getTime());
							tbDependentsBeanModel.setTbdRelationship(StringUtil.getInstance().getString(txtRelationship.getValue()));

							tbDependentsBeanModel.setTbdDependentsType(rdoDependents.getValue() == true ? 0 : (rdoChildren.getValue() == true ? 1 : null));

							DependentsInterfaceAsync.submitDependents(tbDependentsBeanModel, callback);
						} else {
							MessageBox.alert("Alert", "Required field is still empty", null);
						}
					} else {
						MessageBox.alert("Alert", "Required field is still empty", null);
					}
				}
			}
		});
		addButton(btnSave);

		btnAdd = new Button("Add", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (tbAugId != null) {
					doUnlockedComponent(formPanel);

					btnAdd.setVisible(false);
					btnBack.setVisible(false);
					btnCancel.setVisible(true);
					btnSave.setVisible(true);
				}
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
