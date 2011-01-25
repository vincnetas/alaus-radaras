package alaus.radaras.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class BasePanel extends Composite {

	private static BasePanelUiBinder uiBinder = GWT.create(BasePanelUiBinder.class);

	interface BasePanelUiBinder extends UiBinder<Widget, BasePanel> {
	}
	
	@UiField
	StackLayoutPanel stackPanel;
	
	public BasePanel() {
		initWidget(uiBinder.createAndBindUi(this));

		stackPanel.showWidget(stackPanel.getWidgetCount() - 1);
	}

}
