package compta.core.presentation.labelprovider.operation;

import org.eclipse.jface.viewers.LabelProvider;

public class CategorieLP extends LabelProvider {


	
	@Override
	public String getText(Object element) {
		
		
		String res = "ERROR";
		
		if(element instanceof String){
			res = (String) element;
		}
		
		return res;
	}
	
}
