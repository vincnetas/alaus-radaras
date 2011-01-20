package alaus.radaras.client.ui.edit;

import java.util.HashSet;
import java.util.Set;

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
	
	private final Pub pub;
	
	private Set<String> beerIds = new HashSet<String>();
	
	public EditPubWidget(Pub pub) {
		initWidget(uiBinder.createAndBindUi(this));
		
		beerSuggest.addSelectionHandler(this);		
		
		this.pub = pub;
		for (String beerId : pub.getBeerIds()) {
			addBeer(beerId);
		}		
		
		title.setText(pub.getTitle());
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
				beerIds.add(result.getId());
				addBeer(result);				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
		});
	}
	
	private void addBeer(final String beerId) {
		beerPanel.add(new RemovePanel(new BeerInfoWidget(beerId)) {
			
			@Override
			public void onRemove() {
				beerIds.remove(beerId);
				beerPanel.remove(this);
			}
		});
	}
	
	private void addBeer(final Beer beer) {
		beerPanel.add(new RemovePanel(new BeerInfoWidget(beer)) {
			
			@Override
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
		pub.setBeerIds(beerIds);
		
		return pub;
	}	
}