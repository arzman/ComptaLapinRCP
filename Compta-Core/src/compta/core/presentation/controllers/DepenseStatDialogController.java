package compta.core.presentation.controllers;

import java.util.Collection;

public interface DepenseStatDialogController {

	/**
	 * Retourne la valeur moyenne des d�penses de la cat�gorie pass�e en param�tre
	 * @param cat
	 * @return
	 */
	double getMean(String cat);

	/**
	 * Retourne le total des d�penses de la cat�gorie pass�e en param�tre
	 * @param cat
	 * @return
	 */
	double getTotal(String cat);

	/**
	 * Retourne les cat�gories dont les donn�es sont disponibles
	 * @return
	 */
	 Collection<String> getCategorieData();

}
