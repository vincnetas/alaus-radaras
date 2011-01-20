/**
 * 
 */
package alaus.radaras.client.ui.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 *
 */
public abstract class RemovePanel extends Composite {

	private static RemovePanelUiBinder uiBinder = GWT.create(RemovePanelUiBinder.class);

	interface RemovePanelUiBinder extends UiBinder<Widget, RemovePanel> {
	}

	@UiField
	SimplePanel container;
	
	@UiField
	Label remove;

	public RemovePanel(Widget widget) {
		initWidget(uiBinder.createAndBindUi(this));

		container.add(widget);
	}

	@UiHandler("remove")
	void onRemoveClick(ClickEvent e) {
		onRemove();
	}
	
	public abstract void onRemove();

}
