/**
 * 
 */
package alaus.radaras.shared.model;

import java.io.Serializable;

/**
 * @author Vincentas
 * 
 */
public class LocationBounds implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -601596265968489584L;

	private Location southWest;
	
	private Location northEast;

	public LocationBounds() {

	}

	public LocationBounds(Location southWest, Location northEast) {
		this.southWest = southWest;
		this.northEast = northEast;
	}

	/**
	 * @return the southWest
	 */
	public Location getSouthWest() {
		return southWest;
	}

	/**
	 * @param southWest
	 *            the southWest to set
	 */
	public void setSouthWest(Location southWest) {
		this.southWest = southWest;
	}

	/**
	 * @return the northEast
	 */
	public Location getNorthEast() {
		return northEast;
	}

	/**
	 * @param northEast
	 *            the northEast to set
	 */
	public void setNorthEast(Location northEast) {
		this.northEast = northEast;
	}

}
