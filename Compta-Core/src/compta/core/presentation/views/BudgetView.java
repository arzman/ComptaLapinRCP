package compta.core.presentation.views;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import compta.core.presentation.contentprovider.budget.BudgetCP;
import compta.core.presentation.controllers.BudgetViewController;
import compta.core.presentation.controllers.ControllerEvent;
import compta.core.presentation.handlers.action.budget.AddUtiliseInBudgetAction;
import compta.core.presentation.handlers.action.budget.EditBudgetAction;
import compta.core.presentation.handlers.action.budget.RemoveBudgetAction;
import compta.core.presentation.labelprovider.budget.AvancementBudgetCellLP;
import compta.core.presentation.labelprovider.budget.MontantCourantBudgetCellLP;
import compta.core.presentation.labelprovider.budget.MontantLivretBudgetCellLP;
import compta.core.presentation.labelprovider.budget.NomBudgetCellLP;
import compta.core.presentation.labelprovider.budget.ObjectifBudgetCellLP;
import compta.core.presentation.labelprovider.budget.UtiliseBudgetCellLP;
import compta.core.presentation.viewersorter.BudgetViewerSorter;

public class BudgetView extends ViewPart implements ControlledView {

	/**
	 * Clé d'enregistrement des préférences
	 */
	private static final String MEMENTO_COL = "budget.column.";
	
	/**
	 * ID de la vue
	 */
	public static String ID = "Compta.view.BudgetView";

	/**
	 * Le viewer
	 */
	private TableViewer tableViewer;
	
	private BudgetViewerSorter comparator;
	private IMemento _memento;

	public BudgetView() {

		BudgetViewController.getInstance().addControlledView(this);

	}

	private void createContextMenu() {

		final MenuManager manager = new MenuManager();
		final Menu menu = manager.createContextMenu(tableViewer.getControl());

		tableViewer.getControl().setMenu(menu);

		manager.add(new AddUtiliseInBudgetAction(tableViewer));
		manager.add(new EditBudgetAction(tableViewer));
		manager.add(new RemoveBudgetAction(tableViewer));

	}

	@Override
	public void createPartControl(Composite parent_) {
		parent_.setLayout(new GridLayout(1, false));

		final Group grpBudgets = new Group(parent_, SWT.NONE);
		grpBudgets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpBudgets.setText("Budgets");
		grpBudgets.setLayout(new FillLayout(SWT.HORIZONTAL));

		tableViewer = new TableViewer(grpBudgets, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(true);

		comparator = new BudgetViewerSorter();
		tableViewer.setSorter(comparator);

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn.setLabelProvider(new NomBudgetCellLP());
		final TableColumn tblclmnNom = tableViewerColumn.getColumn();
		tblclmnNom.addSelectionListener(getSelectionAdapter(tblclmnNom, 0));
		tblclmnNom.setWidth(100);
		tblclmnNom.setText("Nom");

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn_1.setLabelProvider(new ObjectifBudgetCellLP());
		final TableColumn tblclmnTotal = tableViewerColumn_1.getColumn();
		tblclmnTotal.setWidth(100);
		tblclmnTotal.setText("Objectif");
		tblclmnTotal.addSelectionListener(getSelectionAdapter(tblclmnTotal, 1));

		final TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn_5.setLabelProvider(new AvancementBudgetCellLP());
		final TableColumn tblclmnAvancement = tableViewerColumn_5.getColumn();
		tblclmnAvancement.setWidth(100);
		tblclmnAvancement.setText("Avancement");
		tblclmnAvancement.addSelectionListener(getSelectionAdapter(tblclmnAvancement, 2));

		final TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn_2.setLabelProvider(new MontantCourantBudgetCellLP());
		final TableColumn tblclmnProvisionn = tableViewerColumn_2.getColumn();
		tblclmnProvisionn.setWidth(100);
		tblclmnProvisionn.setText("CC");
		tblclmnProvisionn.addSelectionListener(getSelectionAdapter(tblclmnProvisionn, 3));

		final TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn_4.setLabelProvider(new MontantLivretBudgetCellLP());
		final TableColumn tblclmnProvisionnCl = tableViewerColumn_4.getColumn();
		tblclmnProvisionnCl.setWidth(100);
		tblclmnProvisionnCl.setText("CL");
		tblclmnProvisionnCl.addSelectionListener(getSelectionAdapter(tblclmnProvisionnCl, 4));

		final TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		tableViewerColumn_3.setLabelProvider(new UtiliseBudgetCellLP());
		final TableColumn tblclmnUtilis = tableViewerColumn_3.getColumn();
		tblclmnUtilis.setWidth(100);
		tblclmnUtilis.setText("Utilis\u00E9");
		tblclmnUtilis.addSelectionListener(getSelectionAdapter(tblclmnUtilis, 5));

		tableViewer.setContentProvider(new BudgetCP());

		tableViewer.setInput(new Object());

		createContextMenu();

		if (_memento != null) {

			for (int i = 0; i < tableViewer.getTable().getColumnCount(); i++) {

				if (_memento.getInteger(MEMENTO_COL + i) != null) {
					tableViewer.getTable().getColumn(i).setWidth(_memento.getInteger(MEMENTO_COL + i));
				}
			}
		}

	}

	@Override
	public void dispose() {

		BudgetViewController.getInstance().removeControlledView(this);
		BudgetViewController.getInstance().dipose();
		super.dispose();
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
		final SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				final int dir = comparator.getDirection();
				tableViewer.getTable().setSortDirection(dir);
				tableViewer.getTable().setSortColumn(column);
				tableViewer.refresh();
			}
		};
		return selectionAdapter;
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (memento != null) {
			_memento = memento;
		}
	}

	@Override
	public void processControlEvent(ControllerEvent event) {

		// ajout d'un compte
		if (event.getName().compareTo(BudgetViewController.AJOUT_BUDGET) == 0) {
			tableViewer.refresh();
		}

		// suppression d'un compte
		if (event.getName().compareTo(BudgetViewController.SUPPRESSION_BUDGET) == 0) {
			tableViewer.refresh();
		}

		// edition d'un compte
		if (event.getName().compareTo(BudgetViewController.EDITION_BUDGET) == 0) {
			tableViewer.refresh(event.getValue());
		}

		// edition d'un compte
		if (event.getName().compareTo(BudgetViewController.SORTED_BUDGET) == 0) {
			tableViewer.refresh();
		}

		// refresh global
		if (event.getName().compareTo(BudgetViewController.REFRESH_ALL) == 0) {
			tableViewer.refresh();
		}

	}

	@Override
	public void saveState(IMemento memento) {

		for (int i = 0; i < tableViewer.getTable().getColumnCount(); i++) {
			memento.putInteger(MEMENTO_COL + i, tableViewer.getTable().getColumn(i).getWidth());

		}

		super.saveState(memento);

	}

	@Override
	public void setFocus() {

	}
}
