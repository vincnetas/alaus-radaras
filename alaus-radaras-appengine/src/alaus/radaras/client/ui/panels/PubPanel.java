package alaus.radaras.client.ui.panels;

import alaus.radaras.client.BaseAsyncCallback;
import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubAddedEvent;
import alaus.radaras.client.ui.dialogs.EditDialog;
import alaus.radaras.client.ui.edit.BeerInfoWidget;
import alaus.radaras.client.ui.edit.EditPubWidget;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.InfoWindow;
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
	
	private final Pub pub;
	
	private InfoWindow infoWindow;
	
	public PubPanel(InfoWindow infoWindow, Pub pub) {
		this.pub = pub;
		this.infoWindow = infoWindow;
		
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
	
	@UiHandler("editImage")
	void onEditButtonClick(ClickEvent event) {
		infoWindow.close();
		final EditPubWidget editPubWidget = new EditPubWidget(pub);
		
		EditDialog editPubDialog = new EditDialog(editPubWidget, "Edit Pub") {
			
			@Override
			public void onOkButtonClick(ClickEvent event) {
				Pub a = editPubWidget.getPub();
				Pub update = new Pub();
				update.setParentId(a.getId());
				update.setBeerIds(a.getBeerIds());
				
				Stat.getBeerService().updatePub(update, new BaseAsyncCallback<Pub>() {

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
