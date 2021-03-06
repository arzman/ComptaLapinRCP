
package compta.core.presentation.contentprovider.operation;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.application.manager.TrimestreManager;

public class TransfertCP implements IStructuredContentProvider {

	private final int _numMoi;

	public TransfertCP(final int numMoi) {
		_numMoi = numMoi;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(final Object inputElement_) {

		return TrimestreManager.getInstance().getTransfertForExerciceCourant(_numMoi).toArray();
	}

	@Override
	public void inputChanged(final Viewer viewer_, final Object oldInput_, final Object newInput_) {
		// TODO Auto-generated method stub

	}

}
