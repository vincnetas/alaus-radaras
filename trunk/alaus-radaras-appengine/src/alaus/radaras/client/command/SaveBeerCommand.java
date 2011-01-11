/**
 * 
 */
package alaus.radaras.client.command;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.saved.BeerSavedEvent;
import alaus.radaras.shared.model.Beer;

/**
 * @author Vincentas
 *
 */
public class SaveBeerCommand implements Command {

	private Beer beer;
	
	public SaveBeerCommand(Beer beer) {
		this.beer = beer;
	}
	
	@Override
	public void execute() {
		Stat.getBeerService().saveBeer(beer, new AsyncCallback<Beer>() {
			
			@Override
			public void onSuccess(Beer result) {
				Stat.getHandlerManager().fireEvent(new BeerSavedEvent(result));				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
		});
	}

}
