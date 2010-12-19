package alaus.radaras.server.dao;

import java.util.List;

import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.BrandCountryAssociation;
import alaus.radaras.shared.model.BrandPubAssociation;
import alaus.radaras.shared.model.BrandTagAssociation;
import alaus.radaras.shared.model.Country;
import alaus.radaras.shared.model.Pub;
import alaus.radaras.shared.model.Tag;

public interface BaseDao {

	@SuppressWarnings({ "rawtypes" })
	public abstract void save(List list);

	public abstract List<Brand> getBrands();

	public abstract List<Pub> getPubs();

	public abstract List<Country> getCountries();

	public abstract List<Tag> getTags();

	public abstract List<BrandCountryAssociation> getBrandCountryAssociation();

	public abstract List<BrandPubAssociation> getBrandPubAssociations();

	public abstract List<BrandPubAssociation> getQuotes();

	public abstract List<BrandTagAssociation> getBrandTagAssociations();

}