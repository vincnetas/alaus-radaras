/**
 * 
 */
package alaus.radaras.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.shared.model.Quote;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Vincentas
 *
 */
@Singleton
public class JsonDataServlet extends HttpServlet {

	@Inject
	private BaseDao baseDao;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8487344462024173895L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object[]> data = new HashMap<String, Object[]>();
		
		data.put("brands", getBaseDao().getBrands().toArray());
		data.put("beers", getBaseDao().getBeers().toArray());
		data.put("pubs", getBaseDao().getPubs().toArray());
		data.put("quotes", getBaseDao().getQuotes().toArray());
		
		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				if (f.getDeclaringClass() == Quote.class) {
					if (f.getName().equals("id")) {
						return true;
					}					
				}
					
				return f.getName().equals("jdoDetachedState");
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		}).create();
		
		resp.getWriter().print(gson.toJson(data));
				
		resp.setStatus(HttpServletResponse.SC_OK);
		
		
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
	
	
}
