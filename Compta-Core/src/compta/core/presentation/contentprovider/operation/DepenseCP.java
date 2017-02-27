package compta.core.presentation.contentprovider.operation;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.application.manager.TrimestreManager;

public class DepenseCP implements IStructuredContentProvider {

	private final int _numMoi;

	public DepenseCP(final int num) {
		_numMoi = num;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(final Object inputElement_) {

		return TrimestreManager.getInstance().getDepenseForExerciceCourant(_numMoi).toArray();
	}

	@Override
	public void inputChanged(final Viewer viewer_, final Object oldInput_, final Object newInput_) {
		// TODO Auto-generated method stub

	}

}
