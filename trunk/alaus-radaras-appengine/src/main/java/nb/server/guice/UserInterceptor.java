/**
 * 
 */
package nb.server.guice;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nb.server.service.UserService;

import org.zdevra.guice.mvc.AbstractInterceptorHandler;
import org.zdevra.guice.mvc.ModelAndView;

import com.google.inject.Inject;

/**
 * @author vincentas
 * 
 */
public class UserInterceptor extends AbstractInterceptorHandler {

	private static final Logger logger = Logger.getLogger(UserInterceptor.class.getName());
	
	private static final String CURRENT_USER = "currentUser";

	@Inject
	private UserService userService;
	
	/* (non-Javadoc)
	 * @see org.zdevra.guice.mvc.AbstractInterceptorHandler#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.zdevra.guice.mvc.AbstractInterceptorHandler#postHandle(javax.servlet
	 * .http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.zdevra.guice.mvc.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {		
		request.getSession().setAttribute(CURRENT_USER, getUserService().getCurrentUser());
		request.getSession().setAttribute("loginUrl", getUserService().getLoginUrl(request.getRequestURI()));
		request.getSession().setAttribute("logoutUrl", getUserService().getLogoutUrl(request.getRequestURI()));
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
