
package compta.core.presentation.handlers.action.operation;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationIcon;
import compta.core.common.ComptaException;
import compta.core.presentation.dialogs.operation.CreateTransfertDialog;

public class CreateTransfertAction extends Action {

	private final int _numMois;

	public CreateTransfertAction(int numMoi) {

		_numMois = numMoi;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.ADD_ICON;
	}

	@Override
	public String getText() {

		return "Ajouter transfert";
	}

	@Override
	public void run() {

		super.run();

		final CreateTransfertDialog diag = new CreateTransfertDialog();

		if (diag.open() == Window.OK) {

			if (!diag.getNom().trim().isEmpty() && (diag.getCompteId() != null)) {

				try {
					TrimestreManager.getInstance().createTransfert(diag.getNom(), diag.getMontant(), diag.getCompteId(),
							diag.getCompteIdCible(), _numMois);
				} catch (ComptaException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur", "Impossible de créer le transfert");
				}
			}
		}

	}

}
