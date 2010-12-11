package alaus.radaras.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alaus.radaras.dao.model.Brand;
import alaus.radaras.dao.model.FeelingLucky;
import alaus.radaras.dao.model.Location;
import alaus.radaras.dao.model.Pub;
import alaus.radaras.dao.model.Qoute;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

public class BeerRadarDao {

	private static Map<Context, BeerRadarDao> instances = 
		new HashMap<Context, BeerRadarDao>();
	
	@SuppressWarnings("unused")
	private SQLiteDatabase db;
	
	private BeerRadarDao(Context context) {
		db = new DatabaseAdapter(context).getReadableDatabase();
	}
	
	public static BeerRadarDao getInstance(Context context) {
		BeerRadarDao instance = instances.get(context);
		if (instance == null) {
			instance = new BeerRadarDao(context);
			instances.put(context, instance);
		}
		return instance;
	}
	
	public List<Brand> getBrands() {
		return new ArrayList<Brand>();
	}
	
	public List<Pub> getPubsByBrandId(String brandId, Location location) {
		return new ArrayList<Pub>();
	}
	
	public List<Pub> getNearbyPubs(Location location) {
		return new ArrayList<Pub>();
	}
	
	public Pub getPubByPubId(String pubId) {
		return new Pub();
	}
	
	public FeelingLucky feelingLucky() {
		return new FeelingLucky();
	}
	
	public Drawable getImage(String url) {
		return null;
	}
	
	public Qoute getQoute(int amount)  {
		return new Qoute();
	}
	
}
