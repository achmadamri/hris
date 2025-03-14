package com.gwt.hris.client.window.admin.time;

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
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
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
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewReportEmployeeAttendanceBeanModel;
import com.gwt.hris.client.service.reports.EmployeeAttendanceInterface;
import com.gwt.hris.client.service.reports.EmployeeAttendanceInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;
import com.gwt.hris.client.window.time.attendance.WindowPunchDetail;

public class WindowAdminAttendance extends WindowMain {
	private static WindowAdminAttendance instance = new WindowAdminAttendance();

	public static WindowAdminAttendance getInstance() {
		return instance;
	}

	public WindowAdminAttendance() {
		window = new Window();
		window.setId("WindowAdminAttendance");
		window.setSize(940, 380);
		window.setHeading("Time : Admin Employee Attendance");
	}

	EmployeeAttendanceInterfaceAsync interfaceAsync = GWT.create(EmployeeAttendanceInterface.class);
	SimpleComboBox<String> cmbSearch;
	TextField<String> txtSearch = new TextField<String>();
	TextField<String> txtPageSize = new TextField<String>();
	Button btnGo;
	Button btnSearch;
	Button btnSearchReset;
	RpcProxy<PagingLoadResult<ViewReportEmployeeAttendanceBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewReportEmployeeAttendanceBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<ViewReportEmployeeAttendanceBeanModel> grid;

	int deleteID;

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
		cmbSearch.setWidth(110);
		cmbSearch.add("Name");
		cmbSearch.add("Date");
		cmbSearch.add("In Time");
		cmbSearch.add("In Time Diff");
		cmbSearch.add("Out Time");
		cmbSearch.add("Out Time Diff");
		cmbSearch.add("In Note");
		cmbSearch.add("Out Note");
		cmbSearch.add("Shift ID");
		cmbSearch.add("Shift Name");
		cmbSearch.add("In Name");
		cmbSearch.add("Out Name");
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

		proxy = new RpcProxy<PagingLoadResult<ViewReportEmployeeAttendanceBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewReportEmployeeAttendanceBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getPaging(pagingLoadConfig, cmbSearch.getSimpleValue(), txtSearch.getValue(), new AsyncCallback<PagingLoadResult<ViewReportEmployeeAttendanceBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewReportEmployeeAttendanceBeanModel> result) {
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

		pagingStore = new ListStore<ViewReportEmployeeAttendanceBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbeName", "<center><b>Name</b></center>", 100));
		columns.add(new ColumnConfig("rtbaDate", "<center><b>Date</b></center>", 70));
		columns.add(new ColumnConfig("rtbaInTime", "<center><b>Punch In</b></center>", 70));
		columns.add(new ColumnConfig("rtbaOutTime", "<center><b>Punch Out</b></center>", 70));
		columns.add(new ColumnConfig("tbsName", "<center><b>Shift</b></center>", 70));
		columns.add(new ColumnConfig("tbsInTime", "<center><b>Shift In</b></center>", 70));
		columns.add(new ColumnConfig("tbsOutTime", "<center><b>Shift Out</b></center>", 70));
		
		ColumnConfig columnConfig = new ColumnConfig("rtbaInTimeDiff", "<center><b>In Late</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("rtbaInTimeDiff");
				if (data == null) {
					return "-";
				} else if (data > 0) {
					int hours = (int) ((data / (1000 * 60 * 60)) % 24);
					int minutes = (int) ((data / (1000 * 60)) % 60);
					int seconds = (int) (data / 1000) % 60;

					String strHours = hours < 9 ? "0" + hours : "" + hours;
					String strMinutes = minutes < 9 ? "0" + minutes : "" + minutes;
					String strSeconds = seconds < 9 ? "0" + seconds : "" + seconds;

					return strHours + ":" + strMinutes + ":" + strSeconds;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);
		
		columnConfig = new ColumnConfig("rtbaOutTimeDiff", "<center><b>Undertime</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("rtbaOutTimeDiff");
				if (data == null) {
					return "-";
				} else if (data > 0) {
					int hours = (int) ((data / (1000 * 60 * 60)) % 24);
					int minutes = (int) ((data / (1000 * 60)) % 60);
					int seconds = (int) (data / 1000) % 60;

					String strHours = hours < 9 ? "0" + hours : "" + hours;
					String strMinutes = minutes < 9 ? "0" + minutes : "" + minutes;
					String strSeconds = seconds < 9 ? "0" + seconds : "" + seconds;

					return strHours + ":" + strMinutes + ":" + strSeconds;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);
		
		columnConfig = new ColumnConfig("rtbaOutTimeDiff", "<center><b>Overtime</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("rtbaOutTimeDiff");
				if (data == null) {
					return "-";
				} else if (data > 0) {
					int hours = (int) ((data / (1000 * 60 * 60)) % 24);
					int minutes = (int) ((data / (1000 * 60)) % 60);
					int seconds = (int) (data / 1000) % 60;

					String strHours = hours < 9 ? "0" + hours : "" + hours;
					String strMinutes = minutes < 9 ? "0" + minutes : "" + minutes;
					String strSeconds = seconds < 9 ? "0" + seconds : "" + seconds;

					return strHours + ":" + strMinutes + ":" + strSeconds;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);
		
		columns.add(new ColumnConfig("rtbaInNote", "<center><b>In Note</b></center>", 70));
		columns.add(new ColumnConfig("rtbaOutNote", "<center><b>Out Note</b></center>", 70));
		
		ColumnConfig ccActions = new ColumnConfig();
		ccActions.setId("actions");
		ccActions.setWidth(120);
		ccActions.setHeader("<center><b>Actions</b></center>");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				LayoutContainer layoutContainer = new LayoutContainer();

				String inTime = model.get("tbaInTime");
				String outTime = model.get("tbaOutTime");

				if (inTime != null) {
					HBoxLayout hBoxLayout = new HBoxLayout();
					hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
					hBoxLayout.setPack(BoxLayoutPack.CENTER);
					layoutContainer.setLayout(hBoxLayout);

					HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

					Button btnApprovalView = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							String strDate = model.get("tbaDate").toString();
							WindowPunchDetail windowPunchDetail = WindowPunchDetail.getInstance();
							windowPunchDetail.show("inDetail", strDate);
						}
					});
					btnApprovalView.setToolTip("punch in detail");
					btnApprovalView.setIcon(Resources.ICONS.text());
					layoutContainer.add(btnApprovalView, hBoxLayoutData);
				}

				if (outTime != null) {
					HBoxLayout hBoxLayout = new HBoxLayout();
					hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
					hBoxLayout.setPack(BoxLayoutPack.CENTER);
					layoutContainer.setLayout(hBoxLayout);

					HBoxLayoutData hBoxLayoutData = new HBoxLayoutData(new Margins(0, 5, 0, 0));

					Button btnApprovalView = new Button("", new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							String strDate = model.get("tbaDate").toString();
							WindowPunchDetail windowPunchDetail = WindowPunchDetail.getInstance();
							windowPunchDetail.show("outDetail", strDate);
						}
					});
					btnApprovalView.setToolTip("punch out detail");
					btnApprovalView.setIcon(Resources.ICONS.text());
					layoutContainer.add(btnApprovalView, hBoxLayoutData);
				}

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

		grid = new Grid<ViewReportEmployeeAttendanceBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewReportEmployeeAttendanceBeanModel>>() {
			public void handleEvent(GridEvent<ViewReportEmployeeAttendanceBeanModel> be) {
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

		panel.add(grid);

		panel.setBottomComponent(bottomToolBar);

		window.add(panel);
		window.add(formPanel);
	}

	Button btnExportExcel;

	Button btnClose;

	@Override
	public void addButtons() {
		btnExportExcel = new Button("Export Excel", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				String url = GWT.getHostPageBaseURL() + "excel" + "?name=employeereportattendance&searchkey=" + cmbSearch.getSimpleValue() + "&searchvalue=" + txtSearch.getValue();

				com.google.gwt.user.client.Window.open(url, "", "");
			}
		});
		btnExportExcel.setIcon(Resources.ICONS.excel16());
		window.addButton(btnExportExcel);

		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}
}
