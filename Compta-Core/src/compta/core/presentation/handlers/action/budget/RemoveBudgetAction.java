
package compta.core.presentation.handlers.action.budget;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.BudgetManager;
import compta.core.common.ApplicationIcon;

public class RemoveBudgetAction extends Action {

	private final TableViewer _tableViewer;

	public RemoveBudgetAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.REMOVE_ICON;
	}

	@Override
	public String getText() {

		return "Supprimer le budget";
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

			BudgetManager.getInstance().removeBudget((Identifier) obj);
		}

	}

}
