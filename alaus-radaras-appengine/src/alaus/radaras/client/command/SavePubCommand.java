/**
 * 
 */
package alaus.radaras.client.command;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.saved.PubSavedEvent;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Vincentas
 *
 */
public class SavePubCommand implements Command {

	private Pub pub;
	
	public SavePubCommand(Pub pub) {
		this.pub = pub;
	}
	
	@Override
	public void execute() {
		Stat.getBeerService().savePub(pub, new AsyncCallback<Pub>() {
			
			@Override
			public void onSuccess(Pub result) {
				Stat.getHandlerManager().fireEvent(new PubSavedEvent(result));				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
		});
	}

}
