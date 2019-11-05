package com.gwt.hris.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwt.hris.client.window.index.WindowLogin;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GXT_HRIS implements EntryPoint {
	@Override
	public void onModuleLoad() {
		RootPanel.get("loading-hris").setVisible(false);
		
//		RootPanel rootPanel = RootPanel.get();
//
//		Viewport viewPort = new Viewport();
//		viewPort.setLayout(new FitLayout());
//
//		MainTabLayout mainTabLayout = new MainTabLayout();
//
//		viewPort.add(mainTabLayout);
//
//		rootPanel.add(viewPort);
		
		WindowLogin window = new WindowLogin();
		window.show();
	}
}
