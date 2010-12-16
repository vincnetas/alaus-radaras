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

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Vincentas
 *
 */
public class JsonDataServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8487344462024173895L;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BaseDao baseDao = new BaseDao();
		
		Map<String, Object[]> data = new HashMap<String, Object[]>();
		
		data.put("brands", baseDao.getBrands().toArray());
		data.put("countries", baseDao.getCountries().toArray());
		data.put("pubs", baseDao.getPubs().toArray());
		data.put("tags", baseDao.getTags().toArray());
		
		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
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
}
