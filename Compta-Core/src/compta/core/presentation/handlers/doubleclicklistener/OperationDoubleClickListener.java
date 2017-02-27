
package compta.core.presentation.handlers.doubleclicklistener;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ComptaException;

public class OperationDoubleClickListener implements IDoubleClickListener {

	@Override
	public void doubleClick(final DoubleClickEvent event) {

		if (event.getSelection() instanceof StructuredSelection) {

			final Object obj = ((StructuredSelection) event.getSelection()).getFirstElement();

			if (obj instanceof OperationIdentifier) {

				try {
					TrimestreManager.getInstance().switchEtatOperation((OperationIdentifier) obj);
				} catch (final ComptaException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
							"Impossible de valider l'opération");
				}
			} else {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
						"Impossible de valider l'opération : L'élément sélectionné n'est pas un OpérationIdentifier");
			}

		}

	}

}
