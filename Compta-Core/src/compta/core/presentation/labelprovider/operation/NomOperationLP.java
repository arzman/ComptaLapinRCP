package compta.core.presentation.labelprovider.operation;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationIcon;
import compta.core.common.ComptaException;
import compta.core.domaine.operation.EtatOperation;

public class NomOperationLP extends CellLabelProvider {

	@Override
	public void update(final ViewerCell cell) {

		String txt = "ERROR";
		Image img = null;
		if (cell.getElement() instanceof OperationIdentifier) {

			try {

				txt = TrimestreManager.getInstance().getOperationNom((OperationIdentifier) cell.getElement());

				final EtatOperation etat = TrimestreManager.getInstance().getOperationEtat((OperationIdentifier) cell.getElement());

				if (etat == EtatOperation.PRISE_EN_COMPTE) {

					img = ApplicationIcon.VALID_ICON.createImage();
				}
			} catch (ComptaException e) {

			}

		}

		cell.setText(txt);
		cell.setImage(img);

	}

}
