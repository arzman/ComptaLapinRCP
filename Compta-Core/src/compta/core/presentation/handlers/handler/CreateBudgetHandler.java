
package compta.core.presentation.handlers.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.window.Window;

import compta.core.application.manager.BudgetManager;
import compta.core.presentation.dialogs.budget.EditBudgetDialog;

public class CreateBudgetHandler implements IHandler {

	@Override
	public void addHandlerListener(final IHandlerListener handlerListener) {
		

	}

	@Override
	public void dispose() {
		

	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final EditBudgetDialog diag = new EditBudgetDialog(null);

		if (diag.open() == Window.OK) {

			BudgetManager.getInstance().createBudget(diag.getNom(), diag.getObjectif(), diag.getUtilise());

		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

	@Override
	public boolean isHandled() {
		
		return true;
	}

	@Override
	public void removeHandlerListener(final IHandlerListener handlerListener) {
		

	}

}
