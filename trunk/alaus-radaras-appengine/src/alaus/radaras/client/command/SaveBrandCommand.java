/**
 * 
 */
package alaus.radaras.client.command;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.saved.BrandSavedEvent;
import alaus.radaras.shared.model.Brand;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Vincentas
 *
 */
public class SaveBrandCommand implements Command {

	private Brand brand;
	
	public SaveBrandCommand(Brand brand) {
		this.brand = brand;
	}
	
	@Override
	public void execute() {
		Stat.getBeerService().saveBrand(brand, new AsyncCallback<Brand>() {
			
			@Override
			public void onSuccess(Brand result) {
				Stat.getHandlerManager().fireEvent(new BrandSavedEvent(result));				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}
		});
	}

}
