
package compta.core.presentation.handlers.action.compte;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CompteManager;
import compta.core.common.ApplicationIcon;
import compta.core.common.ComptaException;
import compta.core.presentation.dialogs.compte.EditCompteDialog;

public class EditCompteAction extends Action {

	private final TableViewer _tableViewer;

	public EditCompteAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.EDIT_ICON;
	}

	@Override
	public String getText() {

		return "Editer le compte";
	}

	public boolean isActivated() {

		boolean res = false;

		if (_tableViewer.getSelection() instanceof StructuredSelection) {

			final Object obj = ((StructuredSelection) _tableViewer.getSelection()).getFirstElement();

			if ((obj != null) && (obj instanceof Identifier)) {

				res = true;
			}

		}

		return res;
	}

	@Override
	public void run() {

		if (isActivated()) {

			super.run();

			final Object obj = ((StructuredSelection) _tableViewer.getSelection()).getFirstElement();

			final EditCompteDialog diag = new EditCompteDialog((Identifier) obj);

			if (diag.open() == Window.OK) {
				try {
					CompteManager.getInstance().editCompte((Identifier) obj, diag.getNom(), diag.getSolde(), diag.getIsLivret(),
							diag.getBudgetAllowed());
				} catch (final ComptaException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
							"Impossible d'éditer le compte :" + ((Identifier) obj).getIdentifier());
				}
			}

		} else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
					"Impossible d'éditer le compte : L'élément sélectionné n'est pas un Identifier");
		}

	}

}
