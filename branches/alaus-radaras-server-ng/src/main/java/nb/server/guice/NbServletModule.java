package nb.server.guice;

import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.zdevra.guice.mvc.MvcModule;

import nb.server.controller.impl.BeerController;
import nb.server.controller.impl.PlaceController;
import nb.server.dao.BeerDao;
import nb.server.dao.CompanyDao;
import nb.server.dao.IdProvider;
import nb.server.dao.PlaceDao;
import nb.server.dao.UserDao;
import nb.server.dao.impl.BeerDaoImpl;
import nb.server.dao.impl.CompanyDaoImpl;
import nb.server.dao.impl.IdProviderImpl;
import nb.server.dao.impl.PlaceDaoImpl;
import nb.server.dao.impl.UserDaoImpl;
import nb.server.dispacher.BeerIconDispacher;
import nb.server.dispacher.MailHandler;
import nb.server.dispacher.NokiaUpdateHandler;
import nb.server.dispacher.ReportHandler;
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
import alaus.radaras.server.AdminBeerServiceImpl;
import alaus.radaras.server.CsvDataServlet;
import alaus.radaras.server.ImageServlet;
import alaus.radaras.server.JsonDataServlet;
import alaus.radaras.server.UploadServlet;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.BrandService;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.dao.PubService;
import alaus.radaras.server.dao.impl.BrandDaoImpl;
import alaus.radaras.server.dao.impl.BrandServiceImpl;
import alaus.radaras.server.dao.impl.PubDaoImpl;
import alaus.radaras.server.dao.impl.PubServiceImpl;
import alaus.radaras.server.locator.IPLocator;
import alaus.radaras.server.locator.IPLocatorImpl;

import com.google.appengine.api.users.UserServiceFactory;

public class NbServletModule extends MvcModule {
	
	private static final Logger logger = Logger.getLogger(NbServletModule.class.getName());
	
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
		registerGlobalInterceptor(UserInterceptor.class);
		bind(com.google.appengine.api.users.UserService.class).toInstance(UserServiceFactory.getUserService()); 
		
		bind(BeerDao.class).to(BeerDaoImpl.class);
		bind(PlaceDao.class).to(PlaceDaoImpl.class);
		bind(CompanyDao.class).to(CompanyDaoImpl.class);
		bind(UserDao.class).to(UserDaoImpl.class);
		
		bind(BeerService.class).to(BeerServiceImpl.class);
		bind(PlaceService.class).to(PlaceServiceImpl.class);
		bind(CompanyService.class).to(CompanyServiceImpl.class);
		
		bind(UserService.class).to(UserServiceImpl.class);
		
		bind(RoleHandler.class).to(RoleHandlerImpl.class);
		bind(IdProvider.class).to(IdProviderImpl.class);
		bind(PersistenceManagerFactory.class).toInstance(pmf);		
		bind(NbService.class).to(NbServiceImpl.class);
				
		serve("/android/submit.php").with(ReportHandler.class);
		serve("/android/nokia.php").with(NokiaUpdateHandler.class);
		serve("/_ah/mail/*").with(MailHandler.class);
		
		serve("/jsonrpc").with(JSONDispacher.class);
		serve("/img/beer/*").with(BeerIconDispacher.class);

		
		control("/place/*").withController(PlaceController.class);
		control("/places").withController(PlaceController.class);
		control("/beer/*").withController(BeerController.class);
		control("/beers").withController(BeerController.class);
		
		control("/home").withView("view/home.jsp");
		control("/about").withView("view/about.jsp");
		control("/newPlace").withView("view/newPlace.jsp");
		
		/*
		 * Legacy
		 * 
		 */
        bind(alaus.radaras.server.dao.IdProvider.class).to(alaus.radaras.server.dao.impl.IdProviderImpl.class);
        bind(PubDao.class).to(PubDaoImpl.class);
        bind(alaus.radaras.server.dao.BeerDao.class).to(alaus.radaras.server.dao.impl.BeerDaoImpl.class);
        bind(BrandDao.class).to(BrandDaoImpl.class);
        bind(IPLocator.class).to(IPLocatorImpl.class);
               
        bind(PubService.class).to(PubServiceImpl.class);
        bind(alaus.radaras.server.dao.BeerService.class).to(alaus.radaras.server.dao.impl.BeerServiceImpl.class);
        bind(BrandService.class).to(BrandServiceImpl.class);
        
        serve("/beerEngine/beerService").with(alaus.radaras.server.BeerServiceImpl.class);
        serve("/adminEngine/beerService").with(alaus.radaras.server.BeerServiceImpl.class);
        serve("/adminEngine/adminBeerService").with(AdminBeerServiceImpl.class);
        serve("/json").with(JsonDataServlet.class);     
        serve("/csv").with(CsvDataServlet.class);       
        serve("/admin/upload").with(UploadServlet.class);
        serve("/image/beer/*").with(ImageServlet.class);
	}

}