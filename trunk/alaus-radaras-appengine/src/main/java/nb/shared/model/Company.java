package nb.shared.model;

import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Vincentas
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Company extends BaseHistoryObject {

	/**
	 * Company title in original language.
	 */
	@Persistent
	private String title;

	/**
	 * Icon id for this company.
	 */
	@Persistent
	private String icon;

	/**
	 * Company internet homepage.
	 */
	@Persistent
	private String homepage;

	/**
	 * Origin country of company
	 */
	@Persistent
	private String country;

	/**
	 * Company hometown.
	 */
	@Persistent
	private String hometown;
	
	/**
	 * Id's of this company's beers. This is indirect value for beer companyId {@link Beer}.
	 */
	@Persistent
	private Set<String> beerIds;

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
	 * @return the hometown
	 */
	public String getHometown() {
		return hometown;
	}

	/**
	 * @param hometown the hometown to set
	 */
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	/**
	 * @return the beerIds
	 */
	public Set<String> getBeerIds() {
		return beerIds;
	}

	/**
	 * @param beerIds the beerIds to set
	 */
	public void setBeerIds(Set<String> beerIds) {
		this.beerIds = beerIds;
	}
	
	
}
