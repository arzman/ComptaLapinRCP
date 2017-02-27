package compta.core.presentation.labelprovider.operation;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

public class CategorieCellLP extends CellLabelProvider {

	@Override
	public void update(ViewerCell cell) {

		if (cell.getElement() instanceof String) {
			cell.setText((String) cell.getElement());

		}

	}

}
