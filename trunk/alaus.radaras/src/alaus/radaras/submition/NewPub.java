package alaus.radaras.submition;

import android.location.Location;

public class NewPub {
	
	private boolean near;
	
	private Location location;
	
	private String message;

	public void setNear(boolean near) {
		this.near = near;
	}

	public boolean isNear() {
		return near;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
