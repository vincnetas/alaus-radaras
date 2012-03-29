/**
 * 
 */
package alaus.radaras.server;

import nb.server.guice.NbServletModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author vincentas
 *
 */
public class Stat {

	private static Injector guice = null;
	
	public static synchronized Injector getGuice() {
		if (guice == null) {
			guice = Guice.createInjector(new NbServletModule());
			
		}
		
		return guice;
	}
}
