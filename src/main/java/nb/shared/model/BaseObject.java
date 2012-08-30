/**
 * 
 */
package nb.shared.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author vincentas
 *
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public abstract class BaseObject {

	/**
	 * Instance id. There can be multiple instance id's for one object id.
	 */
	@PrimaryKey
	private String id;


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

}
