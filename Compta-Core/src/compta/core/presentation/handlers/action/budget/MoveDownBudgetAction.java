package compta.core.presentation.handlers.action.budget;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import compta.core.application.identifier.Identifier;
import compta.core.common.ApplicationIcon;

public class MoveDownBudgetAction extends Action {

	private final TableViewer _tableViewer;

	private final List<Identifier> _list;

	public MoveDownBudgetAction(final TableViewer tableViewer, final List<Identifier> list) {

		_tableViewer = tableViewer;
		_list = list;

	}

	@Override
	public ImageDescriptor getImageDescriptor() {

		return ApplicationIcon.DOWN_ICON;
	}

	@Override
	public String getText() {

		return "Descendre";
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
			_list.add(idToMove);

			_tableViewer.refresh();

		}

	}

}
