/**
 * 
 */
package nb.server.service.impl;

import org.w3c.dom.UserDataHandler;

import com.google.inject.Inject;

import nb.server.service.UserService;
import nb.shared.model.User;

/**
 * @author vienozin
 *
 */
public class UserServiceImpl implements UserService {

	@Inject
	private com.google.appengine.api.users.UserService userService;
	
	
	
	/* (non-Javadoc)
	 * @see nb.server.service.UserService#getCurrentUser()
	 */
	@Override
	public User getCurrentUser() {
		com.google.appengine.api.users.User currentUser = getUserService().getCurrentUser();
		User result = new User();
		
		
		return result;
	}

	/**
	 * @return the userService
	 */
	public com.google.appengine.api.users.UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(
			com.google.appengine.api.users.UserService userService) {
		this.userService = userService;
	}
	
	

}
