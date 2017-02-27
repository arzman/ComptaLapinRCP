package compta.core.presentation.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.application.stat.StatistiqueService;

public class DepenseMensuelleStatDialogController implements DepenseStatDialogController {

	private HashMap<Calendar, HashMap<String, Double[]>> _dataMois;
	private Calendar _selectedMonth;

	public DepenseMensuelleStatDialogController() {

		_dataMois = new HashMap<Calendar, HashMap<String, Double[]>>();

		fillDatas();
	}

	private void fillDatas() {

		// on parcourt tout les trimestres de l'application
		for (Identifier trimId : TrimestreManager.getInstance().getTrimestreIdentifierSet()) {

			Calendar deb = TrimestreManager.getInstance().getDateDebutForExerciceIdendifier(trimId);
			// on parcourt tt les mois du trimestre

			for (int i = 0; i < 3; i++) {

				Calendar debMoi = (Calendar) deb.clone();
				debMoi.add(Calendar.MONTH, i);

				HashMap<String, Double[]> dataCate = StatistiqueService.getStatDataForMonth(trimId, i);
				_dataMois.put(debMoi, dataCate);

			}

		}

	}

	public Collection<String> getCategorieData() {

		ArrayList<String> tmp = new ArrayList<>();

		if (_selectedMonth != null) {
			
			for(Object obj : _dataMois.get(_selectedMonth).keySet()){
				if(obj instanceof String){
					tmp.add((String) obj);
				}
			}
			
		}

		return tmp;
	}

	public double getMean(String cate) {
		double res = 0.0;

		if (_selectedMonth != null) {
			res = _dataMois.get(_selectedMonth).get(cate)[0];
		}

		return res;
	}

	public double getTotal(String cate) {
		double res = 0.0;

		if (_selectedMonth != null) {
			res = _dataMois.get(_selectedMonth).get(cate)[1];
		}

		return res;
	}

	public Set<Calendar> getMois() {

		return _dataMois.keySet();
	}

	public void setSelectedMois(Calendar selected) {

		_selectedMonth = selected;

	}

}
