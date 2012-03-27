/**
 * 
 */
package nb.server.dispacher;

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
public class NokiaUpdateHandler extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	
}
