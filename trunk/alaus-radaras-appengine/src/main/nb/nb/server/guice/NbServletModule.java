package nb.server.guice;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import nb.server.controller.impl.HomeController;
import nb.server.controller.impl.PlaceController;
import nb.server.dao.BeerDao;
import nb.server.dao.CompanyDao;
import nb.server.dao.IdProvider;
import nb.server.dao.PlaceDao;
import nb.server.dao.bridge.BeerDaoBridge;
import nb.server.dao.bridge.PlaceDaoBridge;
import nb.server.dao.impl.CompanyDaoImpl;
import nb.server.dao.impl.IdProviderImpl;
import nb.server.json.JSONDispacher;
import nb.server.json.NbService;
import nb.server.json.NbServiceImpl;
import nb.server.service.BeerService;
import nb.server.service.CompanyService;
import nb.server.service.PlaceService;
import nb.server.service.RoleHandler;
import nb.server.service.UserService;
import nb.server.service.impl.BeerServiceImpl;
import nb.server.service.impl.CompanyServiceImpl;
import nb.server.service.impl.PlaceServiceImpl;
import nb.server.service.impl.RoleHandlerImpl;
import nb.server.service.impl.UserServiceImpl;

import org.zdevra.guice.mvc.MvcModule;

public class NbServletModule extends MvcModule {
	
	private static PersistenceManagerFactory pmf;
	static {
		if (pmf == null) {
			pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.zdevra.guice.mvc.MvcModule#configureControllers()
	 */
	@Override
	protected void configureControllers() {
		bind(BeerDao.class).to(BeerDaoBridge.class);
		bind(PlaceDao.class).to(PlaceDaoBridge.class);
		bind(CompanyDao.class).to(CompanyDaoImpl.class);
		
		bind(BeerService.class).to(BeerServiceImpl.class);
		bind(PlaceService.class).to(PlaceServiceImpl.class);
		bind(CompanyService.class).to(CompanyServiceImpl.class);
		
		bind(UserService.class).to(UserServiceImpl.class);
		
		bind(RoleHandler.class).to(RoleHandlerImpl.class);
		bind(IdProvider.class).to(IdProviderImpl.class);
		bind(PersistenceManagerFactory.class).toInstance(pmf);		
		bind(NbService.class).to(NbServiceImpl.class);
				
		serve("/jsonrpc").with(JSONDispacher.class);
		control("/place/*").withController(PlaceController.class).set(); 
		control("/home").withController(HomeController.class).set(); 
		
	}

}