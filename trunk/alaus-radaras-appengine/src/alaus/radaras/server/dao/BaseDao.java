package alaus.radaras.server.dao;

import java.util.List;

import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Quote;

public interface BaseDao {

	@SuppressWarnings({ "rawtypes" })
	public abstract void save(List list);

	public abstract List<Beer> getBeers();

	public abstract List<Brand> getBrands();

	public abstract List<Pub> getPubs();

	public abstract List<Quote> getQuotes();


}