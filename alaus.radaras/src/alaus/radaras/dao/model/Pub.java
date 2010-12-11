package alaus.radaras.dao.model;

import java.util.ArrayList;
import java.util.List;

public class Pub {

	private String id;
	
	private String title;
	
	private Location location;
	
	private String address;
	
	private String notes;
	
	private String phone;
	
	private List<Beer> beers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Beer> getBeers() {
		return beers;
	}

	public void setBeers(List<Beer> beers) {
		this.beers = beers;
	}
	
	public void addBeer(Beer beer) {
		if (beers == null) {
			beers = new ArrayList<Beer>();
		}
		beers.add(beer);
	}
}
