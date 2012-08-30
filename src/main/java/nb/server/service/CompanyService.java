/**
 * 
 */
package nb.server.service;

import java.util.List;

import nb.shared.model.Beer;
import nb.shared.model.Company;

/**
 * @author Vincentas
 *
 */
public interface CompanyService extends BaseService<Company> {

	List<Beer> getBeers(String companyId);
	
	List<Beer> getSuggestedBeers(String companyId);
}
