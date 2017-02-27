
package compta.core.presentation.handlers.action.compte;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CompteManager;
import compta.core.common.ApplicationIcon;

public class RemoveCompteAction extends Action {

	private final TableViewer _tableViewer;

	public RemoveCompteAction(final TableViewer tableViewer) {

		_tableViewer = tableViewer;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.REMOVE_ICON;
	}

	@Override
	public String getText() {

		return "Supprimer le compte";
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

			CompteManager.getInstance().removeCompte((Identifier) obj);
		}

	}

}
