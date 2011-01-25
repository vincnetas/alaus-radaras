/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubFilterEvent;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Vincentas
 *
 */
public class BeerFilter extends BaseFilter {

    private final PubBeerFilter filter;
    
    @Override
    protected Set<Widget> getFilterWidgets(Pub pub) {
        Set<Widget> result = new HashSet<Widget>();
        
        for (final String beerId : pub.getBeerIds()) {
            BeerFilterWidget widget = new BeerFilterWidget(beerId);
            widget.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> arg0) {
                    if (arg0.getValue()) {
                        filter.addBeer(beerId);
                    } else {
                        filter.removeBeer(beerId);
                    }
                    
                    Stat.getHandlerManager().fireEvent(new PubFilterEvent(filter));
                }
            });
        }
        
        return result;
    }
    
    private Set<Beer> getPubBeers(Pub pub) {
        Stat.getBeerService().loadBeer(pub.getBeerIds(), new )
    }
    
    class BeerFilterWidget extends CheckBox {
        
        private final Beer beer;
        
        public BeerFilterWidget(String beerId) {
            super(beer.getTitle());         
            this.beer = beer;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {         
            return beer.hashCode();
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            
            if (obj instanceof BeerFilterWidget) {
                BeerFilterWidget widget = (BeerFilterWidget) obj;
                result = beer.equals(widget.beer);
            }
            
            return result;
        }       
    }
    
    class PubBeerFilter implements PubFilter {

        private final Set<String> beerIds = new HashSet<String>();
        
        @Override
        public boolean match(Pub pub) {
            if (beerIds.isEmpty()) {
                return true;
            }
            
            return pub.getBeerIds().containsAll(beerIds);
        }

        public void addBeer(String beerId) {
            beerIds.add(beerId);
        }
        
        public void removeBeer(String beerId) {
            beerIds.remove(beerId);
        }       
    }
}
