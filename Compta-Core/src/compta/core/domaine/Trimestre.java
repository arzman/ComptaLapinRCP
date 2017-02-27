/**
 * 
 */
package compta.core.domaine;

import java.util.Calendar;

/**
 * Exerice comptable comprennant plusieurs {@link ExerciceMensuel}.
 * 
 * @author Arthur
 * 
 */
public class Trimestre {

	/**
	 * Les exercices mensuels.
	 */
	private ExerciceMensuel[] _exerciceMensuelTable;

	/**
	 * Constructeur par d�faut
	 */
	public Trimestre() {
		setExerciceMensuel(new ExerciceMensuel[3]);

	}

	public Calendar getDateDebut() {

		return getExerciceMensuel()[0].getDateDebut();
	}

	// GETTER'N'SETTER -------------------------------------------------

	public Calendar getDateFin() {
		return getExerciceMensuel()[2].getDateDebut();
	}

	public ExerciceMensuel[] getExerciceMensuel() {
		return _exerciceMensuelTable;
	}

	public void setExerciceMensuel(ExerciceMensuel[] exerciceMensuel) {
		this._exerciceMensuelTable = exerciceMensuel;
	}

}
