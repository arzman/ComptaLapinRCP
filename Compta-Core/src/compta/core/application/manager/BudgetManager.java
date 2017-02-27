/**
 * 
 */
package compta.core.application.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import compta.core.Activator;
import compta.core.application.event.BudgetEvent;
import compta.core.application.event.BudgetEventType;
import compta.core.application.identifier.Identifier;
import compta.core.application.identifier.UtilisationIdentifier;
import compta.core.application.listener.BudgetListener;
import compta.core.common.ComptaException;
import compta.core.domaine.Budget;
import compta.core.domaine.Utilisation;

/**
 * 
 * Gestionnaire des {@link Budget}. Toutes les opérations sur les {@link Budget}
 * gérés par l'application passe par ce singleton.
 * 
 * @author Arthur
 * 
 */
public class BudgetManager {

	/**
	 * Les budgets gérés par l'application.
	 */
	private Map<Identifier, Budget> _budgetMap;

	private final ArrayList<BudgetListener> _budgetListeners;

	private final HashMap<Identifier, Double[]> _dataBudget;

	private ArrayList<Identifier> _sortedBudget;

	/**
	 * L'instance du singleton
	 */
	private static BudgetManager _instance;

	/**
	 * Retourne l'instance du singleton.
	 * 
	 * @return l'instance
	 */
	public static BudgetManager getInstance() {

		if (_instance == null) {
			_instance = new BudgetManager();
		}
		return _instance;

	}

	/**
	 * Constructeur par défaut.
	 */
	private BudgetManager() {

		_budgetListeners = new ArrayList<BudgetListener>();
		_dataBudget = new HashMap<Identifier, Double[]>();

		calculateData();

	}

	/**
	 * Rajoute un listener pour les actions sur les budgets
	 * 
	 * @param listener
	 */
	public void addBudgetListener(final BudgetListener listener) {

		_budgetListeners.add(listener);

	}

	public void calculateData() {

		double dispoCC = 0;
		double dispoCL = 0;
		
		_dataBudget.clear();

		for (final Identifier compteId : CompteManager.getInstance().getCompteIdentifierSet()) {

			try {

				if (CompteManager.getInstance().getCompte(compteId).isBudgetAllowed()) {
					if (CompteManager.getInstance().getCompteIsLivret(compteId)) {
						dispoCL = dispoCL + CompteManager.getInstance().getSoldePrevCompte(compteId, 3);
					} else {
						dispoCC = dispoCC + CompteManager.getInstance().getSoldePrevCompte(compteId, 3);
					}
				}
			} catch (final ComptaException e) {

			}

		}

		for (final Identifier budgetId : getSortedBudget()) {

			double cour = 0;
			double liv = 0;

			if (!_dataBudget.containsKey(budgetId)) {
				_dataBudget.put(budgetId, new Double[] { Double.valueOf(0), Double.valueOf(0), Double.valueOf(0) });
			}

			final Double[] datas = _dataBudget.get(budgetId);

			try {
				final Budget budget = getBudget(budgetId);

				double inBudget = budget.getMontantUtilise();

				if ((dispoCL - (budget.getObjectif() - inBudget)) >= 0) {
					dispoCL = dispoCL - (budget.getObjectif() - inBudget);
					if ((budget.getObjectif() - inBudget) >= 0) {
						liv = budget.getObjectif() - inBudget;
					} else {
						liv = 0.0;
					}
					inBudget = inBudget + liv;
				} else {

					liv = dispoCL;
					dispoCL = 0;
					inBudget = inBudget + liv;

					if ((dispoCC - (budget.getObjectif() - inBudget)) >= 0) {
						dispoCC = dispoCC - (budget.getObjectif() - inBudget);
						if ((budget.getObjectif() - inBudget) >= 0) {
							cour = budget.getObjectif() - inBudget;
						} else {
							cour = 0;
						}
						inBudget = inBudget + cour;
					} else {

						cour = dispoCC;
						dispoCC = 0;
						inBudget = inBudget + cour;
					}

				}

				datas[0] = inBudget / budget.getObjectif();
				datas[1] = cour;
				datas[2] = liv;

				_dataBudget.put(budgetId, datas);
			} catch (final ComptaException e) {

			}

		}

	}

	/**
	 * Crée et ajoute un budget dans l'application
	 * 
	 * @param budgetName
	 * @param objectif
	 * @param utilise
	 */
	public void createBudget(final String budgetName, final double objectif, final double utilise) {

		final Identifier id = new Identifier();
		final Budget bud = new Budget();
		bud.setNom(budgetName);
		bud.setObjectif(objectif);
		bud.setMontantUtilise(utilise);
		
		getBudgetMap().put(id, bud);
		getSortedBudget().add(id);
		
		calculateData();
		fireEvent(new BudgetEvent(BudgetEventType.AJOUT, id));

	}

	/**
	 * Edite le budget
	 * 
	 * @param budget
	 * @param nom
	 * @param objectif
	 * @param utilise
	 * @throws ComptaException
	 */
	public void editBudget(final Identifier budget, final String nom, final double objectif, final double utilise) throws ComptaException {

		getBudget(budget).setNom(nom);
		getBudget(budget).setObjectif(objectif);
		getBudget(budget).setMontantUtilise(utilise);
		calculateData();
		fireEvent(new BudgetEvent(BudgetEventType.EDITION, budget));

	}

	/**
	 * Envoie un evenement aux listeners
	 * 
	 * @param event
	 */
	private void fireEvent(final BudgetEvent event) {

		for (final BudgetListener listener : _budgetListeners) {
			listener.processBudgetEvent(event);
		}

	}

	public Object[] getAllBudgetIdentifier() {

		return getBudgetMap().keySet().toArray();
	}

	public double getAvancement(Identifier element) throws ComptaException {
		return getDataBudget(element)[0];
	}

	/**
	 * Recupere le budget
	 * 
	 * @param identifier
	 * @return
	 * @throws ComptaException
	 */
	public Budget getBudget(final Identifier identifier) throws ComptaException {

		final Budget bdg = getBudgetMap().get(identifier);

		if (bdg == null) {
			throw new ComptaException("Le budget ayant pour id " + identifier.getIdentifier() + " n'existe pas", null);
		}

		return bdg;
	}

	public Map<Identifier, Budget> getBudgetMap() {

		if (_budgetMap == null) {

			try {
				_budgetMap = DataManager.getInstance().loadBudgetMap();
				calculateData();
			} catch (final ComptaException e) {
				_budgetMap = new HashMap<Identifier, Budget>();
			}

		}

		if (_budgetMap == null) {
			_budgetMap = new HashMap<Identifier, Budget>();
		}

		return _budgetMap;
	}

	private Double[] getDataBudget(Identifier element) throws ComptaException {

		final Double[] res = _dataBudget.get(element);

		if (res == null) {
			throw new ComptaException("Les données de comptes sont nulles", null);
		}

		return res;
	}

	public double getMontantCourant(Identifier element) throws ComptaException {
		return getDataBudget(element)[1];
	}

	public double getMontantLivret(Identifier element) throws ComptaException {
		return getDataBudget(element)[2];
	}

	public String getNomBudget(Identifier identifier) throws ComptaException {

		return getBudget(identifier).getNom();
	}

	public double getObjectifBudget(Identifier budgetId) throws ComptaException {

		return getBudget(budgetId).getObjectif();
	}

	public ArrayList<Identifier> getSortedBudget() {

		if (_sortedBudget == null) {

			try {
				_sortedBudget = DataManager.getInstance().loadSortedBudgetList();
			} catch (final ComptaException e) {
				_sortedBudget = new ArrayList<Identifier>();
			}

			if (_sortedBudget == null) {
				_sortedBudget = new ArrayList<Identifier>();
			}

			if (_sortedBudget.isEmpty()) {
				_sortedBudget.addAll(getBudgetMap().keySet());
			}

		}

		return _sortedBudget;
	}

	public double getUtiliseBudget(Identifier budgetId) throws ComptaException {
		return getBudget(budgetId).getMontantUtilise();
	}

	/**
	 * Supprime le budget
	 * 
	 * @param budgetId
	 */
	public void removeBudget(final Identifier budgetId) {

		getBudgetMap().remove(budgetId);
		getSortedBudget().remove(budgetId);
		calculateData();
		fireEvent(new BudgetEvent(BudgetEventType.SUPRESSION, budgetId));

	}

	/**
	 * Enlever un listener pour les actions sur les budgets
	 * 
	 * @param listener
	 */
	public void removeCompteListener(final BudgetListener listener) {

		_budgetListeners.remove(listener);
	}

	/**
	 * Sauvegarde toutes les données relatives aux comptes
	 */
	public void saveAllBudget() {

		try {
			DataManager.getInstance().saveBudgetMap(getBudgetMap());
		} catch (final ComptaException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e.getCause()));
		}

		try {
			DataManager.getInstance().saveBudgetTriList(getSortedBudget());
		} catch (final ComptaException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e.getCause()));
		}

	}

	/**
	 * Modifie la liste des budgets triés
	 * @param list
	 */
	public void setBudgetTrie(ArrayList<Identifier> list) {
		_sortedBudget = list;
		calculateData();
		fireEvent(new BudgetEvent(BudgetEventType.SORTED, null));

	}

	/**
	 * Ajoute la somme entrée en paramètre au montant utilisé du budget
	 * @param idBudget
	 * @param montant le montant
	 * @param libelle le libelle de la depense
	 * @param 
	 * @param parseDouble
	 * @throws ComptaException
	 */
	public void addUtiliseInBudget(Identifier idBudget, double montant,String libelle,Calendar date) throws ComptaException {
		
		Budget bud = getBudget(idBudget);
		bud.setMontantUtilise(bud.getMontantUtilise() + montant);
		
		Utilisation util = new Utilisation(montant, libelle, date);
		bud.ajouteUtilisation(util);
		calculateData();
		fireEvent(new BudgetEvent(BudgetEventType.EDITION,idBudget));
		
	}
	
	public Calendar getDateUtilisation(UtilisationIdentifier idUtilisation) throws ComptaException{
		return getBudget(idUtilisation.getIdBudget()).getUtilisation().get(idUtilisation.getNum()).getDate();
	}
	

	public void reCalculateData() {
		
		calculateData();
		fireEvent(new BudgetEvent(BudgetEventType.AJOUT, null));
		
	}

}
