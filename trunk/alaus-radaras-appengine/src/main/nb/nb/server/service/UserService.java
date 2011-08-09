/**
 * 
 */
package nb.server.service;

import nb.shared.model.User;

/**
 * @author vienozin
 * 
 */
public interface UserService {

	/**
	 * 
	 * @return Returns current logged in user or null if user is not logged in.
	 */
	User getCurrentUser();

}
