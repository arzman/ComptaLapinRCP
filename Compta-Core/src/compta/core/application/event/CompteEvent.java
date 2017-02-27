
package compta.core.application.event;

import compta.core.application.identifier.Identifier;

public class CompteEvent {

	private final CompteEventType _type;

	private final Identifier _compteIdentifier;

	public CompteEvent(CompteEventType type, Identifier compteIdentifier) {

		_type = type;
		_compteIdentifier = compteIdentifier;

	}

	public Identifier getCompteIdentifier() {
		return _compteIdentifier;
	}

	public CompteEventType getType() {
		return _type;
	}

}
