package nb.server.service.impl;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import nb.server.service.BeerService;
import nb.server.service.CompanyService;
import nb.server.service.UserService;
import nb.shared.model.Beer;
import nb.shared.model.Company;

import org.junit.Test;
import org.mockito.Mockito;

public class CompanyServiceImplTest extends BaseServiceImplTest<Company>{

	
	public BeerService getBeerService() {
		return getGuice().getInstance(BeerService.class);
	}
	
	@Override
	public CompanyService getBaseService() {
		return getGuice().getInstance(CompanyService.class);
	}

	@Override
	public Company getSample() {
		Company result = new Company();
		
		return result;
	}
	
	private Beer addBeer(String companyId, boolean approve) {
		Beer beer = new Beer();
		beer.setCompanyId(companyId);
		
		UserService userService = Mockito.mock(UserService.class);
		if (approve) {
			Mockito.when(userService.getCurrentUser()).thenReturn(getAdminUser());			
		} else {
			Mockito.when(userService.getCurrentUser()).thenReturn(getSimpleUser());
		}
		
		BeerServiceImpl beerService = (BeerServiceImpl) getBeerService();
		beerService.setUserService(userService);		
		beerService.suggest(beer);
	
		return beer;
	}
	
	@Test 
	public void testGetSuggestedBeers() {
		addBeer("1", false);		
		addBeer("2", false);
		addBeer("1", true);

		List<Beer> suggestedBeers = getBaseService().getSuggestedBeers("1");
		assertEquals(1, suggestedBeers.size());
		
		addBeer("1", false);
		
		suggestedBeers = getBaseService().getSuggestedBeers("1");
		assertEquals(2, suggestedBeers.size());
	}

	@Test
	public void testGetBeers() {
		addBeer("1", false);
		addBeer("2", false);

		addBeer("1", true);

		List<Beer> beers = getBaseService().getBeers("1");
		assertEquals(1, beers.size());
		
		addBeer("1", true);

		beers = getBaseService().getBeers("1");
		assertEquals(2, beers.size());
	}
	
	


}
