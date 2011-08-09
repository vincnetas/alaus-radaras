/**
 * 
 */
package alaus.radaras.server.json.model;

import javax.jdo.annotations.Persistent;

import com.google.gwt.place.shared.Place;

/**
 * @author vienozin
 *
 */
public class JBeer extends JBase {

	/**
	 * Beer title in original language.
	 */
	@Persistent
	private String title;

	/**
	 * Icon id for this beer.
	 */
	@Persistent
	private String icon;
	
	/**
	 * Id of company producing this beer.
	 */
	@Persistent
	private String companyId;

	/**
	 * Id's of places where this beer is available. This is indirect value for place beerIds {@link Place}.
	 */
	@Persistent
	private String[] placeIds;
	
	/**
	 * Beer bitterness in EBU scale.
	 */
	@Persistent
	private Integer bitterness;
	
	/**
	 * Beer color in EBC scale.
	 */
	@Persistent
	private Integer color;
	
	/**
	 * Extract content in tenth's of percent.
	 */
	@Persistent
	private Integer gravity;
	
	/**
	 * Alcohol content in tenth's of percent.
	 */
	@Persistent
	private Integer alcohol;

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
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	/**
     * @return the placeIds
     */
    public String[] getPlaceIds() {
        return placeIds;
    }

    /**
     * @param placeIds the placeIds to set
     */
    public void setPlaceIds(String[] placeIds) {
        this.placeIds = placeIds;
    }

    /**
	 * @return the bitterness
	 */
	public Integer getBitterness() {
		return bitterness;
	}

	/**
	 * @param bitterness the bitterness to set
	 */
	public void setBitterness(Integer bitterness) {
		this.bitterness = bitterness;
	}

	/**
	 * @return the color
	 */
	public Integer getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Integer color) {
		this.color = color;
	}

	/**
	 * @return the gravity
	 */
	public Integer getGravity() {
		return gravity;
	}

	/**
	 * @param gravity the gravity to set
	 */
	public void setGravity(Integer gravity) {
		this.gravity = gravity;
	}

	/**
	 * @return the alcohol
	 */
	public Integer getAlcohol() {
		return alcohol;
	}

	/**
	 * @param alcohol the alcohol to set
	 */
	public void setAlcohol(Integer alcohol) {
		this.alcohol = alcohol;
	}


}
