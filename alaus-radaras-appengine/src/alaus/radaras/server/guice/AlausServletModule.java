package alaus.radaras.server.guice;

import alaus.radaras.server.BeerServiceImpl;
import alaus.radaras.server.ImageServlet;
import alaus.radaras.server.JsonDataServlet;
import alaus.radaras.server.UploadServlet;
import alaus.radaras.server.dao.BeerDao;
import alaus.radaras.server.dao.BrandDao;
import alaus.radaras.server.dao.IdProvider;
import alaus.radaras.server.dao.PubDao;
import alaus.radaras.server.dao.impl.BeerDaoImpl;
import alaus.radaras.server.dao.impl.BrandDaoImpl;
import alaus.radaras.server.dao.impl.IdProviderImpl;
import alaus.radaras.server.dao.impl.PubDaoImpl;
import alaus.radaras.server.locator.IPLocatorImpl;
import alaus.radaras.server.locator.IPLocator;

import com.google.inject.servlet.ServletModule;

class AlausServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		bind(IdProvider.class).to(IdProviderImpl.class);
		bind(PubDao.class).to(PubDaoImpl.class);
		bind(BeerDao.class).to(BeerDaoImpl.class);
		bind(BrandDao.class).to(BrandDaoImpl.class);
		bind(IPLocator.class).to(IPLocatorImpl.class);
		
		serve("/beerEngine/beerService").with(BeerServiceImpl.class);
		serve("/data").with(JsonDataServlet.class);		
		serve("/admin/upload").with(UploadServlet.class);
		serve("/image/beer/*").with(ImageServlet.class);
	}
}