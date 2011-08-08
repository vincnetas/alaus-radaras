/**
 * 
 */
package alaus.radaras.client.ui.edit;

import java.util.Set;

import alaus.radaras.client.Stat;
import alaus.radaras.shared.Utils;
import alaus.radaras.shared.model.Beer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas Vienozinskis
 *
 */
public class BeerInfoWidget extends Composite {

	private static BeerInfoWidgetUiBinder uiBinder = GWT.create(BeerInfoWidgetUiBinder.class);

	interface BeerInfoWidgetUiBinder extends UiBinder<Widget, BeerInfoWidget> {
	}

	@UiField
	Label label;
	
	private Beer beer;

	public BeerInfoWidget(String beerId) {
		initWidget(uiBinder.createAndBindUi(this));

		label.setText("Loading...");
		Stat.getBeerService().loadBeer(Utils.set(beerId), new AsyncCallback<Set<Beer>>() {

			@Override
			public void onSuccess(Set<Beer> result) {
				for (Beer beer : result) {
					setBeer(beer);
				}
			}

			/* (non-Javadoc)
			 * @see alaus.radaras.client.BaseAsyncCallback#onFailure(java.lang.Throwable)
			 */
			@Override
			public void onFailure(Throwable caught) {
				label.setText("Failed...");
			}
		});
	}

	public BeerInfoWidget(Beer beer) {
		initWidget(uiBinder.createAndBindUi(this));

		setBeer(beer);
	}
	
	private void setBeer(Beer beer) {
		this.beer = beer;
		label.setText(beer.getTitle());
	}
}
