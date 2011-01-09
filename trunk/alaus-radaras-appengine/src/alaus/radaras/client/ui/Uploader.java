/**
 * 
 */
package alaus.radaras.client.ui;

import java.util.List;

import alaus.radaras.client.BeerService;
import alaus.radaras.client.BeerServiceAsync;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 * 
 */
public class Uploader extends Composite {
	
	private static BeerServiceAsync service = GWT.create(BeerService.class);

	private static uploaderUiBinder uiBinder = GWT.create(uploaderUiBinder.class);

	interface uploaderUiBinder extends UiBinder<Widget, Uploader> {
	}

	@UiField
	FormPanel formPanel;

	@UiField
	FileUpload fileUpload;

	@UiField
	ListBox list;

	@UiField
	Button submitButton;

	public Uploader() {
		initWidget(uiBinder.createAndBindUi(this));
		
		service.greetServer("", new AsyncCallback<List<Pub>>() {
			
			@Override
			public void onSuccess(List<Pub> result) {
				for (Pub pub : result) {
					list.addItem(pub.getTitle());
				}				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
				
			}
		});
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
