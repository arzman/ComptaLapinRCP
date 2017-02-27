package compta.core.application.manager;

import java.util.ArrayList;
import java.util.List;

import compta.core.common.ComptaException;

public class CategorieManager {

	private static CategorieManager _instance;

	private List<String> _categorieList;

	/**
	 * Retourne l'instance du singleton
	 * 
	 * @return
	 */
	public static CategorieManager getInstance() {

		if (_instance == null) {
			_instance = new CategorieManager();
		}

		return _instance;
	}

	/**
	 * Constructeur du manager
	 */
	private CategorieManager() {

	}

	public void addCategorie(String cat) {

		getRealCategories().add(cat);

	}

	private List<String> getRealCategories() {

		if (_categorieList == null) {
			try {
				_categorieList = DataManager.getInstance().loadCategorieList();
			} catch (final ComptaException e) {
				_categorieList = new ArrayList<String>();
			}
		}
		return _categorieList;

	}

	public List<String> getCategories() {

		return new ArrayList<String>(getRealCategories());
	}

	public void saveAll() {
		try {
			DataManager.getInstance().saveCategorieList(getRealCategories());
		} catch (final ComptaException e) {
			e.printStackTrace();
		}
	}

}
