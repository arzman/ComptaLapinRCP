package compta.core.presentation.handlers.action.budget;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.BudgetManager;
import compta.core.common.ApplicationIcon;
import compta.core.common.ComptaException;
import compta.core.presentation.dialogs.budget.UtiliseBudgetDialog;

/**
 * 
 * Action "Utiliser budget"
 * @author Arthur
 *
 */
public class AddUtiliseInBudgetAction extends Action {

	/**
	 * Le viewer des budget
	 */
	private final TableViewer _tableViewer;

	public AddUtiliseInBudgetAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.USE_BUDGET_ICON;
	}

	@Override
	public String getText() {

		return "Utiliser";
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

			final UtiliseBudgetDialog diag = new UtiliseBudgetDialog();

			if (diag.open() == Window.OK) {
				try {
					BudgetManager.getInstance().addUtiliseInBudget((Identifier) obj,diag.getMontant(),diag.getLibelle(),diag.getDate());
				} catch (final ComptaException e) {
					MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
							"Impossible d'éditer le budget :" + ((Identifier) obj).getIdentifier());
				}
			}

		} else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
					"Impossible d'éditer le budget : L'élément sélectionné n'est pas un Identifier");
		}

	}
}
