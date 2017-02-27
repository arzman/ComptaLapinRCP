package compta.core.presentation.viewersorter;

import org.eclipse.jface.viewers.Viewer;

import compta.core.application.identifier.UtilisationIdentifier;
import compta.core.application.manager.BudgetManager;
import compta.core.common.ComptaException;

public class UtilisationViewerSorter extends AbstractViewerSorter {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		final UtilisationIdentifier id1 = (UtilisationIdentifier) e1;
		final UtilisationIdentifier id2 = (UtilisationIdentifier) e2;
		int rc = 0;

		try {

			switch (propertyIndex) {
			case 0:
				rc = BudgetManager.getInstance().getDateUtilisation(id1).compareTo(BudgetManager.getInstance().getDateUtilisation(id2));
			case 1:
				rc = BudgetManager.getInstance().geLibelleUtilisation(id1).compareTo(BudgetManager.getInstance().geLibelleUtilisation(id2));
			case 2:
				rc = BudgetManager.getInstance().getMontantUtilisation(id1).compareTo(BudgetManager.getInstance().getMontantUtilisation(id2));
			default:
				rc=0;

			}
		} catch (ComptaException e) {
			// rien le tri dit que c'est égale
		}

		// If descending order, flip the direction
		if (direction == DESCENDING) {
			rc = -rc;
		}
		
		return rc;
	}

}
