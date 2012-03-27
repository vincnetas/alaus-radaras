/**
 * 
 */
package alaus.radaras.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 * 
 */
public class Uploader extends Composite {
	
	private static uploaderUiBinder uiBinder = GWT.create(uploaderUiBinder.class);

	interface uploaderUiBinder extends UiBinder<Widget, Uploader> {
	}

	@UiField
	FormPanel formPanel;

	@UiField
	FileUpload fileUpload;

	@UiField
	Button submitButton;

	public Uploader() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("submitButton")
	void onSubmitButton(ClickEvent e) {
		formPanel.submit();
	}

	@UiHandler("formPanel")
	public void onSubmitComplete(SubmitCompleteEvent event) {
		Window.alert(event.getResults());
	}
}
