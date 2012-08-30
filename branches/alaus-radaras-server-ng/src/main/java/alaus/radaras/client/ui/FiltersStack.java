/**
 * 
 */
package alaus.radaras.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class FiltersStack extends Composite {

	@UiField
	StackLayoutPanel stackPanel;
	
	private static FiltersStackUiBinder uiBinder = GWT.create(FiltersStackUiBinder.class);

	interface FiltersStackUiBinder extends UiBinder<Widget, FiltersStack> {
	}

	public FiltersStack() {
		initWidget(uiBinder.createAndBindUi(this));
		
		stackPanel.showWidget(stackPanel.getWidgetCount() - 1);
	}


}
