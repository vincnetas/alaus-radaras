/**
 * 
 */
package alaus.radaras.client.ui.edit.suggest;

import com.google.gwt.user.client.ui.SuggestBox;

/**
 * @author Vincentas
 * 
 */
public class BrandSuggestBox extends SuggestBox {

	public BrandSuggestBox() {
		super(new BrandSuggestOracle());
	}
}
