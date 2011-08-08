/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.HashSet;
import java.util.Set;

import alaus.radaras.client.BaseAsyncCallback;
import alaus.radaras.client.Stat;
import alaus.radaras.shared.model.Beer;
import alaus.radaras.shared.model.Brand;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Vincentas
 *
 */
public class CountryFilter extends BaseFilter<String, CountryFilterWidget> {

	@Override
	protected void getFilterWidgets(final Pub pub, final AsyncCallback<Set<CountryFilterWidget>> callback) {
        Stat.getBeerService().loadBeer(pub.getBeerIds(), new BaseAsyncCallback<Set<Beer>>() {

			public void onSuccess(Set<Beer> beers) {
		        Set<String> brandIds = new HashSet<String>();
		        
		        for (Beer beer : beers) {
		        	brandIds.add(beer.getBrandId());
		        }
		        
		        Stat.getBeerService().loadBrand(brandIds, new BaseAsyncCallback<Set<Brand>>() {

					public void onSuccess(Set<Brand> brands) {
				        Set<CountryFilterWidget> result = new HashSet<CountryFilterWidget>();

				        for (Brand brand : brands) {
				        	CountryFilterWidget widget = getWidget(brand.getCountry());
				        	if (widget == null) {
				        		widget = new CountryFilterWidget(brand.getCountry(), filter);
				        	}
				        	widget.addPub(pub);
				        	
				        	result.add(widget);
				        }
				        
				        callback.onSuccess(result);						
					}
				});		        
			}
		});
	}
}


