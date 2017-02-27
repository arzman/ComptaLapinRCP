
package compta.core.presentation.controllers;

import compta.core.application.event.CompteEvent;
import compta.core.application.event.CompteEventType;
import compta.core.application.event.ExerciceEvent;
import compta.core.application.event.ExerciceEventType;
import compta.core.application.listener.CompteListener;
import compta.core.application.listener.TrimestreListener;
import compta.core.application.manager.CompteManager;
import compta.core.application.manager.TrimestreManager;

public class CompteViewController extends ViewController implements CompteListener, TrimestreListener {

	public final static String AJOUT_COMPTE = "cvc.ajout.compte";

	public static final String SUPPRESSION_COMPTE = "cvc.suppression.compte";

	public static final String EDITION_COMPTE = "cvc.edition.compte";

	public static final String TRIMESTRE_CHANGED = "cvc.trimestre.changed";

	public static final String OPERATION_EDITED = "cvc.operation.edited";

	public static final String OPERATION_REMOVED = "cvc.operation.removed";

	public static final String OPERATION_ADD = "cvc.operation.add";

	public static final String OPERATION_SWITCH = "cvc.operation.switch";

	private static CompteViewController _instance;

	public static CompteViewController getInstance() {

		if (_instance == null) {

			_instance = new CompteViewController();

		}

		return _instance;
	}

	private CompteViewController() {

		CompteManager.getInstance().addCompteListener(this);
		TrimestreManager.getInstance().addExerciceListener(this);

	}

	public void dipose() {

		CompteManager.getInstance().saveAllCompte();

	}

	@Override
	public void processCompteEvent(final CompteEvent event) {

		if (event.getType() == CompteEventType.AJOUT) {

			fireControllerEvent(new ControllerEvent(AJOUT_COMPTE, null));

		}

		if (event.getType() == CompteEventType.SUPRESSION) {

			fireControllerEvent(new ControllerEvent(SUPPRESSION_COMPTE, null));
		}

		if (event.getType() == CompteEventType.EDITION) {

			fireControllerEvent(new ControllerEvent(EDITION_COMPTE, event.getCompteIdentifier()));
		}

	}

	@Override
	public void processExerciceEvent(final ExerciceEvent event) {

		if (event.getType() == ExerciceEventType.TRIMESTRE_CHANGED) {
			fireControllerEvent(new ControllerEvent(TRIMESTRE_CHANGED, event.getData()));
		}

		if (event.getType() == ExerciceEventType.OPERATION_EDITED) {
			fireControllerEvent(new ControllerEvent(OPERATION_EDITED, event.getData()));
		}

		if (event.getType() == ExerciceEventType.OPERATION_REMOVED) {
			fireControllerEvent(new ControllerEvent(OPERATION_REMOVED, event.getData()));
		}

		if (event.getType() == ExerciceEventType.OPERATION_ADD) {
			fireControllerEvent(new ControllerEvent(OPERATION_ADD, null));
		}

		if (event.getType() == ExerciceEventType.OPERATION_SWITCH) {
			fireControllerEvent(new ControllerEvent(OPERATION_SWITCH, event.getData()));
		}

	}

}
