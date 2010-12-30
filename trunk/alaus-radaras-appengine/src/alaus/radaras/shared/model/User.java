/**
 * 
 */
package alaus.radaras.shared.model;

import java.io.Serializable;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.persistence.Id;

/**
 * @author Vincentas
 *
 */
@PersistenceCapable(detachable = "true")
public class User implements Serializable {
	
	@Id
	private String id;
	
	@Persistent
	private String email;

	@Persistent
	private String name;
}
