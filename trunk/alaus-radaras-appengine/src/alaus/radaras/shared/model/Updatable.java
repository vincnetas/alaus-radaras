/**
 * 
 */
package alaus.radaras.shared.model;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Vincentas
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public class Updatable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private String id;
	
	@Persistent
	private Date lastUpdate;

	@Persistent
	Boolean approved;
	
	@Persistent
	private String updatedBy;

	@Persistent
	private String parentId;
	
	@Persistent
	private Boolean modified;

	/**
	 * @return the modified
	 */
	public Boolean isModified() {
		return (modified == null) ? false : true;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(Boolean modified) {
		this.modified = modified;
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate
	 *            the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param modifiedBy
	 *            the modifiedBy to set
	 */
	public void setUpdatedBy(String modifiedBy) {
		this.updatedBy = modifiedBy;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
	 * @return the approved
	 */
	public Boolean isApproved() {
		return (approved != null) ? approved : false;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
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
		// TODO Auto-generated method stub
		return super.hashCode();
	}}



