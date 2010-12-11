package alaus.radaras.dao;

import java.util.ArrayList;
import java.util.List;

import alaus.radaras.dao.model.Brand;
import alaus.radaras.dao.model.FeelingLucky;
import alaus.radaras.dao.model.Location;
import alaus.radaras.dao.model.Pub;

public class BeerRadarDao {

	private static BeerRadarDao instance;
	
	public static BeerRadarDao getInstance() {
		if (instance == null) {
			instance = new BeerRadarDao();
		}
		return instance;
	}
	
	public List<Brand> getBrands() {
		return new ArrayList<Brand>();
	}
	
	public List<Pub> getPubsByBrand(Brand brand) {
		return new ArrayList<Pub>();
	}
	
	public List<Pub> getNearbyPubs(Location location) {
		return new ArrayList<Pub>();
	}
	
	public FeelingLucky feelingLucky() {
		return new FeelingLucky();
	}
	
}
