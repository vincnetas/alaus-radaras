package alaus.radaras.server.guice;

import nb.server.guice.NbServletModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class AlausGuiceServletContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(
				new AlausServletModule(),
				new NbServletModule()
			);
	}
}