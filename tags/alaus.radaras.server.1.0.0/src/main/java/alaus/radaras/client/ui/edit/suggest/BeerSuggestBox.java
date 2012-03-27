/**
 * 
 */
package alaus.radaras.client.ui.edit.suggest;

import com.google.gwt.user.client.ui.SuggestBox;

/**
 * @author Vincentas
 * 
 */
public class BeerSuggestBox extends SuggestBox {

	public BeerSuggestBox() {
		super(new BeerSuggestOracle());
	}
}




