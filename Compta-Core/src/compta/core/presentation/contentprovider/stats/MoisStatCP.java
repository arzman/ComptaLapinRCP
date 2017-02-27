package compta.core.presentation.contentprovider.stats;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.presentation.controllers.DepenseMensuelleStatDialogController;

public class MoisStatCP implements IStructuredContentProvider {

	
	
	private DepenseMensuelleStatDialogController _controller;

	public MoisStatCP(DepenseMensuelleStatDialogController controller) {
		
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

		return _controller.getMois().toArray();
	}

	

}
