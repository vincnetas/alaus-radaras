package alaus.radaras.submition;

import android.location.Location;

public class NewPub {
	
	private boolean near;
	
	private Location location;
	
	private String text;

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

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
