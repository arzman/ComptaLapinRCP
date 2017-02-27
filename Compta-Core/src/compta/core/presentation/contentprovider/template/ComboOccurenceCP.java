
package compta.core.presentation.contentprovider.template;

import java.util.Calendar;

import org.eclipse.jface.viewers.ArrayContentProvider;

import compta.core.application.template.TrimestreTemplateElementFrequence;

public class ComboOccurenceCP extends ArrayContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {

		Object[] res = new Object[0];

		if (inputElement instanceof TrimestreTemplateElementFrequence) {

			if (((TrimestreTemplateElementFrequence) inputElement) == TrimestreTemplateElementFrequence.TRIMESTRIEL) {

				res = new Object[] { 0, 1, 2 };

			}

			if (((TrimestreTemplateElementFrequence) inputElement) == TrimestreTemplateElementFrequence.HEBDOMADAIRE) {

				res = new Object[] { Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY,
						Calendar.SATURDAY, Calendar.SUNDAY };
			}

		}

		return res;
	}

}
