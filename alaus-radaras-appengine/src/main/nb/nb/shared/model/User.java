/**
 * 
 */
package nb.shared.model;

import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author Vincentas
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class User {

	public enum Role {
		ADMIN,
		TRUSTED_USER,
		USER
	};
	
	/**
	 * User id. Usually email.
	 */
	@PrimaryKey
	private String userId;
	
	/**
	 * User role.
	 */
	private Role role;
	
	/**
	 * Set of places id's that this user is owner of.
	 */
	private Set<String> ownerForPlaces;
	
	/**
	 * Set of companies id's that this user is owner of.
	 */
	private Set<String> ownerForCompanies;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the ownerForPlaces
	 */
	public Set<String> getOwnerForPlaces() {
		return ownerForPlaces;
	}

	/**
	 * @param ownerForPlaces the ownerForPlaces to set
	 */
	public void setOwnerForPlaces(Set<String> ownerForPlaces) {
		this.ownerForPlaces = ownerForPlaces;
	}

	/**
	 * @return the ownerForCompanies
	 */
	public Set<String> getOwnerForCompanies() {
		return ownerForCompanies;
	}

	/**
	 * @param ownerForCompanies the ownerForCompanies to set
	 */
	public void setOwnerForCompanies(Set<String> ownerForCompanies) {
		this.ownerForCompanies = ownerForCompanies;
	}
	
	
}
