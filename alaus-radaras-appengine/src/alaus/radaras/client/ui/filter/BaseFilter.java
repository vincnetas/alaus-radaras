/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.HashMap;
import java.util.Map;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubAddedHandler;
import alaus.radaras.client.events.PubRemovedHandler;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public abstract class BaseFilter extends Composite implements PubAddedHandler, PubRemovedHandler {
	
	private static BaseFilterUiBinder uiBinder = GWT.create(BaseFilterUiBinder.class);

	interface BaseFilterUiBinder extends UiBinder<Widget, BaseFilter> {
	}

	@UiField
	FlowPanel panel;
	
	public BaseFilter() {
		initWidget(uiBinder.createAndBindUi(this));
		Stat.getHandlerManager().addHandler(PubAddedHandler.type, this);
		Stat.getHandlerManager().addHandler(PubRemovedHandler.type, this);
	}

	private Map<Pub, Widget> widgets = new HashMap<Pub, Widget>();
	
	@Override
	public void pubAdded(Pub pub) {		
		Widget button = getPubWidget(pub);
		panel.add(button);
		widgets.put(pub, button);
	}

	@Override
	public void pubRemoved(Pub pub) {
		panel.remove(widgets.get(pub));
	}
	
	protected abstract Widget getPubWidget(Pub pub);

}
