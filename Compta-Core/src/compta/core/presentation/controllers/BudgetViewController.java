package compta.core.presentation.controllers;

import compta.core.application.event.BudgetEvent;
import compta.core.application.event.BudgetEventType;
import compta.core.application.event.CompteEvent;
import compta.core.application.event.CompteEventType;
import compta.core.application.event.ExerciceEvent;
import compta.core.application.event.ExerciceEventType;
import compta.core.application.listener.BudgetListener;
import compta.core.application.listener.CompteListener;
import compta.core.application.listener.TrimestreListener;
import compta.core.application.manager.BudgetManager;
import compta.core.application.manager.CompteManager;
import compta.core.application.manager.TrimestreManager;

public class BudgetViewController extends ViewController implements BudgetListener, TrimestreListener, CompteListener {

	public final static String AJOUT_BUDGET = "bvc.ajout.budget";

	public static final String SUPPRESSION_BUDGET = "bvc.suppression.budget";

	public static final String EDITION_BUDGET = "bvc.edition.budget";

	public static final String SORTED_BUDGET = "bvc.sorted.budget";

	public static final String REFRESH_ALL = "bvc.refresh.budget";

	private static BudgetViewController _instance;

	public static BudgetViewController getInstance() {

		if (_instance == null) {
			_instance = new BudgetViewController();
		}

		return _instance;
	}

	private BudgetViewController() {

		BudgetManager.getInstance().addBudgetListener(this);
		TrimestreManager.getInstance().addExerciceListener(this);
		CompteManager.getInstance().addCompteListener(this);

	}

	public void dipose() {

		BudgetManager.getInstance().saveAllBudget();

	}

	@Override
	public void processBudgetEvent(final BudgetEvent event) {

		if (event.getType() == BudgetEventType.AJOUT) {

			fireControllerEvent(new ControllerEvent(AJOUT_BUDGET, null));

		}

		if (event.getType() == BudgetEventType.SUPRESSION) {

			fireControllerEvent(new ControllerEvent(SUPPRESSION_BUDGET, null));
		}

		if (event.getType() == BudgetEventType.EDITION) {

			fireControllerEvent(new ControllerEvent(EDITION_BUDGET, event.getBudgetIdentifier()));
		}

		if (event.getType() == BudgetEventType.SORTED) {

			fireControllerEvent(new ControllerEvent(SORTED_BUDGET, null));
		}

	}

	@Override
	public void processCompteEvent(CompteEvent event) {

		if (event.getType() == CompteEventType.SUPRESSION) {

			fireControllerEvent(new ControllerEvent(REFRESH_ALL, null));
		}

		if (event.getType() == CompteEventType.EDITION) {

			fireControllerEvent(new ControllerEvent(REFRESH_ALL, null));
		}

		if (event.getType() == CompteEventType.AJOUT) {

			fireControllerEvent(new ControllerEvent(REFRESH_ALL, null));
		}

	}

	@Override
	public void processExerciceEvent(ExerciceEvent event) {

		if (event.getType() == ExerciceEventType.OPERATION_ADD) {

			fireControllerEvent(new ControllerEvent(REFRESH_ALL, null));
		}

		if (event.getType() == ExerciceEventType.OPERATION_EDITED) {

			fireControllerEvent(new ControllerEvent(REFRESH_ALL, null));
		}

		if (event.getType() == ExerciceEventType.OPERATION_REMOVED) {

			fireControllerEvent(new ControllerEvent(REFRESH_ALL, null));
		}

		if (event.getType() == ExerciceEventType.OPERATION_SWITCH) {

			fireControllerEvent(new ControllerEvent(REFRESH_ALL, null));
		}

		if (event.getType() == ExerciceEventType.TRIMESTRE_CHANGED) {

			fireControllerEvent(new ControllerEvent(REFRESH_ALL, null));
		}

	}

}
