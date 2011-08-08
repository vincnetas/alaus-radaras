package alaus.radaras.client.ui.panels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alaus.radaras.client.BaseAsyncCallback;
import alaus.radaras.client.Stat;
import alaus.radaras.client.events.ChangeUserLocationHandler;
import alaus.radaras.client.events.PubAddedEvent;
import alaus.radaras.client.events.PubAddedHandler;
import alaus.radaras.client.events.PubFilterHandler;
import alaus.radaras.client.events.PubRemovedEvent;
import alaus.radaras.client.events.StartAddPubHandler;
import alaus.radaras.client.ui.dialogs.EditDialog;
import alaus.radaras.client.ui.edit.AddPubWidget;
import alaus.radaras.client.ui.filter.PubFilter;
import alaus.radaras.shared.DistanceCalculator;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.ControlAnchor;
import com.google.gwt.maps.client.control.ControlPosition;
import com.google.gwt.maps.client.control.LargeMapControl3D;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.LatLngBounds;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MapPanel extends Composite implements StartAddPubHandler, PubAddedHandler, ChangeUserLocationHandler {

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
		mapWidget.addControl(new LargeMapControl3D(), new ControlPosition(ControlAnchor.TOP_RIGHT, 20, 20));
		
		Stat.getHandlerManager().addHandler(StartAddPubHandler.type, this);
		Stat.getHandlerManager().addHandler(PubAddedHandler.type, this);
		Stat.getHandlerManager().addHandler(ChangeUserLocationHandler.type, this);
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
	
	
	public void startAddPub() {
		addingPub = true;
		
		mapWidget.addOverlay(addPubmarker);
		mapWidget.addMapMouseMoveHandler(beerMapMouseMoveHandler);
	}
	
	private BeerMapMouseMoveHandler beerMapMouseMoveHandler = new BeerMapMouseMoveHandler();
	
	class BeerMapMouseMoveHandler implements MapMouseMoveHandler {
		
		
		public void onMouseMove(MapMouseMoveEvent event) {				
			addPubmarker.setLatLng(event.getLatLng());				
		}
		
	}
	
	class BeerMapClickHandler implements MapClickHandler {

//		private CircleOverlay circleOverlay;
		
		
		public void onClick(MapClickEvent event) {
//			if (event.getOverlay() != null) {
//				if (!event.getOverlay().equals(circleOverlay)) {
//					return;
//				}
//			}
			
			final LatLng location = (event.getLatLng() != null) ? event.getLatLng() : null;//event.getOverlayLatLng();			
			
			if (addingPub) {
				mapWidget.removeMapMouseMoveHandler(beerMapMouseMoveHandler);
				
				Pub pub = new Pub();
				pub.setLocation(latLngToLocation(location));
				
				final AddPubWidget editPubWidget = new AddPubWidget(pub);
				EditDialog editDialog = new EditDialog(editPubWidget, "Add Pub") {
					
					
					public void onOkButtonClick(ClickEvent event) {
						Stat.getBeerService().addPub(editPubWidget.getPub(), new BaseAsyncCallback<Pub>() {
							
							
							public void onSuccess(Pub result) {
								addingPub = false;
								mapWidget.removeOverlay(addPubmarker);
								Stat.getHandlerManager().fireEvent(new PubAddedEvent(result));
								hide();								
							}
						});
					}
				};

				editDialog.setHTML("Edit Pub");
				editDialog.center();
			} 
//			else {
//				if (circleOverlay != null) {
//					mapWidget.removeOverlay(circleOverlay);
//				}
//	
//				double radius = getCircleRadius();
//				circleOverlay = CircleOverlay.newInstance(location, radius);
//				
//				int level = mapWidget.getBoundsZoomLevel(circleOverlay.getBounds());
//					
//				mapWidget.addOverlay(circleOverlay);				
//				mapWidget.setZoomLevel(level);
//				mapWidget.panTo(location);
//				
//				findPubs(location);
//			}
		}		
	}
	
	private void findPubs(LocationBounds bounds) {
		clearPubs();
		
		Stat.getBeerService().findPubs(bounds, new BaseAsyncCallback<List<Pub>>() {
			
			
			public void onSuccess(List<Pub> result) {
				for (final Pub pub : result) {
					Stat.getHandlerManager().fireEvent(new PubAddedEvent(pub));
				}						
			}
			
		});
	}
	
	private void clearPubs() {
		for (PubMarker marker : pubs.values()) {
			mapWidget.removeOverlay(marker);
			Stat.getHandlerManager().fireEvent(new PubRemovedEvent(marker.getPub()));
		}		
		pubs.clear();		
	}

	private Location latLngToLocation(LatLng position) {
		return new Location(position.getLongitude(), position.getLatitude());
	}
	
	private LatLng locationToLatLng(Location position) {
		return LatLng.newInstance(position.getLatitude(), position.getLongitude());
	}

	private Map<Pub, PubMarker> pubs = new HashMap<Pub, PubMarker>();
	
	class PubMarker extends Marker implements PubFilterHandler {
		
		private Pub pub;
		
		public PubMarker(Pub pub) {
			super(locationToLatLng(pub.getLocation()));
			this.pub = pub;
			
			addMarkerClickHandler(new MarkerClickHandler() {
				
				
				public void onClick(MarkerClickEvent event) {
					InfoWindow infoWindow = mapWidget.getInfoWindow();
					infoWindow.open(PubMarker.this, new InfoWindowContent(new PubPanel(PubMarker.this.pub)));									
				}
			});
			
			Stat.getHandlerManager().addHandler(PubFilterHandler.type, this);
		}

		/**
		 * @return the pub
		 */
		public Pub getPub() {
			return pub;
		}

        
        public void filter(PubFilter filter) {
            setVisible(filter.match(getPub()));
        }
	}
	
	
	public void pubAdded(final Pub pub) {
		if (pub.getLocation() != null) {
			PubMarker marker = pubs.get(pub);
			
			if (marker == null) {
				marker = new PubMarker(pub);
		
				mapWidget.addOverlay(marker);
				pubs.put(pub, marker);
			}
		}
	}

	
	public void changeLocation(final String location) {
		Stat.getGeocoder().getLocations(location, new LocationCallback() {
			
			
			public void onSuccess(JsArray<Placemark> locations) {
				Placemark placemark = locations.get(0);
//				mapWidget.setZoomLevel(mapWidget.getBoundsZoomLevel(placemark.getExtendedData().getBounds()));
				mapWidget.panTo(placemark.getPoint());
				
				LocationBounds bounds = new LocationBounds(
						latLngToLocation(mapWidget.getBounds().getSouthWest()),
						latLngToLocation(mapWidget.getBounds().getNorthEast())
						);
				findPubs(bounds);
			}
			
			
			public void onFailure(int statusCode) {
				Window.alert("Failed location lookup for (" + location + ") : " + statusCode);				
			}
		});
	}	
}
