package compta.core.presentation.labelprovider.stat;

import java.util.Calendar;

import org.eclipse.jface.viewers.LabelProvider;

public class AnneeLP extends LabelProvider {

	@Override
	public String getText(Object element) {

		String res = "ERROR";

		if (element instanceof Calendar) {

			res = "Année " + ((Calendar) element).get(Calendar.YEAR);

		}

		return res;
	}

}
