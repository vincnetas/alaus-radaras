/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.HashSet;
import java.util.Set;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubFilterEvent;
import alaus.radaras.shared.model.Brand;
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
public class CountryFilter extends BaseFilter {

	@Override
	protected void getFilterWidgets(final Pub pub, AsyncCallback<Set<Widget>> callback) {
	    Set<Widget> result = new HashSet<Widget>();
	    
	    for (final Brand brand : getPubBrands(pub)) {
	        CountryFilterWidget widget = new CountryFilterWidget(brand.getCountry());
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

	class CountryFilterWidget extends CheckBox {
	    
	    private final String country;
	    
	    public CountryFilterWidget(String country) {
	        super(country);	        
	        this.country = country;
	    }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {         
            return country.hashCode();
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            boolean result = false;
            
            if (obj instanceof CountryFilterWidget) {
                CountryFilterWidget widget = (CountryFilterWidget) obj;
                result = country.equalsIgnoreCase(widget.country);
            }
            
            return result;
        }	    
	}	
}
