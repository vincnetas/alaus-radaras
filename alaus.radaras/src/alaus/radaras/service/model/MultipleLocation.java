package alaus.radaras.service.model;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

public abstract class MultipleLocation {
	
	private List<Location> locations = new ArrayList<Location>();

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<Location> getLocations() {
		return locations;
	}

}
