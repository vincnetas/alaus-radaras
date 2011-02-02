/**
 * 
 */
package alaus.radaras.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class LogoPanel extends Composite {

	private static LogoPanelUiBinder uiBinder = GWT.create(LogoPanelUiBinder.class);

	interface LogoPanelUiBinder extends UiBinder<Widget, LogoPanel> {
	}

	
	
	public LogoPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	
}
