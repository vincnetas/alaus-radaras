package alaus.radaras.client.ui.panels;

import alaus.radaras.client.ui.edit.BeerInfoWidget;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PubPanel extends Composite {

	private static PubPanelUiBinder uiBinder = GWT.create(PubPanelUiBinder.class);

	interface PubPanelUiBinder extends UiBinder<Widget, PubPanel> {
	}
	
	@UiField
	Label title;

	@UiField
	VerticalPanel beerPanel;
	
	private final Pub pub;
		
	public PubPanel(Pub pub) {
		this.pub = pub;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		if (pub.getBeerIds().isEmpty()) {
			 beerPanel.add(new Label("... no info yet"));
		} else {
			for (String beerId : pub.getBeerIds()) {
				addBeer(beerId);
			}
		}
		
		title.setText(pub.getTitle());
	}
	
	private void addBeer(String beerId) {
		beerPanel.add(new BeerInfoWidget(beerId));
	}
	
	/**
	 * @return the pub
	 */
	public Pub getPub() {
		pub.setTitle(title.getText());
		
		return pub;
	}
}
