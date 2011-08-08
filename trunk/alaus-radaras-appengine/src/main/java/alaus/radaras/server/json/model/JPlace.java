/**
 * 
 */
package alaus.radaras.server.json.model;


/**
 * @author vienozin
 * 
 */
public class JPlace {

	private String id;
	
	/**
	 * Type of place.
	 * 
	 * @author Vincentas
	 * 
	 */
	public enum Type {
		/**
		 * Pub.
		 */
		PUB,

		/**
		 * Shop. By and take it away. No drinking in place.
		 */
		SHOP,

		/**
		 * Brewery with pub or restaurant.
		 */
		BREWPUB,

		/**
		 * Restaurant. White sheets and waiting staff.
		 */
		RESTAURANT,

		/**
		 * Shop with pub.
		 */
		PUBSHOP,

		/**
		 * Brewery. Like shop.
		 */
		BREWERY
	};

	/**
	 * Place name in original language.
	 */
	private String title;

	/**
	 * Icon id for this place.
	 */
	private String icon;

	/**
	 * Place longitude.
	 */
	private double longitude;

	/**
	 * Place latitude.
	 */
	private double latitude;

	/**
	 * Two letters ISO country code.
	 */
	private String country;

	/**
	 * City name in original language.
	 */
	private String city;

	/**
	 * Street address for this place.
	 */
	private String streetAddress;

	/**
	 * Phone number in international format for this place.
	 */
	private String phone;

	/**
	 * Internet homepage for this place.
	 */
	private String homepage;

	/**
	 * Place type.
	 */
	private Type type;

	/**
	 * Open hours. Format unknown...
	 */
	private String hours;

	/**
	 * Id's of beers available at this place.
	 */
	private String[] beerIds;

	/**
	 * Tags for this place.
	 */
	private String[] tags;

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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
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
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
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
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
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
	public String[] getBeerIds() {
		return beerIds;
	}

	/**
	 * @param beerIds the beerIds to set
	 */
	public void setBeerIds(String[] beerIds) {
		this.beerIds = beerIds;
	}

	/**
	 * @return the tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	

	
}
