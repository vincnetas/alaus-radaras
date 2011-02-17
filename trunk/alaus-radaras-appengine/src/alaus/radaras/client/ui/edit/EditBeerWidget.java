package alaus.radaras.client.ui.edit;

import alaus.radaras.shared.model.Beer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditBeerWidget extends Composite {

	private static EditBrandWidgetUiBinder uiBinder = GWT.create(EditBrandWidgetUiBinder.class);

	interface EditBrandWidgetUiBinder extends UiBinder<Widget, EditBeerWidget> {
	}

	@UiField
	TextBox title;
	
	@UiField
	TextBox brand;
	
	@UiField
	TextArea description;
	
	@UiField
	TextArea tags;
	
	private Beer beer;

	public EditBeerWidget(Beer beer) {
		initWidget(uiBinder.createAndBindUi(this));
		
		setBeer(beer);
	}
	
	/**
	 * @return the brand
	 */
	public Beer getBeer() {
		beer.setTitle(title.getText());	
		beer.setBrandId(brand.getText());
		beer.setDescription(description.getText());
		beer.setTags(tags.getText());
		
		return beer;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBeer(Beer beer) {
		title.setText(beer.getTitle());
		brand.setText(beer.getBrandId());
		description.setText(beer.getDescription());
		tags.setText(beer.getTagsAsString());
		
		this.beer = beer;
	}

}
