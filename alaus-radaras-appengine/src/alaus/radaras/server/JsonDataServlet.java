/**
 * 
 */
package alaus.radaras.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.Utils;
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
		Update update = new Update();
		
		update.setDeletedBeers(getBeersToDelete());
		update.setDeletedBrands(getBrandsToDelete());
		update.setDeletedPubs(getPubsToDelete());
		
		update.setUpdatedBeers(getBeersToUpdate());
		update.setUpdatedBrands(getBrandsToUpdate());
		update.setUpdatedPubs(getPubsToUpdate());
				
		String fileName = "data.json";
		
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		resp.getWriter().print(Update.toJson(update));
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	private Set<Beer> getBeersToDelete() {
		return Utils.set();
	}
	
	private Set<Brand> getBrandsToDelete() {
		return Utils.set();
	}
	
	private Set<Pub> getPubsToDelete() {
		return Utils.set();
	}
	
	private Set<Beer> getBeersToUpdate() {
		return new HashSet<Beer>(getBeerDao().getAll());
	}
	
	private Set<Brand> getBrandsToUpdate() {
		return new HashSet<Brand>(getBrandDao().getAll());
	}
	
	private Set<Pub> getPubsToUpdate() {
		return new HashSet<Pub>(getPubDao().getAll());
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
