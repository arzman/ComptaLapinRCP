
package compta.core.application.listener;

import compta.core.application.event.BudgetEvent;

public interface BudgetListener {

	void processBudgetEvent(BudgetEvent event);

}
