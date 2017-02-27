package compta.core.application.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.runtime.Platform;

import com.thoughtworks.xstream.XStream;

import compta.core.application.identifier.Identifier;
import compta.core.application.template.TrimestreTemplate;
import compta.core.application.template.TrimestreTemplateElement;
import compta.core.common.ComptaException;
import compta.core.domaine.Budget;
import compta.core.domaine.Compte;
import compta.core.domaine.ExerciceMensuel;
import compta.core.domaine.Trimestre;
import compta.core.domaine.operation.DepenseOperation;
import compta.core.domaine.operation.Operation;
import compta.core.domaine.operation.TransfertOperation;

public class DataManager {

	private static DataManager _instance;

	/**
	 * Retourne l'instance du singleton
	 * 
	 * @return l'instance du singleton
	 */
	public static DataManager getInstance() {

		if (_instance == null) {
			_instance = new DataManager();
		}

		return _instance;
	}

	private final String DATA_PATH = Platform.getInstanceLocation().getURL().getPath();

	private final String CATEGORIE_PATH = DATA_PATH + File.separator + "categories.xml";

	private final String COMPTE_PATH = DATA_PATH + File.separator + "comptes.xml";

	private final String IDCOURANT_PATH = DATA_PATH + File.separator + "idCourant.xml";

	private final String TRIMESTRE_TEMP_PATH = DATA_PATH + File.separator + "trimestreTemplate.xml";

	private final String TRIMESTRE_PATH = DATA_PATH + File.separator + "trimestres.xml";

	private final String BUDGET_TRI_PATH = DATA_PATH + File.separator + "budgets_trie.xml";

	private final String BUDGET_PATH = DATA_PATH + File.separator + "budgets.xml";

	/**
	 * Le sérializeur !!!!!
	 */
	private final XStream _xstream;

	private DataManager() {

		_xstream = new XStream();
		
		_xstream.alias("Identifier", Identifier.class);
		_xstream.alias("Trimestre", Trimestre.class);
		_xstream.alias("Budget", Budget.class);
		_xstream.alias("Compte", Compte.class);
		_xstream.alias("Depense", DepenseOperation.class);
		_xstream.alias("Transfert", TransfertOperation.class);
		_xstream.alias("Ressource", Operation.class);
		_xstream.alias("TrimestreTemplateElement", TrimestreTemplateElement.class);
		_xstream.alias("TrimestreTemplate", TrimestreTemplate.class);
		_xstream.alias("ExerciceMensuel", ExerciceMensuel.class);
		
		
		
	}

	public void exportContext(String path) {

		try {
			final String zipFile = path;
			final String[] sourceFiles = { BUDGET_PATH, BUDGET_TRI_PATH, CATEGORIE_PATH, COMPTE_PATH, IDCOURANT_PATH, TRIMESTRE_PATH,
					TRIMESTRE_TEMP_PATH };

			// create byte buffer
			final byte[] buffer = new byte[1024];

			// create object of FileOutputStream
			final FileOutputStream fout = new FileOutputStream(zipFile);

			// create object of ZipOutputStream from FileOutputStream
			final ZipOutputStream zout = new ZipOutputStream(fout);

			for (int i = 0; i < sourceFiles.length; i++) {

				final File f = new File(sourceFiles[i]);

				if (f.exists()) {

					// create object of FileInputStream for source file
					final FileInputStream fin = new FileInputStream(f.getAbsolutePath());
					zout.putNextEntry(new ZipEntry(f.getName()));

					int length;

					while ((length = fin.read(buffer)) > 0) {
						zout.write(buffer, 0, length);
					}

					zout.closeEntry();
					fin.close();
				}

			}
			zout.close();

		} catch (final IOException ioe) {
			System.out.println("IOException :" + ioe);
		}
	}

	public void importContext(String filePath) {

		final byte[] buffer = new byte[1024];

		// create output directory is not exists
		final File folder = new File(DATA_PATH);
		if (!folder.exists()) {
			folder.mkdir();
		}

		// get the zip file content
		ZipEntry ze = null;
		ZipInputStream zis = null;

		try {
			zis = new ZipInputStream(new FileInputStream(filePath));
			// get the zipped file list entry
			ze = zis.getNextEntry();
		} catch (final IOException e) {

		}

		while (ze != null) {

			try {
				final String fileName = ze.getName();
				final File newFile = new File(DATA_PATH + File.separator + fileName);
				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder
				new File(newFile.getParent()).mkdirs();

				final FileOutputStream fos = new FileOutputStream(newFile);

				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
			} catch (final IOException ex) {

			}
		}

		try {
			zis.closeEntry();
			zis.close();
		} catch (final IOException e) {

		}

	}

	@SuppressWarnings("unchecked")
	public Map<Identifier, Budget> loadBudgetMap() throws ComptaException {
		HashMap<Identifier, Budget> res = null;

		try {

			final Object obj = readGenericObjet(BUDGET_PATH);

			res = (HashMap<Identifier, Budget>) obj;

		} catch (final Exception e) {
			throw new ComptaException("Impossible de récupérer les budgets", e);
		}

		return res;
	}

	/**
	 * Charge la liste des catégories de dépenses
	 * 
	 * @return
	 * @throws ComptaException
	 */
	@SuppressWarnings("unchecked")
	public List<String> loadCategorieList() throws ComptaException {

		List<String> res = null;

		try {

			final Object obj = readGenericObjet(CATEGORIE_PATH);
			res = (List<String>) obj;

		} catch (final Exception e) {
			throw new ComptaException("Impossible de récupérer les catégories", e);
		}
		
		if(res==null){
			res = new ArrayList<String>(); 
		}

		return res;
	}

	/**
	 * Charge les comptes
	 * 
	 * @return
	 * @throws ComptaException
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Identifier, Compte> loadCompteMap() throws ComptaException {

		HashMap<Identifier, Compte> res = null;

		try {

			final Object obj = readGenericObjet(COMPTE_PATH);

			res = (HashMap<Identifier, Compte>) obj;

		} catch (final Exception e) {
			throw new ComptaException("Impossible de récupérer les comptes", e);
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Identifier> loadSortedBudgetList() throws ComptaException {

		ArrayList<Identifier> res = null;

		try {

			final Object obj = readGenericObjet(BUDGET_TRI_PATH);

			res = (ArrayList<Identifier>) obj;

		} catch (final Exception e) {
			throw new ComptaException("Impossible de récupérer les budgets triés", e);
		}

		return res;
	}

	/**
	 * Charge les exercices
	 * 
	 * @return
	 * @throws ComptaException
	 */
	@SuppressWarnings("unchecked")
	public Map<Identifier, Trimestre> loadTrimestreMap() throws ComptaException {

		Map<Identifier, Trimestre> res = null;

		try {

			final Object obj = readGenericObjet(TRIMESTRE_PATH);

			res = (Map<Identifier, Trimestre>) obj;

		} catch (final Exception e) {
			throw new ComptaException("Impossible de récupérer les exercices", e);
		}

		return res;
	}

	/**
	 * Charge le template des trimestres
	 * 
	 * @return
	 * @throws ComptaException
	 */
	public TrimestreTemplate loadTrimestreTemplate() throws ComptaException {

		TrimestreTemplate res = null;

		try {

			final Object obj = readGenericObjet(TRIMESTRE_TEMP_PATH);
			res = (TrimestreTemplate) obj;

		} catch (final Exception e) {
			throw new ComptaException("Impossible de récupérer le template des trimestres", e);
		}

		return res;

	}

	/**
	 * Charge le trimestre courant
	 * 
	 * @return
	 * @throws ComptaException
	 */
	public Identifier loadTrimstreCourantId() throws ComptaException {

		Identifier id = null;

		try {

			id = (Identifier) readGenericObjet(IDCOURANT_PATH);
		} catch (final Exception e) {
			throw new ComptaException("Impossible de récupérer l'identifiant courant", e);
		}

		return id;
	}

	/**
	 * Charge un objet Java
	 * 
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private Object readGenericObjet(final String path) throws FileNotFoundException, IOException, ClassNotFoundException {

		ObjectInputStream in = null;
		Object result = null;
		File file = new File(path);
		if (file.exists()) {
			in = _xstream.createObjectInputStream(new FileReader(path));
			result = in.readObject();
			in.close();
		}

		return result;

	}

	public void saveBudgetMap(Map<Identifier, Budget> budgetMap) throws ComptaException {

		try {

			saveGenericObjet(budgetMap, BUDGET_PATH);

		} catch (final Exception e) {
			throw new ComptaException("Impossible de sauver les budgets", e);
		}

	}

	public void saveBudgetTriList(ArrayList<Identifier> budgetList) throws ComptaException {

		try {

			saveGenericObjet(budgetList, BUDGET_TRI_PATH);

		} catch (final Exception e) {
			throw new ComptaException("Impossible de sauver les budgets triés", e);
		}

	}

	/**
	 * Sauve les exercices
	 * 
	 * @param map
	 * @throws ComptaException
	 */
	public void saveCategorieList(final List<String> list) throws ComptaException {

		try {

			saveGenericObjet(list, CATEGORIE_PATH);

		} catch (final Exception e) {
			throw new ComptaException("Impossible de sauver les catégories", e);
		}
	}

	/**
	 * Sauve les comptes
	 * 
	 * @param map
	 * @throws ComptaException
	 */
	public void saveCompteMap(final HashMap<Identifier, Compte> map) throws ComptaException {

		try {

			saveGenericObjet(map, COMPTE_PATH);

		} catch (final Exception e) {
			throw new ComptaException("Impossible de sauver les comptes", e);
		}
	}

	public void saveExcericeCourant(final Identifier exerciceMMCourantId_) throws ComptaException {

		try {

			saveGenericObjet(exerciceMMCourantId_, IDCOURANT_PATH);
		} catch (final Exception e) {
			throw new ComptaException("Impossible de sauvegarder l'identifiant courant", e);
		}

	}

	/**
	 * Sauve un objet Java
	 * 
	 * @param object
	 * @param path
	 * @throws IOException
	 */
	private void saveGenericObjet(final Object object, final String path) throws IOException {

		if (object != null) {
			ObjectOutputStream out = null;
			out = _xstream.createObjectOutputStream(new FileWriter(path));
			out.writeObject(object);
			out.flush();
			out.close();
		}

	}

	/**
	 * Sauve les exercices
	 * 
	 * @param map
	 * @throws ComptaException
	 */
	public void saveTrimestreMap(final Map<Identifier, Trimestre> map) throws ComptaException {

		try {

			saveGenericObjet(map, TRIMESTRE_PATH);

		} catch (final Exception e) {
			throw new ComptaException("Impossible de sauver les exercices", e);
		}
	}

	/**
	 * Sauve le template des trimestres
	 * 
	 * @param trimestreTemplate
	 * @throws ComptaException
	 */
	public void saveTrimestreTemplate(final TrimestreTemplate trimestreTemplate) throws ComptaException {

		try {

			saveGenericObjet(trimestreTemplate, TRIMESTRE_TEMP_PATH);

		} catch (final Exception e) {
			throw new ComptaException("Impossible de sauver le template de trimestre", e);
		}

	}

	/**
	 * Exporte un trimestre dans un fichier
	 * 
	 * @param trim
	 * @param path
	 * @throws ComptaException
	 */
	public void exportTrimestre(Trimestre trim, String path) throws ComptaException {

		try {
			saveGenericObjet(trim, path);
		} catch (IOException e) {
			throw new ComptaException("Impossible d'exporter le trimestre", e);
		}

	}

	/**
	 * Crée un
	 * 
	 * @param path
	 * @return
	 * @throws ComptaException
	 */
	public Trimestre loadTrimestre(String path) throws ComptaException {

		Trimestre trim = null;

		try {

			Object obj = readGenericObjet(path);

			if (obj instanceof Trimestre) {
				trim = (Trimestre) obj;
			}

		} catch (ClassNotFoundException | IOException e) {
			throw new ComptaException("Impossible importer le trimestre", e);
		}

		return trim;
	}

}
