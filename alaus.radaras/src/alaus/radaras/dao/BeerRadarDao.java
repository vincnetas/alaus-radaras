package alaus.radaras.dao;

import java.util.ArrayList;
import java.util.List;

import alaus.radaras.dao.model.Beer;
import alaus.radaras.dao.model.Location;
import alaus.radaras.dao.model.Pub;

public class BeerRadarDao {

	public List<Beer> getBeers() {
		return new ArrayList<Beer>();
	}
	
	public List<Pub> getPubsByBeer(Beer beer) {
		return new ArrayList<Pub>();
	}
	
	public List<Pub> getNearbyPubs(Location location) {
		return new ArrayList<Pub>();
	}
	
	public Pub getPubInfo() {
		return new Pub();
	}
	
}
