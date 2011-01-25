/**
 * 
 */
package alaus.radaras.client.ui.filter;

import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class CountryFilter extends BaseFilter {

	@Override
	protected Widget getPubWidget(Pub pub) {
		CheckBox checkBox = new CheckBox(pub.getCountry());
		checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> arg0) {

			}
		});		
		
		return checkBox;
	}


	
}
