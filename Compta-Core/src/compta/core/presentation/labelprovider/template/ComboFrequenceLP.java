package compta.core.presentation.labelprovider.template;

import org.eclipse.jface.viewers.LabelProvider;

import compta.core.application.template.TrimestreTemplateElementFrequence;

public class ComboFrequenceLP extends LabelProvider {

	@Override
	public String getText(Object element) {

		String txt = "ERROR";

		if (element instanceof TrimestreTemplateElementFrequence) {

			if (((TrimestreTemplateElementFrequence) element) == TrimestreTemplateElementFrequence.HEBDOMADAIRE) {
				txt = "HEBDOMADAIRE";
			}

			if (((TrimestreTemplateElementFrequence) element) == TrimestreTemplateElementFrequence.MENSUEL) {
				txt = "MENSUEL";
			}

			if (((TrimestreTemplateElementFrequence) element) == TrimestreTemplateElementFrequence.TRIMESTRIEL) {
				txt = "TRIMESTRIEL";
			}
		}

		return txt;
	}

}
