/**
 * 
 */
package alaus.radaras;

import alaus.radaras.dao.model.Pub;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * @author Vincentas
 *
 */
public class PubOverlayItem extends OverlayItem {

	private final Pub pub;
	
	public PubOverlayItem(Pub pub) {
		super(new GeoPoint((int) (pub.getLocation().getLatitude() * 1e6), (int) (pub.getLocation().getLongtitude() * 1e6)), pub.getTitle(), pub.getNotes());
		
		this.pub = pub;
	}

	/**
	 * @return the pub
	 */
	public Pub getPub() {
		return pub;
	}
}
