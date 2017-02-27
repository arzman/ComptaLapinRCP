
package compta.core.presentation.labelprovider.template;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.template.TrimestreTemplateElement;

public class NomTemplateTrimestreEltCellLP extends CellLabelProvider {

	@Override
	public void update(ViewerCell cell) {

		String txt = "ERROR";

		if (cell.getElement() instanceof TrimestreTemplateElement) {

			if (((TrimestreTemplateElement) cell.getElement()).getOperation() != null) {
				txt = ((TrimestreTemplateElement) cell.getElement()).getOperation().getNom();
			}
		}

		cell.setText(txt);
	}

}
