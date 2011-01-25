/**
 * 
 */
package alaus.radaras.client.ui.filter;

import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas Vienozinskis
 *
 */
public interface PubFilter {

    boolean match(Pub pub);
}
