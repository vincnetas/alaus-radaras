/**
 * 
 */
package alaus.radaras.shared.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vincentas
 * 
 */
public class Updatable implements Serializable {

	private String id;
	
	private Set<String> tags = new HashSet<String>();
	
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


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Updatable) {
			Updatable updatable = (Updatable) obj;
			if (updatable.getId() != null && getId() != null) {
				result = updatable.getId().equals(getId());
			}
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}



