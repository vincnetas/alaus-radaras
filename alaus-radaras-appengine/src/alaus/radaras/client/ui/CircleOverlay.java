/**
 * 
 */
package alaus.radaras.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.PolyStyleOptions;
import com.google.gwt.maps.client.overlay.Polygon;

/**
 * @author Vincentas
 * 
 */
public class CircleOverlay extends Polygon {

	public CircleOverlay(LatLng center, double radius) {
		super(getPoints(center, radius));
		setFillStyle(PolyStyleOptions.newInstance("blue"));
	}
	
	private static LatLng[] getPoints(LatLng center, double radius) {
		// calculating km/degree
		double latConv = center.distanceFrom(LatLng.newInstance(center.getLatitude() + 0.1, center.getLongitude())) / 100;
		double lngConv = center.distanceFrom(LatLng.newInstance(center.getLatitude(), center.getLongitude() + 0.1)) / 100;

		List<LatLng> points = new ArrayList<LatLng>();
		int step = 360 / 40;
		for (int i = 0; i <= 360; i += step) {
			LatLng point = LatLng.newInstance(
					center.getLatitude() + (radius / latConv * Math.cos(i * Math.PI / 180)),
					center.getLongitude() + (radius / lngConv * Math.sin(i * Math.PI / 180)));

			points.add(point);
		}
		points.add(points.get(0));
		return points.toArray(new LatLng[0]);
	}
}
