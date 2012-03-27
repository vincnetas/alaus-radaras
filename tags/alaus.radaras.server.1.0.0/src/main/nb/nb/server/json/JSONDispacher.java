/**
 * 
 */
package nb.server.json;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.rpc.server.JsonRpcExecutor;
import org.json.rpc.server.JsonRpcServletTransport;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author vienozin
 *
 */
@Singleton
public class JSONDispacher extends HttpServlet {

	@Inject
	private NbService service;
	
    private JsonRpcExecutor executor;
    
    private synchronized JsonRpcExecutor getExecutor() {
    	if (executor == null) {
            executor = new JsonRpcExecutor();
            executor.addHandler("nb", getService(), NbService.class);
    	}
    	
    	return executor;
    }
    
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setCharacterEncoding("UTF-8");
		getExecutor().execute(new JsonRpcServletTransport(req, resp));
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
		resp.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));		
		resp.setHeader("Access-Control-Allow-Methods", req.getHeader("Access-Control-Request-Method"));
		resp.setHeader("Access-Control-Max-Age", "3628800");
		
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	/**
	 * @return the service
	 */
	public NbService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(NbService service) {
		this.service = service;
	}
}
