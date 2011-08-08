/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import alaus.radaras.client.BaseAsyncCallback;
import alaus.radaras.client.Stat;
import alaus.radaras.client.events.PubAddedHandler;
import alaus.radaras.client.events.PubRemovedHandler;
import alaus.radaras.shared.model.Pub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 * 
 */
public abstract class BaseFilter<E, T extends FilterWidget<E>> extends Composite implements PubAddedHandler, PubRemovedHandler {

    protected final PubFilter filter = new PubFilter();
    
    private static BaseFilterUiBinder uiBinder = GWT.create(BaseFilterUiBinder.class);

    interface BaseFilterUiBinder extends UiBinder<Widget, BaseFilter> {
    }

    @UiField
    Panel panel;

    public BaseFilter() {
        initWidget(uiBinder.createAndBindUi(this));
        Stat.getHandlerManager().addHandler(PubAddedHandler.type, this);
        Stat.getHandlerManager().addHandler(PubRemovedHandler.type, this);
    }
    
    private final Map<T, Set<Pub>> widgetPubs = new HashMap<T, Set<Pub>>();

    private final Map<Pub, Set<T>> pubWidgets = new HashMap<Pub, Set<T>>(); 
    
    public void pubAdded(final Pub pub) {
        if (!pubWidgets.containsKey(pub)) {
        	pubWidgets.put(pub, new HashSet<T>());
        	
	        getFilterWidgets(pub, new BaseAsyncCallback<Set<T>>() {
	
	            public void onSuccess(Set<T> widgets) {
	            	if (pubWidgets.containsKey(pub)) {
	            		pubWidgets.get(pub).addAll(widgets);
	            		
		            	for (T widget : widgets) {
							if (!widgetPubs.containsKey(widget)) {
								widgetPubs.put(widget, new HashSet<Pub>());
								panel.add(widget);
							}
							
							widgetPubs.get(widget).add(pub);
						}
	            	}
	            }
	        });
        }
    }

    public void pubRemoved(final Pub pub) {
        if (pubWidgets.containsKey(pub)) {
        	Set<T> widgets = pubWidgets.get(pub);
        	for (Widget widget : widgets) {
				Set<Pub> pubs = widgetPubs.get(widget);
				pubs.remove(pub);
				
				if (pubs.isEmpty()) {
					panel.remove(widget);
					widgetPubs.remove(widget);
				}
			}
        	
        	pubWidgets.remove(pub);
        }
    }

    protected abstract void getFilterWidgets(Pub pub, AsyncCallback<Set<T>> callback);
        
	protected T getWidget(E value) {
		T result = null;
		for (T widget : widgetPubs.keySet()) {
			if (widget.isFor(value)) {
				result = widget;
				break;
			}
		}

		return result;
	}

}
