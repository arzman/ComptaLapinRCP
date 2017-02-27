
package compta.core.presentation.labelprovider.operation;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ComptaException;

public class CategorieDepenseLP extends CellLabelProvider {

	@Override
	public void update(final ViewerCell cell) {

		String txt = "ERROR";

		if (cell.getElement() instanceof OperationIdentifier) {

			try {
				txt = TrimestreManager.getInstance().getDepenseCategorie((OperationIdentifier) cell.getElement());
			} catch (ComptaException e) {
				// txt = ERROR
			}
		}
		cell.setText(txt);

	}

}
