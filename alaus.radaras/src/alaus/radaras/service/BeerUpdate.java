package alaus.radaras.service;

import alaus.radaras.shared.model.Beer;


public interface BeerUpdate {

    /**
     * Updates brand in DB.
     * 
     * @param beer
     *            Brand to update
     */
    public abstract void updateBrand(Beer beer);

    /**
     * Updates pub in DB.
     * 
     * @param pub Pub to update
     */
    public abstract void updatePub(alaus.radaras.shared.model.Pub pub);

    /**
     * Updates company information in DB.
     * 
     * @param brand
     *            Company data
     */
    public abstract void updateCompany(alaus.radaras.shared.model.Brand brand);

    /**
     * Deletes brand, association with pubs and tags.
     * 
     * @param brandId
     *            Id of brand to delete
     */
    public abstract void deleteBrand(String brandId);

    /**
     * Deletes pub and associations with beers from DB.
     * 
     * @param pubId
     *            Id of pub to delete
     */
    public abstract void deletePub(String pubId);

    /**
     * Deletes company, associated brands (see deleteBrand).
     * 
     * @param companyId
     *            Id of company to delete
     */
    public abstract void deleteCompany(String companyId);

}