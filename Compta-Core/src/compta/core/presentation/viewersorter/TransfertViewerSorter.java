
package compta.core.presentation.viewersorter;

import org.eclipse.jface.viewers.Viewer;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.domaine.operation.TransfertOperation;

/**
 * Comparateur de {@link TransfertOperation}
 * 
 * @author Arthur
 *
 */
public class TransfertViewerSorter extends AbstractViewerSorter {


	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		final OperationIdentifier id1 = (OperationIdentifier) e1;
		final OperationIdentifier id2 = (OperationIdentifier) e2;
		int rc = 0;

		try {
			switch (propertyIndex) {
			case 0:
				rc = TrimestreManager.getInstance().getOperationNom(id1).compareTo(TrimestreManager.getInstance().getOperationNom(id2));
				break;
			case 1:
				rc = Double.compare(TrimestreManager.getInstance().getOperationMontant(id1), TrimestreManager.getInstance()
						.getOperationMontant(id2));
				break;
			case 2:
				rc = TrimestreManager.getInstance().getOperationCompteNom(id1)
						.compareTo(TrimestreManager.getInstance().getOperationCompteNom(id2));
				break;
			case 3:
				rc = TrimestreManager.getInstance().getTransfertCompteCibleNom(id1)
						.compareTo(TrimestreManager.getInstance().getTransfertCompteCibleNom(id2));
				break;
			default:
				rc = 0;
			}
		} catch (final Exception e) {

		}
		// If descending order, flip the direction
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}


}
