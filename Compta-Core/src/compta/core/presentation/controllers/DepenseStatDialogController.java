package compta.core.presentation.controllers;

import java.util.Collection;

public interface DepenseStatDialogController {

	/**
	 * Retourne la valeur moyenne des dépenses de la catégorie passée en paramètre
	 * @param cat
	 * @return
	 */
	double getMean(String cat);

	/**
	 * Retourne le total des dépenses de la catégorie passée en paramètre
	 * @param cat
	 * @return
	 */
	double getTotal(String cat);

	/**
	 * Retourne les catégories dont les données sont disponibles
	 * @return
	 */
	 Collection<String> getCategorieData();

}
