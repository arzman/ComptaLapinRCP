
package compta.core.domaine.operation;

public class DepenseOperation extends Operation {

	private String _categorie;

	public DepenseOperation() {

		super(OperationType.DEPENSE);
	}

	public DepenseOperation(Operation ope) {

		super(OperationType.DEPENSE);
		setNom(ope.getNom());
		setMontant(ope.getMontant());
		setCompteId(ope.getCompteId());
		setEtat(ope.getEtat());

	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		final DepenseOperation zeClone = new DepenseOperation((Operation) super.clone());
		zeClone.setCategorie(_categorie);

		return zeClone;
	}

	public String getCategorie() {
		return _categorie;
	}

	public void setCategorie(String _categorie) {
		this._categorie = _categorie;
	}

}
