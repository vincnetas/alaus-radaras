/**
 * 
 */
package alaus.radaras;

import nb.server.guice.NbServletModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author vincentas
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Injector guice = Guice.createInjector(new NbServletModule());

	}

}
