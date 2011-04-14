/**
 * 
 */
package alaus.radaras.server;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Pub;

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
		Date lastUpdate = parseDate(req.getParameter("lastUpdate"));
		
		Update update = new Update();
		
		update.setLastUpdate(lastUpdate);
		
		update.setDeletedBeers(getBeersToDelete(lastUpdate));
		update.setDeletedBrands(getBrandsToDelete(lastUpdate));
		update.setDeletedPubs(getPubsToDelete(lastUpdate));
		
		update.setUpdatedBeers(getBeersToUpdate(lastUpdate));
		update.setUpdatedBrands(getBrandsToUpdate(lastUpdate));
		update.setUpdatedPubs(getPubsToUpdate(lastUpdate));
				
		String fileName = "data.json";
		
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		resp.getWriter().print(Update.toJson(update));
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	private static Date parseDate(String dateText) {
		Date result = new Date(0);
		
		if (dateText != null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00"));
				result = dateFormat.parse(dateText);
			} catch (ParseException e) {
				// Ignore and return default value
			}
		}
		
		return result;
	}
	
	private Set<String> getBeersToDelete(Date lastUpdate) {
		Set<String> result = new HashSet<String>();
		
		List<Beer> deleted = getBeerDao().getDeleted(lastUpdate);
		for (Beer beer : deleted) {
			result.add(beer.getId());
		}
		
		return result;
	}
	
	private Set<String> getBrandsToDelete(Date lastUpdate) {
		Set<String> result = new HashSet<String>();
		
		List<Brand> deleted = getBrandDao().getDeleted(lastUpdate);
		for (Brand brand : deleted) {
			result.add(brand.getId());
		}
		
		return result;
	}
	
	private Set<String> getPubsToDelete(Date lastUpdate) {
		Set<String> result = new HashSet<String>();
		
		List<Pub> deleted = getPubDao().getDeleted(lastUpdate);
		for (Pub pub : deleted) {
			result.add(pub.getId());
		}
		
		return result;
	}
	
	private Set<Beer> getBeersToUpdate(Date lastUpdate) {
		return new HashSet<Beer>(getBeerDao().getUpdated(lastUpdate));
	}
	
	private Set<Brand> getBrandsToUpdate(Date lastUpdate) {
		return new HashSet<Brand>(getBrandDao().getUpdated(lastUpdate));
	}
	
	private Set<Pub> getPubsToUpdate(Date lastUpdate) {
		return new HashSet<Pub>(getPubDao().getUpdated(lastUpdate));
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
