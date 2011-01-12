package alaus.radaras.client.ui.edit.suggest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import alaus.radaras.client.Stat;
import alaus.radaras.shared.model.Beer;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

class BeerSuggestOracle extends SuggestOracle {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.user.client.ui.SuggestOracle#requestSuggestions(com.google
	 * .gwt.user.client.ui.SuggestOracle.Request,
	 * com.google.gwt.user.client.ui.SuggestOracle.Callback)
	 */
	@Override
	public void requestSuggestions(final Request request, final Callback callback) {
		Stat.getBeerService().getBeerSuggestions(request.getQuery(), request.getLimit(), new AsyncCallback<List<Beer>>() {

			@Override
			public void onSuccess(List<Beer> result) {
				Collection<BeerSuggestion> beerSuggestions = new ArrayList<BeerSuggestion>();
				for (Beer beer : result) {
					beerSuggestions.add(new BeerSuggestion(beer, request.getQuery()));
				}

				if (beerSuggestions.isEmpty()) {
					beerSuggestions.add(new BeerSuggestion(request.getQuery()));
				}

				Response response = new Response(beerSuggestions);
				callback.onSuggestionsReady(request, response);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
		});
	}
}