
package compta.core.presentation.handlers.action.template;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;

import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationIcon;
import compta.core.presentation.dialogs.template.CreateDepenseTemplateEltDialog;

public class CreateDepenseTemplateEltAction extends Action {

	private final TableViewer _tableViewer;

	public CreateDepenseTemplateEltAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

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

		final CreateDepenseTemplateEltDialog diag = new CreateDepenseTemplateEltDialog();

		if (diag.open() == Window.OK) {

			if (!diag.getNom().trim().isEmpty() && (diag.getCompteId() != null)) {

				TrimestreManager.getInstance().createDepenseTemplateElt(diag.getNom(), diag.getMontant(), diag.getCategorie(),
						diag.getCompteId(), diag.getFreq(), diag.getOccurence());
				_tableViewer.refresh();
			}
		}

	}

}
