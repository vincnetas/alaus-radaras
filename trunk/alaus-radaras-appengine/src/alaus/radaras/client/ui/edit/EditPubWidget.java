package alaus.radaras.client.ui.edit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import alaus.radaras.client.BaseAsyncCallback;
import alaus.radaras.client.Stat;
import alaus.radaras.client.ui.edit.suggest.BeerSuggestBox;
import alaus.radaras.client.ui.edit.suggest.BeerSuggestion;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class EditPubWidget extends Composite implements SelectionHandler<Suggestion> {

	private static EditPubWidgetUiBinder uiBinder = GWT.create(EditPubWidgetUiBinder.class);

	interface EditPubWidgetUiBinder extends UiBinder<Widget, EditPubWidget> {
	}
	
	@UiField
	Label title;
	
	@UiField
	BeerSuggestBox beerSuggest;
	
	@UiField
	VerticalPanel beerPanel;
	
	@UiField
	VerticalPanel addedBeerPanel;

	@UiField
	VerticalPanel removedBeerPanel;
	
	private Set<String> removedBeers = new HashSet<String>();
	
	private Set<String> addedBeers = new HashSet<String>();
	
	private Set<String> beers = new HashSet<String>();
	
	private Set<String> allBeers = new HashSet<String>();
	
	private Pub pub;
	
	public EditPubWidget(Pub pub) {
		initWidget(uiBinder.createAndBindUi(this));
		
		beerSuggest.addSelectionHandler(this);		
		setPub(pub);
	}

	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		BeerSuggestion suggestion = (BeerSuggestion) event.getSelectedItem();
		
		if (suggestion.getBeer() != null) {
			addBeer(suggestion.getBeer());
		} else {
			createBeer(suggestion.getQuery());
		}
	}
	
	private void createBeer(String beerNameSuggestion) {
		Beer beer = new Beer();
		beer.setTitle(beerNameSuggestion);
		
		Stat.getBeerService().addBeer(beer, new AsyncCallback<Beer>() {
			
			@Override
			public void onSuccess(Beer result) {
				addBeer(result);				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
		});
	}
	
	private void addBeer(String beerId) {
		BeerInfoWidget beerInfoWidget = new BeerInfoWidget(beerId);
		beerPanel.add(beerInfoWidget);
	}
	
	private void addBeer(Beer beer) {
		BeerInfoWidget beerInfoWidget = new BeerInfoWidget(beer);
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
	public void setPub(final Pub pub) {
		Stat.getBeerService().getPubUpdates(pub.getId(), new BaseAsyncCallback<List<Pub>>() {
			
			@Override
			public void onSuccess(List<Pub> result) {
				Set<String> beerUpdates = new HashSet<String>();
				for (Pub pubUpdate : result) {
					beerUpdates.addAll(pubUpdate.getBeerIds());
				}
				
				removedBeers = new HashSet<String>(pub.getBeerIds());
				removedBeers.removeAll(beerUpdates);
				
				addedBeers = new HashSet<String>(beerUpdates);
				addedBeers.removeAll(pub.getBeerIds());
				
				beers = new HashSet<String>(beerUpdates);
				beers.retainAll(pub.getBeerIds());
				
				allBeers = new HashSet<String>(beerUpdates);
				allBeers.addAll(pub.getBeerIds());				
			}

		});
		
		for (String beerId : pub.getBeerIds()) {
			addBeer(beerId);
		}
		title.setText(pub.getTitle());
		
		this.pub = pub;
	}	
}