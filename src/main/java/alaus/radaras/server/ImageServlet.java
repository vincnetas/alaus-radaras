/**
 * 
 */
package alaus.radaras.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

/**
 * @author Vincentas
 *
 */
@Singleton
public class ImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6846947683095461798L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("/img/alus.png");
	}

	
}
