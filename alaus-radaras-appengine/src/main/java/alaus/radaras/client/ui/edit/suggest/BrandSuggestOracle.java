package alaus.radaras.client.ui.edit.suggest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import alaus.radaras.client.Stat;
import alaus.radaras.shared.model.Brand;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

class BrandSuggestOracle extends SuggestOracle {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.ui.SuggestOracle#requestSuggestions(com.google
	 * .gwt.user.client.ui.SuggestOracle.Request,
	 * com.google.gwt.user.client.ui.SuggestOracle.Callback)
	 */
	
	public void requestSuggestions(final Request request, final Callback callback) {
		Stat.getBeerService().getBrandSuggestions(request.getQuery(), request.getLimit(), new AsyncCallback<List<Brand>>() {

			
			public void onSuccess(List<Brand> result) {
				Collection<BrandSuggestion> beerSuggestions = new ArrayList<BrandSuggestion>();
				for (Brand brand : result) {
					beerSuggestions.add(new BrandSuggestion(brand, request.getQuery()));
				}

				if (beerSuggestions.isEmpty()) {
					beerSuggestions.add(new BrandSuggestion(request.getQuery()));
				}

				Response response = new Response(beerSuggestions);
				callback.onSuggestionsReady(request, response);
			}

			
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
		});
	}
}
