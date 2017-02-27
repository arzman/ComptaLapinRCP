package compta.core.presentation.controllers;

import java.util.Calendar;

import compta.core.application.event.ExerciceEvent;
import compta.core.application.event.ExerciceEventType;
import compta.core.application.identifier.Identifier;
import compta.core.application.listener.TrimestreListener;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationFormatter;
import compta.core.common.ComptaException;
import compta.core.presentation.views.ExerciceView;

/**
 * Controller ( au sens MVC) de l'IHM {@link ExerciceView}.
 * 
 * @author Arthur
 * 
 */
public class ExerciceViewController extends ViewController implements TrimestreListener {

	public static final String SET_TRIMESTRE = "evc.set.trimestre";

	public static final String OPERATION_EDITED = "evc.operation.edited";

	public static final String OPERATION_REMOVED = "evc.operation.removed";

	public static final String OPERATION_ADD = "evc.operation.add";

	/**
	 * Instance du singleton
	 */
	private static ExerciceViewController _instance;

	/**
	 * Retourne l'instance du singleton.
	 * 
	 * @return l'instance
	 */
	public static ExerciceViewController getInstance() {

		if (_instance == null) {
			_instance = new ExerciceViewController();
		}
		return _instance;
	}

	/**
	 * Constructeur par défaut
	 */
	private ExerciceViewController() {

		TrimestreManager.getInstance().addExerciceListener(this);

	}

	public void dipose() {

		TrimestreManager.getInstance().saveAll();

	}

	public Identifier getCurrentTrimestre() {

		return TrimestreManager.getInstance().getTrimestreCourantId();
	}

	/**
	 * Retourne le label à affiché pour l'exercice passé en paramètre
	 * 
	 * @return le label
	 */
	public String getLabelForExercice(final int moisNum) {

		String res = "ERROR";

		try {
			Calendar cal = TrimestreManager.getInstance().getDateForAppExercice(moisNum);
			res = ApplicationFormatter.moiAnneedateFormat.format(cal.getTime());
		} catch (ComptaException e) {

		}
		return res;
	}

	public double getTotalDepense(final int numMois) {

		double res = 0.0;

		try {
			res = TrimestreManager.getInstance().getTotalDepenseForExerciceCourant(numMois);
		} catch (ComptaException e) {

		}

		return res;
	}

	public double getTotalRessource(final int numMois) {

		double res = 0.0;

		try {
			res = TrimestreManager.getInstance().getTotalRessourceForExerciceCourant(numMois);
		} catch (ComptaException e) {

		}

		return res;

	}

	public double getTotalTransfert(final int numMois) {

		double res = 0.0;

		try {

			res = TrimestreManager.getInstance().getTotalTransfertForExerciceCourant(numMois);
		} catch (ComptaException e) {

		}

		return res;

	}

	@Override
	public void processExerciceEvent(final ExerciceEvent event) {

		if (event.getType() == ExerciceEventType.TRIMESTRE_CHANGED) {
			fireControllerEvent(new ControllerEvent(SET_TRIMESTRE, event.getData()));
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
			fireControllerEvent(new ControllerEvent(OPERATION_EDITED, event.getData()));
		}

	}

}
