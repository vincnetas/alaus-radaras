package alaus.radaras.client.ui.edit.suggest;

import alaus.radaras.shared.model.Brand;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class BrandSuggestion implements Suggestion {

	private Brand brand;

	private String query;

	public BrandSuggestion(String query) {
		this.query = query;
	}

	public BrandSuggestion(Brand brand, String query) {
		this.brand = brand;
		this.query = query;
	}

	private String getText() {
		String result;
		if (getBrand() != null) {
			result = getBrand().getTitle();
		} else {
			result = "Add brand (" + getQuery() + ")";
		}

		return result;
	}

	
	public String getDisplayString() {
		return getText();
	}

	
	public String getReplacementString() {
		return getText();
	}

	/**
	 * @return the brand
	 */
	public Brand getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

}
