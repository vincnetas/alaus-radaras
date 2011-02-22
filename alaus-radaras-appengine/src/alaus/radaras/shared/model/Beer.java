/**
 * 
 */
package alaus.radaras.shared.model;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * @author Vincentas
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Beer extends Updatable {

	@Persistent
	private String title;

	@Persistent
	private String brandId;

	@Persistent
	private String icon;

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
	 * @return the brandId
	 */
	public String getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId
	 *            the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
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
}
