package compta.core.presentation.handlers.action.budget;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.common.ApplicationIcon;
import compta.core.presentation.dialogs.budget.VoirBudgetDialog;

/**
 *
 * Action "Voir les utilisations"
 *
 * @author Arthur
 *
 */
public class VoirUtilisationBudgetAction extends Action {

	/**
	 * Le viewer des budget
	 */
	private final TableViewer _tableViewer;

	public VoirUtilisationBudgetAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.SHOW_BUDGET_ICON;
	}

	@Override
	public String getText() {

		return "Voir";
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

			final VoirBudgetDialog diag = new VoirBudgetDialog((Identifier)obj);

			diag.open() ;
			

		} else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
					"Impossible d'éditer le budget : L'élément sélectionné n'est pas un Identifier");
		}

	}

}
