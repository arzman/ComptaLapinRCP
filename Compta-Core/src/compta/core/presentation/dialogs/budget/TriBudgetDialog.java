
package compta.core.presentation.dialogs.budget;

import java.util.ArrayList;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
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
import compta.core.application.manager.BudgetManager;
import compta.core.domaine.Budget;
import compta.core.presentation.contentprovider.budget.SortedBudgetCP;
import compta.core.presentation.handlers.action.budget.MoveDownBudgetAction;
import compta.core.presentation.handlers.action.budget.MoveUpBudgetAction;
import compta.core.presentation.labelprovider.budget.NomBudgetCellLP;

public class TriBudgetDialog extends Dialog {

	/**
	 * La liste des {@link Identifier} des {@link Budget}
	 */
	final ArrayList<Identifier> _list;
	
	/**
	 * Le viewer des {@link Budget}
	 */
	private TableViewer _tableViewer;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public TriBudgetDialog() {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
		_list = new ArrayList<Identifier>(BudgetManager.getInstance().getSortedBudget());
	}

	@Override
	protected void configureShell(Shell newShell) {

		super.configureShell(newShell);
		newShell.setText("Trier les budgets");
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	private void createContextMenu() {

		final MenuManager manager = new MenuManager();
		final Menu menu = manager.createContextMenu(_tableViewer.getControl());

		_tableViewer.getControl().setMenu(menu);

		manager.add(new MoveUpBudgetAction(_tableViewer, _list));
		manager.add(new MoveDownBudgetAction(_tableViewer, _list));

	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);

		final Group grpListeDesBudgets = new Group(container, SWT.NONE);
		grpListeDesBudgets.setText("Liste des budgets");
		grpListeDesBudgets.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpListeDesBudgets.setLayout(new FillLayout(SWT.HORIZONTAL));

		_tableViewer = new TableViewer(grpListeDesBudgets, SWT.FULL_SELECTION);
		final Table _table = _tableViewer.getTable();
		_table.setLinesVisible(true);
		_table.setHeaderVisible(true);

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(_tableViewer, SWT.NONE);
		tableViewerColumn.setLabelProvider(new NomBudgetCellLP());
		final TableColumn tblclmnNom = tableViewerColumn.getColumn();
		tblclmnNom.setWidth(100);
		tblclmnNom.setText("Nom");
		_tableViewer.setContentProvider(new SortedBudgetCP(_list));
		_tableViewer.setInput(new Object());

		createContextMenu();

		return container;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	public ArrayList<Identifier> getList() {

		return _list;
	}

}
