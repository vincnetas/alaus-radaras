package alaus.radaras.client.ui.edit;

import java.util.Set;

import alaus.radaras.client.BaseAsyncCallback;
import alaus.radaras.client.Stat;
import alaus.radaras.client.ui.edit.suggest.BrandSuggestBox;
import alaus.radaras.client.ui.edit.suggest.BrandSuggestion;
import alaus.radaras.shared.Utils;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditBeerWidget extends Composite implements SelectionHandler<Suggestion> {

	private static EditBeerWidgetUiBinder uiBinder = GWT.create(EditBeerWidgetUiBinder.class);

	interface EditBeerWidgetUiBinder extends UiBinder<Widget, EditBeerWidget> {
	}

	@UiField
	TextBox title;
	
	@UiField
	BrandSuggestBox brandSuggest;
	
	private Brand brand;
	
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
	
	/**
	 * @return the brand
	 */
	public Brand getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
		brandSuggest.setText(brand.getTitle());					

	}

	/**
	 * @param beer the beer to set
	 */
	public void setBeer(Beer beer) {
		title.setText(beer.getTitle());
		Stat.getBeerService().loadBrand(Utils.set(beer.getBrandId()), new BaseAsyncCallback<Set<Brand>>() {
			
			@Override
			public void onSuccess(Set<Brand> result) {
				if (result.size() == 1) {
					setBrand(result.iterator().next());
				}
			}
			
		});
		
		this.beer = beer;
	}

	/**
	 * @return the beer
	 */
	public Beer getBeer() {
		beer.setBrandId(brand.getId());
		beer.setTitle(title.getText());
		
		return beer;
	}
}
