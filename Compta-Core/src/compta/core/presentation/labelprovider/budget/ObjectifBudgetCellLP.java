
package compta.core.presentation.labelprovider.budget;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.BudgetManager;
import compta.core.common.ApplicationFormatter;
import compta.core.common.ComptaException;

public class ObjectifBudgetCellLP extends CellLabelProvider {

	public double getSolde(final Identifier budgetId) {
		double res;
		try {
			res = BudgetManager.getInstance().getObjectifBudget(budgetId);
		} catch (final ComptaException e) {
			res = 0;
		}

		return res;

	}

	@Override
	public void update(final ViewerCell cell) {

		final double montant = getSolde((Identifier) cell.getElement());
		cell.setText(ApplicationFormatter.montantFormat.format(montant));

	}

}
