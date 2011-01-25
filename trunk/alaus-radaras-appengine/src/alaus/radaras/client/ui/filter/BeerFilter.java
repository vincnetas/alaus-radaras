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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Vincentas
 *
 */
public class BeerFilter extends BaseFilter {
    
    @Override
    protected void getFilterWidgets(final Pub pub, AsyncCallback<Set<Widget>> callback) {
        Set<Widget> result = new HashSet<Widget>();
        
        for (final String beerId : pub.getBeerIds()) {
            BeerFilterWidget widget = new BeerFilterWidget(beerId);
            widget.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> arg0) {
                    if (arg0.getValue()) {
                        filter.addPub(pub);
                    } else {
                        filter.removePub(pub);
                    }
                    
                    Stat.getHandlerManager().fireEvent(new PubFilterEvent(filter));
                }
            });
        }
        
        callback.onSuccess(result);
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
}
