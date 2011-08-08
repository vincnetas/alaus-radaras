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
public class TagFilter extends BaseFilter<String, TagFilterWidget> {

    @Override
    protected void getFilterWidgets(final Pub pub, final AsyncCallback<Set<TagFilterWidget>> callback) {
        Stat.getBeerService().loadBeer(pub.getBeerIds(), new BaseAsyncCallback<Set<Beer>>() {

			@Override
			public void onSuccess(Set<Beer> beers) {
		        Set<TagFilterWidget> result = new HashSet<TagFilterWidget>();
		        Set<String> tags = new HashSet<String>();
		        
		        for (Beer beer : beers) {
		        	tags.addAll(beer.getTags());
		        }
		        
		        for (String tag : tags) {
		        	TagFilterWidget widget = getWidget(tag);
		        	if (widget == null) {
		        		widget = new TagFilterWidget(tag, filter);
		        	}
		        	widget.addPub(pub);

		        	result.add(widget);
		        }
		        
		        callback.onSuccess(result);
			}
		});
    }
}


