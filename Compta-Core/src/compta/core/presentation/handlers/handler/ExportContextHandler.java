package compta.core.presentation.handlers.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import compta.core.application.manager.BudgetManager;
import compta.core.application.manager.CompteManager;
import compta.core.application.manager.DataManager;
import compta.core.application.manager.TrimestreManager;

public class ExportContextHandler implements IHandler {

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

		TrimestreManager.getInstance().saveAll();
		CompteManager.getInstance().saveAllCompte();
		BudgetManager.getInstance().saveAllBudget();

		final FileDialog diag = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());

		String path = diag.open();

		if ((path != null) && !path.isEmpty()) {

			if (!path.endsWith(".zip")) {
				path = path + ".zip";
			}
			DataManager.getInstance().exportContext(path);

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
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
