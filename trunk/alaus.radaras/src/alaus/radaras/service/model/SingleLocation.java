package alaus.radaras.service.model;

import android.location.Location;

public abstract class SingleLocation {
	
	
	private Location location;
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}


}
