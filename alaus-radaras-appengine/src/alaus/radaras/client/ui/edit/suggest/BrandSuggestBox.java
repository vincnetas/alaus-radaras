/**
 * 
 */
package alaus.radaras.client.ui.edit.suggest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import alaus.radaras.client.Stat;
import alaus.radaras.shared.model.Brand;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;

/**
 * @author Vincentas
 * 
 */
public class BrandSuggestBox extends SuggestBox {

	public BrandSuggestBox() {
		super(new BrandSuggestOracle());
	}
}
