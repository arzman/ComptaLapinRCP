package compta.core.presentation.composite;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.common.ApplicationFormatter;
import compta.core.domaine.operation.OperationType;
import compta.core.presentation.contentprovider.operation.DepenseCP;
import compta.core.presentation.contentprovider.operation.RessourceCP;
import compta.core.presentation.contentprovider.operation.TransfertCP;
import compta.core.presentation.controllers.ExerciceViewController;
import compta.core.presentation.handlers.action.operation.CreateDepenseAction;
import compta.core.presentation.handlers.action.operation.CreateRessourceAction;
import compta.core.presentation.handlers.action.operation.CreateTransfertAction;
import compta.core.presentation.handlers.action.operation.EditDepenseAction;
import compta.core.presentation.handlers.action.operation.EditRessourceAction;
import compta.core.presentation.handlers.action.operation.EditTransfertAction;
import compta.core.presentation.handlers.action.operation.RemoveOperationAction;
import compta.core.presentation.handlers.doubleclicklistener.OperationDoubleClickListener;
import compta.core.presentation.labelprovider.operation.CategorieDepenseLP;
import compta.core.presentation.labelprovider.operation.CompteCibleTransfertLP;
import compta.core.presentation.labelprovider.operation.CompteSourceTransfertLP;
import compta.core.presentation.labelprovider.operation.MontantOperationLP;
import compta.core.presentation.labelprovider.operation.NomOperationLP;
import compta.core.presentation.viewersorter.DepenseViewerSorter;
import compta.core.presentation.viewersorter.RessourceViewerSorter;
import compta.core.presentation.viewersorter.TransfertViewerSorter;

public class ExerciceMensuelComposite extends Composite {

	/**
	 * Conteneur principal
	 */
	private final Group _mainGroup;

	private Group _grpDpenses;

	private final int _numMoi;

	private Group _grpRessources;

	private Group _grpTransfert;

	private TableViewer _tableViewerDepense;

	private TableViewer _tableViewerRessource;

	private TableViewer _tableViewerTransfert;

	private DepenseViewerSorter _depenseComparator;

	private RessourceViewerSorter _ressourceComparator;

	private TransfertViewerSorter _transfertComparator;

	/**
	 * Constructeur par défaut
	 * 
	 * @param parent_
	 *            le parent du {@link ExerciceMensuelComposite}
	 */
	public ExerciceMensuelComposite(Composite parent_, int numMoi) {

		super(parent_, SWT.NONE);
		setLayout(new FillLayout());

		_numMoi = numMoi;

		final ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		_mainGroup = new Group(scrolledComposite, SWT.SHADOW_ETCHED_OUT);

		_mainGroup.setLayout(new GridLayout(1, false));
		_mainGroup.setText(ExerciceViewController.getInstance().getLabelForExercice(numMoi));

		
		createDepenseGroup();

		createRessourcesGroup();

		createTransfertGroup();

		scrolledComposite.setContent(_mainGroup);
		scrolledComposite.setMinSize(_mainGroup.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		updateDepenseLabel();

		updateRessourceLabel();

		updateTransfertLabel();

		createContextMenu();

		createSorter();
	}

	private void createContextMenu() {

		final MenuManager managerDepense = new MenuManager();
		final Menu menuDepense = managerDepense.createContextMenu(_tableViewerDepense.getControl());
		_tableViewerDepense.getControl().setMenu(menuDepense);

		managerDepense.add(new CreateDepenseAction(_numMoi));
		managerDepense.add(new EditDepenseAction(_tableViewerDepense));
		managerDepense.add(new RemoveOperationAction(_tableViewerDepense));

		final MenuManager managerRessource = new MenuManager();
		final Menu menuRessource = managerRessource.createContextMenu(_tableViewerRessource.getControl());
		_tableViewerRessource.getControl().setMenu(menuRessource);

		managerRessource.add(new CreateRessourceAction(_numMoi));
		managerRessource.add(new EditRessourceAction(_tableViewerRessource));
		managerRessource.add(new RemoveOperationAction(_tableViewerRessource));

		final MenuManager managerTransfert = new MenuManager();
		final Menu menuTransfert = managerTransfert.createContextMenu(_tableViewerTransfert.getControl());
		_tableViewerTransfert.getControl().setMenu(menuTransfert);

		managerTransfert.add(new CreateTransfertAction(_numMoi));
		managerTransfert.add(new EditTransfertAction(_tableViewerTransfert));
		managerTransfert.add(new RemoveOperationAction(_tableViewerTransfert));

	}

	private void createDepenseGroup() {

		// Partie Depense
		_grpDpenses = new Group(_mainGroup, SWT.NONE);
		_grpDpenses.setLayout(new FillLayout(SWT.HORIZONTAL));
		_grpDpenses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		_grpDpenses.setText("D\u00E9penses :");

		_tableViewerDepense = new TableViewer(_grpDpenses, SWT.FULL_SELECTION);
		final Table tableDepense = _tableViewerDepense.getTable();
		tableDepense.setHeaderVisible(true);
		tableDepense.setLinesVisible(true);

		_tableViewerDepense.setContentProvider(new DepenseCP(_numMoi));
		_tableViewerDepense.setInput(new Object());

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(_tableViewerDepense, SWT.NONE);
		final TableColumn nomColumnDepense = tableViewerColumn.getColumn();
		nomColumnDepense.setWidth(80);
		nomColumnDepense.setText("Nom");
		tableViewerColumn.setLabelProvider(new NomOperationLP());

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(_tableViewerDepense, SWT.NONE);
		final TableColumn montantColumnDepense = tableViewerColumn_1.getColumn();
		montantColumnDepense.setWidth(80);
		montantColumnDepense.setText("Montant");
		tableViewerColumn_1.setLabelProvider(new MontantOperationLP());

		final TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(_tableViewerDepense, SWT.NONE);
		final TableColumn tblclmnNewColumn = tableViewerColumn_4.getColumn();
		tblclmnNewColumn.setWidth(80);
		tblclmnNewColumn.setText("Cat\u00E9gorie");
		tableViewerColumn_4.setLabelProvider(new CategorieDepenseLP());

		_tableViewerDepense.addDoubleClickListener(new OperationDoubleClickListener());

		_tableViewerDepense.refresh();

	}

	private void createRessourcesGroup() {

		// Partie Ressources
		_grpRessources = new Group(_mainGroup, SWT.NONE);
		_grpRessources.setLayout(new FillLayout(SWT.HORIZONTAL));
		_grpRessources.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		_grpRessources.setText("Ressources :");

		_tableViewerRessource = new TableViewer(_grpRessources, SWT.FULL_SELECTION);
		final Table tableRessource = _tableViewerRessource.getTable();
		tableRessource.setLinesVisible(true);
		tableRessource.setHeaderVisible(true);
		_tableViewerRessource.setContentProvider(new RessourceCP(_numMoi));
		_tableViewerRessource.setInput(new Object());

		final TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(_tableViewerRessource, SWT.NONE);
		final TableColumn nomColumnRessource = tableViewerColumn_2.getColumn();
		nomColumnRessource.setWidth(80);
		nomColumnRessource.setText("Nom");
		tableViewerColumn_2.setLabelProvider(new NomOperationLP());

		final TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(_tableViewerRessource, SWT.NONE);
		final TableColumn tblclmnNewColumn_1 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_1.setWidth(80);
		tblclmnNewColumn_1.setText("Montant");
		tableViewerColumn_3.setLabelProvider(new MontantOperationLP());

		_grpTransfert = new Group(_mainGroup, SWT.NONE);
		_grpTransfert.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		_grpTransfert.setText("Transfert : ");
		_grpTransfert.setLayout(new FillLayout(SWT.HORIZONTAL));

		_tableViewerRessource.addDoubleClickListener(new OperationDoubleClickListener());

		_tableViewerRessource.refresh();

	}

	private void createSorter() {

		_depenseComparator = new DepenseViewerSorter();
		_tableViewerDepense.setSorter(_depenseComparator);

		int index = 0;
		for (final TableColumn col : _tableViewerDepense.getTable().getColumns()) {
			col.addSelectionListener(getSelectionAdapterDepense(col, index));
			index++;
		}

		_ressourceComparator = new RessourceViewerSorter();
		_tableViewerRessource.setSorter(_ressourceComparator);

		index = 0;
		for (final TableColumn col : _tableViewerRessource.getTable().getColumns()) {
			col.addSelectionListener(getSelectionAdapterRessource(col, index));
			index++;
		}

		_transfertComparator = new TransfertViewerSorter();
		_tableViewerTransfert.setSorter(_transfertComparator);

		index = 0;
		for (final TableColumn col : _tableViewerTransfert.getTable().getColumns()) {
			col.addSelectionListener(getSelectionAdapterTransfert(col, index));
			index++;
		}

	}

	private void createTransfertGroup() {

		// Partie transfert
		_tableViewerTransfert = new TableViewer(_grpTransfert, SWT.FULL_SELECTION);
		final Table _table = _tableViewerTransfert.getTable();
		_table.setLinesVisible(true);
		_table.setHeaderVisible(true);
		_tableViewerTransfert.setContentProvider(new TransfertCP(_numMoi));
		_tableViewerTransfert.setInput(new Object());

		final TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(_tableViewerTransfert, SWT.NONE);
		final TableColumn tblclmnNom = tableViewerColumn_5.getColumn();
		tblclmnNom.setWidth(80);
		tblclmnNom.setText("Nom");
		tableViewerColumn_5.setLabelProvider(new NomOperationLP());

		final TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(_tableViewerTransfert, SWT.NONE);
		final TableColumn tblclmnMontant = tableViewerColumn_6.getColumn();
		tblclmnMontant.setWidth(80);
		tblclmnMontant.setText("Montant");
		tableViewerColumn_6.setLabelProvider(new MontantOperationLP());

		final TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(_tableViewerTransfert, SWT.NONE);
		final TableColumn tblclmnNewColumn_2 = tableViewerColumn_8.getColumn();
		tblclmnNewColumn_2.setWidth(80);
		tblclmnNewColumn_2.setText("Compte Source");
		tableViewerColumn_8.setLabelProvider(new CompteSourceTransfertLP());

		final TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(_tableViewerTransfert, SWT.NONE);
		final TableColumn tblclmnCompteCible = tableViewerColumn_7.getColumn();
		tblclmnCompteCible.setWidth(80);
		tblclmnCompteCible.setText("Compte Cible");
		tableViewerColumn_7.setLabelProvider(new CompteCibleTransfertLP());

		_tableViewerTransfert.addDoubleClickListener(new OperationDoubleClickListener());

		_tableViewerTransfert.refresh();

	}

	private SelectionAdapter getSelectionAdapterDepense(final TableColumn column, final int index) {
		final SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				_depenseComparator.setColumn(index);
				final int dir = _depenseComparator.getDirection();
				_tableViewerDepense.getTable().setSortDirection(dir);
				_tableViewerDepense.getTable().setSortColumn(column);
				_tableViewerDepense.refresh();
			}
		};
		return selectionAdapter;
	}

	private SelectionListener getSelectionAdapterRessource(final TableColumn col, final int index) {
		final SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				_ressourceComparator.setColumn(index);
				final int dir = _ressourceComparator.getDirection();
				_tableViewerRessource.getTable().setSortDirection(dir);
				_tableViewerRessource.getTable().setSortColumn(col);
				_tableViewerRessource.refresh();
			}
		};
		return selectionAdapter;
	}

	private SelectionListener getSelectionAdapterTransfert(final TableColumn col, final int index) {
		final SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				_transfertComparator.setColumn(index);
				final int dir = _transfertComparator.getDirection();
				_tableViewerTransfert.getTable().setSortDirection(dir);
				_tableViewerTransfert.getTable().setSortColumn(col);
				_tableViewerTransfert.refresh();
			}
		};
		return selectionAdapter;
	}

	public Table getTableDepense() {

		return _tableViewerDepense.getTable();
	}

	public Table getTableRessource() {

		return _tableViewerRessource.getTable();
	}

	public Table getTableTransfert() {

		return _tableViewerTransfert.getTable();
	}

	/**
	 * MaJ de l'opération dans le tableau et du label des groupes.
	 * 
	 * @param id
	 */
	public void refreshOperation(OperationIdentifier id) {

		if (id.getType() == OperationType.TRANSFERT) {
			updateTransfertLabel();
			_tableViewerTransfert.refresh(id);
		}
		if (id.getType() == OperationType.DEPENSE) {
			updateDepenseLabel();
			_tableViewerDepense.refresh(id);
		}
		if (id.getType() == OperationType.RESSOURCE) {
			updateRessourceLabel();
			_tableViewerRessource.refresh(id);
		}

	}

	/**
	 * MaJ des tableaux et du label des groupes.
	 */
	public void refreshViewer() {

		updateDepenseLabel();
		updateRessourceLabel();
		updateTransfertLabel();

		_tableViewerTransfert.refresh();

		_tableViewerDepense.refresh();

		_tableViewerRessource.refresh();

	}

	private void updateDepenseLabel() {

		_grpDpenses.setText("Dépenses : "
				+ ApplicationFormatter.montantFormat.format(ExerciceViewController.getInstance().getTotalDepense(_numMoi)));
	}

	private void updateRessourceLabel() {

		_grpRessources.setText("Ressources : "
				+ ApplicationFormatter.montantFormat.format(ExerciceViewController.getInstance().getTotalRessource(_numMoi)));
	}

	/**
	 * 
	 */
	private void updateTransfertLabel() {

		_grpTransfert.setText("Transfert : "
				+ ApplicationFormatter.montantFormat.format(ExerciceViewController.getInstance().getTotalTransfert(_numMoi)));
	}
}
