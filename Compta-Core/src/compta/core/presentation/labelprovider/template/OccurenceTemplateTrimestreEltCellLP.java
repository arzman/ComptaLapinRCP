
package compta.core.presentation.labelprovider.template;

import java.util.Calendar;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.template.TrimestreTemplateElement;
import compta.core.application.template.TrimestreTemplateElementFrequence;

public class OccurenceTemplateTrimestreEltCellLP extends CellLabelProvider {

	@Override
	public void update(final ViewerCell cell) {

		String txt = "ERROR";

		if (cell.getElement() instanceof TrimestreTemplateElement) {

			if (((TrimestreTemplateElement) cell.getElement()).getFreq() == TrimestreTemplateElementFrequence.HEBDOMADAIRE) {

				switch (((TrimestreTemplateElement) cell.getElement()).getOccurence()) {

				case Calendar.MONDAY:
					txt = "Lundi";
					break;
				case Calendar.TUESDAY:
					txt = "Mardi";
					break;
				case Calendar.WEDNESDAY:
					txt = "Mercredi";
					break;
				case Calendar.THURSDAY:
					txt = "Jeudi";
					break;
				case Calendar.FRIDAY:
					txt = "Vendredi";
					break;
				case Calendar.SATURDAY:
					txt = "Samedi";
					break;
				case Calendar.SUNDAY:
					txt = "Dimanche";
					break;
				default:
					txt = "ERROR";
					break;

				}

			}

			if (((TrimestreTemplateElement) cell.getElement()).getFreq() == TrimestreTemplateElementFrequence.MENSUEL) {
				txt = "MENSUEL";

			}

			if (((TrimestreTemplateElement) cell.getElement()).getFreq() == TrimestreTemplateElementFrequence.TRIMESTRIEL) {
				switch (((TrimestreTemplateElement) cell.getElement()).getOccurence()) {

				case 0:
					txt = "1er Mois";
					break;
				case 1:
					txt = "2eme Mois";
					break;
				case 2:
					txt = "3eme Mois";
					break;
				default:
					txt = "ERROR";
					break;

				}
			}
		}

		cell.setText(txt);
	}

}
