package com.gwt.hris.client.window.admin.companyinfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.admin.companyinfo.CompanyPropertyInterface;
import com.gwt.hris.client.service.admin.companyinfo.CompanyPropertyInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewPropertyEmployeeBeanModel;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowCompanyProperty extends WindowMain {
	private static WindowCompanyProperty instance = new WindowCompanyProperty();

	public static WindowCompanyProperty getInstance() {
		return instance;
	}

	public WindowCompanyProperty() {
		window = new Window();
		window.setId("WindowCompanyProperty");
		window.setSize(600, 380);
		window.setHeading("Company Info : Company Property");
	}

	CompanyPropertyInterfaceAsync companyPropertyInterfaceAsync = GWT.create(CompanyPropertyInterface.class);
	SimpleComboBox<String> cmbSearch;
	TextField<String> txtSearch = new TextField<String>();
	TextField<String> txtPageSize = new TextField<String>();
	Button btnGo;
	Button btnSearch;
	Button btnSearchReset;
	RpcProxy<PagingLoadResult<ViewPropertyEmployeeBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewPropertyEmployeeBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<ViewPropertyEmployeeBeanModel> grid;

	int intTbcpID;

	Listener<MessageBoxEvent> removeAssignListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				companyPropertyInterfaceAsync.removeAssignCompanyProperty(intTbcpID, callback);
			}
		}
	};

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				companyPropertyInterfaceAsync.deleteCompanyProperty(intTbcpID, callback);
			}
		}
	};

	Listener<MessageBoxEvent> deleteBulkListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				List<ViewPropertyEmployeeBeanModel> lst = grid.getSelectionModel().getSelectedItems();

				Integer ids[] = new Integer[lst.size()];
				Iterator<ViewPropertyEmployeeBeanModel> itr = lst.iterator();
				int i = 0;
				while (itr.hasNext()) {
					ViewPropertyEmployeeBeanModel data = (ViewPropertyEmployeeBeanModel) itr.next();
					ids[i] = data.getTbcpId();
					i++;
				}
				companyPropertyInterfaceAsync.deleteBulkCompanyProperty(ids, callback);
			}
		}
	};

	AsyncCallback<ReturnBean> callback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);
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
	
	ContentPanel panel;

	@Override
	public void addComponents() {
		panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		ToolBar toolBar = new ToolBar();
		bottomToolBar = new PagingToolBar(20);

		toolBar.add(new LabelToolItem("Size : "));

		txtPageSize.setWidth(40);
		txtPageSize.setAutoValidate(true);
		txtPageSize.setRegex("[\\d]+");
		txtPageSize.setValue(String.valueOf(bottomToolBar.getPageSize()));
		toolBar.add(txtPageSize);

		btnGo = new Button("Go", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				bottomToolBar.setPageSize(Integer.parseInt(txtPageSize.getValue()));
				bottomToolBar.refresh();
			}
		});
		toolBar.add(btnGo);

		toolBar.add(new LabelToolItem("Search By : "));

		cmbSearch = new SimpleComboBox<String>();
		cmbSearch.setTriggerAction(TriggerAction.ALL);
		cmbSearch.setEditable(false);
		cmbSearch.setWidth(100);
		cmbSearch.add("Property Name");
		cmbSearch.add("Employee Name");
		toolBar.add(cmbSearch);

		toolBar.add(new LabelToolItem("Search For : "));

		toolBar.add(txtSearch);

		btnSearch = new Button("Search", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				bottomToolBar.refresh();
			}
		});
		toolBar.add(btnSearch);

		btnSearchReset = new Button("Reset", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				cmbSearch.reset();
				txtSearch.setValue("");
				bottomToolBar.refresh();
			}
		});
		toolBar.add(btnSearchReset);

		panel.setTopComponent(toolBar);

		CheckBoxSelectionModel<ViewPropertyEmployeeBeanModel> checkBoxSelectionModel = new CheckBoxSelectionModel<ViewPropertyEmployeeBeanModel>();

		proxy = new RpcProxy<PagingLoadResult<ViewPropertyEmployeeBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewPropertyEmployeeBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				companyPropertyInterfaceAsync.getCompanyPropertyPaging(pagingLoadConfig, cmbSearch.getSimpleValue(), txtSearch.getValue(), new AsyncCallback<PagingLoadResult<ViewPropertyEmployeeBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewPropertyEmployeeBeanModel> result) {
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
				
				panel.getBottomComponent().setEnabled(true);
			}
		};

		pagingLoader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy);
		pagingLoader.setRemoteSort(true);

		pagingStore = new ListStore<ViewPropertyEmployeeBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(checkBoxSelectionModel.getColumn());
		columns.add(new ColumnConfig("tbcpNama", "<center><b>Property Name</b></center>", 250));
		columns.add(new ColumnConfig("tbeName", "<center><b>Employee Name</b></center>", 100));

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
						int id = Integer.parseInt(model.get("tbcpId").toString());
						WindowCompanyPropertyAddEdit windowAddEdit = WindowCompanyPropertyAddEdit.getInstance();
						windowAddEdit.show("edit", id);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit, hBoxLayoutData);

				if (model.get("tbeId") == null) {
					Button btnAssign = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							intTbcpID = Integer.parseInt(model.get("tbcpId").toString());
							String str = model.get("tbcpNama").toString();
							WindowCompanyPropertyAssign windowAssign = WindowCompanyPropertyAssign.getInstance();
							windowAssign.show(str, intTbcpID);
						}
					});
					btnAssign.setToolTip("assign user");
					btnAssign.setIcon(Resources.ICONS.user_add());
					layoutContainer.add(btnAssign, hBoxLayoutData);
				} else {
					Button btnRemoveAssign = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							intTbcpID = Integer.parseInt(model.get("tbcpId").toString());

							MessageBox.confirm("Confirmation", "Are you sure you want to remove assigned user from property \"" + model.get("tbcpNama") + "\" ?", removeAssignListener);
						}
					});
					btnRemoveAssign.setToolTip("remove assigned user");
					btnRemoveAssign.setIcon(Resources.ICONS.user_delete());
					layoutContainer.add(btnRemoveAssign, hBoxLayoutData);
				}

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						intTbcpID = Integer.parseInt(model.get("tbcpId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete property \"" + model.get("tbcpNama") + "\" ?", deleteListener);
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

		grid = new Grid<ViewPropertyEmployeeBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewPropertyEmployeeBeanModel>>() {
			public void handleEvent(GridEvent<ViewPropertyEmployeeBeanModel> be) {
				PagingLoadConfig config = new BasePagingLoadConfig();
				config.setOffset(0);
				config.setLimit(50);

				Map<String, Object> state = grid.getState();
				if (state.containsKey("offset")) {
					int offset = (Integer) state.get("offset");
					int limit = (Integer) state.get("limit");
					config.setOffset(offset);
					config.setLimit(limit);
				}
				if (state.containsKey("sortField")) {
					config.setSortField((String) state.get("sortField"));
					config.setSortDir(SortDir.valueOf((String) state.get("sortDir")));
				}
				pagingLoader.load(config);
			}
		});
		grid.setLoadMask(true);
		grid.setAutoExpandColumn("space");
		grid.addPlugin(checkBoxSelectionModel);
		grid.setSelectionModel(checkBoxSelectionModel);

		panel.add(grid);

		panel.setBottomComponent(bottomToolBar);

		window.add(panel);
	}

	Button btnAdd;

	Button btnDelete;

	Button btnClose;

	@Override
	public void addButtons() {
		btnAdd = new Button("Add", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowCompanyPropertyAddEdit windowAddEdit = WindowCompanyPropertyAddEdit.getInstance();
				windowAddEdit.show("add", 0);
			}
		});
		window.addButton(btnAdd);

		btnDelete = new Button("Delete", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (grid.getSelectionModel().getSelectedItems().size() > 0) {
					MessageBox.confirm("Confirmation", "Are you sure you want to delete the selected property ?", deleteBulkListener);
				}
			}
		});
		window.addButton(btnDelete);

		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}
}
