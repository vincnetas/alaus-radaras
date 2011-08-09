/**
 * 
 */
package nb.server.service.impl;

import nb.server.service.UserService;
import nb.shared.model.User;

/**
 * @author vienozin
 *
 */
public class UserServiceImpl implements UserService {

	/* (non-Javadoc)
	 * @see nb.server.service.UserService#getCurrentUser()
	 */
	@Override
	public User getCurrentUser() {
		return new User();
	}

}
