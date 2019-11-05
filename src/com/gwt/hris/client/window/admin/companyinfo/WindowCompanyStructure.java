package com.gwt.hris.client.window.admin.companyinfo;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout.HBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.resources.Resources;
import com.gwt.hris.client.service.admin.companyinfo.CompanyStructureInterface;
import com.gwt.hris.client.service.admin.companyinfo.CompanyStructureInterfaceAsync;
import com.gwt.hris.client.service.bean.ReturnBean;
import com.gwt.hris.client.service.bean.ViewCompanyOrganizationBeanModel;
import com.gwt.hris.client.window.MainTabLayout;
import com.gwt.hris.client.window.WindowMain;

public class WindowCompanyStructure extends WindowMain {
	private static WindowCompanyStructure instance = new WindowCompanyStructure();

	public static WindowCompanyStructure getInstance() {
		return instance;
	}

	public WindowCompanyStructure() {
		window = new Window();
		window.setId("WindowCompanyStructure");
		window.setSize(620, 300);
		window.setHeading("Company Info : Company Structure");
	}

	CompanyStructureInterfaceAsync companyStructureInterface = GWT.create(CompanyStructureInterface.class);

	RpcProxy<List<ViewCompanyOrganizationBeanModel>> proxy;
	TreeLoader<ViewCompanyOrganizationBeanModel> loader;
	TreeStore<ViewCompanyOrganizationBeanModel> store;

	int deleteID;

	Listener<MessageBoxEvent> deleteListener = new Listener<MessageBoxEvent>() {
		public void handleEvent(MessageBoxEvent ce) {
			String strBtnText = ce.getButtonClicked().getText();

			if ("Yes".equals(strBtnText)) {
				companyStructureInterface.deleteTbOrganization(deleteID, callback);
			}
		}
	};

	AsyncCallback<ReturnBean> callback = new AsyncCallback<ReturnBean>() {
		@Override
		public void onSuccess(ReturnBean result) {
			if (result.getOperationStatus()) {
				MessageBox.info("Info", "Operation Success.<br/><br/><br/>Message :<br/>" + result.getMessage(), null);

				loader.load();
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

	@Override
	public void addComponents() {
		// data proxy
		proxy = new RpcProxy<List<ViewCompanyOrganizationBeanModel>>() {
			@Override
			protected void load(Object loadConfig, final AsyncCallback<List<ViewCompanyOrganizationBeanModel>> callback) {
				companyStructureInterface.getViewCompanyOrganizationsList((ViewCompanyOrganizationBeanModel) loadConfig, new AsyncCallback<List<ViewCompanyOrganizationBeanModel>>() {
					@Override
					public void onSuccess(List<ViewCompanyOrganizationBeanModel> result) {
						if (result.size() > 0) {
							if (result.get(0).get("messages") == null) {
								callback.onSuccess(result);
							} else {
								MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + result.get(0).get("messages").toString(), null);
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

		// tree loader
		loader = new BaseTreeLoader<ViewCompanyOrganizationBeanModel>(proxy) {
			@Override
			public boolean hasChildren(ViewCompanyOrganizationBeanModel parent) {
				return parent instanceof ViewCompanyOrganizationBeanModel;
			}
		};

		// trees store
		store = new TreeStore<ViewCompanyOrganizationBeanModel>(loader);

		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

		ColumnConfig ccTboNama = new ColumnConfig("tboNama", "<center><b>Name</b></center>", 200);
		ccTboNama.setRenderer(new TreeGridCellRenderer<ModelData>());
		columns.add(ccTboNama);

		columns.add(new ColumnConfig("tboId", "<center><b>ID</b></center>", 75));
		columns.add(new ColumnConfig("tbpName", "<center><b>Company Name</b></center>", 200));

		ColumnConfig ccActions = new ColumnConfig();
		ccActions.setId("actions");
		ccActions.setHeader("<center><b>Actions</b></center>");
		ccActions.setRenderer(new GridCellRenderer<ModelData>() {
			@Override
			public Object render(final ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<ModelData> store, Grid<ModelData> grid) {
				LayoutContainer layoutContainer = new LayoutContainer();
				HBoxLayout hBoxLayout = new HBoxLayout();
				hBoxLayout.setHBoxLayoutAlign(HBoxLayoutAlign.MIDDLE);
				hBoxLayout.setPack(BoxLayoutPack.CENTER);
				layoutContainer.setLayout(hBoxLayout);

				Button btnEdit = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						int id = Integer.parseInt(model.get("tboId").toString());
						WindowCompanyStructureAddEdit windowCompanyStructureAddEdit = WindowCompanyStructureAddEdit.getInstance();
						windowCompanyStructureAddEdit.show("edit", id);
					}
				});
				btnEdit.setToolTip("edit");
				btnEdit.setIcon(Resources.ICONS.form());
				layoutContainer.add(btnEdit);

				Button btnDelete = new Button("", new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						deleteID = Integer.parseInt(model.get("tboId").toString());

						MessageBox.confirm("Confirmation", "Are you sure you want to delete \"" + model.get("tboNama") + "\" ?", deleteListener);
					}
				});
				btnDelete.setToolTip("delete");
				btnDelete.setIcon(Resources.ICONS.delete());
				layoutContainer.add(btnDelete);

				return layoutContainer;
			}
		});
		columns.add(ccActions);

		ColumnModel cm = new ColumnModel(columns);

		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.setLayout(new FitLayout());

		TreeGrid<ModelData> tree = new TreeGrid<ModelData>(store, cm);
		tree.setLoadMask(true);
		tree.setAutoExpandColumn("actions");
		cp.add(tree);

		window.add(cp);
	}

	Button btnAdd;
	Button btnClose;

	@Override
	public void addButtons() {
		btnAdd = new Button("Add", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				WindowCompanyStructureAddEdit windowCompanyStructureAddEdit = WindowCompanyStructureAddEdit.getInstance();
				windowCompanyStructureAddEdit.show("add", 0);
			}
		});
		window.addButton(btnAdd);

		btnClose = new Button("Close", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				MainTabLayout.closeTab(window.getHeading());
			}
		});
		window.addButton(btnClose);
	}

	@Override
	public void init() {
		loader.load();
	}
}
