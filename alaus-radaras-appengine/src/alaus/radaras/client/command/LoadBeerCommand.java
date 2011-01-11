/**
 * 
 */
package alaus.radaras.client.command;

import java.util.HashSet;
import java.util.Set;

import alaus.radaras.client.Stat;
import alaus.radaras.shared.model.Beer;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Vincentas
 *
 */
public class LoadBeerCommand implements Command {

	private Set<String> beerIds = new HashSet<String>();
	
	public LoadBeerCommand(String beerId) {
		this.beerIds.add(beerId);
	}
	
	public LoadBeerCommand(Set<String> beerIds) {
		this.beerIds.addAll(beerIds);
	}
	
	/* (non-Javadoc)
	 * @see alaus.radaras.client.command.Command#execute()
	 */
	@Override
	public void execute() {
		Stat.getBeerService().loadBeer(beerIds, new AsyncCallback<Set<Beer>>() {
			
			@Override
			public void onSuccess(Set<Beer> result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
		});
	}

}
