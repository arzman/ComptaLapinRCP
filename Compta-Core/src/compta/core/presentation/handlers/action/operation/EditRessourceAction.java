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
import compta.core.presentation.dialogs.operation.CreateRessourceDialog;

public class EditRessourceAction extends Action {

	private final TableViewer _tableViewer;

	public EditRessourceAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.EDIT_ICON;
	}

	@Override
	public String getText() {

		return "Editer ressource";
	}

	@Override
	public void run() {

		super.run();

		if (_tableViewer.getSelection() instanceof IStructuredSelection) {

			final IStructuredSelection iss = (IStructuredSelection) _tableViewer.getSelection();

			if ((iss.getFirstElement() != null) && (iss.getFirstElement() instanceof OperationIdentifier)) {

				final OperationIdentifier id = (OperationIdentifier) iss.getFirstElement();
				final CreateRessourceDialog diag = new CreateRessourceDialog();

				try {
					diag.setNom(TrimestreManager.getInstance().getOperationNom(id));
					diag.setMontant(TrimestreManager.getInstance().getOperationMontant(id));
					diag.setCompteId(TrimestreManager.getInstance().getOperationCompte(id));

					if (diag.open() == Window.OK) {

						if (!diag.getNom().trim().isEmpty() && (diag.getCompteId() != null)) {

							TrimestreManager.getInstance().editRessource(diag.getNom(), diag.getMontant(), diag.getCompteId(), id);
						}
					}
				} catch (ComptaException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
							"Impossible d'éditer la ressource");
				}

			}

		}

	}

}
