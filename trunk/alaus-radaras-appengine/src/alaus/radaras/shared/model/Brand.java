package alaus.radaras.shared.model;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Brand extends Updatable {

	@Persistent
	private String title;

	@Persistent
	private String icon;

	@Persistent
	private String homePage;

	@Persistent
	private String country;

	@Persistent
	private String homeTown;

	@Persistent
	private String description;

	@Persistent
	private Set<String> tags = new HashSet<String>();

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
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
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the homePage
	 */
	public String getHomePage() {
		return homePage;
	}

	/**
	 * @param homePage
	 *            the homePage to set
	 */
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the homeTown
	 */
	public String getHomeTown() {
		return homeTown;
	}

	/**
	 * @param homeTown
	 *            the homeTown to set
	 */
	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the tags
	 */
	public Set<String> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	
	public void setTags(String tags) {
		Set<String> result = new HashSet<String>();
		
		if (tags != null) {
			String[] strings = tags.split(",");
			for (String string : strings) {
				result.add(string.trim().toLowerCase());
			}
		}
		
		setTags(result);
	}
	
	public String getTagsAsString() {
		StringBuilder builder = new StringBuilder();
		
		boolean first = true;
		for (String tag : getTags()) {
			if (!first) {
				builder.append(", ");
			} else {
				first = false;
			}
			
			builder.append(tag);
		}
		
		return builder.toString();
	}

}
