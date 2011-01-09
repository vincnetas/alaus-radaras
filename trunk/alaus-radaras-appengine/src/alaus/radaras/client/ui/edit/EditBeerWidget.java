package alaus.radaras.client.ui.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class EditBeerWidget extends Composite {

	private static EditBeerWidgetUiBinder uiBinder = GWT.create(EditBeerWidgetUiBinder.class);

	interface EditBeerWidgetUiBinder extends UiBinder<Widget, EditBeerWidget> {
	}

	@UiField
	TextBox title;
	
	@UiField
	SuggestBox brand;
	
	@UiField
	TextArea description;
	
	@UiField
	TextBox tags;
	
	public EditBeerWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
