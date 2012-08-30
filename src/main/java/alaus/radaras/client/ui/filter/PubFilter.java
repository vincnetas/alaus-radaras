/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.Collection;
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

    public void addPubs(Collection<Pub> pubs) {
        this.pubs.addAll(pubs);
    }
    
    public void removePubs(Collection<Pub> pubs) {
        this.pubs.removeAll(pubs);
    }   
}