package alaus.radaras.client.ui.panels;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.StartAddPubHandler;
import alaus.radaras.client.ui.CircleOverlay;
import alaus.radaras.shared.DistanceCalculator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler.MapMouseMoveEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.PolyStyleOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MapPanel extends Composite implements StartAddPubHandler {

	private static MapPanelUiBinder uiBinder = GWT.create(MapPanelUiBinder.class);

	interface MapPanelUiBinder extends UiBinder<Widget, MapPanel> {
	}

	@UiField
	MapWidget mapWidget;
	
	private CircleOverlay circleOverlay;
	
	public MapPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		mapWidget.setZoomLevel(8);
		mapWidget.setScrollWheelZoomEnabled(true);
		mapWidget.addMapClickHandler(new MapClickHandler() {
			
			@Override
			public void onClick(MapClickEvent event) {
				if (circleOverlay != null) {
					mapWidget.removeOverlay(circleOverlay);
				}

				circleOverlay = new CircleOverlay(event.getLatLng(), getCircleRadius());
				
				int level = mapWidget.getBoundsZoomLevel(circleOverlay.getBounds());

				
				mapWidget.addOverlay(circleOverlay);				
				mapWidget.setZoomLevel(level);
				mapWidget.panTo(event.getLatLng());
			}
		});
		
		Stat.getHandlerManager().addHandler(StartAddPubHandler.type, this);
		
	}
	
	private double getCircleRadius() {
		return Math.min(getHeightInKm(), 20) / 2;
	}
	
	private double getHeightInKm() {
		LatLngBounds bounds = mapWidget.getBounds();
		return DistanceCalculator.getGeographicalDisance(
				LatLng.newInstance(bounds.getNorthEast().getLatitude(), bounds.getNorthEast().getLongitude()),
				LatLng.newInstance(bounds.getSouthWest().getLatitude(), bounds.getNorthEast().getLongitude()));
	}

	@Override
	public void startAddPub() {
		final Marker marker = new Marker(LatLng.newInstance(0, 0));
		
		mapWidget.addMapMouseMoveHandler(new MapMouseMoveHandler() {
			
			@Override
			public void onMouseMove(MapMouseMoveEvent event) {				
				marker.setLatLng(event.getLatLng());				
			}
		});
	}
	
}
