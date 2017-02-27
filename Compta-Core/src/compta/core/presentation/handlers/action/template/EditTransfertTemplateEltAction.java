package compta.core.presentation.handlers.action.template;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;

import compta.core.application.template.TrimestreTemplateElement;
import compta.core.common.ApplicationIcon;
import compta.core.domaine.operation.TransfertOperation;
import compta.core.presentation.dialogs.template.CreateTransfertTemplateEltDialog;

public class EditTransfertTemplateEltAction extends Action {

	private final TableViewer _tableViewer;

	public EditTransfertTemplateEltAction(final TableViewer tableViewer) {

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

			final Object obj = ((IStructuredSelection) _tableViewer.getSelection()).getFirstElement();

			if (obj instanceof TrimestreTemplateElement) {

				final CreateTransfertTemplateEltDialog diag = new CreateTransfertTemplateEltDialog();
				final TrimestreTemplateElement tempElt = (TrimestreTemplateElement) obj;

				diag.setFreq(tempElt.getFreq());
				diag.setOccurence(tempElt.getOccurence());

				diag.setNom(tempElt.getOperation().getNom());
				diag.setMontant(tempElt.getOperation().getMontant());
				diag.setCompteId(tempElt.getOperation().getCompteId());

				diag.setCompteCibleId(((TransfertOperation) tempElt.getOperation()).getCompteCibleId());

				if (diag.open() == Window.OK) {

					if (!diag.getNom().trim().isEmpty() && (diag.getCompteId() != null)) {

						tempElt.setFreq(diag.getFreq());
						tempElt.setOccurence(diag.getOccurence());

						tempElt.getOperation().setNom(diag.getNom());
						tempElt.getOperation().setMontant(diag.getMontant());
						tempElt.getOperation().setCompteId(diag.getCompteId());

						((TransfertOperation) tempElt.getOperation()).setCompteCibleId(diag.getCompteCibleId());

						_tableViewer.refresh();
					}
				}

			}

		}

	}

}
