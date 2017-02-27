package compta.core.presentation.dialogs.template;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

import compta.core.presentation.contentprovider.template.DepenseTrimestreTemplateCP;
import compta.core.presentation.contentprovider.template.RessourceTrimestreTemplateCP;
import compta.core.presentation.contentprovider.template.TransfertTrimestreTemplateCP;
import compta.core.presentation.handlers.action.template.CreateDepenseTemplateEltAction;
import compta.core.presentation.handlers.action.template.CreateRessourceTemplateEltAction;
import compta.core.presentation.handlers.action.template.CreateTransfertTemplateEltAction;
import compta.core.presentation.handlers.action.template.EditDepenseTemplateEltAction;
import compta.core.presentation.handlers.action.template.EditRessourceTemplateEltAction;
import compta.core.presentation.handlers.action.template.EditTransfertTemplateEltAction;
import compta.core.presentation.handlers.action.template.RemoveTemplateEltAction;
import compta.core.presentation.labelprovider.template.CategorieTemplateTrimestreEltCellLP;
import compta.core.presentation.labelprovider.template.CompteCibleTransfertTemplateTrimestreEltCellLP;
import compta.core.presentation.labelprovider.template.CompteTemplateTrimestreEltCellLP;
import compta.core.presentation.labelprovider.template.FrequenceTemplateTrimestreEltCellLP;
import compta.core.presentation.labelprovider.template.MontantTemplateTrimestreEltCellLP;
import compta.core.presentation.labelprovider.template.NomTemplateTrimestreEltCellLP;
import compta.core.presentation.labelprovider.template.OccurenceTemplateTrimestreEltCellLP;

public class EditTemplateTrimestreDialog extends Dialog {

	private TableViewer _depenseEltViewer;
	private TableViewer _ressourceEltViewer;
	private TableViewer _transfertEltViewer;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public EditTemplateTrimestreDialog() {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);

	}

	@Override
	protected void configureShell(final Shell newShell) {

		super.configureShell(newShell);
		newShell.setText("Configurer la création de trimestre");
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
	}

	private void createContextMenu() {

		final MenuManager managerDepense = new MenuManager();
		final Menu menuDepense = managerDepense.createContextMenu(_depenseEltViewer.getControl());
		_depenseEltViewer.getControl().setMenu(menuDepense);

		managerDepense.add(new CreateDepenseTemplateEltAction(_depenseEltViewer));
		managerDepense.add(new EditDepenseTemplateEltAction(_depenseEltViewer));
		managerDepense.add(new RemoveTemplateEltAction(_depenseEltViewer));

		final MenuManager managerRessource = new MenuManager();
		final Menu menuRessource = managerRessource.createContextMenu(_ressourceEltViewer.getControl());
		_ressourceEltViewer.getControl().setMenu(menuRessource);

		managerRessource.add(new CreateRessourceTemplateEltAction(_ressourceEltViewer));
		managerRessource.add(new EditRessourceTemplateEltAction(_ressourceEltViewer));
		managerRessource.add(new RemoveTemplateEltAction(_ressourceEltViewer));

		final MenuManager managerTransfert = new MenuManager();
		final Menu menuTransfert = managerTransfert.createContextMenu(_transfertEltViewer.getControl());
		_transfertEltViewer.getControl().setMenu(menuTransfert);

		managerTransfert.add(new CreateTransfertTemplateEltAction(_transfertEltViewer));
		managerTransfert.add(new EditTransfertTemplateEltAction(_transfertEltViewer));
		managerTransfert.add(new RemoveTemplateEltAction(_transfertEltViewer));
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));

		createGroupDepenses(container);

		createGroupRessources(container);

		createGroupTransfert(container);

		createContextMenu();

		return container;
	}

	private void createGroupDepenses(final Composite container) {

		final Group grpDpenses = new Group(container, SWT.NONE);
		grpDpenses.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpDpenses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpDpenses.setText("D\u00E9penses");

		_depenseEltViewer = new TableViewer(grpDpenses, SWT.BORDER | SWT.FULL_SELECTION);
		final Table _table = _depenseEltViewer.getTable();
		_table.setLinesVisible(true);
		_table.setHeaderVisible(true);

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(_depenseEltViewer, SWT.NONE);
		final TableColumn tblclmnNom = tableViewerColumn.getColumn();
		tblclmnNom.setWidth(100);
		tblclmnNom.setText("Nom");
		tableViewerColumn.setLabelProvider(new NomTemplateTrimestreEltCellLP());

		final TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(_depenseEltViewer, SWT.NONE);
		final TableColumn tblclmnMontant = tableViewerColumn_1.getColumn();
		tblclmnMontant.setWidth(100);
		tblclmnMontant.setText("Montant");
		tableViewerColumn_1.setLabelProvider(new MontantTemplateTrimestreEltCellLP());

		final TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(_depenseEltViewer, SWT.NONE);
		tableViewerColumn_2.setLabelProvider(new CategorieTemplateTrimestreEltCellLP());
		final TableColumn tblclmnCategorie = tableViewerColumn_2.getColumn();
		tblclmnCategorie.setWidth(100);
		tblclmnCategorie.setText("Categorie");

		final TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(_depenseEltViewer, SWT.NONE);
		tableViewerColumn_7.setLabelProvider(new CompteTemplateTrimestreEltCellLP());
		final TableColumn tblclmnCompte = tableViewerColumn_7.getColumn();
		tblclmnCompte.setWidth(100);
		tblclmnCompte.setText("Compte");

		final TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(_depenseEltViewer, SWT.NONE);
		tableViewerColumn_3.setLabelProvider(new FrequenceTemplateTrimestreEltCellLP());
		final TableColumn tblclmnFrquence = tableViewerColumn_3.getColumn();
		tblclmnFrquence.setWidth(100);
		tblclmnFrquence.setText("Fr\u00E9quence");

		final TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(_depenseEltViewer, SWT.NONE);
		tableViewerColumn_4.setLabelProvider(new OccurenceTemplateTrimestreEltCellLP());
		final TableColumn tblclmnOccurence = tableViewerColumn_4.getColumn();
		tblclmnOccurence.setWidth(100);
		tblclmnOccurence.setText("Occurence");

		_depenseEltViewer.setContentProvider(new DepenseTrimestreTemplateCP());
		_depenseEltViewer.setInput(new Object());

	}

	private void createGroupRessources(final Composite container) {

		final Group grpRessources = new Group(container, SWT.NONE);
		grpRessources.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRessources.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpRessources.setText("Ressources");

		_ressourceEltViewer = new TableViewer(grpRessources, SWT.BORDER | SWT.FULL_SELECTION);
		final Table _table_1 = _ressourceEltViewer.getTable();
		_table_1.setLinesVisible(true);
		_table_1.setHeaderVisible(true);

		final TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(_ressourceEltViewer, SWT.NONE);
		tableViewerColumn_5.setLabelProvider(new NomTemplateTrimestreEltCellLP());
		final TableColumn tableColumn = tableViewerColumn_5.getColumn();
		tableColumn.setWidth(100);
		tableColumn.setText("Nom");

		final TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(_ressourceEltViewer, SWT.NONE);
		tableViewerColumn_6.setLabelProvider(new MontantTemplateTrimestreEltCellLP());
		final TableColumn tableColumn_1 = tableViewerColumn_6.getColumn();
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("Montant");

		final TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(_ressourceEltViewer, SWT.NONE);
		tableViewerColumn_8.setLabelProvider(new CompteTemplateTrimestreEltCellLP());
		final TableColumn tableColumn_2 = tableViewerColumn_8.getColumn();
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("Compte");

		final TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(_ressourceEltViewer, SWT.NONE);
		tableViewerColumn_9.setLabelProvider(new FrequenceTemplateTrimestreEltCellLP());
		final TableColumn tableColumn_3 = tableViewerColumn_9.getColumn();
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("Fr\u00E9quence");

		final TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(_ressourceEltViewer, SWT.NONE);
		tableViewerColumn_10.setLabelProvider(new OccurenceTemplateTrimestreEltCellLP());
		final TableColumn tableColumn_4 = tableViewerColumn_10.getColumn();
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("Occurence");

		_ressourceEltViewer.setContentProvider(new RessourceTrimestreTemplateCP());
		_ressourceEltViewer.setInput(new Object());

	}

	private void createGroupTransfert(final Composite container) {

		final Group grpTransferts = new Group(container, SWT.NONE);
		grpTransferts.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTransferts.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTransferts.setText("Transferts");

		_transfertEltViewer = new TableViewer(grpTransferts, SWT.BORDER | SWT.FULL_SELECTION);
		final Table _table_2 = _transfertEltViewer.getTable();
		_table_2.setLinesVisible(true);
		_table_2.setHeaderVisible(true);

		final TableViewerColumn tableViewerColumn_11 = new TableViewerColumn(_transfertEltViewer, SWT.NONE);
		tableViewerColumn_11.setLabelProvider(new NomTemplateTrimestreEltCellLP());
		final TableColumn tableColumn_5 = tableViewerColumn_11.getColumn();
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("Nom");

		final TableViewerColumn tableViewerColumn_12 = new TableViewerColumn(_transfertEltViewer, SWT.NONE);
		tableViewerColumn_12.setLabelProvider(new MontantTemplateTrimestreEltCellLP());
		final TableColumn tableColumn_6 = tableViewerColumn_12.getColumn();
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("Montant");

		final TableViewerColumn tableViewerColumn_13 = new TableViewerColumn(_transfertEltViewer, SWT.NONE);
		tableViewerColumn_13.setLabelProvider(new CompteTemplateTrimestreEltCellLP());
		final TableColumn tblclmnCompteSource = tableViewerColumn_13.getColumn();
		tblclmnCompteSource.setWidth(100);
		tblclmnCompteSource.setText("Compte Source");

		final TableViewerColumn tableViewerColumn_14 = new TableViewerColumn(_transfertEltViewer, SWT.NONE);
		tableViewerColumn_14.setLabelProvider(new CompteCibleTransfertTemplateTrimestreEltCellLP());
		final TableColumn tblclmnCompteCible = tableViewerColumn_14.getColumn();
		tblclmnCompteCible.setWidth(100);
		tblclmnCompteCible.setText("Compte Cible");

		final TableViewerColumn tableViewerColumn_15 = new TableViewerColumn(_transfertEltViewer, SWT.NONE);
		tableViewerColumn_15.setLabelProvider(new FrequenceTemplateTrimestreEltCellLP());
		final TableColumn tableColumn_7 = tableViewerColumn_15.getColumn();
		tableColumn_7.setWidth(100);
		tableColumn_7.setText("Fr\u00E9quence");

		final TableViewerColumn tableViewerColumn_16 = new TableViewerColumn(_transfertEltViewer, SWT.NONE);
		tableViewerColumn_16.setLabelProvider(new OccurenceTemplateTrimestreEltCellLP());
		final TableColumn tableColumn_8 = tableViewerColumn_16.getColumn();
		tableColumn_8.setWidth(100);
		tableColumn_8.setText("Occurence");

		_transfertEltViewer.setContentProvider(new TransfertTrimestreTemplateCP());
		_transfertEltViewer.setInput(new Object());

	}

}
