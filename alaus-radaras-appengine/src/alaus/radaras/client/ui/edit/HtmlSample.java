package alaus.radaras.client.ui.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.UIObject;

public class HtmlSample extends UIObject {

	private static HtmlSampleUiBinder uiBinder = GWT.create(HtmlSampleUiBinder.class);

	interface HtmlSampleUiBinder extends UiBinder<Element, HtmlSample> {
	}

	@UiField
	SpanElement nameSpan;

	public HtmlSample(String firstName) {
		setElement(uiBinder.createAndBindUi(this));
		nameSpan.setInnerText(firstName);
	}

}
