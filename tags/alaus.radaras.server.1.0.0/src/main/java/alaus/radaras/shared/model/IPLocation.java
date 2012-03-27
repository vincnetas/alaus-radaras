/**
 * 
 */
package alaus.radaras.shared.model;

import java.io.Serializable;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class IPLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6393376486638717403L;

	private String country;
	
	private String city;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result;
		if (getCity() != null && getCountry() != null) {
			result = getCity() + ", " + getCountry();
		} else if (getCountry() !=  null) {
			result = getCountry();
		} else {
			result = "";
		}
		
		return result;
	}
	
	
	
	
	
}
