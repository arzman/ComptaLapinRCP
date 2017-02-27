package compta.core.presentation.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.application.stat.StatistiqueService;
import compta.core.common.ComptaException;

public class DepenseAnnuelleStatDialogController implements DepenseStatDialogController {

	private final HashMap<Calendar, HashMap<String, Double[]>> _dataAnnee;
	private Calendar _selectedYear;

	public DepenseAnnuelleStatDialogController() {

		_dataAnnee = new HashMap<Calendar, HashMap<String, Double[]>>();

		fillDatas();
	}

	/**
	 * Remplit les données
	 */
	private void fillDatas() {

		// on parcourt tout les trimestres de l'application
		for (final Identifier trimId : TrimestreManager.getInstance().getTrimestreIdentifierSet()) {

			try {
				for (int i = 0; i < 3; i++) {

					final Calendar debMoi = (Calendar) TrimestreManager.getInstance().getTrimestre(trimId).getExerciceMensuel()[i]
							.getDateDebut().clone();

					debMoi.set(debMoi.get(Calendar.YEAR), 1, 1, 0, 0, 0);
					debMoi.set(Calendar.MILLISECOND, 0);

					if (!_dataAnnee.containsKey(debMoi)) {
						_dataAnnee.put(debMoi, new HashMap<String, Double[]>());
					}

				}
			} catch (ComptaException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
						"Impossible de récupérer un trimestre");
			}

		}

		for (final Calendar year : _dataAnnee.keySet()) {

			_dataAnnee.put(year, StatistiqueService.getStatDataForYear(year));

		}

	}

	/**
	 * Retourne les années dont les données sont disponibles
	 * 
	 * @return
	 */
	public Set<Calendar> getAnnee() {

		return _dataAnnee.keySet();
	}

	@Override
	public Collection<String> getCategorieData() {

		final ArrayList<String> res = new ArrayList<>();

		if (_selectedYear != null) {
			for (final Object obj : _dataAnnee.get(_selectedYear).keySet()) {

				if (obj instanceof String) {

					res.add((String) obj);

				}

			}

		}

		return res;
	}

	@Override
	public double getMean(String cate) {
		double res = 0.0;

		if (_selectedYear != null) {
			res = _dataAnnee.get(_selectedYear).get(cate)[0];
		}

		return res;
	}

	@Override
	public double getTotal(String cate) {
		double res = 0.0;

		if (_selectedYear != null) {
			res = _dataAnnee.get(_selectedYear).get(cate)[1];
		}

		return res;
	}

	/**
	 * Change l'année sélectionnée
	 * 
	 * @param selected
	 */
	public void setSelectedAnnee(Calendar selected) {

		_selectedYear = selected;

	}

}
