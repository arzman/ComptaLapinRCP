
package compta.core.presentation.contentprovider.operation;

import org.eclipse.jface.viewers.ArrayContentProvider;

import compta.core.application.manager.CompteManager;

public class ComboCategorieCP extends ArrayContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {

		return CompteManager.getInstance().getCompteIdentifierAsArray();
	}

}
