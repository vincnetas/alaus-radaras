/**
 * 
 */
package alaus.radaras.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.UIObject;

/**
 * @author vienozin
 *
 */
public class TH extends UIObject {

	private static THUiBinder uiBinder = GWT.create(THUiBinder.class);

	interface THUiBinder extends UiBinder<Element, TH> {
	}

	@UiField
	SpanElement nameSpan;

	public TH(String firstName) {
		setElement(uiBinder.createAndBindUi(this));

		// Can access @UiField after calling createAndBindUi
		nameSpan.setInnerText(firstName);
	}

}
