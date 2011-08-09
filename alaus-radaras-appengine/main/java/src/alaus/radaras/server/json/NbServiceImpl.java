/**
 * 
 */
package alaus.radaras.server.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BeerService;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.BrandService;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.dao.PubService;
import alaus.radaras.server.json.model.JBeer;
import alaus.radaras.server.json.model.JCompany;
import alaus.radaras.server.json.model.JPlace;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Pub;

import com.google.inject.Inject;

/**
 * @author vienozin
 *
 */
public class NbServiceImpl implements NbService {

	@Inject
	private PubDao pubDao;
	
	@Inject
	private BeerDao beerDao;
	
	@Inject
	private BrandDao brandDao;

	@Inject
	private PubService pubService;
	
	@Inject
	private BeerService beerService;
	
	@Inject
	private BrandService brandService;
	
	@Override
	public JPlace[] getPlaces() {
		return convertPub(getPubDao().getApproved());
	}
	
	private JPlace[] convertPub(Collection<Pub> pubs) {
		List<JPlace> result = new ArrayList<JPlace>(pubs.size());
		
		for (Pub pub : pubs) {
			JPlace place = new JPlace();
			place.setBeerIds(pub.getBeerIds().toArray(new String[0]));
			place.setCity(pub.getCity());
			place.setCountry(pub.getCountry());
			place.setHomepage(pub.getHomepage());
			place.setHours(pub.getHours());
			place.setIcon(null);
			place.setLatitude(pub.getLatitude());
			place.setLongitude(pub.getLongitude());
			place.setPhone(pub.getPhone());
			place.setStreetAddress(pub.getAddress());
			place.setTags(pub.getTags().toArray(new String[0]));
			place.setType(null);
			place.setTitle(pub.getTitle());
			place.setId(pub.getId());

			result.add(place);
		}
		
		
		return result.toArray(new JPlace[0]);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.json.NbService#getBeers()
	 */
	@Override
	public JBeer[] getBeers() {
		return convertBeer(getBeerDao().getApproved());
	}
	
	private JBeer[] convertBeer(Collection<Beer> beers) {
		List<JBeer> result = new ArrayList<JBeer>(beers.size());
		
		for (Beer beer : beers) {
			JBeer jBeer = new JBeer();
			
			jBeer.setObjectId(beer.getParentId());
			jBeer.setCompanyId(beer.getBrandId());
			jBeer.setIcon(beer.getIcon());
			jBeer.setId(beer.getId());
			jBeer.setTitle(beer.getTitle());

			result.add(jBeer);
		}
		
		return result.toArray(new JBeer[0]);
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.json.NbService#getCompanies()
	 */
	@Override
	public JCompany[] getCompanies() {
		return convertBrand(getBrandDao().getApproved());
	}
	
	private JCompany[] convertBrand(Collection<Brand> brands) {
		List<JCompany> result = new ArrayList<JCompany>(brands.size());
		
		for (Brand brand : brands) {
			JCompany company = new JCompany();

			List<Beer> beerList = getBeerDao().getBrandBeer(brand.getParentId());
			List<String> beerIds = new ArrayList<String>(beerList.size());
			for (Beer beer : beerList) {
				beerIds.add(beer.getParentId());
			}
			
			company.setBeerIds(beerIds.toArray(new String[0]));
			company.setCountry(brand.getCountry());
			company.setHomepage(brand.getHomePage());
			company.setHometown(brand.getHometown());
			company.setIcon(brand.getIcon());
			company.setId(brand.getId());
			company.setObjectId(brand.getParentId());
			company.setTitle(brand.getTitle());
			
			result.add(company);
		}
		
		return result.toArray(new JCompany[0]);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.json.NbService#getPlaceSuggestions(java.lang.String)
	 */
	@Override
	public JPlace[] getPlaceSuggestions(String placeId) {
		return convertPub(getPubDao().getUpdates(placeId));
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.json.NbService#getBeerSuggestions(java.lang.String)
	 */
	@Override
	public JBeer[] getBeerSuggestions(String beerId) {
		return convertBeer(getBeerDao().getUpdates(beerId));
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.json.NbService#getCompanySuggestions(java.lang.String)
	 */
	@Override
	public JCompany[] getCompanySuggestions(String companyId) {
		return convertBrand(getBrandDao().getUpdates(companyId));
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.server.json.NbService#savePlace(alaus.radaras.server.json.model.JPlace)
	 */
	@Override
	public JPlace savePlace(JPlace place) {
		
		getPubService().add(object);
		// TODO Auto-generated method stub
		return null;
	}
	
	private Pub convertJPlace(JPlace place) {
		Pub result = new Pub();
		
		result.setAddress(address);
		result.setBeerIds(beerIds);
		result.setCity(city);
		result.setCountry(country);
		result.setDescription(description);
		result.setHomepage(homepage);
		result.setHours(hours);
		result.setId(id);
		result.setLatitude(latitude);
		result.setLongitude(longitude);
		result.setParentId(parentId);
		result.setPhone(phone);
		result.setTags(tags);
		result.setTitle(title);
		
		return result;
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.json.NbService#saveBeer(alaus.radaras.server.json.model.JBeer)
	 */
	@Override
	public JBeer saveBeer(JBeer beer) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see alaus.radaras.server.json.NbService#saveCompany(alaus.radaras.server.json.model.JCompany)
	 */
	@Override
	public JCompany saveCompany(JCompany company) {
		// TODO Auto-generated method stub
		return null;
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

	/**
	 * @return the pubService
	 */
	public PubService getPubService() {
		return pubService;
	}

	/**
	 * @param pubService the pubService to set
	 */
	public void setPubService(PubService pubService) {
		this.pubService = pubService;
	}

	/**
	 * @return the beerService
	 */
	public BeerService getBeerService() {
		return beerService;
	}

	/**
	 * @param beerService the beerService to set
	 */
	public void setBeerService(BeerService beerService) {
		this.beerService = beerService;
	}

	/**
	 * @return the brandService
	 */
	public BrandService getBrandService() {
		return brandService;
	}

	/**
	 * @param brandService the brandService to set
	 */
	public void setBrandService(BrandService brandService) {
		this.brandService = brandService;
	}
}
