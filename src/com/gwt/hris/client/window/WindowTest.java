package com.gwt.hris.client.window;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

public class WindowTest extends WindowMain {
	private static WindowTest instance = new WindowTest();

	public static WindowTest getInstance() {
		return instance;
	}

	public WindowTest() {
		window = new Window();
		window.setId("WindowTest");
		window.setSize(600, 380);
		window.setHeading("WindowTest");
	}

	@Override
	public void addComponents() {
		String folderPath = GWT.getHostPageBaseURL() + "upload/photo/";
		String filePath = "9j5k5iyir50m6i6ml0dnzeep5.png_thumb.png";
		Image img = new Image(folderPath + filePath);

		formPanel.add(img);

		// formPanel.setAction("/hris/fileupload");
		// formPanel.setEncoding(Encoding.MULTIPART);
		// formPanel.setMethod(Method.POST);
		//
		// formPanel.addListener(Events.Submit, new Listener<FormEvent>() {
		// @Override
		// public void handleEvent(FormEvent be) {
		// MessageBox.alert("Alert", "Success upload file. <br>ResultHtml:<br>"
		// + be.getResultHtml(), null);
		// }
		// });
		//
		// FileUploadField file = new FileUploadField();
		// file.setAllowBlank(false);
		// file.setName("uploadedfile");
		// file.setFieldLabel("File");
		// formPanel.add(file, formData);

		window.add(formPanel);

		Button btn = new Button();
		btn = new Button("Submit");
		btn.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				formPanel.submit();
			}
		});
		window.addButton(btn);
	}

	@Override
	public void addButtons() {
	}
}
