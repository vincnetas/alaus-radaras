/**
 * 
 */
package alaus.radaras.client.ui;

import alaus.radaras.shared.model.Location;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class SelectLocationWidget extends Composite {

	private static SelectLocationWidgetUiBinder uiBinder = GWT.create(SelectLocationWidgetUiBinder.class);

	interface SelectLocationWidgetUiBinder extends UiBinder<Widget, SelectLocationWidget> {
	}

	@UiField
	TextBox longitude;
	
	@UiField
	TextBox latitude;
	
	private Location location = new Location();
	
	public SelectLocationWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public SelectLocationWidget(Location location) {
		initWidget(uiBinder.createAndBindUi(this));
		
		setLocation(location);
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		location.setLatitude(Double.valueOf(latitude.getText()));
		location.setLongitude(Double.valueOf(longitude.getText()));
		
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		if (location != null) {
			latitude.setText(Double.toString(location.getLatitude()));
			longitude.setText(Double.toString(location.getLongitude()));
			this.location = location;
		}		
	}
}
