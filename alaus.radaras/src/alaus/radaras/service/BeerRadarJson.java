package alaus.radaras.service;

import java.util.List;

import alaus.radaras.service.model.Brand;
import alaus.radaras.service.model.Country;
import alaus.radaras.service.model.FeelingLucky;
import alaus.radaras.service.model.Location;
import alaus.radaras.service.model.Pub;
import alaus.radaras.service.model.Qoute;
import alaus.radaras.service.model.Tag;
import android.graphics.drawable.Drawable;

class BeerRadarJson extends BeerRadar {

	@Override
	public FeelingLucky feelingLucky() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Brand getBrand(String brandId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Brand> getBrands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Brand> getBrandsByCountry(String country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Brand> getBrandsByPubId(String pubId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Brand> getBrandsByTag(String tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Country> getCountries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country getCountry(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drawable getImage(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pub> getNearbyPubs(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pub getPub(String pubId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pub> getPubsByBrandId(String brandId, Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pub> getPubsByCountry(String country, Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pub> getPubsByTag(String tag, Location location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Qoute getQoute(int amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag getTag(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tag> getTags() {
		// TODO Auto-generated method stub
		return null;
	}

}
