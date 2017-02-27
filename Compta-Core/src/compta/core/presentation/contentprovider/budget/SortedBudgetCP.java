package compta.core.presentation.contentprovider.budget;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.application.identifier.Identifier;

public class SortedBudgetCP implements IStructuredContentProvider {

	private final List<Identifier> _list;

	public SortedBudgetCP(List<Identifier> list) {

		_list = list;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(final Object inputElement_) {

		return _list.toArray();
	}

	@Override
	public void inputChanged(final Viewer viewer_, final Object oldInput_, final Object newInput_) {
		// TODO Auto-generated method stub

	}

}
