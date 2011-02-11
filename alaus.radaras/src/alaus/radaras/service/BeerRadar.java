package alaus.radaras.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Country;
import alaus.radaras.service.model.FeelingLucky;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.Qoute;
import alaus.radaras.service.model.Tag;
import alaus.radaras.service.model.Taxi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;

/**
 * Entry point for BeerRadar service. 
 * 
 * @author sauliusm
 *
 */
public abstract class BeerRadar {

	private static Map<Context, BeerRadarSqlite> instances = 
		new HashMap<Context, BeerRadarSqlite>();
		
	public static BeerRadar getInstance(Context context) {
		BeerRadarSqlite instance = BeerRadar.instances.get(context);
		if (instance == null) {
			//
			// use embedded database while JSON API is on its way
			instance = new BeerRadarSqlite(context);
			BeerRadar.instances.put(context, instance);
		}
		return instance;
	}

	public abstract List<Brand> getBrands(Location location);

	public abstract List<Brand> getBrandsByPubId(String pubId);

	public abstract List<Brand> getBrandsByCountry(String country, Location location);

	public abstract List<Brand> getBrandsByTag(String tag, Location location);

	public abstract List<Pub> getPubsByBrandId(String brandId, Location location);

	public abstract List<Pub> getPubsByTag(String tag, Location location);

	public abstract List<Pub> getPubsByCountry(String country, Location location);

	public abstract List<Pub> getNearbyPubs(Location location);

	public abstract Pub getPub(String pubId);

	public abstract Brand getBrand(String brandId);

	public abstract FeelingLucky feelingLucky(Location location);

	public abstract Drawable getImage(String url);

	public abstract Qoute getQoute(int amount);

	public abstract List<Country> getCountries(Location location);

	public abstract List<Tag> getTags(Location location);

	public abstract Tag getTag(String code);

	public abstract Country getCountry(String code);
	
	public abstract double getMaxDistance();

	public abstract List<Taxi> getTaxies(Location location);
}