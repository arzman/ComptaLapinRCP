
package compta.core.application.identifier;

/**
 * Identifiant applicatif 
 * @author Arthur
 *
 */
public class Identifier implements Cloneable {

	private final long _identifier;

	public Identifier() {
		_identifier = System.currentTimeMillis();
	}

	public Identifier(long id) {
		_identifier = id;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		final Identifier zeClone = new Identifier(_identifier);

		return zeClone;
	}

	@Override
	public boolean equals(Object obj) {

		boolean res = false;
		if (obj instanceof Identifier) {

			res = _identifier == ((Identifier) obj)._identifier;
		}

		return res;
	}

	public long getIdentifier() {
		return _identifier;
	}

	@Override
	public int hashCode() {

		return Long.valueOf(_identifier).hashCode();
	}

}
