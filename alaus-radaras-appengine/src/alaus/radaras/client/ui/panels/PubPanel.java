package alaus.radaras.client.ui.panels;

import alaus.radaras.client.ui.dialogs.EditPubDialog;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class PubPanel extends Composite {

	private static PubPanelUiBinder uiBinder = GWT.create(PubPanelUiBinder.class);

	interface PubPanelUiBinder extends UiBinder<Widget, PubPanel> {
	}
	
	@UiField
	Label title;

	@UiField
	Label description;
	
	private Pub pub;

	public PubPanel(Pub pub) {
		initWidget(uiBinder.createAndBindUi(this));
		
		title.setText(pub.getTitle());
		description.setText(pub.getDescription());
		
		this.pub = pub;
	}
	@UiHandler("editButton")
	void onEditButtonClick(ClickEvent event) {
		EditPubDialog editPubDialog = new EditPubDialog(pub);
		editPubDialog.center();
	}
}
