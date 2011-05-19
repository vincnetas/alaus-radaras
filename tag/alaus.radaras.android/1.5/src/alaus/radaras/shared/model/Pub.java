package alaus.radaras.shared.model;

import java.util.HashSet;
import java.util.Set;

public class Pub extends Updatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7356414026970385426L;

	private String title;
	
	private Double longitude;

	private Double latitude;
	
	private String country;
		
	private String city;
	
	private String address;
	
	private String phone;
	
	private String description;
	
	private String homepage;
	
	private String hours;
	
	private Set<String> beerIds = new HashSet<String>();
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		Location result = null;
		if (longitude != null && latitude != null) {
			result = new Location(longitude, latitude);
		} 
		
		return result;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.latitude = location.getLatitude();
		this.longitude = location.getLongitude();
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the homepage
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * @param homepage the homepage to set
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * @return the hours
	 */
	public String getHours() {
		return hours;
	}

	/**
	 * @param hours the hours to set
	 */
	public void setHours(String hours) {
		this.hours = hours;
	}

	/**
	 * @return the beerIds
	 */
	public Set<String> getBeerIds() {
		if (beerIds == null) {
			beerIds = new HashSet<String>();
		}
		
		return beerIds;
	}

	/**
	 * @param beerIds the beerIds to set
	 */
	public void setBeerIds(Set<String> beerIds) {
		this.beerIds = beerIds;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
}
