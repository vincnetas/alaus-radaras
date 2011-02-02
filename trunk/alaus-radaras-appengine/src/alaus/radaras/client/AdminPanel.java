/**
 * 
 */
package alaus.radaras.client;

import java.util.ArrayList;
import java.util.List;

import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.LocationBounds;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 * 
 */
public class AdminPanel extends Composite {

	private static AdminPanelUiBinder uiBinder = GWT.create(AdminPanelUiBinder.class);

	interface AdminPanelUiBinder extends UiBinder<Widget, AdminPanel> {
	}
		
	private List<Pub> data = new ArrayList<Pub>();

	private CellTable<Pub> table = PubEdit.getTable();
	
	@UiField
	Panel pubsPanel;
	
	@UiField
	Panel importPanel;
	
	@UiField
	Button addPubButton;
	
	@UiField
	Button addBeerButton;
	
	@UiField
	Button addBrandButton;
	
	public AdminPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		pubsPanel.add(table);
		
		Stat.getBeerService().findPubs(new LocationBounds(new Location(10, 20), new Location(10, 20)), new BaseAsyncCallback<List<Pub>>() {
			@Override
			public void onSuccess(List<Pub> result) {
				setData(result);
			}
		});
	}
	
	@UiHandler("addPubButton")
	public void addPubButtonClicked(ClickEvent event) {
		Pub pub = new Pub();
		getData().add(0, pub);
		setData(getData());	
	}
	
	/**
	 * @return the data
	 */
	public List<Pub> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<Pub> data) {
		this.data = data;
		
		table.setRowCount(data.size(), true);
		table.setRowData(data);
	}
}