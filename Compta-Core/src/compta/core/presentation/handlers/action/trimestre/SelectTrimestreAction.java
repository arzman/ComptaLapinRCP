package compta.core.presentation.handlers.action.trimestre;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.window.Window;

import compta.core.application.manager.BudgetManager;
import compta.core.application.manager.TrimestreManager;
import compta.core.presentation.dialogs.trimestre.TrimestreSelectionDialog;

public class SelectTrimestreAction implements IHandler {

	@Override
	public void addHandlerListener(final IHandlerListener handlerListener_) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(final ExecutionEvent event_) throws ExecutionException {

		final TrimestreSelectionDialog tsd = new TrimestreSelectionDialog();

		if (tsd.open() == Window.OK) {

			TrimestreManager.getInstance().setExerciceCourant(tsd.getSelected());
			
			BudgetManager.getInstance().reCalculateData();
			
			

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
	public void removeHandlerListener(final IHandlerListener handlerListener_) {
		// TODO Auto-generated method stub

	}

}
