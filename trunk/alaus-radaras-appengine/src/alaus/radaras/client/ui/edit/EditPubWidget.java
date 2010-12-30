package alaus.radaras.client.ui.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EditPubWidget extends Composite {

	private static EditPubWidgetUiBinder uiBinder = GWT.create(EditPubWidgetUiBinder.class);

	interface EditPubWidgetUiBinder extends UiBinder<Widget, EditPubWidget> {
	}

	@UiField
	TextBox title;
	
	@UiField
	Button selectLocation;
	
	@UiField
	SuggestBox country;
	
	@UiField
	SuggestBox city;
	
	@UiField
	TextBox address;
	
	@UiField
	TextBox phone;
	
	@UiField
	TextArea description;
	
	@UiField
	TextBox homepage;
	
	@UiField
	TextBox hours;
	
	@UiField
	TextBox tags;
	
	@UiField
	Button selectBeer;
	
	public EditPubWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
