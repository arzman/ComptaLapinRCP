
package compta.core.application.event;

public class ExerciceEvent {

	private final ExerciceEventType _type;

	private final Object _data;

	public ExerciceEvent(ExerciceEventType type, Object exerciceIdentifier) {

		_type = type;
		_data = exerciceIdentifier;

	}

	public Object getData() {
		return _data;
	}

	public ExerciceEventType getType() {
		return _type;
	}

}
