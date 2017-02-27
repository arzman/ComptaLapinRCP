package compta.core.presentation.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.application.synth.SynthesisService;
import compta.core.common.ComptaException;

public class DepenseAnnuelleSynthDialogController {

	private HashMap<Calendar, HashMap<Calendar, HashMap<String, Double[]>>> _data;

	private Calendar _selectedAnnee;

	private String _selectedCat;

	public DepenseAnnuelleSynthDialogController() {

		_data = new HashMap<Calendar, HashMap<Calendar, HashMap<String, Double[]>>>();

		fillData();

	}

	private void fillData() {

		for (final Identifier trimId : TrimestreManager.getInstance().getTrimestreIdentifierSet()) {

			try {
				for (int i = 0; i < 3; i++) {

					final Calendar debMoi = (Calendar) TrimestreManager.getInstance().getTrimestre(trimId).getExerciceMensuel()[i]
							.getDateDebut().clone();

					debMoi.set(debMoi.get(Calendar.YEAR), 1, 1, 0, 0, 0);
					debMoi.set(Calendar.MILLISECOND, 0);

					if (!_data.containsKey(debMoi)) {
						_data.put(debMoi, new HashMap<Calendar, HashMap<String, Double[]>>());
					}

				}
			} catch (ComptaException e) {
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Erreur",
						"Impossible de récupérer un trimestre");
			}

		}

		for (final Calendar year : _data.keySet()) {

			_data.put(year, SynthesisService.getSynthDataForYear(year));

		}

	}

	public void setSelectedAnnee(Calendar selected) {
		_selectedAnnee = selected;

	}

	public void setSelectedCategorie(String selected) {
		_selectedCat = selected;

	}

	public Set<Calendar> getAnneeSet() {

		return _data.keySet();
	}

	public List<String> getCategories() {

		ArrayList<String> res = new ArrayList<String>();

		if (_selectedAnnee != null) {

			for (HashMap<String, Double[]> map : _data.get(_selectedAnnee).values()) {

				for (String cat : map.keySet()) {

					if (!res.contains(cat)) {
						res.add(cat);
					}
				}
			}
		}

		return res;
	}

	public ArrayList<Calendar> getSortedMonth() {

		ArrayList<Calendar> month = new ArrayList<>();
		if (_selectedAnnee != null) {
			month.addAll(_data.get(_selectedAnnee).keySet());
			Collections.sort(month);
		}

		return month;
	}

	public Calendar getSelectedAnnee() {
		return _selectedAnnee;
	}

	public String getSelectedCategorie() {
		return _selectedCat;
	}

	public double getTotal(Calendar month) {

		double res = 0.0;

		if (_selectedAnnee != null && _selectedCat != null) {

			if (_data.get(_selectedAnnee).get(month).get(_selectedCat) != null) {
				res = _data.get(_selectedAnnee).get(month).get(_selectedCat)[1];
			}
		}

		return res;
	}

}
