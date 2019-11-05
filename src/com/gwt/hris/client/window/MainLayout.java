package com.gwt.hris.client.window;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.layout.FlowData;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwt.hris.client.service.admin.systemsettings.MenuInterface;
import com.gwt.hris.client.service.admin.systemsettings.MenuInterfaceAsync;
import com.gwt.hris.client.service.bean.TbMenuBeanModel;
import com.gwt.hris.client.window.index.WindowLogin;

public class MainLayout extends LayoutContainer {
	private static MainLayout instance;

	public static MainLayout getInstance() {
		return instance;
	}

	public MainLayout() {
		instance = this;
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		WindowLogin window = new WindowLogin();
		window.show();
	}

	MenuBar bar;
	WindowManager manager;

	public void createMenu() {
		bar = new MenuBar();
		bar.setBorders(true);
		bar.setStyleAttribute("borderTop", "none");

		ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setTopComponent(bar);

		add(panel, new FlowData(0));

		MenuInterfaceAsync menuInterfaceAsync = GWT.create(MenuInterface.class);
		manager = new WindowManager();
		menuInterfaceAsync.getMenu(menuInterfaceCallback);
	}

	AsyncCallback<TbMenuBeanModel> menuInterfaceCallback = new AsyncCallback<TbMenuBeanModel>() {
		@Override
		public void onSuccess(TbMenuBeanModel result) {
			for (TbMenuBeanModel tbMenuBeanModel1 : result.getModels()) {
				Menu menu1 = new Menu();
				bar.add(new MenuBarItem(tbMenuBeanModel1.getTbmNama(), menu1));

				if (tbMenuBeanModel1.getModels() != null) {
					for (TbMenuBeanModel tbMenuBeanModel2 : tbMenuBeanModel1.getModels()) {
						final String strNama1 = tbMenuBeanModel2.getTbmNama();
						MenuItem menuItem1 = new MenuItem(strNama1, new SelectionListener<MenuEvent>() {
							@Override
							public void componentSelected(MenuEvent ce) {
								manager.showWindow(strNama1);
							}
						});
						menu1.add(menuItem1);

						if (tbMenuBeanModel2.getModels() != null) {
							Menu menu2 = new Menu();
							menuItem1.setSubMenu(menu2);

							for (TbMenuBeanModel tbMenuBeanModel3 : tbMenuBeanModel2.getModels()) {
								final String strNama2 = tbMenuBeanModel3.getTbmNama();
								MenuItem menuItem2 = new MenuItem(strNama2, new SelectionListener<MenuEvent>() {
									@Override
									public void componentSelected(MenuEvent ce) {
										manager.showWindow(strNama2);
									}
								});
								menu2.add(menuItem2);

								if (tbMenuBeanModel3.getModels() != null) {
									Menu menu3 = new Menu();
									menuItem2.setSubMenu(menu3);

									for (TbMenuBeanModel tbMenuBeanModel4 : tbMenuBeanModel3.getModels()) {
										final String strNama3 = tbMenuBeanModel4.getTbmNama();
										MenuItem menuItem3 = new MenuItem(strNama3, new SelectionListener<MenuEvent>() {
											@Override
											public void componentSelected(MenuEvent ce) {
												manager.showWindow(strNama3);
											}
										});
										menu3.add(menuItem3);

										if (tbMenuBeanModel4.getModels() != null) {
											Menu menu4 = new Menu();
											menuItem3.setSubMenu(menu4);

											for (TbMenuBeanModel tbMenuBeanModel5 : tbMenuBeanModel4.getModels()) {
												final String strNama4 = tbMenuBeanModel5.getTbmNama();
												MenuItem menuItem4 = new MenuItem(strNama4, new SelectionListener<MenuEvent>() {
													@Override
													public void componentSelected(MenuEvent ce) {
														manager.showWindow(strNama4);
													}
												});
												menu4.add(menuItem4);
											}
										}
									}
								}
							}
						}
					}
				}
			}

			layout();
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.alert("Alert", "Operation Error.<br/><br/><br/>Error Message :<br/>" + caught.getMessage(), null);

			caught.printStackTrace();
		}
	};
}
