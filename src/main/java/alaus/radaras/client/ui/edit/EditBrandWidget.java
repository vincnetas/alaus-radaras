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
	TextBox icon;
	
	@UiField
	TextBox homepage;
	
	@UiField
	SuggestBox country;
	
	@UiField
	SuggestBox hometown;
	
	@UiField
	TextArea description;
	
	@UiField
	TextArea tags;
	
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
		brand.setIcon(icon.getText());
		brand.setHomePage(homepage.getText());
		brand.setCountry(country.getText());
		brand.setHometown(hometown.getText());
		brand.setDescription(description.getText());
		brand.setTags(tags.getText());
		
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(Brand brand) {
		title.setText(brand.getTitle());
		icon.setText(brand.getIcon());
		homepage.setText(brand.getHomePage());
		country.setText(brand.getCountry());
		hometown.setText(brand.getHometown());
		description.setText(brand.getDescription());
		tags.setText(brand.getTagsAsString());
		
		this.brand = brand;
	}

}
