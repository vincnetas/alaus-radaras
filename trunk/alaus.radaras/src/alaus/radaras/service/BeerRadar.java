package alaus.radaras.service;

import java.util.List;

import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.FeelingLucky;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.Qoute;
import alaus.radaras.service.model.Taxi;
import android.graphics.drawable.Drawable;
import android.location.Location;

/**
 * Entry point for BeerRadar service. 
 * 
 * @author sauliusm
 *
 */
public interface BeerRadar {

	List<Brand> getBrandsByCompany(String companyId);
	
	List<Brand> getBrands(Location location);

	List<Brand> getBrandsByPubId(String pubId);

	List<Brand> getBrandsByCountry(String country, Location location);

	List<Brand> getBrandsByTag(String tag, Location location);

	List<Pub> getPubsByBrandId(String brandId, Location location);

	List<Pub> getPubsByTag(String tag, Location location);

	List<Pub> getPubsByCountry(String country, Location location);

	List<Pub> getNearbyPubs(Location location);

	Pub getPub(String pubId);

	Brand getBrand(String brandId);

	FeelingLucky feelingLucky(Location location);

	Drawable getImage(String url);

	Qoute getQoute(int amount);

	List<String> getCountries(Location location);

	List<String> getTags(Location location);

	List<Taxi> getTaxies(Location location);
}