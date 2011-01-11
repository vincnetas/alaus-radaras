/**
 * 
 */
package alaus.radaras.client.ui;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.StartAddPubEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * @author Vincentas
 *
 */
public class ToolPanel extends Composite {

	private static ToolPanelUiBinder uiBinder = GWT.create(ToolPanelUiBinder.class);

	interface ToolPanelUiBinder extends UiBinder<Widget, ToolPanel> {
	}

	@UiField
	Button addPubButton;
	
	@UiField
	Button uploadButton;
	
	public ToolPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("addPubButton")
	void onAddPubButtonClick(ClickEvent event) {
		Stat.getHandlerManager().fireEvent(new StartAddPubEvent());
	}

	@UiHandler("uploadButton")
	void onUploadButtonClick(ClickEvent event) {
		DialogBox dialogBox = new DialogBox(true, true);
		dialogBox.setHTML("Upload");
		dialogBox.setWidget(new Uploader());
		dialogBox.center();
	}
}
