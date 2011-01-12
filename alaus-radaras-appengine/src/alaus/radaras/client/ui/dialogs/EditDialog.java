/**
 * 
 */
package alaus.radaras.client.ui.dialogs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 * 
 */
public abstract class EditDialog extends DialogBox {

	private static DialogsUiBinder uiBinder = GWT.create(DialogsUiBinder.class);

	interface DialogsUiBinder extends UiBinder<Widget, EditDialog> {
	}

	@UiField(provided=true)
	Widget widget;
	
	@UiField 
	Button okButton;
	
	@UiField 
	Button cancelButton;
	
	public EditDialog(Widget widget) {
		this.widget = widget;		
		uiBinder.createAndBindUi(this);
	}

	@UiFactory
	DialogBox thatsJustMe() {
		// UiBinder will call this to get a DialogBox instance
		// and this is the DialogBox instance we want to use
		return this;
	}
	
	@UiHandler("okButton")
	public abstract void onOkButtonClick(ClickEvent event);
	
	@UiHandler("cancelButton")
	public void onCancelButtonClick(ClickEvent event) {
		hide();
	}
}
