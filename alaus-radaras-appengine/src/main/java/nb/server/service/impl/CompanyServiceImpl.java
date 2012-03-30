/**
 * 
 */
package nb.server.service.impl;

import java.util.List;

import nb.server.dao.BaseHistoryDao;
import nb.server.dao.BeerDao;
import nb.server.dao.CompanyDao;
import nb.server.service.CompanyService;
import nb.shared.model.BaseHistoryObject.State;
import nb.shared.model.Beer;
import nb.shared.model.Company;

import com.google.inject.Inject;

/**
 * @author vienozin
 * 
 */
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements CompanyService {

	@Inject
	private CompanyDao companyDao;

	@Inject
	private BeerDao beerDao;
	
	/* (non-Javadoc)
	 * @see nb.server.service.CompanyService#getBeers(java.lang.String)
	 */
	@Override
	public List<Beer> getBeers(String companyId) {
		Beer filter = new Beer();
		filter.setCompanyId(companyId);
		filter.setState(State.CURRENT);
		return getBeerDao().findBy(filter);
	}

	/* (non-Javadoc)
	 * @see nb.server.service.impl.BaseServiceImpl#getBaseDao()
	 */
	@Override
	public BaseHistoryDao<Company> getBaseDao() {
		return getCompanyDao();
	}

	/**
	 * @return the companyDao
	 */
	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	/**
	 * @param companyDao the companyDao to set
	 */
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
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

	@Override
	public List<Beer> getSuggestedBeers(String companyId) {
		Beer filter = new Beer();
		filter.setState(State.SUGGESTION);
		filter.setCompanyId(companyId);
		return getBeerDao().findBy(filter);
	}

	
	
}
