package alaus.radaras.client.ui.edit;

import java.util.HashSet;
import java.util.Set;

import alaus.radaras.client.Stat;
import alaus.radaras.client.command.LoadBeerCommand;
import alaus.radaras.client.command.SavePubCommand;
import alaus.radaras.client.events.saved.BeerSavedHandler;
import alaus.radaras.client.events.saved.PubSavedHandler;
import alaus.radaras.client.ui.dialogs.EditDialog;
import alaus.radaras.client.ui.edit.suggest.BeerSuggestBox;
import alaus.radaras.client.ui.edit.suggest.BeerSuggestion;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditPubWidget extends SaveComposite implements SelectionHandler<Suggestion> {

	private static EditPubWidgetUiBinder uiBinder = GWT.create(EditPubWidgetUiBinder.class);

	interface EditPubWidgetUiBinder extends UiBinder<Widget, EditPubWidget> {
	}
	
	@UiField
	TextBox title;
	
	@UiField
	BeerSuggestBox beerSuggest;
	
	@UiField
	HTMLPanel beerPanel;
	
	private Set<Beer> beers = new HashSet<Beer>();
	
	private Set<String> beerIds = new HashSet<String>();
	
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
		
		final BeerSavedHandler handler = new BeerSavedHandler() {
			
			@Override
			public void saved(Beer beer) {
				addBeer(beer);				
			}
		};
		
		Stat.getHandlerManager().addHandler(BeerSavedHandler.type, handler);
		
		EditDialog editDialog = new EditDialog(new EditBeerWidget(beer));
		editDialog.center();	
		editDialog.addCloseHandler(new CloseHandler<PopupPanel>() {
			
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				Stat.getHandlerManager().removeHandler(BeerSavedHandler.type, handler);
			}
		});
	}
	
	private void addBeer(Beer beer) {
		beers.add(beer);
		beerIds.add(beer.getId());		
		beerPanel.add(new Label(beer.getTitle()));
	}
	
	/**
	 * @return the pub
	 */
	public Pub getPub() {
		pub.setTitle(title.getText());
		pub.setBeerIds(beerIds);
		
		return pub;
	}

	/**
	 * @param pub the pub to set
	 */
	public void setPub(Pub pub) {
		beerIds = pub.getBeerIds();
		title.setText(pub.getTitle());
		
		Stat.execute(new LoadBeerCommand(beerIds));
		
		this.pub = pub;
	}

	private PubSavedHandler handler = null;
	
	@Override
	public void save(final EditDialog editDialog) {
		if (handler == null) {
			handler = new PubSavedHandler() {
				
				@Override
				public void saved(Pub pub) {
					editDialog.hide();
					Stat.getHandlerManager().removeHandler(PubSavedHandler.type, handler);
				}
			};
			Stat.getHandlerManager().addHandler(PubSavedHandler.type, handler);
		}
			
		Stat.execute(new SavePubCommand(getPub()));		
	}
	
}