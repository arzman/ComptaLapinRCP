
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import compta.core.presentation.contentprovider.compte.CompteCP;
import compta.core.presentation.controllers.CompteViewController;
import compta.core.presentation.controllers.ControllerEvent;
import compta.core.presentation.handlers.action.compte.EditCompteAction;
import compta.core.presentation.handlers.action.compte.RemoveCompteAction;
import compta.core.presentation.labelprovider.compte.NomCompteCellLP;
import compta.core.presentation.labelprovider.compte.SoldeCompteCellLP;
import compta.core.presentation.labelprovider.compte.SoldePrevCompteCellLP;
import compta.core.presentation.viewersorter.CompteViewerSorter;

public class CompteView extends ViewPart implements ControlledView {

	public static final String ID = "Compta.view.CompteView";

	private static final String MEMENTO_COL = "compte.col";

	private TableViewer tableViewer;

	private CompteViewerSorter comparator;

	private IMemento _memento;

	public CompteView() {

		CompteViewController.getInstance().addControlledView(this);

	}

	private void createContextMenu() {

		final MenuManager manager = new MenuManager();
		final Menu menu = manager.createContextMenu(tableViewer.getControl());

		tableViewer.getControl().setMenu(menu);

		manager.add(new RemoveCompteAction(tableViewer));
		manager.add(new EditCompteAction(tableViewer));

	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		final Group grpListeDesComptes = new Group(parent, SWT.NONE);
		grpListeDesComptes.setText("Liste des comptes");
		grpListeDesComptes.setLayout(new GridLayout(1, false));

		tableViewer = new TableViewer(grpListeDesComptes, SWT.BORDER | SWT.FULL_SELECTION);
		final Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		comparator = new CompteViewerSorter();
		tableViewer.setSorter(comparator);

		tableViewer.setContentProvider(new CompteCP());
		tableViewer.setInput(new Object());

		final TableViewerColumn nomColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		nomColumn.setLabelProvider(new NomCompteCellLP());
		final TableColumn tblclmnNom = nomColumn.getColumn();
		tblclmnNom.setWidth(100);
		tblclmnNom.setText("Nom");
		tblclmnNom.addSelectionListener(getSelectionAdapter(tblclmnNom, 0));

		final TableViewerColumn soldeColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		soldeColumn.setLabelProvider(new SoldeCompteCellLP());
		final TableColumn tblclmnNewColumn = soldeColumn.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Solde");
		tblclmnNewColumn.addSelectionListener(getSelectionAdapter(tblclmnNewColumn, 1));

		final TableViewerColumn soldePrev1Column = new TableViewerColumn(tableViewer, SWT.NONE);
		soldePrev1Column.setLabelProvider(new SoldePrevCompteCellLP(1));
		final TableColumn tblclmnSoldePrv1Column = soldePrev1Column.getColumn();
		tblclmnSoldePrv1Column.setWidth(100);
		tblclmnSoldePrv1Column.setText("1er Mois");
		tblclmnSoldePrv1Column.addSelectionListener(getSelectionAdapter(tblclmnSoldePrv1Column, 2));

		final TableViewerColumn soldePrev2Column = new TableViewerColumn(tableViewer, SWT.NONE);
		soldePrev2Column.setLabelProvider(new SoldePrevCompteCellLP(2));
		final TableColumn tblclmnSoldePrv2Column = soldePrev2Column.getColumn();
		tblclmnSoldePrv2Column.setWidth(100);
		tblclmnSoldePrv2Column.setText("2eme Mois");
		tblclmnSoldePrv2Column.addSelectionListener(getSelectionAdapter(tblclmnSoldePrv2Column, 3));

		final TableViewerColumn soldePrev3Column = new TableViewerColumn(tableViewer, SWT.NONE);
		soldePrev3Column.setLabelProvider(new SoldePrevCompteCellLP(3));
		final TableColumn tblclmnSoldePrv3Column = soldePrev3Column.getColumn();
		tblclmnSoldePrv3Column.setWidth(100);
		tblclmnSoldePrv3Column.setText("3eme Mois");
		tblclmnSoldePrv3Column.addSelectionListener(getSelectionAdapter(tblclmnSoldePrv3Column, 4));

		createContextMenu();

		if (_memento != null) {

			for (int i = 0; i < tableViewer.getTable().getColumnCount(); i++) {

				if (_memento.getInteger(MEMENTO_COL + i) != null) {
					tableViewer.getTable().getColumn(i).setWidth(_memento.getInteger(MEMENTO_COL + i));
				}
			}
		}

		tableViewer.refresh();

	}

	@Override
	public void dispose() {

		CompteViewController.getInstance().removeControlledView(this);
		CompteViewController.getInstance().dipose();
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
		if (event.getName().compareTo(CompteViewController.AJOUT_COMPTE) == 0) {
			tableViewer.refresh();
		}

		// suppression d'un compte
		if (event.getName().compareTo(CompteViewController.SUPPRESSION_COMPTE) == 0) {
			tableViewer.refresh();
		}

		// edition d'un compte
		if (event.getName().compareTo(CompteViewController.EDITION_COMPTE) == 0) {
			tableViewer.refresh(event.getValue());
		}

		if (event.getName().compareTo(CompteViewController.TRIMESTRE_CHANGED) == 0) {
			tableViewer.refresh();
		}

		if (event.getName().compareTo(CompteViewController.OPERATION_EDITED) == 0) {
			tableViewer.refresh();
		}

		if (event.getName().compareTo(CompteViewController.OPERATION_REMOVED) == 0) {
			tableViewer.refresh();
		}

		if (event.getName().compareTo(CompteViewController.OPERATION_ADD) == 0) {
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
