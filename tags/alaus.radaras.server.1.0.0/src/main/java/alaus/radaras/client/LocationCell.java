package alaus.radaras.client;

import alaus.radaras.shared.model.Location;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;

class LocationCell extends AbstractEditableCell<Location, Pub> {

    /* (non-Javadoc)
     * @see com.google.gwt.cell.client.AbstractEditableCell#isEditing(com.google.gwt.cell.client.Cell.Context, com.google.gwt.dom.client.Element, java.lang.Object)
     */
    @Override
    public boolean isEditing(com.google.gwt.cell.client.Cell.Context arg0, Element arg1, Location arg2) {
        return false;
    }

    /* (non-Javadoc)
     * @see com.google.gwt.cell.client.AbstractCell#render(com.google.gwt.cell.client.Cell.Context, java.lang.Object, com.google.gwt.safehtml.shared.SafeHtmlBuilder)
     */
    @Override
    public void render(com.google.gwt.cell.client.Cell.Context arg0, Location arg1, SafeHtmlBuilder arg2) {
    	if (arg1 != null) {
    		arg2.append(SimpleSafeHtmlRenderer.getInstance().render(arg1.getLatitude() + " " + arg1.getLongitude()));
    	} else {
    		arg2.append(SimpleSafeHtmlRenderer.getInstance().render("0 0"));
    	}
    }


}