package alaus.radaras.client.ui.edit;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditPubWidget extends Composite {

	private static EditPubWidgetUiBinder uiBinder = GWT.create(EditPubWidgetUiBinder.class);

	interface EditPubWidgetUiBinder extends UiBinder<Widget, EditPubWidget> {
	}
	
	@UiField
	TextArea description;
	
	@UiField
	TextBox title;
	
//	@UiField
//	Button selectLocation;
//	
//	@UiField
//	SuggestBox country;
//	
//	@UiField
//	SuggestBox city;
//	
//	@UiField
//	TextBox address;
//	
//	@UiField
//	TextBox phone;
//
//	@UiField
//	TextBox homepage;
//	
//	@UiField
//	TextBox hours;
//	
//	@UiField
//	TextBox tags;
//	
//	@UiField
//	Button selectBeer;
	
	private Pub pub;
	
	public EditPubWidget(Pub pub) {
		this.pub = pub;
		initWidget(uiBinder.createAndBindUi(this));
		
		description.setText(pub.getDescription());
		title.setText(pub.getTitle());
	}

	/**
	 * @return the pub
	 */
	public Pub getPub() {
		pub.setDescription(description.getText());
		pub.setTitle(title.getText());
		
		return pub;
	}
}