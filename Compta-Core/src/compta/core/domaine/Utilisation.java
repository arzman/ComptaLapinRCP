package compta.core.domaine;

import java.util.Calendar;

/**
 * Mod�lise une utilisation d'un budget. Soit une d�pense (libell�) d'un montant � une date
 * @author Arthur
 *
 */
public class Utilisation {

	/**
	 * le montant
	 */
	private double _montant;
	
	/**
	 * le libell�
	 */
	private String _libelle;
	
	/**
	 * la date
	 */
	private Calendar _date;
	
	/**
	 * Constructeur de l'utilisation
	 * @param montant le montant utilis�
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
