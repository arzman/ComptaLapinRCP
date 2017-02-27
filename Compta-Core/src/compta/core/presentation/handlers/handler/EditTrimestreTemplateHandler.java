package compta.core.presentation.handlers.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import compta.core.application.manager.TrimestreManager;
import compta.core.common.ComptaException;
import compta.core.presentation.dialogs.template.EditTemplateTrimestreDialog;

public class EditTrimestreTemplateHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		final EditTemplateTrimestreDialog diag = new EditTemplateTrimestreDialog();
		diag.open();
		try {
			TrimestreManager.getInstance().setTrimestreTemplate();
		} catch (final ComptaException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur", "Impossible de sauver le template");
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
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
