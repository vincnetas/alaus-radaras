/**
 * 
 */
package nb.server.service.impl;

import nb.server.dao.UserDao;
import nb.server.service.UserService;
import nb.shared.model.User;

import com.google.inject.Inject;

/**
 * @author vienozin
 *
 */
public class UserServiceImpl implements UserService {

	@Inject
	private com.google.appengine.api.users.UserService userService;
	
	@Inject
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see nb.server.service.UserService#getCurrentUser()
	 */
	@Override
	public User getCurrentUser() {
		User result = null;
		
		com.google.appengine.api.users.User currentUser = getUserService().getCurrentUser();
		if (currentUser != null) {
			result = getUserDao().read(currentUser.getEmail());
		}
		
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

	/**
	 * @return the userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	

}
