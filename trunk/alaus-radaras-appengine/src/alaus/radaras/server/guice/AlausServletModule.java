package alaus.radaras.server.guice;

import alaus.radaras.server.GreetingServiceImpl;
import alaus.radaras.server.JsonDataServlet;
import alaus.radaras.server.UploadServlet;
import alaus.radaras.server.dao.BaseDao;
import alaus.radaras.server.dao.impl.BaseDaoImpl;

import com.google.inject.servlet.ServletModule;

class AlausServletModule extends ServletModule {
	@Override
	protected void configureServlets() {
		bind(BaseDao.class).to(BaseDaoImpl.class);
		
		serve("/alaus_radaras_appengine/greet").with(GreetingServiceImpl.class);
		serve("/data").with(JsonDataServlet.class);		
		serve("/admin/upload").with(UploadServlet.class);
	}
}