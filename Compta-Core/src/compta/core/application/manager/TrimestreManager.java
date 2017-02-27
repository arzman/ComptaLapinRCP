/**
 * 
 */
package compta.core.application.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import compta.core.Activator;
import compta.core.application.event.ExerciceEvent;
import compta.core.application.event.ExerciceEventType;
import compta.core.application.identifier.Identifier;
import compta.core.application.identifier.OperationIdentifier;
import compta.core.application.listener.TrimestreListener;
import compta.core.application.template.TrimestreTemplate;
import compta.core.application.template.TrimestreTemplateElement;
import compta.core.application.template.TrimestreTemplateElementFrequence;
import compta.core.common.ComptaException;
import compta.core.domaine.ExerciceMensuel;
import compta.core.domaine.Trimestre;
import compta.core.domaine.operation.DepenseOperation;
import compta.core.domaine.operation.EtatOperation;
import compta.core.domaine.operation.Operation;
import compta.core.domaine.operation.OperationType;
import compta.core.domaine.operation.TransfertOperation;

/**
 * @author Arthur
 * 
 */
public class TrimestreManager {

	private Map<Identifier, Trimestre> _trimestreMap;

	/**
	 * L'identifiant de l'exercice multi-mensuel courant.
	 */
	private Identifier _trimestreCourantId;

	private final ArrayList<TrimestreListener> _exerciceListeners;

	private TrimestreTemplate _trimestreTemplate;

	/**
	 * L'instance du singleton
	 */
	private static TrimestreManager _instance;

	/**
	 * Retourne l'instance du singleton.
	 * 
	 * @return l'instance du singleton
	 */
	public static TrimestreManager getInstance() {

		if (_instance == null) {
			_instance = new TrimestreManager();
		}

		return _instance;
	}

	/**
	 * Constructeur par défaut
	 */
	private TrimestreManager() {

		_exerciceListeners = new ArrayList<TrimestreListener>();
		getMap();
		loadTrimestreCourant();

	}

	/**
	 * Rajoute un listener pour les actions sur les exercices
	 * 
	 * @param listener
	 */
	public void addExerciceListener(final TrimestreListener listener) {

		_exerciceListeners.add(listener);

	}

	/**
	 * Crée une dépense et l'ajoute dans le trimestre courant.
	 * 
	 * @param nom
	 * @param montant
	 * @param categorie
	 * @param compteId
	 * @param numMoi
	 * @throws ComptaException
	 */
	public void createDepense(final String nom, final double montant, final String categorie, final Identifier compteId, final int numMoi)
			throws ComptaException {

		final ExerciceMensuel em = getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMoi];
		em.ajouterDepense(nom, montant, categorie, compteId);

		if (!CategorieManager.getInstance().getCategories().contains(categorie)) {
			CategorieManager.getInstance().addCategorie(categorie);
		}

		BudgetManager.getInstance().calculateData();
		fireEvent(new ExerciceEvent(ExerciceEventType.OPERATION_ADD, null));

	}

	/**
	 * Crée une dépense dans le template
	 * 
	 * @param nom
	 * @param montant
	 * @param categorie
	 * @param compteId
	 * @param freq
	 * @param occurence
	 */
	public void createDepenseTemplateElt(String nom, double montant, String categorie, Identifier compteId,
			TrimestreTemplateElementFrequence freq, int occurence) {

		final TrimestreTemplateElement elt = new TrimestreTemplateElement();

		elt.setFreq(freq);
		elt.setOccurence(occurence);
		final DepenseOperation ope = new DepenseOperation();
		ope.setNom(nom);
		ope.setCategorie(categorie);
		ope.setCompteId(compteId);
		ope.setMontant(montant);
		elt.setOperation(ope);
		getTrimestreTemplate().getDepensesElement().add(elt);

		if (!CategorieManager.getInstance().getCategories().contains(categorie)) {
			CategorieManager.getInstance().addCategorie(categorie);
		}

	}

	/**
	 * Crée uneé ressource et l'ajoute dans le trimestre courant
	 * 
	 * @param nom
	 * @param montant
	 * @param compteId
	 * @param numMoi
	 * @throws ComptaException
	 */
	public void createRessource(final String nom, final double montant, final Identifier compteId, final int numMoi) throws ComptaException {

		final ExerciceMensuel em = getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMoi];
		em.ajouterRessource(nom, montant, compteId);

		BudgetManager.getInstance().calculateData();
		fireEvent(new ExerciceEvent(ExerciceEventType.OPERATION_ADD, null));

	}

	/**
	 * Crée une ressource dans le template
	 * 
	 * @param nom
	 * @param montant
	 * @param compteId
	 * @param freq
	 * @param occurence
	 */
	public void createRessourceTemplateElt(String nom, double montant, Identifier compteId, TrimestreTemplateElementFrequence freq,
			int occurence) {

		final TrimestreTemplateElement elt = new TrimestreTemplateElement();

		elt.setFreq(freq);
		elt.setOccurence(occurence);
		final Operation ope = new Operation(OperationType.RESSOURCE);
		ope.setNom(nom);
		ope.setCompteId(compteId);
		ope.setMontant(montant);
		elt.setOperation(ope);
		getTrimestreTemplate().getRessourcesElement().add(elt);

	}

	/**
	 * Crée un transfert et l'ajoute dans le trimestre courant
	 * 
	 * @param nom
	 * @param montant
	 * @param compteId
	 * @param compteIdCible
	 * @param numMoi
	 * @throws ComptaException
	 */
	public void createTransfert(final String nom, final double montant, final Identifier compteId, final Identifier compteIdCible,
			final int numMoi) throws ComptaException {

		final ExerciceMensuel em = getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMoi];
		em.ajouterTransfert(nom, montant, compteId, compteIdCible);

		BudgetManager.getInstance().calculateData();
		fireEvent(new ExerciceEvent(ExerciceEventType.OPERATION_ADD, null));

	}

	public void createTransfertTemplateElt(String nom, double montant, Identifier compteId, Identifier compteCibleId,
			TrimestreTemplateElementFrequence freq, int occurence) {

		final TrimestreTemplateElement elt = new TrimestreTemplateElement();

		elt.setFreq(freq);
		elt.setOccurence(occurence);
		final TransfertOperation ope = new TransfertOperation();
		ope.setNom(nom);
		ope.setCompteId(compteId);
		ope.setMontant(montant);
		ope.setCompteCibleId(compteCibleId);
		elt.setOperation(ope);
		getTrimestreTemplate().getTransfertElement().add(elt);

	}

	/**
	 * Crée un nouvel exercice
	 * 
	 * @param nbrMoi_
	 *            le nombre de mois contenu dans l'exercice
	 * @param dateDeb
	 *            date de début de l'exercice
	 * 
	 * @return l'identifiant de l'exercice créé.
	 */
	public void creerTrimestre(final Calendar dateDeb_) {

		// création du nouvel exercice
		final Trimestre trimestre = new Trimestre();
		final Identifier id = new Identifier();

		// création des mois dans l'exercice multimensuel
		final int numMoi = dateDeb_.get(Calendar.MONTH);
		final ExerciceMensuel[] tmpList = new ExerciceMensuel[3];

		for (int i = 0; i < 3; i++) {

			final ExerciceMensuel em = new ExerciceMensuel();
			final Calendar debut = Calendar.getInstance();
			debut.set(Calendar.DAY_OF_MONTH, 1);
			debut.set(Calendar.MONTH, (i + numMoi) % 12);
			debut.set(Calendar.YEAR, dateDeb_.get(Calendar.YEAR) + ((i + numMoi) / 12));
			em.setDateDebut(debut);

			final Calendar fin = Calendar.getInstance();
			fin.set(Calendar.DAY_OF_MONTH, debut.getActualMaximum(Calendar.MONTH));
			fin.set(Calendar.MONTH, (i + numMoi) % 12);
			fin.set(Calendar.YEAR, dateDeb_.get(Calendar.YEAR) + ((i + numMoi) / 12));
			em.setDateFin(fin);

			final List<DepenseOperation> depList = new ArrayList<DepenseOperation>();
			final List<Operation> ressList = new ArrayList<Operation>();
			final List<TransfertOperation> transList = new ArrayList<TransfertOperation>();

			// opération hebdomadaire
			depList.addAll(getTrimestreTemplate().getDepenseHebdo((i + numMoi) % 12, dateDeb_.get(Calendar.YEAR) + ((i + numMoi) / 12)));
			ressList.addAll(getTrimestreTemplate().getRessourceHebdo((i + numMoi) % 12, dateDeb_.get(Calendar.YEAR) + ((i + numMoi) / 12)));
			transList
					.addAll(getTrimestreTemplate().getTransfertHebdo((i + numMoi) % 12, dateDeb_.get(Calendar.YEAR) + ((i + numMoi) / 12)));

			// opération mensuelle
			depList.addAll(getTrimestreTemplate().getDepenseMensuel());
			ressList.addAll(getTrimestreTemplate().getRessourceMensuel());
			transList.addAll(getTrimestreTemplate().getTransfertMensuel());

			// opération trimestrielle
			depList.addAll(getTrimestreTemplate().getDepenseTrimestriel(i));
			ressList.addAll(getTrimestreTemplate().getRessourceTrimestriel(i));
			transList.addAll(getTrimestreTemplate().getTransfertTrimestriel(i));

			em.setDepensesList(depList);
			em.setRessourcesList(ressList);
			em.setTransfertList(transList);

			tmpList[i] = em;
		}

		trimestre.setExerciceMensuel(tmpList);
		getMap().put(id, trimestre);

	}

	/**
	 * Edite une dépense contenue dans l'application
	 * 
	 * @param nom
	 * @param montant
	 * @param categorie
	 * @param compteId
	 * @param operationId
	 */
	public void editDepense(final String nom, final double montant, final String categorie, final Identifier compteId,
			final OperationIdentifier operationId) {

		final ExerciceMensuel em = getMap().get(operationId.getTrimestreIdentifier()).getExerciceMensuel()[operationId.getNumMoi()];

		if (em != null) {

			final DepenseOperation depense = em.getDepensesList().get(operationId.getNumOperation());

			depense.setNom(nom);
			depense.setMontant(montant);
			depense.setCategorie(categorie);
			depense.setCompteId(compteId);

		}

		if (!CategorieManager.getInstance().getCategories().contains(categorie)) {
			CategorieManager.getInstance().addCategorie(categorie);
		}

		BudgetManager.getInstance().calculateData();
		fireEvent(new ExerciceEvent(ExerciceEventType.OPERATION_EDITED, operationId));

	}

	/**
	 * Edite une ressource contenue dans l'application
	 * 
	 * @param nom
	 * @param montant
	 * @param compteId
	 * @param id
	 */
	public void editRessource(final String nom, final double montant, final Identifier compteId, final OperationIdentifier id) {

		final ExerciceMensuel em = getMap().get(id.getTrimestreIdentifier()).getExerciceMensuel()[id.getNumMoi()];

		if (em != null) {

			final Operation depense = em.getRessourcesList().get(id.getNumOperation());

			depense.setNom(nom);
			depense.setMontant(montant);
			depense.setCompteId(compteId);

		}

		BudgetManager.getInstance().calculateData();
		fireEvent(new ExerciceEvent(ExerciceEventType.OPERATION_EDITED, id));

	}

	/**
	 * Edite un transfert contenu dans l'application
	 * 
	 * @param nom
	 * @param montant
	 * @param compteId
	 * @param compteIdCible
	 * @param id
	 */
	public void editTransfert(final String nom, final double montant, final Identifier compteId, final Identifier compteIdCible,
			final OperationIdentifier id) {

		final ExerciceMensuel em = getMap().get(id.getTrimestreIdentifier()).getExerciceMensuel()[id.getNumMoi()];

		if (em != null) {

			final TransfertOperation transfert = em.getTransfertList().get(id.getNumOperation());

			transfert.setNom(nom);
			transfert.setMontant(montant);
			transfert.setCompteId(compteId);
			transfert.setCompteCibleId(compteIdCible);

		}

		BudgetManager.getInstance().calculateData();
		fireEvent(new ExerciceEvent(ExerciceEventType.OPERATION_EDITED, id));

	}

	/**
	 * Envoie un evenement aux listeners
	 * 
	 * @param event
	 */
	private void fireEvent(final ExerciceEvent event) {

		for (final TrimestreListener listener : _exerciceListeners) {
			listener.processExerciceEvent(event);
		}

	}

	/**
	 * Retourne la date de début de l'exercice multimensuel
	 * 
	 * @param id_
	 *            l'identidfiant de l'exercice multi mensuel
	 * @return la date de début de l'exercice
	 */
	public Calendar getDateDebutForExerciceIdendifier(final Identifier id_) {

		Calendar res = null;

		final Trimestre trimestre = getMap().get(id_);

		if (trimestre != null) {

			res = trimestre.getDateDebut();

		}

		return res;
	}

	/**
	 * Retourne la date de fin de l'exercice multimensuel
	 * 
	 * @param id_
	 *            l'identidfiant de l'exercice multi mensuel
	 * @return la date de début de l'exercice
	 */
	public Calendar getDateFinForExerciceIdendifier(final Identifier id_) {
		Calendar res = null;

		final Trimestre trimestre = getMap().get(id_);

		if (trimestre != null) {

			res = trimestre.getDateFin();

		}

		return res;
	}

	/**
	 * Retourne la date de début d'un moi du trimestre courant
	 * 
	 * @param numMoi
	 * @return
	 * @throws ComptaException
	 */
	public Calendar getDateForAppExercice(final int numMoi) throws ComptaException {

		return getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMoi].getDateDebut();
	}

	/**
	 * Retourne la catégorie d'une dépense
	 * 
	 * @param id
	 * @return
	 * @throws ComptaException
	 */
	public String getDepenseCategorie(final OperationIdentifier id) throws ComptaException {

		return getTrimestre(id.getTrimestreIdentifier()).getExerciceMensuel()[id.getNumMoi()].getDepensesList().get(id.getNumOperation())
				.getCategorie();
	}

	/**
	 * Retourne les dépenses pour un exercice mensuel
	 * 
	 * @param _numMoi
	 * @return
	 */
	public List<OperationIdentifier> getDepenseForExerciceCourant(final int _numMoi) {

		final List<OperationIdentifier> res = new ArrayList<OperationIdentifier>();

		if (getTrimestreCourantId() != null) {

			try {
				final int size = getDomaineDepenseForExerciceCourant(_numMoi).size();
				for (int i = 0; i < size; i++) {
					res.add(new OperationIdentifier(getTrimestreCourantId(), _numMoi, OperationType.DEPENSE, i));
				}
			} catch (ComptaException e) {
				Activator
						.getDefault()
						.getLog()
						.log(new Status(Status.ERROR, Activator.PLUGIN_ID,
								"Erreur lors de la récupération des dépenses du trimestre pour le mois " + _numMoi));
			}

		}

		return res;
	}

	/**
	 * Retourne la liste des depenses du mois ayant le numéro passé en paramètre
	 * dans le trimestre courant. Le retour est sous forme d'une
	 * {@link ArrayList} de {@link DepenseOperation}.
	 * 
	 * @param numMoi
	 * @return
	 * @throws ComptaException
	 */
	public List<DepenseOperation> getDomaineDepenseForExerciceCourant(final int numMoi) throws ComptaException {

		return getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMoi].getDepensesList();
	}

	/**
	 * Retourne l'opération correspondant à l'identifiant.
	 * 
	 * @param id
	 * @return
	 * @throws ComptaException
	 */
	public Operation getDomaineOperation(final OperationIdentifier id) throws ComptaException {

		Operation res = null;

		final ExerciceMensuel em = getTrimestre(id.getTrimestreIdentifier()).getExerciceMensuel()[id.getNumMoi()];

		if (id.getType() == OperationType.DEPENSE) {
			res = em.getDepensesList().get(id.getNumOperation());
		}

		if (id.getType() == OperationType.TRANSFERT) {
			res = em.getTransfertList().get(id.getNumOperation());
		}

		if (id.getType() == OperationType.RESSOURCE) {
			res = em.getRessourcesList().get(id.getNumOperation());
		}

		return res;

	}

	/**
	 * Retourne la liste des ressources du mois ayant le numéro passé en
	 * paramètre dans le trimestre courant. Le retour est sous forme d'une
	 * {@link ArrayList} de {@link Operation}.
	 * 
	 * @param numMoi
	 * @return
	 * @throws ComptaException
	 */
	public List<Operation> getDomaineRessourceForExerciceCourant(final int numMoi) throws ComptaException {

		return getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMoi].getRessourcesList();
	}

	/**
	 * Retourne la liste des transferts du mois ayant le numéro passé en
	 * paramètre dans le trimestre courant. Le retour est sous forme d'une
	 * {@link ArrayList} de {@link Operation}.
	 * 
	 * @param numMoi
	 * @return
	 * @throws ComptaException
	 */
	public List<TransfertOperation> getDomaineTransfertForExerciceCourant(final int numMoi) throws ComptaException {

		return getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMoi].getTransfertList();
	}

	/**
	 * Charge la map des exercices si celle-ci est null.
	 * 
	 * @return la map des exercices.
	 */
	private Map<Identifier, Trimestre> getMap() {

		if (_trimestreMap == null) {
			_trimestreMap = loadTrimestreMap();
		}

		return _trimestreMap;
	}

	/**
	 * Retourne l'identifiant du compte source de l'operation passée en
	 * paramètre.
	 * 
	 * @param id
	 * @return
	 * @throws ComptaException
	 */
	public Identifier getOperationCompte(final OperationIdentifier id) throws ComptaException {

		Identifier cptId = null;

		final ExerciceMensuel em = getTrimestre(id.getTrimestreIdentifier()).getExerciceMensuel()[id.getNumMoi()];

		if (id.getType() == OperationType.DEPENSE) {
			cptId = em.getDepensesList().get(id.getNumOperation()).getCompteId();
		}

		if (id.getType() == OperationType.TRANSFERT) {
			cptId = em.getTransfertList().get(id.getNumOperation()).getCompteId();
		}

		if (id.getType() == OperationType.RESSOURCE) {
			cptId = em.getRessourcesList().get(id.getNumOperation()).getCompteId();
		}

		return cptId;
	}

	/**
	 * Retourne le nom du compte source de l'operation passée en paramètre.
	 * 
	 * @param id
	 * @return
	 * @throws ComptaException
	 */
	public String getOperationCompteNom(final OperationIdentifier id) throws ComptaException {

		return CompteManager.getInstance().getNomCompte(getOperationCompte(id));
	}

	/**
	 * Retourne l'état de l'opération
	 * 
	 * @param element
	 * @return
	 * @throws ComptaException
	 */
	public EtatOperation getOperationEtat(final OperationIdentifier id) throws ComptaException {

		EtatOperation res = null;

		final Operation ope = getDomaineOperation(id);

		if (ope != null) {
			res = ope.getEtat();
		}

		return res;
	}

	/**
	 * Retourne le montant de l'opération
	 * 
	 * @param id
	 * @return
	 * @throws ComptaException
	 */
	public double getOperationMontant(final OperationIdentifier id) throws ComptaException {

		double res = 0.0;

		final ExerciceMensuel em = getTrimestre(id.getTrimestreIdentifier()).getExerciceMensuel()[id.getNumMoi()];

		if (id.getType() == OperationType.DEPENSE) {
			res = em.getDepensesList().get(id.getNumOperation()).getMontant();
		}

		if (id.getType() == OperationType.TRANSFERT) {
			res = em.getTransfertList().get(id.getNumOperation()).getMontant();
		}

		if (id.getType() == OperationType.RESSOURCE) {
			res = em.getRessourcesList().get(id.getNumOperation()).getMontant();
		}

		return res;
	}

	/**
	 * Retourne le nom de l'opération passée en paramètre.
	 * 
	 * @param id
	 * @return
	 * @throws ComptaException
	 */
	public String getOperationNom(final OperationIdentifier id) throws ComptaException {

		return getDomaineOperation(id).getNom();
	}

	/**
	 * Retourne les ressources pour un mois du trimestre courant
	 * 
	 * @param numMois
	 * @return
	 */
	public List<OperationIdentifier> getRessourceForExerciceCourant(final Integer numMois) {

		final List<OperationIdentifier> res = new ArrayList<OperationIdentifier>();

		if (getTrimestreCourantId() != null) {

			try {
				final int size = getDomaineRessourceForExerciceCourant(numMois).size();
				for (int i = 0; i < size; i++) {

					res.add(new OperationIdentifier(getTrimestreCourantId(), numMois, OperationType.RESSOURCE, i));
				}
			} catch (ComptaException e) {
				Activator
						.getDefault()
						.getLog()
						.log(new Status(Status.ERROR, Activator.PLUGIN_ID,
								"Erreur lors de la récupération des ressources du trimestre pour le mois " + numMois));
			}

		}

		return res;
	}

	/**
	 * Retourne le total des dépenses pour un mois du trimestre courant
	 * 
	 * @param numMois
	 * @return
	 * @throws ComptaException
	 */
	public double getTotalDepenseForExerciceCourant(final int numMois) throws ComptaException {

		double res = 0.0;

		for (final DepenseOperation dep : getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMois].getDepensesList()) {
			res = res + dep.getMontant();
		}

		return res;
	}

	/**
	 * Retourne le total des ressources pour un mois du trimestre courant
	 * 
	 * @param numMois
	 * @return
	 * @throws ComptaException
	 */
	public double getTotalRessourceForExerciceCourant(final int numMois) throws ComptaException {

		double res = 0.0;

		for (final Operation ress : getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMois].getRessourcesList()) {
			res = res + ress.getMontant();
		}

		return res;
	}

	/**
	 * Retourne le total des transferts pour un mois du trimestre courant
	 * 
	 * @param numMois
	 * @return
	 * @throws ComptaException
	 */
	public double getTotalTransfertForExerciceCourant(final int numMois) throws ComptaException {

		double res = 0.0;

		for (final TransfertOperation transf : getTrimestre(getTrimestreCourantId()).getExerciceMensuel()[numMois].getTransfertList()) {
			res = res + transf.getMontant();
		}

		return res;
	}

	/**
	 * Retourne le compte cible d'un transfert
	 * 
	 * @param id
	 * @return
	 * @throws ComptaException
	 */
	public Identifier getTransfertCompteCible(final OperationIdentifier id) throws ComptaException {

		return getTrimestre(id.getTrimestreIdentifier()).getExerciceMensuel()[id.getNumMoi()].getTransfertList().get(id.getNumOperation())
				.getCompteCibleId();
	}

	/**
	 * Retourne le nom du compte cible du transfert passé en paramètre.
	 * 
	 * @param id
	 * @return
	 * @throws ComptaException
	 */
	public String getTransfertCompteCibleNom(final OperationIdentifier id) throws ComptaException {

		return CompteManager.getInstance().getNomCompte(getTransfertCompteCible(id));
	}

	public List<OperationIdentifier> getTransfertForExerciceCourant(final Integer numMois) {

		final List<OperationIdentifier> res = new ArrayList<OperationIdentifier>();

		if (getTrimestreCourantId() != null) {

			try {
				final int size = getDomaineTransfertForExerciceCourant(numMois).size();
				for (int i = 0; i < size; i++) {
					res.add(new OperationIdentifier(getTrimestreCourantId(), numMois, OperationType.TRANSFERT, i));
				}
			} catch (ComptaException e) {
				Activator
						.getDefault()
						.getLog()
						.log(new Status(Status.ERROR, Activator.PLUGIN_ID,
								"Erreur lors de la récupération des transferts du trimestre pour le mois " + numMois));
			}

		}
		return res;
	}

	/**
	 * Retourne le trimestre courant
	 * 
	 * @return
	 */
	public Identifier getTrimestreCourantId() {
		return _trimestreCourantId;
	}

	/**
	 * Retourne les identifiants des trimestres de l'application
	 * 
	 * @return
	 */
	public Set<Identifier> getTrimestreIdentifierSet() {

		return getMap().keySet();
	}

	/**
	 * Retourne le template des trimestres
	 * 
	 * @return
	 */
	public TrimestreTemplate getTrimestreTemplate() {

		if (_trimestreTemplate == null) {
			try {
				_trimestreTemplate = DataManager.getInstance().loadTrimestreTemplate();
			} catch (final ComptaException e) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
				_trimestreTemplate = new TrimestreTemplate();
			}
		}

		if (_trimestreTemplate == null) {
			_trimestreTemplate = new TrimestreTemplate();
		}

		return _trimestreTemplate;
	}

	/**
	 * Charge le trimestre courant
	 */
	private void loadTrimestreCourant() {

		try {
			final Identifier obj = DataManager.getInstance().loadTrimstreCourantId();
			setExerciceCourant(obj);
		} catch (final ComptaException e) {
			// on averti que l'on a pas réussi a charger l'identifiant de
			// l'exercice courant
			Activator.getDefault().getLog().log(new Status(IStatus.WARNING, Activator.PLUGIN_ID, e.getMessage()));

			Calendar dateComp = null;
			Identifier tmpId = null;

			// on prend le dernier exercice
			for (final Entry<Identifier, Trimestre> entry : getMap().entrySet()) {

				final Calendar dateExcerice = entry.getValue().getDateDebut();

				if (dateComp == null) {
					dateComp = dateExcerice;
					tmpId = entry.getKey();

				} else {

					if (dateComp.compareTo(dateExcerice) < 0) {
						dateComp = dateExcerice;
						tmpId = entry.getKey();
					}

				}

			}

			setExerciceCourant(tmpId);

		}

	}

	/**
	 * Charge la map des exercices multimensuel
	 * 
	 * @return la map chargée
	 */
	private Map<Identifier, Trimestre> loadTrimestreMap() {

		Map<Identifier, Trimestre> res = null;
		try {
			res = DataManager.getInstance().loadTrimestreMap();
		} catch (final ComptaException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}

		if (res == null) {
			res = new HashMap<Identifier, Trimestre>();
		}

		return res;
	}

	/**
	 * Enlever un listener pour les actions sur les exercices
	 * 
	 * @param listener
	 */
	public void removeExerciceListener(final TrimestreListener listener) {

		_exerciceListeners.remove(listener);
	}

	/**
	 * Supprime une opération de l'application
	 * 
	 * @param id
	 * @throws ComptaException
	 */
	public void removeOperation(final OperationIdentifier id) throws ComptaException {

		final ExerciceMensuel em = getTrimestre(id.getTrimestreIdentifier()).getExerciceMensuel()[id.getNumMoi()];

		if (id.getType() == OperationType.DEPENSE) {
			em.getDepensesList().remove(id.getNumOperation());
		}

		if (id.getType() == OperationType.RESSOURCE) {
			em.getRessourcesList().remove(id.getNumOperation());
		}

		if (id.getType() == OperationType.TRANSFERT) {
			em.getTransfertList().remove(id.getNumOperation());
		}

		fireEvent(new ExerciceEvent(ExerciceEventType.OPERATION_REMOVED, id));

	}

	/**
	 * Supprime un élément du template de trimestre
	 * 
	 * @param elt
	 */
	public void removeTrimestreTemplateElt(TrimestreTemplateElement elt) {

		if (elt.getOperation().getType() == OperationType.DEPENSE) {
			getTrimestreTemplate().getDepensesElement().remove(elt);
		}

		if (elt.getOperation().getType() == OperationType.RESSOURCE) {
			getTrimestreTemplate().getRessourcesElement().remove(elt);
		}

		if (elt.getOperation().getType() == OperationType.TRANSFERT) {
			getTrimestreTemplate().getTransfertElement().remove(elt);
		}

	}

	/**
	 * Sauve toutes les données
	 */
	public void saveAll() {
		try {
			DataManager.getInstance().saveTrimestreMap(getMap());
		} catch (final ComptaException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}
		try {
			DataManager.getInstance().saveExcericeCourant(getTrimestreCourantId());
		} catch (final ComptaException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}

		try {
			DataManager.getInstance().saveTrimestreTemplate(getTrimestreTemplate());
		} catch (final ComptaException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}

	}

	/**
	 * Valorise l'identifiant de l'exercice mutli-mensuel en cours. Si besoin la
	 * liste des exercices mensuel est remise à zéro.
	 * 
	 * @param exerciceCourant
	 *            le nouvel identifiant
	 */
	public void setExerciceCourant(final Identifier exerciceCourant) {

		_trimestreCourantId = exerciceCourant;
		fireEvent(new ExerciceEvent(ExerciceEventType.TRIMESTRE_CHANGED, exerciceCourant));

	}

	/**
	 * Edite et sauve le template des trimestres
	 * 
	 * @param template
	 * @throws ComptaException
	 */
	public void setTrimestreTemplate() throws ComptaException {

		DataManager.getInstance().saveTrimestreTemplate(getTrimestreTemplate());

	}

	/**
	 * Change l'état d'une opération
	 * 
	 * @param id
	 * @throws ComptaException
	 */
	public void switchEtatOperation(final OperationIdentifier id) throws ComptaException {

		final Operation ope = getDomaineOperation(id);

		if (ope.getEtat() == EtatOperation.PREVISION) {
			ope.setEtat(EtatOperation.PRISE_EN_COMPTE);
		} else {
			if (ope.getEtat() == EtatOperation.PRISE_EN_COMPTE) {
				ope.setEtat(EtatOperation.PREVISION);
			}
		}

		CompteManager.getInstance().switchOperation(id, ope.getEtat());

		fireEvent(new ExerciceEvent(ExerciceEventType.OPERATION_EDITED, id));

	}

	/**
	 * Supprime un trimestre de l'application
	 * 
	 * @param idSelected
	 */
	public void removeTrimestre(Identifier idSelected) {
		if (getMap().containsKey(idSelected)) {
			getMap().remove(idSelected);
		}

	}

	/**
	 * Export le trimestre dans un fichier
	 * 
	 * @param idSelected
	 * @param path
	 */
	public void exportTrimestre(Identifier idSelected, String path) {

		Trimestre trim = getMap().get(idSelected);
		if (trim != null) {
			try {
				DataManager.getInstance().exportTrimestre(trim, path);
			} catch (ComptaException e) {
				Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
			}
		}

	}

	/**
	 * Import un trimestre dans l'application depuis un fichier
	 * 
	 * @param path
	 */
	public void importTrimestre(String path) {

		try {
			Trimestre trim = DataManager.getInstance().loadTrimestre(path);

			if (trim != null) {

				Identifier id = new Identifier();
				getMap().put(id, trim);
			}

		} catch (ComptaException e) {
			Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage(), e));
		}

	}

	public List<OperationIdentifier> getDepenseForTrimestre(Identifier trimId, int numMoi) throws ComptaException {

		final List<OperationIdentifier> res = new ArrayList<OperationIdentifier>();

		final int size = getTrimestre(trimId).getExerciceMensuel()[numMoi].getDepensesList().size();
		for (int i = 0; i < size; i++) {
			res.add(new OperationIdentifier(trimId, numMoi, OperationType.DEPENSE, i));
		}

		return res;
	}

	public Trimestre getTrimestre(Identifier trimId) throws ComptaException {

		Trimestre res = null;

		if (trimId != null && getMap().containsKey(trimId)) {
			res = getMap().get(trimId);
		}

		if (res == null) {
			throw new ComptaException("Impossible de charger le trimestre", null);
		}

		return res;
	}

	public Collection<Trimestre> getTrimestreSet() {
		return getMap().values();
	}

}
