
package compta.core.application.event;

import compta.core.application.identifier.Identifier;

public class BudgetEvent {

	private final BudgetEventType _type;

	private final Identifier _budgetIdentifier;

	public BudgetEvent(BudgetEventType type, Identifier budgetIdentifier) {

		_type = type;
		_budgetIdentifier = budgetIdentifier;

	}

	public Identifier getBudgetIdentifier() {
		return _budgetIdentifier;
	}

	public BudgetEventType getType() {
		return _type;
	}

}
