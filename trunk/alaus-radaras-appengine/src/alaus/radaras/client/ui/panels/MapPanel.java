package alaus.radaras.client.ui.panels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubAddedEvent;
import alaus.radaras.client.events.PubAddedHandler;
import alaus.radaras.client.events.StartAddPubHandler;
import alaus.radaras.client.ui.CircleOverlay;
import alaus.radaras.client.ui.dialogs.EditPubDialog;
import alaus.radaras.shared.DistanceCalculator;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class MapPanel extends Composite implements StartAddPubHandler, PubAddedHandler {

	private static MapPanelUiBinder uiBinder = GWT.create(MapPanelUiBinder.class);

	interface MapPanelUiBinder extends UiBinder<Widget, MapPanel> {
	}
	
	@UiField
	MapWidget mapWidget;
	
	public MapPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		mapWidget.setZoomLevel(8);
		mapWidget.setScrollWheelZoomEnabled(true);
		mapWidget.addMapClickHandler(new BeerMapClickHandler());
		
		Stat.getHandlerManager().addHandler(StartAddPubHandler.type, this);
		Stat.getHandlerManager().addHandler(PubAddedHandler.type, this);
		
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

	final Marker addPubmarker = new Marker(LatLng.newInstance(0, 0));
	
	private boolean addingPub = false;
	
	@Override
	public void startAddPub() {
		addingPub = true;
		
		mapWidget.addOverlay(addPubmarker);
		mapWidget.addMapMouseMoveHandler(beerMapMouseMoveHandler);
	}
	
	private BeerMapMouseMoveHandler beerMapMouseMoveHandler = new BeerMapMouseMoveHandler();
	
	class BeerMapMouseMoveHandler implements MapMouseMoveHandler {
		
		@Override
		public void onMouseMove(MapMouseMoveEvent event) {				
			addPubmarker.setLatLng(event.getLatLng());				
		}
		
	}
	
	class BeerMapClickHandler implements MapClickHandler {

		private CircleOverlay circleOverlay;
		
		@Override
		public void onClick(MapClickEvent event) {
			if (event.getOverlay() != null) {
				if (!event.getOverlay().equals(circleOverlay)) {
					return;
				}
			}
			
			LatLng location = (event.getLatLng() != null) ? event.getLatLng() : event.getOverlayLatLng();
			
			if (addingPub) {
				mapWidget.removeMapMouseMoveHandler(beerMapMouseMoveHandler);
				
				Pub pub = new Pub();
				pub.setLocation(latLngToLocation(location));
				
				EditPubDialog editPubDialog = new EditPubDialog(pub);
				editPubDialog.center();
				editPubDialog.addCloseHandler(new CloseHandler<PopupPanel>() {
					
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						addingPub = false;
						mapWidget.removeOverlay(addPubmarker);
					}
				});
			} else {
				if (circleOverlay != null) {
					mapWidget.removeOverlay(circleOverlay);
				}
	
				double radius = getCircleRadius();
				circleOverlay = CircleOverlay.newInstance(location, radius);
				
				int level = mapWidget.getBoundsZoomLevel(circleOverlay.getBounds());
					
				mapWidget.addOverlay(circleOverlay);				
				mapWidget.setZoomLevel(level);
				mapWidget.panTo(location);
				
				Stat.getBeerService().findPubs(latLngToLocation(location), radius, new AsyncCallback<List<Pub>>() {
					
					@Override
					public void onSuccess(List<Pub> result) {
						for (final Pub pub : result) {
							Stat.getHandlerManager().fireEvent(new PubAddedEvent(pub));
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("fail");
					}
				});
			}
		}		
	}
	
	private Location latLngToLocation(LatLng position) {
		return new Location(position.getLongitude(), position.getLatitude());
	}
	
	private LatLng locationToLatLng(Location position) {
		return LatLng.newInstance(position.getLatitude(), position.getLongitude());
	}

	private Map<Pub, Marker> pubs = new HashMap<Pub, Marker>();
	
	@Override
	public void pubAdded(final Pub pub) {
		if (pub.getLocation() != null) {			
			final Marker marker;
			if (pubs.containsKey(pub)) {
				marker = pubs.get(pub);
			} else {
				marker = new Marker(locationToLatLng(pub.getLocation()));
			}
			mapWidget.addOverlay(marker);
			
			marker.addMarkerClickHandler(new MarkerClickHandler() {
				
				@Override
				public void onClick(MarkerClickEvent event) {
					InfoWindow infoWindow = mapWidget.getInfoWindow();
					infoWindow.open(marker, new InfoWindowContent(new PubPanel(pub)));									
				}
			});
		}
	}
	
}
