package alaus.radaras.shared.model;

import java.io.Serializable;

public class Location implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7850139646130809824L;

	private double longitude;

	private double latitude;

	public Location() {

	}

	public Location(double longitude, double latitude) {
		setLatitude(latitude);
		setLongitude(longitude);
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Lat: " + getLatitude() + " Lon: " + getLongitude();
	}
	
	 

}
