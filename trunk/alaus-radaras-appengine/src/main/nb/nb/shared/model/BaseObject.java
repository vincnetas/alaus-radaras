package nb.shared.model;

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
public abstract class BaseObject implements Serializable {

	public enum State {SUGGESTION, CURRENT, HISOTRY, REJECTED, DELETED};
	
	/**
	 * Instance id. There can be multiple instance id's for one object id.
	 */
	@PrimaryKey
	private String id;

	/**
	 * Object id.
	 */
	@Persistent
	private String objectId;
	
	/**
	 * Id of user who created this object.
	 */
	@Persistent
	private String createdBy;
	
	/**
	 * Id of user who approved/rejected this object.
	 */
	@Persistent
	private String approvedBy;
	
	/**
	 * Id of user who deleted this object
	 */
	@Persistent
	private String deletedBy;
	
	/**
	 * Date when this object was created.
	 */
	@Persistent
	private Date creationDate;
	
	/**
	 * Date when this object was approved/rejected.
	 */
	@Persistent
	private Date approvalDate;
	
	/**
	 * Date when this object was deleted.
	 */
	@Persistent
	private Date deletionDate;
	
	/**
	 * Date when first state of this object was created.
	 */
	@Persistent
	private Date firstCreationDate;
	
	/**
	 * Object state.
	 */
	@Persistent
	private State state;

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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the firstCreationDate
	 */
	public Date getFirstCreationDate() {
		return firstCreationDate;
	}

	/**
	 * @param firstCreationDate the firstCreationDate to set
	 */
	public void setFirstCreationDate(Date firstCreationDate) {
		this.firstCreationDate = firstCreationDate;
	}

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	/**
	 * @return the approvalDate
	 */
	public Date getApprovalDate() {
		return approvalDate;
	}

	/**
	 * @param approvalDate the approvalDate to set
	 */
	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	/**
	 * @return the deletedBy
	 */
	public String getDeletedBy() {
		return deletedBy;
	}

	/**
	 * @param deletedBy the deletedBy to set
	 */
	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	/**
	 * @return the deletionDate
	 */
	public Date getDeletionDate() {
		return deletionDate;
	}

	/**
	 * @param deletionDate the deletionDate to set
	 */
	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}

	/**
	 * @return the objectId
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof BaseObject) {
			result = getId().equals(((BaseObject)obj).getId());
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {		
		return String.valueOf(getId()).hashCode();		
	}
}