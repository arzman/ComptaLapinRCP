package compta.core.presentation.handlers.action.trimestre;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.jface.window.Window;

import compta.core.application.manager.TrimestreManager;
import compta.core.presentation.dialogs.date.DateChooserDialog;

public class CreerTrimestreAction implements IHandler {

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

		final DateChooserDialog dcd = new DateChooserDialog("Veuillez choisir la date de début du trimestre");

		if (dcd.open() == Window.OK) {
			TrimestreManager.getInstance().creerTrimestre(dcd.getDate());
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
	public void removeHandlerListener(final IHandlerListener handlerListener_) {
		// TODO Auto-generated method stub

	}

}
