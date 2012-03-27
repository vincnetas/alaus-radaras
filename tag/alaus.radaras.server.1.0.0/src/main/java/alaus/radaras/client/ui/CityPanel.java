/**
 * 
 */
package alaus.radaras.client.ui;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.ChangeUserLocationEvent;
import alaus.radaras.shared.model.IPLocation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public class CityPanel extends Composite {

	private static CityPanelUiBinder uiBinder = GWT.create(CityPanelUiBinder.class);
	
	@UiField TextBox textBox;

	interface CityPanelUiBinder extends UiBinder<Widget, CityPanel> {
	}

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public CityPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		
		Stat.getBeerService().getMyLocation(new AsyncCallback<IPLocation>() {

		    public void onSuccess(IPLocation result) {
				String value = result.toString();
				if (!value.isEmpty()) {
					textBox.setText(value);
					Stat.getHandlerManager().fireEvent(new ChangeUserLocationEvent(value));
				}
			}
			
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub				
			}
		});
	}
	
	@UiHandler("textBox")
	void onTextBoxValueChange(ValueChangeEvent<String> event) {
		Stat.getHandlerManager().fireEvent(new ChangeUserLocationEvent(event.getValue()));
	}
}
