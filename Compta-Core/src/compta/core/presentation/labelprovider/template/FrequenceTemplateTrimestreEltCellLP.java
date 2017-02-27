
package compta.core.presentation.labelprovider.template;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.template.TrimestreTemplateElement;
import compta.core.application.template.TrimestreTemplateElementFrequence;

public class FrequenceTemplateTrimestreEltCellLP extends CellLabelProvider {

	@Override
	public void update(ViewerCell cell) {

		String txt = "ERROR";

		if (cell.getElement() instanceof TrimestreTemplateElement) {

			if (((TrimestreTemplateElement) cell.getElement()).getFreq() == TrimestreTemplateElementFrequence.HEBDOMADAIRE) {
				txt = "HEBDOMADAIRE";
			}

			if (((TrimestreTemplateElement) cell.getElement()).getFreq() == TrimestreTemplateElementFrequence.MENSUEL) {
				txt = "MENSUEL";
			}

			if (((TrimestreTemplateElement) cell.getElement()).getFreq() == TrimestreTemplateElementFrequence.TRIMESTRIEL) {
				txt = "TRIMESTRIEL";
			}
		}

		cell.setText(txt);
	}

}
