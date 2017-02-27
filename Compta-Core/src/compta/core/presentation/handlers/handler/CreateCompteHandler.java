
package compta.core.presentation.handlers.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.window.Window;

import compta.core.application.manager.CompteManager;
import compta.core.presentation.dialogs.compte.EditCompteDialog;

public class CreateCompteHandler implements IHandler {

	@Override
	public void addHandlerListener(final IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final EditCompteDialog diag = new EditCompteDialog(null);

		if (diag.open() == Window.OK) {

			CompteManager.getInstance().ajouterUnCompte(diag.getNom(), diag.getSolde(), diag.getIsLivret(), diag.getBudgetAllowed());

		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void removeHandlerListener(final IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
