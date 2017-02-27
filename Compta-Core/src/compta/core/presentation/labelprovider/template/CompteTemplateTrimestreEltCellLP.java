package compta.core.presentation.labelprovider.template;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.manager.CompteManager;
import compta.core.application.template.TrimestreTemplateElement;
import compta.core.common.ComptaException;
import compta.core.domaine.operation.Operation;

public class CompteTemplateTrimestreEltCellLP extends CellLabelProvider {

	@Override
	public void update(ViewerCell cell) {

		String txt = "ERROR";

		if (cell.getElement() instanceof TrimestreTemplateElement) {

			final Operation ope = ((TrimestreTemplateElement) cell.getElement()).getOperation();
			if ((ope != null) && (ope.getCompteId() != null)) {
				try {
					txt = CompteManager.getInstance().getNomCompte(ope.getCompteId());
				} catch (final ComptaException e) {
					txt = "ERROR";
				}
			}
		}

		cell.setText(txt);
	}

}
