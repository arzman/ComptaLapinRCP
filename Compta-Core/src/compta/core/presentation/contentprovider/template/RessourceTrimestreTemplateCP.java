package compta.core.presentation.contentprovider.template;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.application.manager.TrimestreManager;

public class RessourceTrimestreTemplateCP implements IStructuredContentProvider {

	public RessourceTrimestreTemplateCP() {

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(final Object inputElement_) {

		return TrimestreManager.getInstance().getTrimestreTemplate().getRessourcesElement().toArray();
	}

	@Override
	public void inputChanged(final Viewer viewer_, final Object oldInput_, final Object newInput_) {
		// TODO Auto-generated method stub

	}

}
