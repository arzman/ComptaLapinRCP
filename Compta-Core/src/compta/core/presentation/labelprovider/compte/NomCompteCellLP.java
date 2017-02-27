
package compta.core.presentation.labelprovider.compte;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CompteManager;
import compta.core.common.ComptaException;

public class NomCompteCellLP extends CellLabelProvider {

	@Override
	public void update(final ViewerCell cell) {

		try {
			cell.setText(CompteManager.getInstance().getNomCompte((Identifier) cell.getElement()));
		} catch (final ComptaException e) {
			cell.setText("ERROR");
		}

	}

}
