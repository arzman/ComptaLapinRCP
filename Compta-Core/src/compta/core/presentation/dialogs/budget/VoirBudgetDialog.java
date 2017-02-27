package compta.core.presentation.dialogs.budget;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.domaine.Budget;
import compta.core.domaine.Utilisation;
import compta.core.presentation.handlers.action.budget.AddUtiliseInBudgetAction;
import compta.core.presentation.handlers.action.budget.EditBudgetAction;
import compta.core.presentation.handlers.action.budget.RemoveBudgetAction;
import compta.core.presentation.labelprovider.budget.NomBudgetCellLP;
import compta.core.presentation.viewersorter.BudgetViewerSorter;
import compta.core.presentation.viewersorter.UtilisationViewerSorter;


/**
 * 
 * {@link Dialog} permettant de visualiser les {@link Utilisation} d'un {@link Budget}
 * 
 * @author Arthur
 *
 */
public class VoirBudgetDialog extends Dialog {

	
	/**
	 * Le viewer des utilisations
	 */
	private TableViewer _tableViewer;
	/**
	 * Le comparateur de ligne
	 */
	private UtilisationViewerSorter _comparator;
	
	
	/**
	 * Ouvre la fenetre pour l'{@link Identifier} du {@link Budget} en paramètre
	 * 
	 * @param idBudget
	 */
	public VoirBudgetDialog(Identifier idBudget) {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());

	}
	
	@Override
	protected void configureShell(Shell newShell) {

		super.configureShell(newShell);
		newShell.setText("Utilisations du budget");
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);

		final Group grpListeDesUtilisation = new Group(container, SWT.NONE);
		grpListeDesUtilisation.setText("Liste des utilisations");
		grpListeDesUtilisation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpListeDesUtilisation.setLayout(new FillLayout(SWT.HORIZONTAL));

		_tableViewer = new TableViewer(grpListeDesUtilisation, SWT.FULL_SELECTION);
		final Table table = _tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		
		_comparator = new UtilisationViewerSorter();
		_tableViewer.setSorter(_comparator);
		
		
		final TableViewerColumn tableViewerColumn = new TableViewerColumn(_tableViewer, SWT.NONE);
		tableViewerColumn.setLabelProvider(new DateUtilisationCellLP());
		final TableColumn tblclmnNom = tableViewerColumn.getColumn();
		tblclmnNom.addSelectionListener(getSelectionAdapter(tblclmnNom, 0));
		tblclmnNom.setWidth(100);
		tblclmnNom.setText("Date");
		
		
		return container;
	}
	
	
	private void createContextMenu() {

		final MenuManager manager = new MenuManager();
		final Menu menu = manager.createContextMenu(_tableViewer.getControl());

		_tableViewer.getControl().setMenu(menu);

		manager.add(new EditeUtilisationAction(_tableViewer));


	}
	
	
	/**
	 * Ajoute un listener sur le header d'une colonne pour inverser le tri
	 * 
	 * @param column
	 * @param index
	 * @return
	 */
	private SelectionAdapter getSelectionAdapter(final TableColumn column, final int index) {
		final SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				_comparator.setColumn(index);
				final int dir = _comparator.getDirection();
				_tableViewer.getTable().setSortDirection(dir);
				_tableViewer.getTable().setSortColumn(column);
				_tableViewer.refresh();
			}
		};
		return selectionAdapter;
	}
	

}
