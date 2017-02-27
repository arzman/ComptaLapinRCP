package compta.core.presentation.handlers.action.budget;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import compta.core.application.identifier.Identifier;
import compta.core.common.ApplicationIcon;

public class MoveUpBudgetAction extends Action {

	private final TableViewer _tableViewer;

	private final List<Identifier> _list;

	public MoveUpBudgetAction(final TableViewer tableViewer, final List<Identifier> list) {

		_tableViewer = tableViewer;
		_list = list;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.UP_ICON;
	}

	@Override
	public String getText() {

		return "Monter";
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

			final int index = _list.indexOf(obj);
			final Identifier idToMove = _list.get(index);
			_list.remove(index);

			if ((index - 1) > -1) {
				_list.add(index - 1, idToMove);
			}

			_tableViewer.refresh();

		}

	}

}
