
package compta.core.presentation.labelprovider.template;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.template.TrimestreTemplateElement;
import compta.core.common.ApplicationFormatter;

public class MontantTemplateTrimestreEltCellLP extends CellLabelProvider {

	@Override
	public void update(ViewerCell cell) {

		double txt = 0.0;

		if (cell.getElement() instanceof TrimestreTemplateElement) {

			if (((TrimestreTemplateElement) cell.getElement()).getOperation() != null) {
				txt = ((TrimestreTemplateElement) cell.getElement()).getOperation().getMontant();
			}
		}

		cell.setText(ApplicationFormatter.montantFormat.format(txt));
	}

}
