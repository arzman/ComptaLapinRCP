
package compta.core.presentation.labelprovider.compte;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CompteManager;
import compta.core.common.ComptaException;

public class SoldePrevCompteCellLP extends SoldeCompteCellLP {

	private final int _numPrev;

	public SoldePrevCompteCellLP(final int numPrev) {
		super();
		_numPrev = numPrev;
	}

	@Override
	public double getSolde(final Identifier compteId) {

		double res;
		try {
			res = CompteManager.getInstance().getSoldePrevCompte(compteId, _numPrev);
		} catch (final ComptaException e) {
			res = 0;
		}

		return res;
	}
}
