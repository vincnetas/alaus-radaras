package alaus.radaras.client.ui.edit;

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
	TextBox tags;
	
	public EditBrandWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
