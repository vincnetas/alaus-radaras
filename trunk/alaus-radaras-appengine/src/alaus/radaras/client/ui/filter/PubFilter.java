/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.HashSet;
import java.util.Set;

import alaus.radaras.shared.model.Pub;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class PubFilter {
    
    private final Set<Pub> pubs = new HashSet<Pub>();
    
    public boolean match(Pub pub) {
        if (pubs.isEmpty()) {
            return true;
        }
        
        return pubs.contains(pub);
    }

    public void addPub(Pub pub) {
        pubs.add(pub);
    }
    
    public void removePub(Pub pub) {
        pubs.remove(pub);
    }   
}