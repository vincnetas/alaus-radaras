/**
 * 
 */
package alaus.radaras.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Quote;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Vincentas
 * 
 */
@Singleton
public class UploadServlet extends HttpServlet {
		
	@Inject
	private PubDao pubDao;
	
	@Inject
	private BeerDao beerDao;
	
	@Inject
	private BrandDao brandDao;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8055885423693792847L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();

		try {
			FileItemIterator iter = upload.getItemIterator(req);
	
			while (iter.hasNext()) {
				FileItemStream item = iter.next();

				String name = item.getFieldName();
				if ("file".equalsIgnoreCase(name)) {
					InputStream inputStream = item.openStream();
					
					String type = item.getName();
					
					if ("brands.txt".equalsIgnoreCase(type)) {
						getBrandDao().save(parseBrands(inputStream));
					} else if ("pubs.txt".equalsIgnoreCase(type)) {
						getPubDao().save(parsePubs(inputStream));
					} else {
						throw new ServletException("Unknown type : " + type);
					}
				} else {
					throw new ServletException("Need file");
				}
			}
		} catch (FileUploadException fue) {
			throw new ServletException(fue);
		}
		
		resp.getWriter().print("Imported");
		resp.setStatus(HttpServletResponse.SC_OK);
	}

	private List<Brand> parseBrands(InputStream inputStream) throws IOException {
		List<Brand> result = new ArrayList<Brand>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] columns = line.split("\t");
			Brand brand = new Brand();
			brand.setId(columns[0]);
			brand.setTitle(columns[1]);
			brand.setIcon(columns[0]);
			
			result.add(brand);
		}

		return result;
	}

	private List<Pub> parsePubs(InputStream inputStream) throws NumberFormatException, IOException {
		List<Pub> result = new ArrayList<Pub>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] columns = line.split("\t");
			Pub pub = new Pub();
			pub.setId(columns[0]);
			pub.setTitle(columns[1]);
			pub.setAddress(columns[2]);
			pub.setPhone(columns[3]);
			pub.setHomepage(columns[4]);
			pub.setLocation(new Location(Double.parseDouble(columns[6]), Double.parseDouble(columns[5])));
			
			result.add(pub);
		}

		return result;
	}

	private List<Quote> parseQuotes(InputStream inputStream) throws IOException {
		List<Quote> result = new ArrayList<Quote>();
		
		BufferedReader reader = new BufferedReader(	new InputStreamReader(inputStream));
		String line;
		
		while ((line = reader.readLine()) != null) {
			String[] columns = line.split("\t");
			
			Quote qoute = new Quote(
					columns[1],
					Integer.valueOf(columns[0]));

			result.add(	qoute);
		}
		
		return result;
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
