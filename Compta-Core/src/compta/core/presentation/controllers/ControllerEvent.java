
package compta.core.presentation.controllers;

public class ControllerEvent {

	private final String _name;

	private final Object _value;

	public ControllerEvent(final String aName, final Object aValue) {

		_name = aName;
		_value = aValue;
	}

	public String getName() {
		return _name;
	}

	public Object getValue() {
		return _value;
	}

}
