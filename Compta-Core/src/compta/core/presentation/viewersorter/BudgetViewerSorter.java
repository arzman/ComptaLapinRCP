
package compta.core.presentation.viewersorter;

import org.eclipse.jface.viewers.Viewer;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.BudgetManager;
import compta.core.common.ComptaException;

/**
 * 
 * Comparateur de budget
 * 
 * @author Arthur
 *
 */
public class BudgetViewerSorter extends AbstractViewerSorter {

	

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		final Identifier id1 = (Identifier) e1;
		final Identifier id2 = (Identifier) e2;
		int rc = 0;

		try {
			switch (propertyIndex) {
			case 0:
				rc = BudgetManager.getInstance().getNomBudget(id1).compareTo(BudgetManager.getInstance().getNomBudget(id2));
				break;
			case 1:
				rc = Double.compare(BudgetManager.getInstance().getObjectifBudget(id1), BudgetManager.getInstance().getObjectifBudget(id2));
				break;
			case 2:
				rc = Double.compare(BudgetManager.getInstance().getAvancement(id1), BudgetManager.getInstance().getAvancement(id2));
				break;
			case 3:
				rc = Double.compare(BudgetManager.getInstance().getMontantCourant(id1), BudgetManager.getInstance().getMontantCourant(id2));
				break;
			case 4:
				rc = Double.compare(BudgetManager.getInstance().getMontantLivret(id1), BudgetManager.getInstance().getMontantLivret(id2));
				break;
			case 5:
				rc = Double.compare(BudgetManager.getInstance().getUtiliseBudget(id1), BudgetManager.getInstance().getUtiliseBudget(id2));
				break;
			default:
				rc = 0;
			}
		} catch (final ComptaException e) {

		}
		// If descending order, flip the direction
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}


}
