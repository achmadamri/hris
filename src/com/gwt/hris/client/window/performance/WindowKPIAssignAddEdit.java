package com.gwt.hris.client.window.performance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Style.Orientation;
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
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.HeaderGroupConfig;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.TbEmployeeBeanModel;
import com.gwt.hris.client.service.bean.TbKpiAssignBeanModel;
import com.gwt.hris.client.service.bean.ViewKpiBeanModel;
import com.gwt.hris.client.service.performance.KPIAssignInterface;
import com.gwt.hris.client.service.performance.KPIAssignInterfaceAsync;
import com.gwt.hris.client.service.performance.KPIInterface;
import com.gwt.hris.client.service.performance.KPIInterfaceAsync;
import com.gwt.hris.client.util.bean.ComboBoxData;
import com.gwt.hris.client.window.WindowMain;

public class WindowKPIAssignAddEdit extends WindowMain {
	private static WindowKPIAssignAddEdit instance = new WindowKPIAssignAddEdit();

	public static WindowKPIAssignAddEdit getInstance() {
		return instance;
	}

	public WindowKPIAssignAddEdit() {
		window = new Window();
		window.setId("WindowKPIAssignAddEdit");
		window.setSize(880, 380);
		window.setHeading("Performance : KPI Assign");
	}

	public String strNav = "";
	public int id = 0;

	public void show(String strNav, int id) {
		this.strNav = strNav;
		this.id = id;

		super.show(true);
	}

	KPIInterfaceAsync interfaceAsync = GWT.create(KPIInterface.class);
	KPIAssignInterfaceAsync kpiAssignInterfaceAsync = GWT.create(KPIAssignInterface.class);

	ComboBox<ComboBoxData> cmbEmployee = new ComboBox<ComboBoxData>();
	ComboBoxData selectedData;
	ListStore<ComboBoxData> cmbEmployeeStore = new ListStore<ComboBoxData>();

	AsyncCallback<TbEmployeeBeanModel> getTbEmployeeAllCallback = new AsyncCallback<TbEmployeeBeanModel>() {
		@Override
		public void onSuccess(TbEmployeeBeanModel result) {
			if (result.getOperationStatus()) {
				cmbEmployeeStore.removeAll();

				for (TbEmployeeBeanModel obj : result.getModels()) {
					ComboBoxData data = new ComboBoxData(obj.getTbeId().toString(), obj.getTbeName());
					cmbEmployeeStore.add(data);
				}

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

	AsyncCallback<TbKpiAssignBeanModel> getCallback = new AsyncCallback<TbKpiAssignBeanModel>() {
		@Override
		public void onSuccess(TbKpiAssignBeanModel result) {
			if (result.getOperationStatus()) {
				formPanel.unmask();
				btnBack.setEnabled(true);

				kpiAssignInterfaceAsync.getTbEmployeeBeanAssign(getTbEmployeeAllCallback);
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

				doUnlockedComponent(window, -1, true);
				doResetComponent(window, -1, true);

				btnBack.setVisible(true);
				btnSave.setVisible(true);

				bottomToolBar.refresh();
				WindowKPIAssign.getInstance().bottomToolBar.refresh();
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

	FormPanel formPanelInner;
	RpcProxy<PagingLoadResult<ViewKpiBeanModel>> proxy;
	PagingLoader<PagingLoadResult<ModelData>> pagingLoader;
	GroupingStore<ViewKpiBeanModel> pagingStore;
	PagingToolBar bottomToolBar = new PagingToolBar(20);
	Grid<ViewKpiBeanModel> grid;

	@Override
	public void addComponents() {
		formPanel.setLayout(new RowLayout(Orientation.VERTICAL));

		formPanelInner = new FormPanel();
		formPanelInner.setLabelWidth(100);
		formPanelInner.setHeaderVisible(false);
		formPanelInner.setFrame(false);
		formPanelInner.setBodyBorder(false);
		formPanelInner.setBorders(false);
		formPanelInner.setStyleAttribute("backgroundColor", "#F2F2F2");

		cmbEmployee.setId("cmbEmployee");
		cmbEmployee.setFieldLabel("Employee Name *");
		cmbEmployee.setEmptyText("Select");
		cmbEmployee.setDisplayField("value");
		cmbEmployee.setForceSelection(true);
		cmbEmployee.setTypeAhead(true);
		cmbEmployee.setTriggerAction(TriggerAction.ALL);
		cmbEmployee.setStore(cmbEmployeeStore);
		cmbEmployee.setAllowBlank(false);
		cmbEmployee.addListener(Events.Select, new Listener<BaseEvent>() {
			public void handleEvent(BaseEvent be) {
				bottomToolBar.refresh();
			}
		});
		formPanelInner.add(cmbEmployee, formData);

		formPanel.add(formPanelInner, new RowData(1, -1));

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setLayout(new FitLayout());

		CheckBoxSelectionModel<ViewKpiBeanModel> checkBoxSelectionModel = new CheckBoxSelectionModel<ViewKpiBeanModel>();

		proxy = new RpcProxy<PagingLoadResult<ViewKpiBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<ViewKpiBeanModel>> callback) {
				PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
				pagingLoadConfig.set("limit", bottomToolBar.getPageSize());
				interfaceAsync.getKpiPaging(pagingLoadConfig, "tbeId", cmbEmployee.getValue() == null ? "" : cmbEmployee.getValue().get("id").toString(), new AsyncCallback<PagingLoadResult<ViewKpiBeanModel>>() {
					@Override
					public void onSuccess(PagingLoadResult<ViewKpiBeanModel> result) {
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

		pagingStore = new GroupingStore<ViewKpiBeanModel>(pagingLoader);
		pagingStore.groupBy("tbkgName");

		bottomToolBar.bind(pagingLoader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(checkBoxSelectionModel.getColumn());
		columns.add(new ColumnConfig("tbkId", "<center><b>KPI ID</b></center>", 50));
		columns.add(new ColumnConfig("tbkgName", "<center><b>KPI Group</b></center>", 100));
		columns.add(new ColumnConfig("tbkDescription", "<center><b>Description</b></center>", 200));
		columns.add(new ColumnConfig("tbkTargetNilai1", "<center><b>Nilai 1</b></center>", 100));
		columns.add(new ColumnConfig("tbkTargetNilai2", "<center><b>Nilai 2</b></center>", 100));
		columns.add(new ColumnConfig("tbkTargetNilai3", "<center><b>Nilai 3</b></center>", 100));
		columns.add(new ColumnConfig("tbkTargetNilai4", "<center><b>Nilai 4</b></center>", 100));
		columns.add(new ColumnConfig("tbkTargetNilai5", "<center><b>Nilai 5</b></center>", 100));

		ColumnConfig columnConfig = new ColumnConfig("tbkBobot", "<center><b>Bobot</b></center>", 75);
		columnConfig.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				Integer data = model.get("tbkBobot");
				return data + "%";
			}
		});
		columns.add(columnConfig);

		ColumnConfig ccActions = new ColumnConfig();
		ccActions.setId("space");
		ccActions.setHeader("");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				return null;
			}
		});
		columns.add(ccActions);

		ColumnModel cm = new ColumnModel(columns);
		cm.addHeaderGroup(0, 4, new HeaderGroupConfig("<center><b>Target Nilai</b></center>", 1, 5));

		grid = new Grid<ViewKpiBeanModel>(pagingStore, cm);
		grid.addListener(Events.Attach, new Listener<GridEvent<ViewKpiBeanModel>>() {
			public void handleEvent(GridEvent<ViewKpiBeanModel> be) {
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

		formPanel.add(panel, new RowData(1, 1));

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
					TbKpiAssignBeanModel beanModel = new TbKpiAssignBeanModel();
					beanModel.setTbeId(Integer.parseInt(cmbEmployee.getValue().get("id").toString()));

					List<ViewKpiBeanModel> lst = grid.getSelectionModel().getSelectedItems();

					Integer ids[] = new Integer[lst.size()];
					Iterator<ViewKpiBeanModel> itr = lst.iterator();
					int i = 0;
					while (itr.hasNext()) {
						ViewKpiBeanModel data = (ViewKpiBeanModel) itr.next();
						ids[i] = data.getTbkId();
						i++;
					}
					beanModel.set("tbkIds", ids);

					kpiAssignInterfaceAsync.submitKpiAssign(beanModel, submitCallback);
				} else {
					MessageBox.alert("Alert", "Required field is still empty or invalid", null);
				}
			}
		});
		window.addButton(btnSave);

		btnBack = new Button("Back", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowKPIAssign window = WindowKPIAssign.getInstance();
				window.show(true);
			}
		});
		window.addButton(btnBack);
	}

	@Override
	public void init() {
		formPanelInner.setLabelWidth(120);

		doUnlockedComponent(window, -1, true);
		doResetComponent(window, -1, true);

		btnBack.setVisible(true);
		btnSave.setVisible(true);

		kpiAssignInterfaceAsync.getTbEmployeeBeanAssign(getTbEmployeeAllCallback);
	}
}
