package compta.core.presentation.labelprovider.template;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.template.TrimestreTemplateElement;
import compta.core.domaine.operation.DepenseOperation;
import compta.core.domaine.operation.Operation;

public class CategorieTemplateTrimestreEltCellLP extends CellLabelProvider {

	@Override
	public void update(final ViewerCell cell) {

		String txt = "ERROR";

		if (cell.getElement() instanceof TrimestreTemplateElement) {

			final Operation ope = ((TrimestreTemplateElement) cell.getElement()).getOperation();
			if ((ope != null) && (ope instanceof DepenseOperation)) {
				txt = ((DepenseOperation) ope).getCategorie();
			}
		}
		cell.setText(txt);
	}

}
