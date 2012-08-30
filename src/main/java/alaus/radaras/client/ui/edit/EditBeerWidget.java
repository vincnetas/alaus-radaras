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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditBeerWidget extends Composite implements SelectionHandler<Suggestion> {

	private static EditBrandWidgetUiBinder uiBinder = GWT.create(EditBrandWidgetUiBinder.class);

	interface EditBrandWidgetUiBinder extends UiBinder<Widget, EditBeerWidget> {
	}

	@UiField
	TextBox title;
	
	@UiField
	BrandSuggestBox brand;
	
	@UiField
	TextBox icon;
	
	@UiField
	TextArea description;
	
	@UiField
	TextArea tags;
	
	private Beer beer;

	public EditBeerWidget(Beer beer) {
		initWidget(uiBinder.createAndBindUi(this));
		
		brand.addSelectionHandler(this);

		setBeer(beer);
	}
	
	/**
	 * @return the brand
	 */
	public Beer getBeer() {
		beer.setTitle(title.getText());	
		beer.setBrandId(brandValue.getId());
		beer.setDescription(description.getText());
		beer.setTags(tags.getText());
		beer.setIcon(icon.getText());
		
		return beer;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBeer(Beer beer) {
		title.setText(beer.getTitle());
		Stat.getBeerService().loadBrand(Utils.set(beer.getBrandId()), new BaseAsyncCallback<Set<Brand>>() {

			
			public void onSuccess(Set<Brand> arg0) {				
				Brand value = arg0.toArray(new Brand[1])[0];
				brandValue = value;
				brand.setText(value.getTitle());
			}
			
		});

		description.setText(beer.getDescription());
		tags.setText(beer.getTagsAsString());
		icon.setText(beer.getIcon());
		
		this.beer = beer;
	}

	private Brand brandValue;
	
	
	public void onSelection(SelectionEvent<Suggestion> arg0) {
		BrandSuggestion suggestion = (BrandSuggestion) arg0.getSelectedItem();
		brandValue = suggestion.getBrand();
	}

}
