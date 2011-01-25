/**
 * 
 */
package alaus.radaras.client.ui.filter;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class TagFilter extends BaseFilter {

	@Override
	protected Widget getPubWidget(Pub pub) {
		Image image = new Image("/img/alus.png");
		PushButton button = new PushButton(image);
		button.setWidth("20px");
		button.setHeight("20px");
		button.setTitle("pub");
		button.setText("pub");
		
		return button;
	}
}
