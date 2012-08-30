/**
 * 
 */
package nb.server.service.impl;

import java.util.HashSet;

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
			result = new User();
			result.setUserId(currentUser.getEmail());
			result = getUserDao().findOne(result);
			
			if (result == null) {
				result = new User();
				result.setOwnerForCompanies(new HashSet<String>());
				result.setOwnerForPlaces(new HashSet<String>());
				
				if (getUserService().isUserAdmin()) {
					result.setRole(User.Role.ADMIN);
				} else {
					result.setRole(User.Role.USER);
				}				
				
				result.setUserId(currentUser.getEmail());

				getUserDao().create(result);
			}
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

	@Override
	public String getLoginUrl(String redirectUrl) {
		return getUserService().createLoginURL(redirectUrl);
	}

	@Override
	public String getLogoutUrl(String redirectUrl) {
		return getUserService().createLogoutURL(redirectUrl);
	}
	
	
	

}
