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

import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.PubDao;
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
	private PubDao pubDao;
	
	@Inject
	private BeerDao beerDao;
	
	@Inject
	private BrandDao brandDao;
	
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
		
		String fileName = "data.json";
		String what = req.getParameter("what");
		if (what == null) {
			data.put("brands", getBrandDao().getAll().toArray());	
			data.put("beers", getBeerDao().getAll().toArray());	
			data.put("pubs", getPubDao().getAll().toArray());	
		} else if (what.equalsIgnoreCase("brands")) {
			data.put("brands", getBrandDao().getAll().toArray());	
			fileName = "brands.json";
		} else if (what.equalsIgnoreCase("beers")) {
			data.put("beers", getBeerDao().getAll().toArray());
			fileName = "beers.json";
		} else if (what.equalsIgnoreCase("pubs")) {
			data.put("pubs", getPubDao().getAll().toArray());
			fileName = "pubs.json";
		} else {
			data.put("brands", getBrandDao().getAll().toArray());	
			data.put("beers", getBeerDao().getAll().toArray());	
			data.put("pubs", getPubDao().getAll().toArray());	
		}
		
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().print(getGson().toJson(data));
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	private Gson getGson() {
		return new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			
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
	}

	/**
	 * @return the pubDao
	 */
	public PubDao getPubDao() {
		return pubDao;
	}

	/**
	 * @param pubDao the pubDao to set
	 */
	public void setPubDao(PubDao pubDao) {
		this.pubDao = pubDao;
	}

	/**
	 * @return the beerDao
	 */
	public BeerDao getBeerDao() {
		return beerDao;
	}

	/**
	 * @param beerDao the beerDao to set
	 */
	public void setBeerDao(BeerDao beerDao) {
		this.beerDao = beerDao;
	}

	/**
	 * @return the brandDao
	 */
	public BrandDao getBrandDao() {
		return brandDao;
	}

	/**
	 * @param brandDao the brandDao to set
	 */
	public void setBrandDao(BrandDao brandDao) {
		this.brandDao = brandDao;
	}
	
	
}
