package compta.core.application.stat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.Status;

import compta.core.Activator;
import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.common.ComptaException;
import compta.core.domaine.ExerciceMensuel;
import compta.core.domaine.Trimestre;
import compta.core.domaine.operation.DepenseOperation;

public class StatistiqueService {

	public static HashMap<String, Double[]> getStatDataForMonth(Identifier trimId, int i) {

		HashMap<String, Double[]> res = new HashMap<>();

		HashMap<String, List<Double>> tmp = new HashMap<>();

		try {

			Trimestre trim = TrimestreManager.getInstance().getTrimestre(trimId);

			// pré-process
			for (DepenseOperation dep : trim.getExerciceMensuel()[i].getDepensesList()) {

				String cat = dep.getCategorie();

				if (!tmp.containsKey(cat)) {
					tmp.put(cat, new ArrayList<Double>());
				}

				tmp.get(cat).add(dep.getMontant());
			}

		} catch (ComptaException e) {
			Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, "Erreur lors d'un calcul de stat mensuelle"));
		}

		// process
		for (String cat : tmp.keySet()) {

			double sum = 0;

			for (Double mnt : tmp.get(cat)) {
				sum = sum + mnt;
			}

			double moy = sum / tmp.get(cat).size();

			res.put(cat, new Double[] { moy, sum });

		}

		return res;
	}

	public static HashMap<String, Double[]> getStatDataForYear(Calendar year) {

		HashMap<String, Double[]> res = new HashMap<>();

		HashMap<String, List<Double>> tmp = new HashMap<>();

		for (Trimestre trim : TrimestreManager.getInstance().getTrimestreSet()) {

			for (ExerciceMensuel mois : trim.getExerciceMensuel()) {

				if (mois.getDateDebut().get(Calendar.YEAR) == year.get(Calendar.YEAR)) {

					for (DepenseOperation dep : mois.getDepensesList()) {

						String cat = dep.getCategorie();

						if (!tmp.containsKey(cat)) {
							tmp.put(cat, new ArrayList<Double>());
						}
						tmp.get(cat).add(dep.getMontant());
					}

				}

			}

		}

		// process
		for (String cat : tmp.keySet()) {

			double sum = 0;

			for (Double mnt : tmp.get(cat)) {
				sum = sum + mnt;
			}

			double moy = sum / tmp.get(cat).size();

			res.put(cat, new Double[] { moy, sum });

		}

		return res;
	}

}
