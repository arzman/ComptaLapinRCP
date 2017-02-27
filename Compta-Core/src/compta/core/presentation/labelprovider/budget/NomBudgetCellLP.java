package compta.core.presentation.labelprovider.budget;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.BudgetManager;
import compta.core.common.ComptaException;

public class NomBudgetCellLP extends CellLabelProvider {

	@Override
	public void update(final ViewerCell cell) {

		try {
			cell.setText(BudgetManager.getInstance().getNomBudget((Identifier) cell.getElement()));
		} catch (final ComptaException e) {
			cell.setText("ERROR");
		}

	}

}
