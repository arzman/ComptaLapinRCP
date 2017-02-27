package compta.core.presentation.labelprovider.trimestre;

import java.util.Calendar;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationFormatter;

public class TrimestreCellLP extends CellLabelProvider {

	@Override
	public void update(ViewerCell cell) {
		
		String res = "";
		if (cell.getElement() instanceof Identifier) {
			final Calendar dateDebut = TrimestreManager.getInstance().getDateDebutForExerciceIdendifier((Identifier) cell.getElement());
			
			res = ApplicationFormatter.moiAnneedateFormat.format(dateDebut.getTime());

			res = res + " -> ";

			final Calendar dateFin = TrimestreManager.getInstance().getDateFinForExerciceIdendifier((Identifier) cell.getElement());
			res = res + ApplicationFormatter.moiAnneedateFormat.format(dateFin.getTime());
		}

		cell.setText(res);

	}

}
