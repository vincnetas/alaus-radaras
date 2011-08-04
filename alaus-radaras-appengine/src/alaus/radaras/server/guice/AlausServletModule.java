package alaus.radaras.server.guice;

import alaus.radaras.server.AdminBeerServiceImpl;
import alaus.radaras.server.CsvDataServlet;
import alaus.radaras.server.ImageServlet;
import alaus.radaras.server.JsonDataServlet;
import alaus.radaras.server.UploadServlet;
import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BeerService;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.BrandService;
import alaus.radaras.server.dao.IdProvider;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.dao.PubService;
import alaus.radaras.server.dao.impl.BeerDaoImpl;
import alaus.radaras.server.dao.impl.BeerServiceImpl;
import alaus.radaras.server.dao.impl.BrandDaoImpl;
import alaus.radaras.server.dao.impl.BrandServiceImpl;
import alaus.radaras.server.dao.impl.IdProviderImpl;
import alaus.radaras.server.dao.impl.PubDaoImpl;
import alaus.radaras.server.dao.impl.PubServiceImpl;
import alaus.radaras.server.json.JSONDispacher;
import alaus.radaras.server.json.NbService;
import alaus.radaras.server.json.NbServiceImpl;
import alaus.radaras.server.locator.IPLocator;
import alaus.radaras.server.locator.IPLocatorImpl;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.servlet.ServletModule;



class AlausServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		bind(IdProvider.class).to(IdProviderImpl.class);
		bind(PubDao.class).to(PubDaoImpl.class);
		bind(BeerDao.class).to(BeerDaoImpl.class);
		bind(BrandDao.class).to(BrandDaoImpl.class);
		bind(IPLocator.class).to(IPLocatorImpl.class);
		bind(UserService.class).toInstance(UserServiceFactory.getUserService());		
		bind(PubService.class).to(PubServiceImpl.class);
		bind(BeerService.class).to(BeerServiceImpl.class);
		bind(BrandService.class).to(BrandServiceImpl.class);
		
		serve("/beerEngine/beerService").with(alaus.radaras.server.BeerServiceImpl.class);
		serve("/adminEngine/beerService").with(alaus.radaras.server.BeerServiceImpl.class);
		serve("/adminEngine/adminBeerService").with(AdminBeerServiceImpl.class);
		serve("/json").with(JsonDataServlet.class);		
		serve("/csv").with(CsvDataServlet.class);		
		serve("/admin/upload").with(UploadServlet.class);
		serve("/image/beer/*").with(ImageServlet.class);
		
		bind(NbService.class).to(NbServiceImpl.class);
		
		serve("/jsonrpc").with(JSONDispacher.class);

	}
}