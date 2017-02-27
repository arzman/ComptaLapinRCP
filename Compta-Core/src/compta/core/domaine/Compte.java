
package compta.core.domaine;

public class Compte {

	private double _solde;

	private String _nom;

	private boolean _isLivret;

	private boolean _budgetAllowed;

	public Compte(String aNom) {
		setNom(aNom);
		setSolde(0.0);
		_isLivret = false;
		_budgetAllowed = true;
	}


	public String getNom() {
		return _nom;
	}

	public double getSolde() {
		return _solde;
	}

	public boolean isBudgetAllowed() {
		return _budgetAllowed;
	}

	public boolean isLivret() {
		return _isLivret;
	}

	public void setBudgetAllowed(boolean budgetAllowed) {
		_budgetAllowed = budgetAllowed;
	}

	public void setLivret(boolean isLivret) {
		_isLivret = isLivret;
	}

	public void setNom(String _nom) {
		this._nom = _nom;
	}

	public void setSolde(double _solde) {
		this._solde = _solde;
	}

}
