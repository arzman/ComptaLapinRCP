package compta.core.domaine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import compta.core.application.identifier.Identifier;
import compta.core.domaine.operation.DepenseOperation;
import compta.core.domaine.operation.Operation;
import compta.core.domaine.operation.OperationType;
import compta.core.domaine.operation.TransfertOperation;

/**
 * 
 * Exercice comptable sur 1 mois. Regroupe toute les opérations sur un mois.
 * 
 * @author Arthur
 * 
 */
public class ExerciceMensuel implements Comparable<ExerciceMensuel> {

	/**
	 * Liste des ressources durant l'exercice.
	 */
	private List<Operation> _ressourcesList;

	/**
	 * Liste des dépenses durant l'exercice.
	 */
	private List<DepenseOperation> _depensesList;

	/**
	 * Liste des opérations durant l'exercice.
	 */
	private List<TransfertOperation> _transfertList;
	/**
	 * Date de début de l'exercice
	 */
	private Calendar _dateDebut;
	/**
	 * Date de fin de l'exercice
	 */
	private Calendar _dateFin;

	/**
	 * 
	 * Constructeur par défaut
	 */
	public ExerciceMensuel() {

		_ressourcesList = new ArrayList<Operation>();
		_depensesList = new ArrayList<DepenseOperation>();
		_transfertList = new ArrayList<TransfertOperation>();

	}

	/**
	 * Ajoute une dépense.
	 * 
	 * @param nom_
	 *            le nom de la dépense
	 * @param montant_
	 *            le montant de la dépense
	 * @return true si l'ajout a été effectué false sinon
	 */
	public boolean ajouterDepense(String nom_, double montant_, String _categorie, Identifier compteId) {

		final DepenseOperation depense = new DepenseOperation();
		depense.setNom(nom_);
		depense.setMontant(montant_);
		depense.setCategorie(_categorie);
		depense.setCompteId(compteId);

		return _depensesList.add(depense);

	}

	/**
	 * Ajoute une ressource.
	 * 
	 * @param nom_
	 *            le nom de la ressource
	 * @param montant_
	 *            le montant de la ressource
	 * @param compteId
	 * @return true si l'ajout a été effectué false sinon
	 */
	public boolean ajouterRessource(String nom_, double montant_, Identifier compteId) {

		final Operation ressouce = new Operation(OperationType.RESSOURCE);
		ressouce.setNom(nom_);
		ressouce.setMontant(montant_);
		ressouce.setCompteId(compteId);

		return _ressourcesList.add(ressouce);

	}

	/**
	 * Ajoute un transfert.
	 * 
	 * @param nom_
	 *            le nom de la ressource
	 * @param montant_
	 *            le montant de la ressource
	 * @param compteIdCible
	 * @param compteId
	 * @return true si l'ajout a été effectué false sinon
	 */
	public boolean ajouterTransfert(String nom_, double montant_, Identifier compteId, Identifier compteIdCible) {

		final TransfertOperation transfert = new TransfertOperation();
		transfert.setNom(nom_);
		transfert.setMontant(montant_);
		transfert.setCompteId(compteId);
		transfert.setCompteCibleId(compteIdCible);

		return _transfertList.add(transfert);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(ExerciceMensuel o_) {

		return _dateDebut.compareTo(o_._dateDebut);
	}

	// GETTER'N'SETTER -----------------------

	public Calendar getDateDebut() {
		return _dateDebut;
	}

	public Calendar getDateFin() {
		return _dateFin;
	}

	/**
	 * @return the depensesList
	 */
	public List<DepenseOperation> getDepensesList() {
		return _depensesList;
	}

	/**
	 * @return the _ressourcesList
	 */
	public List<Operation> getRessourcesList() {
		return _ressourcesList;
	}

	/**
	 * @return the transfertList
	 */
	public List<TransfertOperation> getTransfertList() {
		return _transfertList;
	}

	public void setDateDebut(Calendar dateDebut) {
		_dateDebut = dateDebut;
	}

	public void setDateFin(Calendar dateFin) {
		_dateFin = dateFin;
	}

	/**
	 * @param depensesList
	 *            the depensesList to set
	 */
	public void setDepensesList(List<DepenseOperation> depensesList) {
		_depensesList = depensesList;
	}

	/**
	 * @param _ressourcesList
	 *            the _ressourcesList to set
	 */
	public void setRessourcesList(List<Operation> _ressourcesList) {
		this._ressourcesList = _ressourcesList;
	}

	/**
	 * @param transfertList
	 *            the transfertList to set
	 */
	public void setTransfertList(List<TransfertOperation> transfertList) {
		_transfertList = transfertList;
	}

}
