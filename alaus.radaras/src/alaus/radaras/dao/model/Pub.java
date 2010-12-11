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
	
	private String url;
	
	private List<Brand> brands;

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

	public List<Brand> getBrands() {
		return brands;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}
	
	public void addBeer(Brand beer) {
		if (brands == null) {
			brands = new ArrayList<Brand>();
		}
		brands.add(beer);
	}
}
