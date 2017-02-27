package compta.core.presentation.viewersorter;

import org.eclipse.jface.viewers.Viewer;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CompteManager;
import compta.core.common.ComptaException;


/**
 * Comparateur de {@link Compte}
 * 
 * @author Arthur
 *
 */
public class CompteViewerSorter extends AbstractViewerSorter {


	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		final Identifier id1 = (Identifier) e1;
		final Identifier id2 = (Identifier) e2;
		int rc = 0;

		try {

			switch (propertyIndex) {
			case 0:
				rc = CompteManager.getInstance().getCompte(id1).getNom().compareTo(CompteManager.getInstance().getCompte(id2).getNom());
				break;
			case 1:
				rc = Double.compare(CompteManager.getInstance().getSoldeCompte(id1), CompteManager.getInstance().getSoldeCompte(id2));
				break;
			case 2:
				rc = Double.compare(CompteManager.getInstance().getSoldePrevCompte(id1, 1),
						CompteManager.getInstance().getSoldePrevCompte(id2, 1));
				break;
			case 3:
				rc = Double.compare(CompteManager.getInstance().getSoldePrevCompte(id1, 2),
						CompteManager.getInstance().getSoldePrevCompte(id2, 2));
				break;
			case 4:
				rc = Double.compare(CompteManager.getInstance().getSoldePrevCompte(id1, 3),
						CompteManager.getInstance().getSoldePrevCompte(id2, 3));
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
