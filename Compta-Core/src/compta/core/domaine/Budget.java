/**
 * 
 */
package compta.core.domaine;

import java.util.ArrayList;

/**
 * Modélise un Budget. Soit un montant à atteindre et qui peut être utilisé. On
 * doit garder en mémoire les {@link Utilisation} qui ont été faite.
 * 
 * @author Arthur
 * 
 */
public class Budget {

	/**
	 * Montant totale du bugdet
	 */
	private double _objectif;

	/**
	 * Montant déjà utilisé
	 */
	private double _montantUtilise;

	/**
	 * Le nom du budget
	 */
	private String _nom;

	/**
	 * Les utilisations du budget
	 */
	private ArrayList<Utilisation> _utilisations;

	/**
	 * Constructeur
	 */
	public Budget() {

		_objectif = 0;
		_montantUtilise = 0;
		_nom = "Nouveau Budget";
		_utilisations = new ArrayList<>();
	}

	/**
	 * @return the montantUtilise
	 */
	public double getMontantUtilise() {
		return _montantUtilise;
	}

	public String getNom() {
		return _nom;
	}

	public double getObjectif() {
		return _objectif;
	}

	/**
	 * @param montantUtilise
	 *            the montantUtilise to set
	 */
	public void setMontantUtilise(double montantUtilise) {
		_montantUtilise = montantUtilise;
	}

	public void setNom(String nom) {
		_nom = nom;
	}

	public void setObjectif(double montantTotal) {
		_objectif = montantTotal;
	}

	/**
	 * Ajoute une {@link Utilisation} au budget
	 * 
	 * @param util
	 *            l'utilisation
	 */
	public void ajouteUtilisation(Utilisation util) {

		if (_utilisations == null) {
			_utilisations = new ArrayList<>();
		}
		_utilisations.add(util);
	}

	public ArrayList<Utilisation> getUtilisation() {
		return _utilisations;
	}

}
