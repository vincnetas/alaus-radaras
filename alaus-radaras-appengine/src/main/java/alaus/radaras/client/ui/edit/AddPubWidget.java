package alaus.radaras.client.ui.edit;

import java.util.HashSet;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AddPubWidget extends Composite implements SelectionHandler<Suggestion> {

	private static EditPubWidgetUiBinder uiBinder = GWT.create(EditPubWidgetUiBinder.class);

	interface EditPubWidgetUiBinder extends UiBinder<Widget, AddPubWidget> {
	}
	
	@UiField
	TextBox title;
	
	@UiField
	BeerSuggestBox beerSuggest;
	
	@UiField
	VerticalPanel beerPanel;
	
	private final Pub pub;
	
	private Set<String> beerIds = new HashSet<String>();
	
	public AddPubWidget(Pub pub) {
		this.pub = pub;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		beerSuggest.addSelectionHandler(this);	
		
		title.setText(pub.getTitle());
		for (String beerId : pub.getBeerIds()) {
			addBeer(beerId);
		}				
	}

	
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
		
		Stat.getBeerService().addBeer(beer, new BaseAsyncCallback<Beer>() {
			
			
			public void onSuccess(Beer result) {
				addBeer(result);				
			}

		});
	}
	
	private void addBeer(final String beerId) {
		beerIds.add(beerId);
		beerPanel.add(new RemovePanel(new BeerInfoWidget(beerId)) {
			
			
			public void onRemove() {
				beerIds.remove(beerId);
				beerPanel.remove(this);
			}
		});
	}
	
	private void addBeer(final Beer beer) {
		beerIds.add(beer.getId());
		beerPanel.add(new RemovePanel(new BeerInfoWidget(beer)) {
			
			
			public void onRemove() {
				beerIds.remove(beer.getId());
				beerPanel.remove(this);
			}
		});
	}
	
	/**
	 * @return the pub
	 */
	public Pub getPub() {
		pub.setTitle(title.getText());
		pub.setBeerIds(beerIds);
		
		return pub;
	}
}