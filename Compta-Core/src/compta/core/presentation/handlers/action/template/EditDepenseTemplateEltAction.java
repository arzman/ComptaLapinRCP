package compta.core.presentation.handlers.action.template;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;

import compta.core.application.template.TrimestreTemplateElement;
import compta.core.common.ApplicationIcon;
import compta.core.domaine.operation.DepenseOperation;
import compta.core.presentation.dialogs.template.CreateDepenseTemplateEltDialog;

public class EditDepenseTemplateEltAction extends Action {

	private final TableViewer _tableViewer;

	public EditDepenseTemplateEltAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.EDIT_ICON;
	}

	@Override
	public String getText() {

		return "Editer d�pense";
	}

	@Override
	public void run() {

		super.run();

		if (_tableViewer.getSelection() instanceof IStructuredSelection) {

			final Object obj = ((IStructuredSelection) _tableViewer.getSelection()).getFirstElement();

			if (obj instanceof TrimestreTemplateElement) {

				final CreateDepenseTemplateEltDialog diag = new CreateDepenseTemplateEltDialog();
				final TrimestreTemplateElement tempElt = (TrimestreTemplateElement) obj;

				diag.setFreq(tempElt.getFreq());
				diag.setOccurence(tempElt.getOccurence());

				diag.setNom(tempElt.getOperation().getNom());
				diag.setMontant(tempElt.getOperation().getMontant());
				diag.setCompteId(tempElt.getOperation().getCompteId());
				diag.setCategorie(((DepenseOperation) tempElt.getOperation()).getCategorie());

				if (diag.open() == Window.OK) {

					if (!diag.getNom().trim().isEmpty() && (diag.getCompteId() != null)) {

						tempElt.setFreq(diag.getFreq());
						tempElt.setOccurence(diag.getOccurence());
						tempElt.getOperation().setNom(diag.getNom());
						tempElt.getOperation().setMontant(diag.getMontant());
						tempElt.getOperation().setCompteId(diag.getCompteId());
						((DepenseOperation) tempElt.getOperation()).setCategorie(diag.getCategorie());

						_tableViewer.refresh();
					}
				}

			}

		}

	}

}
