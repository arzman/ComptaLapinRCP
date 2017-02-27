package compta.core.presentation.labelprovider.stat;

import java.util.Calendar;

import org.eclipse.jface.viewers.LabelProvider;

import compta.core.common.ApplicationFormatter;

public class MoisLP extends LabelProvider {

	
	@Override
	public String getText(Object element) {
		
		
		String res = "ERROR";
		
		if(element instanceof Calendar){
	
			res = ApplicationFormatter.moiAnneedateFormat.format(((Calendar)element).getTime());

		}
		
		
		return res;
	}

}
