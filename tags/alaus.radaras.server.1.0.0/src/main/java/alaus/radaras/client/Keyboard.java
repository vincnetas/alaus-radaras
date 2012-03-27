package alaus.radaras.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;

public final class Keyboard {
	private static final class CloseListener implements ClosingHandler {
		public native void onWindowClosed()
		/*-{
			$doc.onkeydown = null;
			$doc.onkeypress = null;
			$doc.onkeyup = null;
		}-*/;

		public String onWindowClosing() {
			return null;
		}

		private native void init()
		/*-{
			$doc.onkeydown = function(evt) {
			  @alaus.radaras.client.Keyboard::onKeyDown(Lcom/google/gwt/user/client/Event;)(evt
			|| $wnd.event);
			}
			$doc.onkeypress = function(evt) {
			  @alaus.radaras.client.Keyboard::onKeyPress(Lcom/google/gwt/user/client/Event;)(evt
			|| $wnd.event);
			}
			$doc.onkeyup = function(evt) {
			  @alaus.radaras.client.Keyboard::onKeyUp(Lcom/google/gwt/user/client/Event;)(evt
			|| $wnd.event);
			}
		}-*/;

		
		public void onWindowClosing(ClosingEvent event) {
			onWindowClosed();
		}

	}

	static void init () {
		CloseListener closeListener = new CloseListener();
		Window.addWindowClosingHandler(closeListener);
		closeListener.init();
	}

	private static void onKeyDown(Event event) {		
		char keyCode = (char) DOM.eventGetKeyCode(event);
		// ....your code here....
	}

	private static void onKeyPress(Event event) {
		char keyCode = (char) DOM.eventGetKeyCode(event);
		Window.alert("Code " + event.getKeyCode());
	}

	private static void onKeyUp(Event event) {
		char keyCode = (char) DOM.eventGetKeyCode(event);
		// ....your code here....
	}

	/**
	 * Prevent instantiation.
	 */
	private Keyboard() {
	}
}