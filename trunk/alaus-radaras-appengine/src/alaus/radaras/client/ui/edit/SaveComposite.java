/**
 * 
 */
package alaus.radaras.client.ui.edit;

import alaus.radaras.client.ui.dialogs.EditDialog;

import com.google.gwt.user.client.ui.Composite;

/**
 * @author Vincentas
 *
 */
public abstract class SaveComposite extends Composite {

	public abstract void save(EditDialog editDialog);

}
