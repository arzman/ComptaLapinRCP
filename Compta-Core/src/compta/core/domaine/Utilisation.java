package compta.core.domaine;

import java.util.Calendar;

/**
 * Modélise une utilisation d'un budget. Soit une dépense (libellé) d'un montant à une date
 * @author Arthur
 *
 */
public class Utilisation {

	/**
	 * le montant
	 */
	private double _montant;
	
	/**
	 * le libellé
	 */
	private String _libelle;
	
	/**
	 * la date
	 */
	private Calendar _date;
	
	/**
	 * Constructeur de l'utilisation
	 * @param montant le montant utilisé
	 * @param libelle le nom de l'utilisation
	 * @param date la date de l'utilisation
	 */
	public Utilisation(double montant,String libelle,Calendar date) {
		
		montant = _montant;
		libelle = _libelle;
		date = _date;
	}

	public double getMontant() {
		return _montant;
	}

	public String getLibelle() {
		return _libelle;
	}

	public Calendar getDate() {
		return _date;
	}
}
