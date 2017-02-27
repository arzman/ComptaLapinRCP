
package compta.core.presentation.labelprovider.budget;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.BudgetManager;
import compta.core.common.ApplicationFormatter;
import compta.core.common.ComptaException;

public class MontantLivretBudgetCellLP extends CellLabelProvider {

	@Override
	public void update(ViewerCell cell) {

		String txt = "ERROR";

		try {
			final double montant = BudgetManager.getInstance().getMontantLivret((Identifier) cell.getElement());
			txt = ApplicationFormatter.montantFormat.format(montant);
		} catch (final ComptaException ex) {

		}

		cell.setText(txt);

	}

}
