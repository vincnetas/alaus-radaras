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
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Country;
import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Tag;

/**
 * @author Vincentas
 * 
 */
public class UploadServlet extends HttpServlet {

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
		BaseDao baseDao = new BaseDao();
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
						baseDao.save(parseBrands(inputStream));
					} else if ("tags.txt".equalsIgnoreCase(type)) {
						baseDao.save(parseTags(inputStream));
					} else if ("countries.txt".equalsIgnoreCase(type)) {
						baseDao.save(parseCountries(inputStream));
					} else if ("pubs.txt".equalsIgnoreCase(type)) {
						baseDao.save(parsePubs(inputStream));
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
			pub.setUrl(columns[4]);
			pub.setLocation(new Location(Double.parseDouble(columns[6]), Double.parseDouble(columns[5])));
			
			result.add(pub);
		}

		return result;
	}

	private List<Country> parseCountries(InputStream inputStream) throws IOException {
		List<Country> result = new ArrayList<Country>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] columns = line.split("\t");
			Country country = new Country();
			country.setCode(columns[0]);
			country.setName(columns[1]);
			
			result.add(country);
		}

		return result;
	}

	private List<Tag> parseTags(InputStream inputStream) throws IOException {
		List<Tag> result = new ArrayList<Tag>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		String line = null;
		while ((line = reader.readLine()) != null) {
			String[] columns = line.split("\t");
			Tag tag = new Tag();
			tag.setCode(columns[0]);
			tag.setTitle(columns[1]);
			
			result.add(tag);
		}

		return result;
	}
}
