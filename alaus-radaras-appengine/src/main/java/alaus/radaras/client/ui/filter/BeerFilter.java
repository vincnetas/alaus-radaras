/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.HashSet;
import java.util.Set;

import alaus.radaras.client.BaseAsyncCallback;
import alaus.radaras.client.Stat;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Vincentas
 *
 */
public class BeerFilter extends BaseFilter<Beer, BeerFilterWidget> {
    
    @Override
    protected void getFilterWidgets(final Pub pub, final AsyncCallback<Set<BeerFilterWidget>> callback) {
        Stat.getBeerService().loadBeer(pub.getBeerIds(), new BaseAsyncCallback<Set<Beer>>() {

			@Override
			public void onSuccess(Set<Beer> beers) {
		        Set<BeerFilterWidget> result = new HashSet<BeerFilterWidget>();
		        
		        for (Beer beer : beers) {
		        	BeerFilterWidget widget = getWidget(beer);
		        	if (widget == null) {
		        		widget = new BeerFilterWidget(beer, filter);
		        	}
		        	widget.addPub(pub);
		        	
		        	result.add(widget);
		        }
		        
		        callback.onSuccess(result);
			}
		});
    }   
    
    
}


