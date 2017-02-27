package compta.core.presentation.contentprovider.synth;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import compta.core.presentation.controllers.DepenseAnnuelleSynthDialogController;

public class AnneeSynthCP implements IStructuredContentProvider {

	private DepenseAnnuelleSynthDialogController _controler;

	public AnneeSynthCP(DepenseAnnuelleSynthDialogController controler) {
		_controler = controler;
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
		return _controler.getAnneeSet().toArray();
	}

	
}
