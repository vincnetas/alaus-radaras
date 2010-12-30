package alaus.radaras.shared.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Quote extends Updatable {
	
	@Persistent
	private int index;
	
	@Persistent
	private String text;

	
	public Quote(String text, int index) {
		setIndex(index);
		setText(text);
	}
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		setId(String.valueOf(text.hashCode()));
		this.text = text;
	}	
}
