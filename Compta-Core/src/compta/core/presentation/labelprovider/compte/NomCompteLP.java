
package compta.core.presentation.labelprovider.compte;

import org.eclipse.jface.viewers.LabelProvider;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CompteManager;
import compta.core.common.ComptaException;

public class NomCompteLP extends LabelProvider {

	@Override
	public String getText(Object element) {

		String txt;

		try {
			txt = CompteManager.getInstance().getNomCompte((Identifier) element);
		} catch (final ComptaException e) {
			txt = "ERROR";
		}

		return txt;
	}

}
