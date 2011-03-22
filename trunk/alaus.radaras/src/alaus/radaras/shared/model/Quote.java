package alaus.radaras.shared.model;


public class Quote extends Updatable {
	
	private int index;
	
	private String text;
	
	public Quote() {
		
	}
	
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
