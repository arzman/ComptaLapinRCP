
package compta.core.presentation.contentprovider.template;

import org.eclipse.jface.viewers.ArrayContentProvider;

import compta.core.application.template.TrimestreTemplateElementFrequence;

public class ComboFrequenceCP extends ArrayContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {

		return TrimestreTemplateElementFrequence.values();
	}

}
