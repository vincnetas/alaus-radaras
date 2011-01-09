/**
 * 
 */
package alaus.radaras.client.ui.dialogs;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubAddedEvent;
import alaus.radaras.client.ui.edit.EditPubWidget;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 * 
 */
public class EditPubDialog extends DialogBox {

	private static DialogsUiBinder uiBinder = GWT.create(DialogsUiBinder.class);

	interface DialogsUiBinder extends UiBinder<Widget, EditPubDialog> {
	}

	@UiField(provided=true)
	EditPubWidget editPubWidget;
	
	@UiField 
	Button okButton;
	
	@UiField 
	Button cancelButton;
	
	public EditPubDialog(Pub pub) {
		editPubWidget = new EditPubWidget(pub);
		
		uiBinder.createAndBindUi(this);
	}

	@UiFactory
	DialogBox thatsJustMe() {
		// UiBinder will call this to get a DialogBox instance
		// and this is the DialogBox instance we want to use
		return this;
	}
	
	@UiHandler("okButton")
	void onOkButtonClick(ClickEvent event) {
		final Pub pub = getPub();
		Stat.getBeerService().savePub(pub, new AsyncCallback<Void>() {
			
			@Override
			public void onSuccess(Void result) {
				Stat.getHandlerManager().fireEvent(new PubAddedEvent(pub));
				hide();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Save failed");
				
			}
		});		
	}
	
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {
		hide();
	}
	
	public Pub getPub() {
		return editPubWidget.getPub();
	}
}
