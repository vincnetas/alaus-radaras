package alaus.radaras.client.ui.edit;

import alaus.radaras.shared.model.Brand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditBrandWidget extends Composite {

	private static EditBrandWidgetUiBinder uiBinder = GWT.create(EditBrandWidgetUiBinder.class);

	interface EditBrandWidgetUiBinder extends UiBinder<Widget, EditBrandWidget> {
	}

	@UiField
	TextBox title;
	
	@UiField
	TextBox homepage;
	
	@UiField
	SuggestBox country;
	
	@UiField
	SuggestBox hometown;
	
	@UiField
	TextArea description;
	
	private Brand brand;

	public EditBrandWidget(Brand brand) {
		initWidget(uiBinder.createAndBindUi(this));
		
		setBrand(brand);
	}
	
	/**
	 * @return the brand
	 */
	public Brand getBrand() {
		brand.setTitle(title.getText());		
		brand.setHomePage(homepage.getText());
		brand.setCountry(country.getText());
		brand.setHomeTown(hometown.getText());
		brand.setDescription(description.getText());
		
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(Brand brand) {
		title.setText(brand.getTitle());
		homepage.setText(brand.getHomePage());
		country.setText(brand.getCountry());
		hometown.setText(brand.getHomeTown());
		description.setText(brand.getDescription());
		
		this.brand = brand;
	}

}
