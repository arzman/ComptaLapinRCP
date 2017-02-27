package compta.core.presentation.handlers.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import compta.core.presentation.dialogs.trimestre.ManageTrimestreDialog;

public class ManageTrimestreHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		

	}

	@Override
	public void dispose() {
		

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		ManageTrimestreDialog diag = new ManageTrimestreDialog();
		
		diag.open();
		
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
	public void removeHandlerListener(IHandlerListener handlerListener) {

	}

}
