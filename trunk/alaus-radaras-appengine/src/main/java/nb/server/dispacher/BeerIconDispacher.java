/**
 * 
 */
package nb.server.dispacher;

import java.io.File;
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
public class BeerIconDispacher extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String requestUri = req.getRequestURI();

		try {
			if (new File(requestUri).exists()) {
				getServletConfig().getServletContext()
						.getRequestDispatcher(requestUri).forward(req, resp);
			} else {
				getServletConfig().getServletContext()
						.getRequestDispatcher("/img/beer/brand_default.png")
						.forward(req, resp);
			}
		} catch (Exception exception) {
			// probably permissions denied to read non existing file. assume
			// file not found.
			getServletConfig().getServletContext()
					.getRequestDispatcher("/img/beer/brand_default.png")
					.forward(req, resp);
		}
	}
}
