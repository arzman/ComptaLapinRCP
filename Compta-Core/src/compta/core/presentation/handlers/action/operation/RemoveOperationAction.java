package compta.core.presentation.handlers.action.operation;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationIcon;
import compta.core.common.ComptaException;

public class RemoveOperationAction extends Action {

	private final TableViewer _tableViewer;

	public RemoveOperationAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.REMOVE_ICON;
	}

	@Override
	public String getText() {

		return "Supprimer";
	}

	public boolean isActivate() {

		boolean res = false;

		if (_tableViewer.getSelection() instanceof StructuredSelection) {

			final Object obj = ((StructuredSelection) _tableViewer.getSelection()).getFirstElement();

			if ((obj != null) && (obj instanceof OperationIdentifier)) {

				res = true;
			}

		}

		return res;
	}

	@Override
	public void run() {

		if (isActivate()) {

			super.run();
			try {
				TrimestreManager.getInstance().removeOperation(
						(OperationIdentifier) ((StructuredSelection) _tableViewer.getSelection()).getFirstElement());
			} catch (ComptaException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
						"Impossible de supprimer l'opération");
			}
		}

	}

}
