/**
 * 
 */
package alaus.radaras.client;

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

	/**
	 * Because this class has a default constructor, it can be used as a binder
	 * template. In other words, it can be used in other *.ui.xml files as
	 * follows: <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 * xmlns:g="urn:import:**user's package**">
	 * <g:**UserClassName**>Hello!</g:**UserClassName> </ui:UiBinder> Note that
	 * depending on the widget that is used, it may be necessary to implement
	 * HasHTML instead of HasText.
	 */
	public Uploader() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	FormPanel formPanel;

	@UiField
	FileUpload fileUpload;

	@UiField
	Button submitButton;

	public Uploader(String firstName) {
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
