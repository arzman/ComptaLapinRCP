package compta.core.presentation.contentprovider.stats;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.presentation.controllers.DepenseStatDialogController;

public class CategorieAnneeStatCP implements IStructuredContentProvider {

	
	private DepenseStatDialogController _controller;

	public CategorieAnneeStatCP(DepenseStatDialogController controller) {
		
		_controller = controller;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return _controller.getCategorieData().toArray();
	}


}
