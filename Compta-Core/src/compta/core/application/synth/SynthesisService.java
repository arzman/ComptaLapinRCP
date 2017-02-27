package compta.core.application.synth;

import java.util.Calendar;
import java.util.HashMap;

import org.eclipse.core.runtime.Status;

import compta.core.Activator;
import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.application.stat.StatistiqueService;
import compta.core.common.ComptaException;
import compta.core.domaine.ExerciceMensuel;
import compta.core.domaine.Trimestre;

public class SynthesisService {

	public static HashMap<Calendar, HashMap<String, Double[]>> getSynthDataForYear(Calendar year) {

		HashMap<Calendar, HashMap<String, Double[]>> res = new HashMap<>();

		try{
		
		for (Identifier trimId : TrimestreManager.getInstance().getTrimestreIdentifierSet()) {

			int indexMois = 0;
			Trimestre trim = TrimestreManager.getInstance().getTrimestre(trimId);
			for (ExerciceMensuel mois : trim.getExerciceMensuel()) {

				if (mois.getDateDebut().get(Calendar.YEAR) == year.get(Calendar.YEAR)) {

					Calendar debMoi = (Calendar) mois.getDateDebut().clone();
					debMoi.set(Calendar.DAY_OF_MONTH, 1);
					res.put(debMoi, StatistiqueService.getStatDataForMonth(trimId, indexMois));
				}
				indexMois++;
			}

		}
		
		}catch(ComptaException e){
			Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, "Erreur lors d'une synthèse"));
		}

		return res;
	}

}
