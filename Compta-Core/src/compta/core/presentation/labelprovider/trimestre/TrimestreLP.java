package compta.core.presentation.labelprovider.trimestre;

import java.util.Calendar;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ApplicationFormatter;

public class TrimestreLP implements ILabelProvider {

	@Override
	public void addListener(final ILabelProviderListener listener_) {
		

	}

	@Override
	public void dispose() {
		

	}

	@Override
	public Image getImage(final Object element_) {
		
		return null;
	}

	@Override
	public String getText(final Object element_) {

		String res = "";
		if (element_ instanceof Identifier) {
			final Calendar dateDebut = TrimestreManager.getInstance().getDateDebutForExerciceIdendifier((Identifier) element_);
			res = ApplicationFormatter.moiAnneedateFormat.format(dateDebut.getTime());

			res = res + " -> ";

			final Calendar dateFin = TrimestreManager.getInstance().getDateFinForExerciceIdendifier((Identifier) element_);
			res = res + ApplicationFormatter.moiAnneedateFormat.format(dateFin.getTime());
		}

		return res;
	}

	@Override
	public boolean isLabelProperty(final Object element_, final String property_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(final ILabelProviderListener listener_) {
		// TODO Auto-generated method stub

	}

}
