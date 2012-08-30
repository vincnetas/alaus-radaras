/**
 * 
 */
package nb.server.controller.impl;

import org.zdevra.guice.mvc.annotations.Controller;
import org.zdevra.guice.mvc.annotations.Path;

import com.google.inject.Singleton;

/**
 * @author Vincentas Vienozinskis
 *
 */
@Singleton
@Controller
public class HomeController {

	@Path("/")
    public void home() {

    }
	

}
