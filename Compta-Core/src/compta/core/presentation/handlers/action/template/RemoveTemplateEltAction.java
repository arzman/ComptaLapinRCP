
package compta.core.presentation.handlers.action.template;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import compta.core.application.manager.TrimestreManager;
import compta.core.application.template.TrimestreTemplateElement;
import compta.core.common.ApplicationIcon;

public class RemoveTemplateEltAction extends Action {

	private final TableViewer _tableViewer;

	public RemoveTemplateEltAction(final TableViewer tableViewer) {

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

			if ((obj != null) && (obj instanceof TrimestreTemplateElement)) {

				res = true;
			}

		}

		return res;
	}

	@Override
	public void run() {

		if (isActivate()) {

			super.run();
			final Object obj = ((StructuredSelection) _tableViewer.getSelection()).getFirstElement();
			TrimestreManager.getInstance().removeTrimestreTemplateElt((TrimestreTemplateElement) obj);
			_tableViewer.refresh();
		}

	}

}
