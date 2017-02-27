
package compta.core.application.identifier;

import compta.core.domaine.operation.OperationType;

/**
 * Identifiant applicatif d'une opération
 * @author Arthur
 *
 */
public class OperationIdentifier {

	private final Identifier _trimestreIdentifier;

	private final int _numMoi;

	private final int _numOperation;

	private final OperationType _type;

	public OperationIdentifier(Identifier excerciceIdentifier, int numMoi, OperationType type, int numOperation) {
		_trimestreIdentifier = excerciceIdentifier;
		_numMoi = numMoi;
		_numOperation = numOperation;
		_type = type;
	}

	@Override
	public boolean equals(Object obj) {

		boolean res = false;
		if (obj instanceof OperationIdentifier) {

			res = (_trimestreIdentifier == ((OperationIdentifier) obj)._trimestreIdentifier)
					&& (_numMoi == ((OperationIdentifier) obj)._numMoi) && (_numOperation == ((OperationIdentifier) obj)._numOperation);
		}

		return res;
	}

	/**
	 * @return the numMoi
	 */
	public int getNumMoi() {
		return _numMoi;
	}

	/**
	 * @return the numOperation
	 */
	public int getNumOperation() {
		return _numOperation;
	}

	/**
	 * @return the excerciceIdentifier
	 */
	public Identifier getTrimestreIdentifier() {
		return _trimestreIdentifier;
	}

	/**
	 * @return the type
	 */
	public OperationType getType() {
		return _type;
	}

	@Override
	public int hashCode() {

		return _trimestreIdentifier.hashCode() + (5 * _numMoi) + (7 * _numOperation);
	}

}
