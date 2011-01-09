/**
 * 
 */
package alaus.radaras.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class BeerPanel extends Composite {

	private static BeerPanelUiBinder uiBinder = GWT.create(BeerPanelUiBinder.class);

	interface BeerPanelUiBinder extends UiBinder<Widget, BeerPanel> {
	}

	@UiField
	HTMLPanel beerPanel;
	
	public BeerPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
