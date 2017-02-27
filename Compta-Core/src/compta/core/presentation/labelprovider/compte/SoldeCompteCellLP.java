
package compta.core.presentation.labelprovider.compte;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CompteManager;
import compta.core.common.ApplicationFormatter;
import compta.core.common.ComptaException;

public class SoldeCompteCellLP extends CellLabelProvider {

	public double getSolde(final Identifier compteId) {
		double res;
		try {
			res = CompteManager.getInstance().getSoldeCompte(compteId);
		} catch (final ComptaException e) {
			res = 0;
		}

		return res;

	}

	@Override
	public void update(final ViewerCell cell) {

		final double montant = getSolde((Identifier) cell.getElement());
		cell.setText(ApplicationFormatter.montantFormat.format(montant));

		if (montant < 0) {
			cell.setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
		} else {
			cell.setForeground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_BLACK));
		}

	}

}
