package alaus.radaras.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class SearchWidget extends Composite {

	private static SearchWidgetUiBinder uiBinder = GWT.create(SearchWidgetUiBinder.class);

	interface SearchWidgetUiBinder extends UiBinder<Widget, SearchWidget> {
	}

	@UiField(provided = true)
	ListBox listBox;

	public SearchWidget() {
		listBox = new ListBox(true);
		initWidget(uiBinder.createAndBindUi(this));		
	}
}
