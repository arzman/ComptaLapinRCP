
package compta.core.application.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;

import compta.core.Activator;
import compta.core.application.event.CompteEvent;
import compta.core.application.event.CompteEventType;
import compta.core.application.identifier.Identifier;
import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.listener.CompteListener;
import compta.core.common.ComptaException;
import compta.core.domaine.Compte;
import compta.core.domaine.operation.DepenseOperation;
import compta.core.domaine.operation.EtatOperation;
import compta.core.domaine.operation.Operation;
import compta.core.domaine.operation.OperationType;
import compta.core.domaine.operation.TransfertOperation;

public class CompteManager {

	private static CompteManager _instance;
	
	private HashMap<Identifier, Compte> _compteMap;

	private final ArrayList<CompteListener> _compteListeners;

	/**
	 * Retourne l'instance du singleton
	 * 
	 * @return
	 */
	public static CompteManager getInstance() {

		if (_instance == null) {
			_instance = new CompteManager();
		}

		return _instance;
	}


	/**
	 * Constructeur du manager
	 */
	private CompteManager() {

		// récupération des comptes
		loadMap();
		_compteListeners = new ArrayList<CompteListener>();

	}

	/**
	 * Rajoute un listener pour les actions sur les comptes
	 * 
	 * @param listener
	 */
	public void addCompteListener(final CompteListener listener) {

		_compteListeners.add(listener);

	}

	public void ajouterUnCompte(String nom, double solde, boolean isLivret, boolean budgetAllowed) {

		final Compte compte = new Compte(nom);
		compte.setBudgetAllowed(budgetAllowed);
		compte.setLivret(isLivret);
		compte.setSolde(solde);

		final Identifier id = new Identifier();

		getCompteMap().put(id, compte);
		fireEvent(new CompteEvent(CompteEventType.AJOUT, id));

	}

	/**
	 * Edite le compte
	 * 
	 * @param compteId
	 * @param nom
	 * @param solde
	 * @throws ComptaException
	 */
	public void editCompte(final Identifier compteId, final String nom, final double solde, final boolean isLivret,
			final boolean budgetAllowed) throws ComptaException {

		getCompte(compteId).setNom(nom);
		getCompte(compteId).setSolde(solde);
		getCompte(compteId).setLivret(isLivret);
		getCompte(compteId).setBudgetAllowed(budgetAllowed);
		fireEvent(new CompteEvent(CompteEventType.EDITION, compteId));

	}

	/**
	 * Envoie un evenement aux listeners
	 * 
	 * @param event
	 */
	private void fireEvent(final CompteEvent event) {

		for (final CompteListener listener : _compteListeners) {
			listener.processCompteEvent(event);
		}

	}

	/**
	 * Recupere le compte
	 * 
	 * @param identifier
	 * @return
	 * @throws ComptaException
	 */
	public Compte getCompte(final Identifier identifier) throws ComptaException {

		final Compte cpt = getCompteMap().get(identifier);

		if (cpt == null) {
			throw new ComptaException("Le compte ayant pour id " + identifier.getIdentifier() + " n'existe pas", null);
		}

		return cpt;
	}

	/**
	 * Retourne un tableau des identifiants des comptes. Utile pour les
	 * {@link IStructuredContentProvider} des {@link TableViewer}.
	 * 
	 * @return
	 */
	public Object[] getCompteIdentifierAsArray() {

		return getCompteMap().keySet().toArray();
	}

	public Set<Identifier> getCompteIdentifierSet() {

		return getCompteMap().keySet();
	}

	public boolean getCompteIsLivret(Identifier compteId) throws ComptaException {

		return getCompte(compteId).isLivret();
	}

	/**
	 * Retourne la map des comptes. Si celle-ci est null, on tente un
	 * chargement.
	 * 
	 * @return
	 */
	private HashMap<Identifier, Compte> getCompteMap() {

		if (_compteMap == null) {
			loadMap();
		}

		return _compteMap;
	}

	/**
	 * Recupere le nom du compte
	 * 
	 * @param compteId
	 * @return
	 * @throws ComptaException
	 */
	public String getNomCompte(final Identifier compteId) throws ComptaException {

		return getCompte(compteId).getNom();
	}

	/**
	 * Recupere le solde du compte
	 * 
	 * @param compteId
	 * @return
	 * @throws ComptaException
	 */
	public double getSoldeCompte(final Identifier compteId) throws ComptaException {

		return getCompte(compteId).getSolde();
	}

	public double getSoldePrevCompte(final Identifier compteId, final int numPrev) throws ComptaException {

		double res = 0.0;

		res = getSoldeCompte(compteId);

		if (TrimestreManager.getInstance().getTrimestreCourantId() != null) {

			for (int numMoi = 0; numMoi < numPrev; numMoi++) {

				// les dépenses
				for (final DepenseOperation depense : TrimestreManager.getInstance().getDomaineDepenseForExerciceCourant(numMoi)) {

					if ((depense.getCompteId() != null) && depense.getCompteId().equals(compteId)
							&& (depense.getEtat() == EtatOperation.PREVISION)) {
						res = res - depense.getMontant();
					}

				}

				// les ressources
				for (final Operation ressource : TrimestreManager.getInstance().getDomaineRessourceForExerciceCourant(numMoi)) {

					if ((ressource.getCompteId() != null) && ressource.getCompteId().equals(compteId)
							&& (ressource.getEtat() == EtatOperation.PREVISION)) {
						res = res + ressource.getMontant();
					}

				}

				// les transferts
				for (final TransfertOperation transfert : TrimestreManager.getInstance().getDomaineTransfertForExerciceCourant(numMoi)) {

					if (transfert.getEtat() == EtatOperation.PREVISION) {

						if ((transfert.getCompteCibleId() != null) && transfert.getCompteCibleId().equals(compteId)) {

							res = res + transfert.getMontant();

						}

						if ((transfert.getCompteId() != null) && transfert.getCompteId().equals(compteId)) {

							res = res - transfert.getMontant();

						}
					}

				}
			}

		}

		return res;
	}

	/**
	 * Recupere la map des comptes
	 */
	private void loadMap() {

		try {
			_compteMap = DataManager.getInstance().loadCompteMap();
		} catch (final ComptaException e) {
			_compteMap = new HashMap<Identifier, Compte>();
		}

		if (_compteMap == null) {

			_compteMap = new HashMap<Identifier, Compte>();
		}
	}

	/**
	 * Supprime le compte
	 * 
	 * @param compteId
	 */
	public void removeCompte(final Identifier compteId) {

		getCompteMap().remove(compteId);
		fireEvent(new CompteEvent(CompteEventType.SUPRESSION, compteId));

	}

	/**
	 * Enlever un listener pour les actions sur les comptes
	 * 
	 * @param listener
	 */
	public void removeCompteListener(final CompteListener listener) {

		_compteListeners.remove(listener);
	}

	/**
	 * Sauvegarde les comptes
	 */
	public void saveAllCompte() {

		try {
			DataManager.getInstance().saveCompteMap(getCompteMap());
		} catch (final ComptaException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e.getCause()));
		}

	}

	public void switchOperation(OperationIdentifier id, EtatOperation etatFinal) throws ComptaException {

		final Operation ope = TrimestreManager.getInstance().getDomaineOperation(id);

		if (id.getType() == OperationType.DEPENSE) {

			if (ope.getEtat() == EtatOperation.PRISE_EN_COMPTE) {
				final Compte compte = getCompte(ope.getCompteId());
				compte.setSolde(compte.getSolde() - ope.getMontant());
			}

			if (ope.getEtat() == EtatOperation.PREVISION) {
				final Compte compte = getCompteMap().get(ope.getCompteId());
				compte.setSolde(compte.getSolde() + ope.getMontant());
			}

		}

		if (id.getType() == OperationType.RESSOURCE) {

			if (ope.getEtat() == EtatOperation.PRISE_EN_COMPTE) {
				final Compte compte = getCompte(ope.getCompteId());
				compte.setSolde(compte.getSolde() + ope.getMontant());
			}

			if (ope.getEtat() == EtatOperation.PREVISION) {
				final Compte compte = getCompte(ope.getCompteId());
				compte.setSolde(compte.getSolde() - ope.getMontant());
			}

		}

		if (id.getType() == OperationType.TRANSFERT) {

			final TransfertOperation trans = (TransfertOperation) ope;

			if (trans.getEtat() == EtatOperation.PRISE_EN_COMPTE) {
				final Compte compte = getCompte(ope.getCompteId());
				compte.setSolde(compte.getSolde() - ope.getMontant());

				final Compte compteCible = getCompte(trans.getCompteCibleId());
				compteCible.setSolde(compteCible.getSolde() + ope.getMontant());
			}

			if (ope.getEtat() == EtatOperation.PREVISION) {

				final Compte compte = getCompte(ope.getCompteId());
				compte.setSolde(compte.getSolde() + ope.getMontant());

				final Compte compteCible = getCompte(trans.getCompteCibleId());
				compteCible.setSolde(compteCible.getSolde() - ope.getMontant());
			}

		}

	}

}
