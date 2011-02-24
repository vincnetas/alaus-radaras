/**
 * 
 */
package alaus.radaras.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Pub;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Vincentas
 *
 */
@Singleton
public class CVSDataServlet extends HttpServlet {
	
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
		String fileName = "data.txt";
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		resp.setContentType("text/text");
		resp.setCharacterEncoding("UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);

		List<Pub> pubs = getPubDao().getAll();
		Map<String, Set<String>> beerMap = new HashMap<String, Set<String>>();
		PrintWriter writer = resp.getWriter();
		for (Pub pub : pubs) {
			writer.append(pub.getId()).append("\t");
			writer.append(pub.getTitle()).append("\t");
			writer.append(pub.getAddress()).append("\t");
			writer.append(pub.getCity()).append("\t");
			writer.append(pub.getPhone()).append("\t");
			writer.append(pub.getHomepage()).append("\t");
			writer.append(Double.toString(pub.getLatitude())).append("\t");
			writer.append(Double.toString(pub.getLongitude())).append("\r\n");
			
			for (String beerId : pub.getBeerIds()) {
				Set<String> set = beerMap.get(beerId);
				if (set == null) {
					set = new HashSet<String>();
					beerMap.put(beerId, set);
				}
				
				set.add(pub.getId());
			}
		}
		
		List<Beer> beers = getBeerDao().getAll();
		for (Beer beer : beers) {
			writer.append(beer.getIcon()).append("\t");
			writer.append(beer.getTitle()).append("\t");
			writer.append(StringUtils.join(beerMap.get(beer.getId()), ",")).append("\t");
			writer.append(getBrandDao().get(beer.getBrandId()).getCountry()).append("\t");
			writer.append(StringUtils.join(beer.getTags(), ",")).append("\r\n");
		}
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
