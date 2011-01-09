/**
 * 
 */
package alaus.radaras.shared.model;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Vincentas
 * 
 */
public class BeerLocation implements Serializable {

	private String title;

	private Location location;

	private Set<String> beerIds;

	private Set<String> tags;

	public BeerLocation(String title, Location location, Set<String> beerIds, Set<String> tags) {
		setTitle(title);
		setLocation(location);
		setBeerIds(beerIds);
		setTags(tags);
	}

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
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
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

	/**
	 * @return the beerIds
	 */
	public Set<String> getBeerIds() {
		return beerIds;
	}

	/**
	 * @param beerIds
	 *            the beerIds to set
	 */
	public void setBeerIds(Set<String> beerIds) {
		this.beerIds = beerIds;
	}

}
