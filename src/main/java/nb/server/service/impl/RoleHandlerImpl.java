/**
 * 
 */
package nb.server.service.impl;

import nb.server.service.RoleHandler;
import nb.shared.model.BaseHistoryObject;
import nb.shared.model.User;
import nb.shared.model.User.Role;

/**
 * @author vienozin
 *
 */
public class RoleHandlerImpl implements RoleHandler {

	/* (non-Javadoc)
	 * @see nb.server.service.RoleHandler#canSuggest(nb.shared.model.User, nb.shared.model.BaseObject)
	 */
	@Override
	public boolean canSuggest(User user, BaseHistoryObject object) {
		/*
		 * Everyone can suggest
		 */
		return true;
	}

	/* (non-Javadoc)
	 * @see nb.server.service.RoleHandler#canReject(nb.shared.model.User, nb.shared.model.BaseObject)
	 */
	@Override
	public boolean canReject(User user, BaseHistoryObject object) {
		if (user.getRole().equals(Role.ADMIN)) {
			return true;
		}
		
		if (user.getOwnerForPlaces().contains(object.getObjectId())) {
			return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see nb.server.service.RoleHandler#canDelete(nb.shared.model.User, nb.shared.model.BaseObject)
	 */
	@Override
	public boolean canDelete(User user, BaseHistoryObject object) {
		if (user.getRole().equals(Role.ADMIN)) {
			return true;
		}
		
		if (user.getOwnerForPlaces().contains(object.getObjectId())) {
			return true;
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see nb.server.service.RoleHandler#canApprove(nb.shared.model.User, nb.shared.model.BaseObject)
	 */
	@Override
	public boolean canApprove(User user, BaseHistoryObject object) {
		if (user.getRole().equals(Role.ADMIN)) {
			return true;
		}
		
		if (user.getOwnerForPlaces().contains(object.getObjectId())) {
			return true;
		}
		
		return false;
	}

}
