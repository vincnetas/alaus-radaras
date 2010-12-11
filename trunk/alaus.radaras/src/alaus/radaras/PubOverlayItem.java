/**
 * 
 */
package alaus.radaras;

import alaus.radaras.dao.model.Pub;
import alaus.radaras.utils.Utils;

import com.google.android.maps.OverlayItem;

/**
 * @author Vincentas
 *
 */
public class PubOverlayItem extends OverlayItem {

	private final Pub pub;
	
	public PubOverlayItem(Pub pub) {
		super(Utils.geoPoint(pub.getLocation()), pub.getTitle(), pub.getNotes());
		
		this.pub = pub;
	}

	/**
	 * @return the pub
	 */
	public Pub getPub() {
		return pub;
	}
}
