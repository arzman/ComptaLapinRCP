package compta.core.presentation.handlers.action.operation;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationIcon;
import compta.core.common.ComptaException;
import compta.core.presentation.dialogs.operation.CreateDepenseDialog;

public class CreateDepenseAction extends Action {

	private final int _numMois;

	public CreateDepenseAction(int numMoi) {

		_numMois = numMoi;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.ADD_ICON;
	}

	@Override
	public String getText() {

		return "Ajouter dépense";
	}

	@Override
	public void run() {

		super.run();

		final CreateDepenseDialog diag = new CreateDepenseDialog();

		if (diag.open() == Window.OK) {

			if (!diag.getNom().trim().isEmpty() && (diag.getCompteId() != null)) {

				try {
					TrimestreManager.getInstance().createDepense(diag.getNom(), diag.getMontant(), diag.getCategorie(), diag.getCompteId(),
							_numMois);
				} catch (ComptaException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
							"Impossible de créer la dépense");
				}
			}
		}

	}

}
