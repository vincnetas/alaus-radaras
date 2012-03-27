package alaus.radaras.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BrandPanel extends Composite {

	private static BrandPanelUiBinder uiBinder = GWT.create(BrandPanelUiBinder.class);

	interface BrandPanelUiBinder extends UiBinder<Widget, BrandPanel> {
	}

	public BrandPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
