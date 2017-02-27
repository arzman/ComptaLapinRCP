package compta.core.application.template;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import compta.core.domaine.operation.DepenseOperation;
import compta.core.domaine.operation.Operation;
import compta.core.domaine.operation.TransfertOperation;

public class TrimestreTemplate {

	/**
	 * Template des d�penses
	 */
	private final List<TrimestreTemplateElement> _depensesElement;

	/**
	 * Template des ressources
	 */
	private final List<TrimestreTemplateElement> _ressourcesElement;

	/**
	 * Template des transferts
	 */
	private final List<TrimestreTemplateElement> _transfertElement;

	public TrimestreTemplate() {

		_depensesElement = new ArrayList<TrimestreTemplateElement>();
		_ressourcesElement = new ArrayList<TrimestreTemplateElement>();
		_transfertElement = new ArrayList<TrimestreTemplateElement>();
	}

	/**
	 * Cr�e une liste d'op�ration hebdomadaire � partir d'�l�ment de template
	 * 
	 * @param eltList
	 * @param year
	 *            l'ann�e sur laquelle les op�rations sont cr��es
	 * @param numMonthInYeah
	 *            mois sur lequel les op�rations sont cr��s
	 * @return
	 */
	private List<Operation> createHebdoOperation(final List<TrimestreTemplateElement> eltList, final int year, final int numMonthInYeah) {

		final List<Operation> listToFill = new ArrayList<Operation>();

		for (final TrimestreTemplateElement elt : eltList) {

			if (elt.getFreq() == TrimestreTemplateElementFrequence.HEBDOMADAIRE) {

				// on se place au d�but du mois
				final Calendar cal = Calendar.getInstance();
				cal.set(year, numMonthInYeah, 1);

				// on compte le nombre de jour
				int compteur = 0;
				for (int i = 0; i < cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

					cal.roll(Calendar.DAY_OF_MONTH, 1);

					if (cal.get(Calendar.DAY_OF_WEEK) == elt.getOccurence()) {
						compteur++;
					}
				}

				for (int j = 0; j < compteur; j++) {

					try {
						listToFill.add((Operation) elt.getOperation().clone());
					} catch (final CloneNotSupportedException e) {

					}
				}

			}

		}

		return listToFill;

	}

	/**
	 * Cr�er une liste d'op�ration mensuelle � partir des �lements template
	 * pass� en param�tre.
	 * 
	 * @param depensesElement
	 * @return
	 */
	private List<Operation> createMensuelOperation(final List<TrimestreTemplateElement> eltList) {

		final List<Operation> listToFill = new ArrayList<Operation>();

		for (final TrimestreTemplateElement elt : eltList) {

			if (elt.getFreq() == TrimestreTemplateElementFrequence.MENSUEL) {

				try {
					listToFill.add((Operation) elt.getOperation().clone());
				} catch (final CloneNotSupportedException e) {

				}

			}

		}

		return listToFill;
	}

	/**
	 * 
	 * @param depensesElement
	 * @param numMoi
	 * @return
	 */
	private List<Operation> createTrimestrielOperation(final List<TrimestreTemplateElement> eltList, final int numMoi) {
		final List<Operation> listToFill = new ArrayList<Operation>();

		for (final TrimestreTemplateElement elt : eltList) {

			if (elt.getFreq() == TrimestreTemplateElementFrequence.TRIMESTRIEL) {

				if (elt.getOccurence() == numMoi) {
					try {
						listToFill.add((Operation) elt.getOperation().clone());
					} catch (final CloneNotSupportedException e) {

					}
				}
			}
		}

		return listToFill;
	}

	/**
	 * Retourne la liste des d�penses hebdomadaires.
	 * 
	 * @param numMonthInYeah
	 *            mois sur lequel les d�penses sont cr��es
	 * @param year
	 *            ann�e sur laquelle les d�penses sont cr��es
	 * @return
	 */
	public List<DepenseOperation> getDepenseHebdo(final int numMonthInYeah, final int year) {

		final ArrayList<DepenseOperation> res = new ArrayList<DepenseOperation>();

		final List<Operation> listOpe = createHebdoOperation(_depensesElement, year, numMonthInYeah);

		for (final Operation ope : listOpe) {
			if (ope instanceof DepenseOperation) {
				res.add((DepenseOperation) ope);
			}
		}

		return res;
	}

	/**
	 * Retourne les d�penses mensuelles.
	 * 
	 * @return
	 */
	public List<DepenseOperation> getDepenseMensuel() {

		final ArrayList<DepenseOperation> res = new ArrayList<DepenseOperation>();

		final List<Operation> listOpe = createMensuelOperation(_depensesElement);

		for (final Operation ope : listOpe) {
			if (ope instanceof DepenseOperation) {
				res.add((DepenseOperation) ope);
			}
		}

		return res;

	}

	/**
	 * @return the depensesElement
	 */
	public List<TrimestreTemplateElement> getDepensesElement() {
		return _depensesElement;
	}

	/**
	 * Retourne la liste des d�penses trimestrielles
	 * 
	 * @param numMoi
	 * @return
	 */
	public List<DepenseOperation> getDepenseTrimestriel(final int numMoi) {

		final ArrayList<DepenseOperation> res = new ArrayList<DepenseOperation>();

		final List<Operation> listOpe = createTrimestrielOperation(_depensesElement, numMoi);

		for (final Operation ope : listOpe) {
			if (ope instanceof DepenseOperation) {
				res.add((DepenseOperation) ope);
			}
		}

		return res;
	}

	/**
	 * Retourne la liste des ressources hebdomadaires.
	 * 
	 * @param numMonthInYeah
	 *            mois sur lequel les ressources sont cr��es
	 * @param year
	 *            ann�e sur laquelle les ressources sont cr��es
	 * @return
	 */
	public List<Operation> getRessourceHebdo(final int numMonthInYeah, final int year) {

		return createHebdoOperation(_ressourcesElement, year, numMonthInYeah);
	}

	/**
	 * Retourne la liste des ressources mensuelles.
	 * 
	 * @return
	 */
	public List<Operation> getRessourceMensuel() {
		final ArrayList<Operation> res = new ArrayList<Operation>();

		final List<Operation> listOpe = createMensuelOperation(_ressourcesElement);

		for (final Operation ope : listOpe) {

			res.add(ope);

		}

		return res;
	}

	/**
	 * @return the ressourcesElement
	 */
	public List<TrimestreTemplateElement> getRessourcesElement() {
		return _ressourcesElement;
	}

	/**
	 * Retourne la liste des ressources trimestrielles
	 * 
	 * @param numMoi
	 * @return
	 */
	public List<Operation> getRessourceTrimestriel(final int numMoi) {
		final ArrayList<Operation> res = new ArrayList<Operation>();

		final List<Operation> listOpe = createTrimestrielOperation(_ressourcesElement, numMoi);

		for (final Operation ope : listOpe) {

			res.add(ope);

		}

		return res;
	}

	/**
	 * @return the transfertElement
	 */
	public List<TrimestreTemplateElement> getTransfertElement() {
		return _transfertElement;
	}

	/**
	 * Retourne la liste des transferts hebdomadaires.
	 * 
	 * @param numMonthInYeah
	 *            mois sur lequel les transferts sont cr��s
	 * @param year
	 *            ann�e sur laquelle les transferts sont cr��s
	 * @return
	 */
	public List<TransfertOperation> getTransfertHebdo(final int numMonthInYeah, final int year) {

		final ArrayList<TransfertOperation> res = new ArrayList<TransfertOperation>();

		final List<Operation> listOpe = createHebdoOperation(_transfertElement, year, numMonthInYeah);

		for (final Operation ope : listOpe) {
			if (ope instanceof TransfertOperation) {
				res.add((TransfertOperation) ope);
			}
		}

		return res;

	}

	/**
	 * Retourne la liste des transferts mensuel.
	 * 
	 * @return
	 */
	public List<TransfertOperation> getTransfertMensuel() {
		final ArrayList<TransfertOperation> res = new ArrayList<TransfertOperation>();

		final List<Operation> listOpe = createMensuelOperation(_transfertElement);

		for (final Operation ope : listOpe) {
			if (ope instanceof TransfertOperation) {
				res.add((TransfertOperation) ope);
			}
		}

		return res;
	}

	/**
	 * Retourne la liste des transfert trimestrielles
	 * 
	 * @param numMoi
	 * @return
	 */
	public List<TransfertOperation> getTransfertTrimestriel(final int numMoi) {
		final ArrayList<TransfertOperation> res = new ArrayList<TransfertOperation>();

		final List<Operation> listOpe = createTrimestrielOperation(_transfertElement, numMoi);

		for (final Operation ope : listOpe) {
			if (ope instanceof TransfertOperation) {
				res.add((TransfertOperation) ope);
			}
		}

		return res;
	}

}
