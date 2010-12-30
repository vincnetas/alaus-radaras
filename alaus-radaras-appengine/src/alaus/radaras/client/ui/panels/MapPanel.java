package alaus.radaras.client.ui.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MapPanel extends Composite {

	private static MapPanelUiBinder uiBinder = GWT.create(MapPanelUiBinder.class);

	interface MapPanelUiBinder extends UiBinder<Widget, MapPanel> {
	}

	@UiField
	MapWidget mapWidget;
	
	public MapPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
