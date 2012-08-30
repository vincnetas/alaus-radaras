/**
 * 
 */
package alaus.radaras.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

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
public class CsvDataServlet extends HttpServlet {
	
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
		String fileName = "data.zip";
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		resp.setContentType("application/zip");
//		resp.setCharacterEncoding("UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);

		ZipOutputStream zos = new ZipOutputStream(resp.getOutputStream());
		Writer writer = new OutputStreamWriter(zos, "UTF-8");
		
		ZipEntry pubsEntry = new ZipEntry("pubs.txt");
		
		zos.putNextEntry(pubsEntry);
		
		Map<String, Set<String>> beerMap = new HashMap<String, Set<String>>();
		
		boolean firstPub = true;
		for (Pub pub : getPubDao().getAll()) {
			if (firstPub) {
				firstPub = false;
			} else {
				writer.append("\r\n");
			}
			
			writer.append(pub.getId()).append("\t");
			writer.append(pub.getTitle()).append("\t");
			writer.append(pub.getAddress()).append("\t");
			writer.append(pub.getCity()).append("\t");
			writer.append(pub.getPhone()).append("\t");
			writer.append(pub.getHomepage()).append("\t");
			writer.append(Double.toString(pub.getLatitude())).append("\t");
			writer.append(Double.toString(pub.getLongitude()));
			
			for (String beerId : pub.getBeerIds()) {
				Set<String> set = beerMap.get(beerId);
				if (set == null) {
					set = new HashSet<String>();
					beerMap.put(beerId, set);
				}
				
				set.add(pub.getId());
			}
		}
		writer.flush();
		zos.closeEntry();
		zos.putNextEntry(new ZipEntry("beers.txt"));
		
		boolean firstBeer = true;
		for (Beer beer : getBeerDao().getAll()) {
			if (firstBeer) {
				firstBeer = false;
			} else {
				writer.append("\r\n");
			}
			
			writer.append(beer.getId()).append("\t");
			writer.append(beer.getIcon()).append("\t");
			writer.append(beer.getTitle()).append("\t");
			writer.append(StringUtils.join(beerMap.get(beer.getId()), ",")).append("\t");
			writer.append(StringUtils.join(beer.getTags(), ",")).append("\t");;
			writer.append(beer.getBrandId());
		}
		writer.flush();
		zos.closeEntry();
		
		zos.putNextEntry(new ZipEntry("brands.txt"));
		
		boolean firstBrand = true;
		for (Brand brand : getBrandDao().getAll()) {
			if (firstBrand) {
				firstBrand = false;
			} else {
				writer.append("\r\n");
			}
			
			writer.append(brand.getId()).append("\t");
			writer.append(brand.getTitle()).append("\t");
			writer.append(brand.getCountry().toUpperCase()).append("\t");
			writer.append(StringUtils.join(brand.getTags(), ","));
		}
		writer.flush();
		zos.closeEntry();
		zos.close();
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
