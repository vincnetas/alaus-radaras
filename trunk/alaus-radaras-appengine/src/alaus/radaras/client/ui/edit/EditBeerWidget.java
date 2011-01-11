package alaus.radaras.client.ui.edit;

import alaus.radaras.client.Stat;
import alaus.radaras.client.command.SaveBeerCommand;
import alaus.radaras.client.events.saved.BeerSavedHandler;
import alaus.radaras.client.ui.dialogs.EditDialog;
import alaus.radaras.client.ui.edit.suggest.BrandSuggestBox;
import alaus.radaras.client.ui.edit.suggest.BrandSuggestion;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditBeerWidget extends SaveComposite implements SelectionHandler<Suggestion> {

	private static EditBeerWidgetUiBinder uiBinder = GWT.create(EditBeerWidgetUiBinder.class);

	interface EditBeerWidgetUiBinder extends UiBinder<Widget, EditBeerWidget> {
	}

	@UiField
	TextBox title;
	
	@UiField
	BrandSuggestBox brandSuggest;
	
	private Beer beer;
	
	public EditBeerWidget(Beer beer) {
		initWidget(uiBinder.createAndBindUi(this));		
		brandSuggest.addSelectionHandler(this);
		
		setBeer(beer);
	}
	
	@Override
	public void onSelection(SelectionEvent<Suggestion> event) {
		BrandSuggestion brandSuggestion = (BrandSuggestion) event.getSelectedItem();

		if (brandSuggestion.getBrand() != null) {
			setBrand(brandSuggestion.getBrand());
		} else {
			createBrand(brandSuggestion.getQuery());
		}
	}
	
	private void createBrand(String brandNameSuggestion) {
		
	}
	
	private void setBrand(Brand brand) {
		
	}

	/**
	 * @param beer the beer to set
	 */
	public void setBeer(Beer beer) {
		this.beer = beer;asd
	}

	/**
	 * @return the beer
	 */
	public Beer getBeer() {
		return beer;asd
	}

	private BeerSavedHandler handler = null;
	
	@Override
	public void save(final EditDialog editDialog) {
		if (handler == null) {
			handler = new BeerSavedHandler() {

				@Override
				public void saved(Beer beer) {
					editDialog.hide();
					Stat.getHandlerManager().removeHandler(BeerSavedHandler.type, this);
				}
			};
			
			Stat.getHandlerManager().addHandler(BeerSavedHandler.type, handler);
		}		
		
		Stat.execute(new SaveBeerCommand(getBeer()));		
	}
}
