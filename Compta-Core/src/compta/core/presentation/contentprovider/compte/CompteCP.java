
package compta.core.presentation.contentprovider.compte;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.application.manager.CompteManager;

public class CompteCP implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(final Object inputElement) {

		return CompteManager.getInstance().getCompteIdentifierAsArray();
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		// TODO Auto-generated method stub

	}

}
