/**
 * 
 */
package nb.server.service;

import nb.shared.model.BaseHistoryObject;
import nb.shared.model.User;

/**
 * @author vienozin
 *
 */
public interface RoleHandler {

	/**
	 * Checks if user has rights to suggest object.
	 * 
	 * @param user
	 *            User.
	 * @param object
	 *            Object.
	 * @return Returns if user is allowed to suggest.
	 */
	boolean canSuggest(User user, BaseHistoryObject object);

	/**
	 * Checks if user has rights to reject object.
	 * 
	 * @param user
	 *            User.
	 * @param object
	 *            Object.
	 * @return Returns if user is allowed to reject.
	 */
	boolean canReject(User user, BaseHistoryObject object);

	/**
	 * Checks if user has rights to delete object.
	 * 
	 * @param user
	 *            User.
	 * @param object
	 *            Object.
	 * @return Returns if user is allowed to delete.
	 */
	boolean canDelete(User user, BaseHistoryObject object);

	/**
	 * Checks if user has rights to approve object.
	 * 
	 * @param user
	 *            User.
	 * @param object
	 *            Object.
	 * @return Returns if user is allowed to approve.
	 */
	boolean canApprove(User user, BaseHistoryObject object);


}
