/**
 * 
 */
package nb.server.controller.impl;

import org.zdevra.guice.mvc.Controller;
import org.zdevra.guice.mvc.RequestMapping;

import com.google.inject.Singleton;

/**
 * @author Vincentas Vienozinskis
 *
 */
@Singleton
@Controller
public class HomeController {

	@RequestMapping(path = "/", toView="view/home.jsp")
    public void home() {

    }

}
