/**
 * 
 */
package alaus.radaras.shared.model;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Vincentas
 *
 */
@PersistenceCapable(detachable="true")
public class BrandPubAssociation extends Association {

	@PrimaryKey
	private String id;
	
	@Persistent
	private String brand;
	
	@Persistent
	private String pub;
	
	public BrandPubAssociation(String brand, String pub) {
		setBrand(brand);
		setPub(pub);
		setId(brand + "," + pub);
	}
	
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
	 * @return the aId
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param aId the aId to set
	 */
	public void setBrand(String aId) {
		this.brand = aId;
	}

	/**
	 * @return the bId
	 */
	public String getPub() {
		return pub;
	}

	/**
	 * @param bId the bId to set
	 */
	public void setPub(String bId) {
		this.pub = bId;
	}


}
