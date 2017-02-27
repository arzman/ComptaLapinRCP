package compta.core.presentation.contentprovider.trimestre;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.application.manager.TrimestreManager;

public class TrimestreCP implements IStructuredContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public Object[] getElements(final Object inputElement_) {

		return TrimestreManager.getInstance().getTrimestreIdentifierSet().toArray();
	}

	@Override
	public void inputChanged(final Viewer viewer_, final Object oldInput_, final Object newInput_) {

	}

}
