/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.HashSet;
import java.util.Set;

import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubFilterEvent;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;

/**
 * @author Vincentas
 *
 */
public abstract class FilterWidget<T> extends Composite implements ValueChangeHandler<Boolean> {

	private final PubFilter filter;
	
	private final T value;
	
	private final Set<Pub> pubs = new HashSet<Pub>();
	
	public FilterWidget(T value, PubFilter filter) {
		this.value = value;
		this.filter = filter;
	}
	
	/* (non-Javadoc)
	 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
	 */
	@Override
	public void onValueChange(ValueChangeEvent<Boolean> value) {
        if (value.getValue()) {
            filter.addPubs(pubs);
        } else {
            filter.removePubs(pubs);
        }
        
        Stat.getHandlerManager().fireEvent(new PubFilterEvent(filter));
	}
	
    public void addPub(Pub pub) {
    	pubs.add(pub);
    }
    
    public void removePub(Pub pub) {
    	pubs.remove(pub);
    }
    
    public boolean isFor(T value) {
    	return this.value.equals(value);
    }

}
