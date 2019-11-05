package com.gwt.hris.client.window.time.timesheets;

import java.util.ArrayList;
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
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.GroupingStore;
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
import com.extjs.gxt.ui.client.widget.grid.GridGroupRenderer;
import com.extjs.gxt.ui.client.widget.grid.GroupColumnData;
import com.extjs.gxt.ui.client.widget.grid.GroupingView;
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
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewTimesheetBeanModel;
import com.gwt.hris.client.service.time.timesheets.ApprovalTimesheetInterface;
import com.gwt.hris.client.service.time.timesheets.ApprovalTimesheetInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowApprovalTimesheet extends WindowMain {
	private static WindowApprovalTimesheet instance = new WindowApprovalTimesheet();

	public static WindowApprovalTimesheet getInstance() {
		return instance;
	}

	public WindowApprovalTimesheet() {
		window = new Window();
		window.setId("WindowApprovalTimesheet");
		window.setSize(1000, 380);
		window.setHeading("Timesheets : Approval Timesheet");
	}

	ApprovalTimesheetInterfaceAsync interfaceAsync = GWT.create(ApprovalTimesheetInterface.class);
	SimpleComboBox<String> cmbStartOfWeek;
	SimpleComboBox<String> cmbSearch;
	TextField<String> txtSearch = new TextField<String>();
	Button btnSearch;
	Button btnSearchReset;
	RpcProxy<PagingLoadResult<ViewTimesheetBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	GroupingStore<ViewTimesheetBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(999999);
	Grid<ViewTimesheetBeanModel> grid;

	int deleteID;

	AsyncCallback<ReturnBean> getStartOfWeekCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				cmbStartOfWeek.clear();

				boolean setValue = false;
				ReturnBean datas[] = result.get("datas");
				for (ReturnBean data : datas) {
					cmbStartOfWeek.add(data.get("startOfWeek").toString());
					if (setValue == false) {
						cmbStartOfWeek.setSimpleValue(data.get("startOfWeek").toString());
						setValue = true;
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
	
	ContentPanel panel;

	@Override
	public void addComponents() {
		panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		ToolBar toolBar = new ToolBar();
		bottomToolBar = new PagingToolBar(20);

		toolBar.add(new LabelToolItem("Start Of Week : "));

		cmbStartOfWeek = new SimpleComboBox<String>();
		cmbStartOfWeek.setTriggerAction(TriggerAction.ALL);
		cmbStartOfWeek.setEditable(false);
		cmbStartOfWeek.setWidth(110);
		cmbStartOfWeek.addListener(Events.Select, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				bottomToolBar.refresh();
			}
		});
		toolBar.add(cmbStartOfWeek);
		interfaceAsync.getStartOfWeek(getStartOfWeekCallback);

		toolBar.add(new LabelToolItem("Search By : "));

		cmbSearch = new SimpleComboBox<String>();
		cmbSearch.setTriggerAction(TriggerAction.ALL);
		cmbSearch.setEditable(false);
		cmbSearch.setWidth(110);
		cmbSearch.add("Project");
		cmbSearch.add("Activity");
		cmbSearch.add("Start of Week");
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

		CheckBoxSelectionModel<ViewTimesheetBeanModel> checkBoxSelectionModel = new CheckBoxSelectionModel<ViewTimesheetBeanModel>();

		proxy = new RpcProxy<PagingLoadResult<ViewTimesheetBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewTimesheetBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getTimesheetPaging(pagingLoadConfig, cmbStartOfWeek.getSimpleValue(), cmbSearch.getSimpleValue(), txtSearch.getValue(), new AsyncCallback<PagingLoadResult<ViewTimesheetBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewTimesheetBeanModel> result) {
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

		pagingStore = new GroupingStore<ViewTimesheetBeanModel>(pagingLoader);
		pagingStore.groupBy("tbeName");

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(checkBoxSelectionModel.getColumn());
		columns.add(new ColumnConfig("tbtStartOfWeek", "<center><b>Start Of Week</b></center>", 100));
		columns.add(new ColumnConfig("tbcName", "<center><b>Customer</b></center>", 200));

		ColumnConfig columnConfig = new ColumnConfig("tbpName", "<center><b>Project</b></center>", 200);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return model.get("tbpName").toString() + " (" + model.get("tbpProjectId").toString() + ")";
			}
		});
		columns.add(columnConfig);

		columns.add(new ColumnConfig("tbpaName", "<center><b>Activity</b></center>", 150));

		columnConfig = new ColumnConfig("totalHour", "<center><b>Total</b></center>", 60);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return model.get("tbtTotalHour") + " hour(s)";
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbtApprovalStatus", "<center><b>Approval Status</b></center>", 100);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbtApprovalStatus");
				return (data == 0 ? "Pending" : (data == 1 ? "Approved" : "Rejected"));
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
				Integer data = model.get("tbtApprovalStatus");

				if (data == 0) {
					LayoutContainer layoutContainer = new LayoutContainer();
					HBoxLayout hBoxLayout = new HBoxLayout();
					hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
					hBoxLayout.setPack(BoxLayoutPack.CENTER);
					layoutContainer.setLayout(hBoxLayout);

					HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

					Button btnApprovalView = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							int id = Integer.parseInt(model.get("tbtId").toString());
							WindowApprovalTimesheetApprovalView windowApprovalTimesheetApprovalView = WindowApprovalTimesheetApprovalView.getInstance();
							windowApprovalTimesheetApprovalView.show(id);
						}
					});
					btnApprovalView.setToolTip("approval view");
					btnApprovalView.setIcon(Resources.ICONS.text());
					layoutContainer.add(btnApprovalView, hBoxLayoutData);

					return layoutContainer;
				} else {
					LayoutContainer layoutContainer = new LayoutContainer();
					HBoxLayout hBoxLayout = new HBoxLayout();
					hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
					hBoxLayout.setPack(BoxLayoutPack.CENTER);
					layoutContainer.setLayout(hBoxLayout);

					HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

					Button btnView = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							int id = Integer.parseInt(model.get("tbtId").toString());
							WindowApprovalTimesheetView windowApprovalTimesheetView = WindowApprovalTimesheetView.getInstance();
							windowApprovalTimesheetView.show(id);
						}
					});
					btnView.setToolTip("view");
					btnView.setIcon(Resources.ICONS.text());
					layoutContainer.add(btnView, hBoxLayoutData);

					return layoutContainer;
				}
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

		GroupingView view = new GroupingView();
		view.setShowGroupedColumn(false);
		view.setGroupRenderer(new GridGroupRenderer() {
			public String render(GroupColumnData data) {
				List<ModelData> lst = data.models;
				return "Employee Name : " + data.group + "<br>Total Hour : " + lst.get(0).get("tbtGrandTotalHour") + " hour(s)";
			}
		});

		grid = new Grid<ViewTimesheetBeanModel>(pagingStore, cm);
		grid.setView(view);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewTimesheetBeanModel>>() {
			public void handleEvent(GridEvent<ViewTimesheetBeanModel> be) {
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

	Button btnClose;

	@Override
	public void addButtons() {
		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}
}
