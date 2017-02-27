package compta.core.presentation.handlers.action.operation;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationIcon;
import compta.core.common.ComptaException;
import compta.core.presentation.dialogs.operation.CreateTransfertDialog;

public class EditTransfertAction extends Action {

	private final TableViewer _tableViewer;

	public EditTransfertAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.EDIT_ICON;
	}

	@Override
	public String getText() {

		return "Editer transfert";
	}

	@Override
	public void run() {

		super.run();

		if (_tableViewer.getSelection() instanceof IStructuredSelection) {

			final IStructuredSelection iss = (IStructuredSelection) _tableViewer.getSelection();

			if ((iss.getFirstElement() != null) && (iss.getFirstElement() instanceof OperationIdentifier)) {

				final OperationIdentifier id = (OperationIdentifier) iss.getFirstElement();
				final CreateTransfertDialog diag = new CreateTransfertDialog();

				try {
					diag.setNom(TrimestreManager.getInstance().getOperationNom(id));
					diag.setMontant(TrimestreManager.getInstance().getOperationMontant(id));
					diag.setCompteId(TrimestreManager.getInstance().getOperationCompte(id));
					diag.setCompteIdCible(TrimestreManager.getInstance().getTransfertCompteCible(id));

					if (diag.open() == Window.OK) {

						if (!diag.getNom().trim().isEmpty() && (diag.getCompteId() != null)) {

							TrimestreManager.getInstance().editTransfert(diag.getNom(), diag.getMontant(), diag.getCompteId(),
									diag.getCompteIdCible(), id);
						}
					}
				} catch (ComptaException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
							"Impossible d'éditer le transfert");
				}

			}

		}

	}

}
