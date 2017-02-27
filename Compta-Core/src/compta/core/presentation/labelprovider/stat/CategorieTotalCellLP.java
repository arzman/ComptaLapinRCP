package compta.core.presentation.labelprovider.stat;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.common.ApplicationFormatter;
import compta.core.presentation.controllers.DepenseStatDialogController;

public class CategorieTotalCellLP extends CellLabelProvider {

	private DepenseStatDialogController _controler;

	public CategorieTotalCellLP(DepenseStatDialogController controler) {

		_controler = controler;
	}

	@Override
	public void update(ViewerCell cell) {
		if (cell.getElement() instanceof String) {

			cell.setText(ApplicationFormatter.montantFormat.format(_controler.getTotal((String) cell.getElement())));

		}

	}

}
