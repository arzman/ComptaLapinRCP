/**
 * 
 */
package compta.core.domaine.operation;

import compta.core.application.identifier.Identifier;

/**
 * @author Arthur
 * 
 */
public class Operation implements Cloneable {

	/**
	 * Etat de l'opération
	 */
	private EtatOperation _etat;
	/**
	 * Le montant de l'opération
	 */
	private double _montant;

	/**
	 * Le nom
	 */
	protected String _nom;

	/**
	 * Le type d'operation
	 */
	protected OperationType _type;

	protected Identifier _compteId;

	/**
	 * Construction par défaut
	 */
	public Operation(OperationType type) {
		setEtat(EtatOperation.PREVISION);
		_type = type;

	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		final Operation zeClone = new Operation(_type);
		zeClone.setNom(_nom);
		zeClone.setMontant(_montant);
		zeClone.setCompteId((Identifier) _compteId.clone());
		zeClone.setEtat(_etat);

		return zeClone;
	}

	public Identifier getCompteId() {
		return _compteId;
	}

	/**
	 * {@inheritDoc]
	 */
	public EtatOperation getEtat() {
		return _etat;
	}

	/**
	 * {@inheritDoc}
	 */
	public double getMontant() {
		return _montant;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public String getNom() {
		return _nom;
	}

	public OperationType getType() {
		return _type;
	}

	public void setCompteId(Identifier _compteId) {
		this._compteId = _compteId;
	}

	/**
	 * @param etat
	 *            the etat to set
	 */
	public void setEtat(EtatOperation etat) {
		_etat = etat;
	}

	/**
	 * @param montant
	 *            the montant to set
	 */
	public void setMontant(double montant) {
		_montant = montant;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		_nom = nom;
	}

}
