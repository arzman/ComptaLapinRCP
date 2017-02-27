package compta.core.presentation.contentprovider.budget;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.application.manager.BudgetManager;

public class BudgetCP implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(final Object inputElement_) {

		return BudgetManager.getInstance().getAllBudgetIdentifier();
	}

	@Override
	public void inputChanged(final Viewer viewer_, final Object oldInput_, final Object newInput_) {
		// TODO Auto-generated method stub

	}

}
