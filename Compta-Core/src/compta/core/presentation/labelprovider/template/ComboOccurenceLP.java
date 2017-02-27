package compta.core.presentation.labelprovider.template;

import java.util.Calendar;

import org.eclipse.jface.viewers.LabelProvider;

import compta.core.application.template.TrimestreTemplateElementFrequence;

public class ComboOccurenceLP extends LabelProvider {

	private TrimestreTemplateElementFrequence _freq;

	public ComboOccurenceLP(TrimestreTemplateElementFrequence freq) {

		_freq = freq;
	}

	/**
	 * @return the freq
	 */
	public TrimestreTemplateElementFrequence getFreq() {
		return _freq;
	}

	@Override
	public String getText(Object element) {

		String txt = "Rien";

		if (element instanceof Integer) {

			final int entier = (Integer) element;

			if (_freq == TrimestreTemplateElementFrequence.HEBDOMADAIRE) {

				switch (entier) {

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

			if (_freq == TrimestreTemplateElementFrequence.TRIMESTRIEL) {

				switch (entier) {

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

		return txt;
	}

	/**
	 * @param freq
	 *            the freq to set
	 */
	public void setFreq(TrimestreTemplateElementFrequence freq) {
		_freq = freq;
	}
}
