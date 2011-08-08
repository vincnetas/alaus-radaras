package alaus.radaras.client.ui.edit.suggest;

import alaus.radaras.shared.model.Beer;

import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class BeerSuggestion implements Suggestion {

	private Beer beer;

	private String query;

	public BeerSuggestion(String query) {
		this.query = query;
	}

	public BeerSuggestion(Beer beer, String query) {
		this.beer = beer;
		this.query = query;
	}
	
	public String getDisplayString() {
		String result;
		if (getBeer() != null) {
			result = getBeer().getTitle();
		} else {
			result = "Add beer (" + getQuery() + ")";
		}
		
		return result;
	}

	public String getReplacementString() {
		return "";
	}

	/**
	 * @return the beer
	 */
	public Beer getBeer() {
		return beer;
	}

	/**
	 * @param beer the beer to set
	 */
	public void setBeer(Beer beer) {
		this.beer = beer;
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