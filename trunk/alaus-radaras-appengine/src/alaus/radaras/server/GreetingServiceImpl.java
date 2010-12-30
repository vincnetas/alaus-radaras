package alaus.radaras.server;

import java.util.List;

import alaus.radaras.client.GreetingService;
import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The server side implementation of the RPC service.
 */
@Singleton
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	@Inject
	private BaseDao baseDao;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5405571273602475982L;

	public List<Pub> greetServer(String input) throws IllegalArgumentException {
		return getBaseDao().getPubs();
	}

	/**
	 * @return the baseDao
	 */
	public BaseDao getBaseDao() {
		return baseDao;
	}

	/**
	 * @param baseDao the baseDao to set
	 */
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
