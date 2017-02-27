package compta.core.presentation.contentprovider.stats;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.presentation.controllers.DepenseAnnuelleStatDialogController;

public class AnneeStatCP implements IStructuredContentProvider {

	
	
	private DepenseAnnuelleStatDialogController _controller;

	public AnneeStatCP(DepenseAnnuelleStatDialogController controller) {
		
		_controller = controller;
	}
	
	@Override
	public void dispose() {

		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		
	}

	@Override
	public Object[] getElements(Object inputElement) {

		return _controller.getAnnee().toArray();
	}

	

}
