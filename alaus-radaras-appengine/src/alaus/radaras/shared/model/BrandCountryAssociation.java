package alaus.radaras.shared.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class BrandCountryAssociation extends Association {

	@PrimaryKey
	private String id;
	
	@Persistent
	private String brand;
	
	@Persistent
	private String country;
	
	public BrandCountryAssociation(String brand, String country) {
		setBrand(brand);
		setCountry(country);
		setId(brand + "," + country);
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

	/**
	 * @return the aId
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param aId the aId to set
	 */
	public void setBrand(String aId) {
		this.brand = aId;
	}

	/**
	 * @return the bId
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param bId the bId to set
	 */
	public void setCountry(String bId) {
		this.country = bId;
	}


}
