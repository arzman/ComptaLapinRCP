
package compta.core.presentation.handlers.action.operation;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationIcon;
import compta.core.common.ComptaException;
import compta.core.presentation.dialogs.operation.CreateRessourceDialog;

public class CreateRessourceAction extends Action {

	private final int _numMoi;

	public CreateRessourceAction(int numMoi) {

		_numMoi = numMoi;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.ADD_ICON;
	}

	@Override
	public String getText() {

		return "Ajouter ressource";
	}

	@Override
	public void run() {

		super.run();

		final CreateRessourceDialog diag = new CreateRessourceDialog();

		if (diag.open() == Window.OK) {

			if (!diag.getNom().trim().isEmpty() && (diag.getCompteId() != null)) {

				try {
					TrimestreManager.getInstance().createRessource(diag.getNom(), diag.getMontant(), diag.getCompteId(), _numMoi);
				} catch (ComptaException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur", "Impossible de créer la ressource");
				}
			}
		}

	}

}
