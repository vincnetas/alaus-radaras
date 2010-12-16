package alaus.radaras.shared.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(detachable="true")
public class Location {

	@Persistent
	private double longtitude;
	
	@Persistent
	private double latitude;

	public Location() {
		
	}
	
	public Location(double longtitude, double latitude) {
		setLatitude(latitude);
		setLongtitude(longtitude);
	}
	
	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}
