package alaus.radaras.client.ui.panels;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import alaus.radaras.client.BaseAsyncCallback;
import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubAddedEvent;
import alaus.radaras.client.ui.dialogs.EditDialog;
import alaus.radaras.client.ui.edit.BeerInfoWidget;
import alaus.radaras.client.ui.edit.EditPubWidget;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
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
	
	@UiField
	Image editImage;
	
	private Pub pub;
	
	private final Map<String, BeerInfoWidget> beerWidgets = new HashMap<String, BeerInfoWidget>();

	public PubPanel(Pub pub) {
		initWidget(uiBinder.createAndBindUi(this));
		
		setPub(pub);
	}
	
	@UiHandler("editImage")
	void onEditButtonClick(ClickEvent event) {
		final EditPubWidget editPubWidget = new EditPubWidget(pub);
		EditDialog editPubDialog = new EditDialog(editPubWidget) {
			
			@Override
			public void onOkButtonClick(ClickEvent event) {
				Stat.getBeerService().savePub(editPubWidget.getPub(), new BaseAsyncCallback<Pub>() {

					@Override
					public void onSuccess(Pub result) {
						Stat.getHandlerManager().fireEvent(new PubAddedEvent(result));
						hide();
					}
				});
			}
		};
		editPubDialog.setHTML("Edit Pub");
		
		editPubDialog.center();
	}
	
	private void addBeer(String beerId) {
		BeerInfoWidget beerInfoWidget = new BeerInfoWidget(beerId);
		beerWidgets.put(beerId, beerInfoWidget);
		beerPanel.add(beerInfoWidget);
	}
	
	/**
	 * @return the pub
	 */
	public Pub getPub() {
		pub.setTitle(title.getText());
		pub.setBeerIds(new HashSet<String>(beerWidgets.keySet()));
		
		return pub;
	}

	/**
	 * @param pub the pub to set
	 */
	public void setPub(Pub pub) {
		if (pub.getBeerIds().isEmpty()) {
			 beerPanel.add(new Label("... no info yet"));
		} else {
			for (String beerId : pub.getBeerIds()) {
				addBeer(beerId);
			}
		}
		
		title.setText(pub.getTitle());
		
		this.pub = pub;
	}
}
