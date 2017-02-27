package compta.core.presentation.labelprovider.template;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.manager.CompteManager;
import compta.core.application.template.TrimestreTemplateElement;
import compta.core.common.ComptaException;
import compta.core.domaine.operation.Operation;
import compta.core.domaine.operation.TransfertOperation;

public class CompteCibleTransfertTemplateTrimestreEltCellLP extends CellLabelProvider {

	@Override
	public void update(final ViewerCell cell) {

		String txt = "ERROR";

		if (cell.getElement() instanceof TrimestreTemplateElement) {

			final Operation ope = ((TrimestreTemplateElement) cell.getElement()).getOperation();
			if ((ope != null) && (ope instanceof TransfertOperation)) {
				try {
					txt = CompteManager.getInstance().getNomCompte(((TransfertOperation) ope).getCompteCibleId());
				} catch (final ComptaException e) {
					txt = "ERROR";
				}
			}
		}
		cell.setText(txt);
	}

}
