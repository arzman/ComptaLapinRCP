package compta.core.presentation.labelprovider.operation;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationFormatter;
import compta.core.common.ComptaException;

public class MontantOperationLP extends CellLabelProvider {

	@Override
	public void update(final ViewerCell cell) {

		String txt = "ERROR";
		if (cell.getElement() instanceof OperationIdentifier) {

			try {
				txt = ApplicationFormatter.montantFormat.format(TrimestreManager.getInstance().getOperationMontant(
						(OperationIdentifier) cell.getElement()));
			} catch (ComptaException e) {
				
			}

		}

		cell.setText(txt);

	}
}
