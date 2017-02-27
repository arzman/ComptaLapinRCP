package compta.core.application.identifier;


/**
 * Identifiant applicatif d'une utilisation d'un {@link compta.core.domaine.Budget}.
 * @author Arthur
 *
 */
public class UtilisationIdentifier {

	/**
	 * L'identifiant du budget
	 */
	private final Identifier _budgetId;

	/**
	 * La position dans le budget
	 */
	private final int _num;

	public UtilisationIdentifier(Identifier budgetId, int num) {

		_budgetId = budgetId;
		_num = num;

	}

	@Override
	public boolean equals(Object obj) {

		boolean res = false;
		// il faut que la classe soit bonne
		if (obj instanceof UtilisationIdentifier) {
			// on compare les valeurs internes
			res = _budgetId.getIdentifier() == ((UtilisationIdentifier) obj)._budgetId.getIdentifier();
			res = res && _num == ((UtilisationIdentifier) obj)._num;

		}

		return res;
	}

	/**
	 * Retourne l'identifiant du budget 
	 * @return
	 */
	public Identifier getIdBudget() {
		return _budgetId;
	}

	/**
	 * Retourne la position de l'utilisation
	 * @return
	 */
	public int getNum() {

		return _num;
	}

}
