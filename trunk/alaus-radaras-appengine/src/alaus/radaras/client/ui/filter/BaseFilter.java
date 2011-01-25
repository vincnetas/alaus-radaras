/**
 * 
 */
package alaus.radaras.client.ui.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Vincentas
 * 
 */
public abstract class BaseFilter extends Composite implements PubAddedHandler, PubRemovedHandler {

    protected final PubFilter filter = new PubFilter();
    
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

    private final List<Widget> widgets = new ArrayList<Widget>();

    private final Set<Pub> pubs = new HashSet<Pub>();

    @Override
    public void pubAdded(final Pub pub) {
        if (pubs.contains(pub)) {
            return;
        }
        
        getFilterWidgets(pub, new BaseAsyncCallback<Set<Widget>>() {

            @Override
            public void onSuccess(Set<Widget> filterWidgets) {
                if (!pubs.contains(pub)) {
                    for (Widget widget : filterWidgets) {
                        if (!widgets.contains(widget)) {
                            panel.add(widget);
                        }
                    }
                    widgets.addAll(filterWidgets);

                    pubs.add(pub);
                }
            }
        });

    }

    @Override
    public void pubRemoved(final Pub pub) {
        if (!pubs.contains(pub)) {
            return;
        }
        
        getFilterWidgets(pub, new BaseAsyncCallback<Set<Widget>>() {

            @Override
            public void onSuccess(Set<Widget> filterWidgets) {
                if (pubs.contains(pub)) {
                    for (Widget widget : filterWidgets) {
                        widgets.remove(widget);
                        if (!widgets.contains(widget)) {
                            panel.remove(widget);
                        }
                    }

                    pubs.remove(pub);
                }
            }
        });
    }

    protected abstract void getFilterWidgets(Pub pub, AsyncCallback<Set<Widget>> callback);
}
