
package compta.core.domaine.operation;

import compta.core.application.identifier.Identifier;

public class TransfertOperation extends Operation {

	private Identifier _compteCibleId;

	public TransfertOperation() {

		super(OperationType.TRANSFERT);
	}

	public TransfertOperation(Operation ope) {

		super(OperationType.TRANSFERT);
		setNom(ope.getNom());
		setMontant(ope.getMontant());
		setCompteId(ope.getCompteId());
		setEtat(ope.getEtat());

	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		final TransfertOperation zeClone = new TransfertOperation((Operation) super.clone());

		zeClone.setCompteCibleId((Identifier) _compteCibleId.clone());

		return zeClone;
	}

	public Identifier getCompteCibleId() {
		return _compteCibleId;
	}

	public void setCompteCibleId(Identifier _compteCibleId) {
		this._compteCibleId = _compteCibleId;
	}

}
