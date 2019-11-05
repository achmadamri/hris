package com.gwt.hris.client.window.time.attendance;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewAttendanceBeanModel;
import com.gwt.hris.client.service.time.attendance.AttendanceInterface;
import com.gwt.hris.client.service.time.attendance.AttendanceInterfaceAsync;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowAttendance extends WindowMain {
	private static WindowAttendance instance = new WindowAttendance();

	public static WindowAttendance getInstance() {
		return instance;
	}

	public WindowAttendance() {
		window = new Window();
		window.setId("WindowAttendance");
		window.setSize(830, 380);
		window.setHeading("Attendance : Attendance");
	}
	
	public void init() {
		interfaceAsync.getPunchStatus("", getPunchStatusCallback);
	}

	AttendanceInterfaceAsync interfaceAsync = GWT.create(AttendanceInterface.class);
	SimpleComboBox<String> cmbMonth;
	Button btnGo;
	RpcProxy<PagingLoadResult<ViewAttendanceBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	ListStore<ViewAttendanceBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(31);
	Grid<ViewAttendanceBeanModel> grid;

	int deleteID;

	AsyncCallback<ReturnBean> getPunchStatusCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				if ("true".equals(result.get("in"))) {
					btnPunchInOut.setText("Punch In");
				} else {
					btnPunchInOut.setText("Punch Out");
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

	AsyncCallback<ReturnBean> getMonthCallback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				cmbMonth.clear();

				Date dateNow = new Date();
				DateTimeFormat fmt = DateTimeFormat.getFormat("MM");

				ReturnBean datas[] = result.get("datas");
				for (ReturnBean data : datas) {
					cmbMonth.add(data.get("month").toString());
					if (fmt.format(dateNow).equals(data.get("month").toString())) {
						cmbMonth.setSimpleValue(data.get("month").toString());
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
		bottomToolBar = new PagingToolBar(31);

		toolBar.add(new LabelToolItem("Month : "));

		cmbMonth = new SimpleComboBox<String>();
		cmbMonth.setTriggerAction(TriggerAction.ALL);
		cmbMonth.setEditable(false);
		cmbMonth.setWidth(50);
		toolBar.add(cmbMonth);
		interfaceAsync.getMonth(getMonthCallback);

		btnGo = new Button("Go", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				bottomToolBar.refresh();
			}
		});
		toolBar.add(btnGo);

		panel.setTopComponent(toolBar);

		proxy = new RpcProxy<PagingLoadResult<ViewAttendanceBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewAttendanceBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getTimesheetPaging(pagingLoadConfig, cmbMonth.getSimpleValue(), null, null, new AsyncCallback<PagingLoadResult<ViewAttendanceBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewAttendanceBeanModel> result) {
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

		pagingStore = new ListStore<ViewAttendanceBeanModel>(pagingLoader);

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("tbaDate", "<center><b>Date</b></center>", 70));

		ColumnConfig columnConfig = new ColumnConfig("tbaInTime", "<center><b>Punch In</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbaInTime");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaOutTime", "<center><b>Punch Out</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbaOutTime");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaLongitude", "<center><b>Longitude</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbaLongitude");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaLatitude", "<center><b>Latitude</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbaLatitude");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbsName", "<center><b>Shift</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbsName");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbsInTime", "<center><b>Shift In</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbsInTime");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbsOutTime", "<center><b>Shift Out</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				String data = model.get("tbsOutTime");
				if (data != null) {
					return data;
				} else {
					return "-";
				}
			}
		});
		columns.add(columnConfig);

		columnConfig = new ColumnConfig("tbaInTimeDiff", "<center><b>In Late</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbaInTimeDiff");
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

		columnConfig = new ColumnConfig("tbaOutTimeDiff", "<center><b>Undertime</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbaOutTimeDiff");
				if (data == null) {
					return "-";
				} else if (data < 0) {
					data = data * -1;
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

		columnConfig = new ColumnConfig("tbaOutTimeDiff", "<center><b>Overtime</b></center>", 70);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbaOutTimeDiff");
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

		grid = new Grid<ViewAttendanceBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewAttendanceBeanModel>>() {
			public void handleEvent(GridEvent<ViewAttendanceBeanModel> be) {
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
	}

	Button btnPunchInOut;

	Button btnClose;

	@Override
	public void addButtons() {
		btnPunchInOut = new Button("Punch In", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowPunchInOutAddEdit windowPunchInOutAddEdit = WindowPunchInOutAddEdit.getInstance();
				windowPunchInOutAddEdit.show();
			}
		});
		window.addButton(btnPunchInOut);

		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}
}
