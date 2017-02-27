package compta.core.presentation.dialogs.stat;

import java.util.Calendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

import compta.core.Activator;
import compta.core.presentation.composite.CategoriePieChartComposite;
import compta.core.presentation.contentprovider.stats.CategorieMoisStatCP;
import compta.core.presentation.contentprovider.stats.MoisStatCP;
import compta.core.presentation.controllers.DepenseMensuelleStatDialogController;
import compta.core.presentation.labelprovider.operation.CategorieCellLP;
import compta.core.presentation.labelprovider.stat.CategorieMeanCellLP;
import compta.core.presentation.labelprovider.stat.CategorieTotalCellLP;
import compta.core.presentation.labelprovider.stat.MoisLP;

public class DepenseMensuelleStatDialog extends Dialog {

	private Table _table;

	private final DepenseMensuelleStatDialogController _controler;

	private ComboViewer _comboViewer;

	private TableViewer _tableViewer;

	private Group grpGraphique;

	private CategoriePieChartComposite cpcc;
	
	private final String DIALOG_SETTINGS = "dmsd.setting";

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public DepenseMensuelleStatDialog() {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
		_controler = new DepenseMensuelleStatDialogController();

	}

	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText("Statistiques Mensuelles");
		super.configureShell(newShell);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));

		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		final Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));

		Group grpSlectionDuMois = new Group(composite, SWT.NONE);
		grpSlectionDuMois.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpSlectionDuMois.setText("S\u00E9lection du mois :");
		grpSlectionDuMois.setLayout(new FillLayout(SWT.HORIZONTAL));

		_comboViewer = new ComboViewer(grpSlectionDuMois, SWT.NONE);
		_comboViewer.setContentProvider(new MoisStatCP(_controler));
		_comboViewer.setLabelProvider(new MoisLP());
		_comboViewer.setInput(new Object());
		_comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				if (_comboViewer.getSelection() instanceof IStructuredSelection) {

					if (((IStructuredSelection) _comboViewer.getSelection()).getFirstElement() instanceof Calendar) {

						_controler.setSelectedMois((Calendar) ((IStructuredSelection) _comboViewer.getSelection()).getFirstElement());
						_tableViewer.refresh();
						cpcc.reDoChart();

					}

				}

			}
		});
		
		_comboViewer.setSorter(new ViewerSorter(){
			
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				
				return ((Calendar)e1).compareTo((Calendar) e2);
			}
			
		});

		Group grpDpenses = new Group(composite, SWT.NONE);
		grpDpenses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpDpenses.setText("D\u00E9penses :");
		grpDpenses.setLayout(new FillLayout(SWT.HORIZONTAL));

		_tableViewer = new TableViewer(grpDpenses, SWT.BORDER | SWT.FULL_SELECTION);
		_table = _tableViewer.getTable();
		_table.setLinesVisible(true);
		_table.setHeaderVisible(true);
		_tableViewer.setContentProvider(new CategorieMoisStatCP(_controler));
		_tableViewer.setInput(new Object());

		TableViewerColumn tableViewerColumn = new TableViewerColumn(_tableViewer, SWT.NONE);
		TableColumn tblclmnCatgorie = tableViewerColumn.getColumn();
		tblclmnCatgorie.setWidth(200);
		tblclmnCatgorie.setText("Cat\u00E9gorie");
		tableViewerColumn.setLabelProvider(new CategorieCellLP());

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(_tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_1.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Moyenne");
		tableViewerColumn_1.setLabelProvider(new CategorieMeanCellLP(_controler));

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(_tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_1.setWidth(100);
		tblclmnNewColumn_1.setText("Total");
		tableViewerColumn_2.setLabelProvider(new CategorieTotalCellLP(_controler));

		grpGraphique = new Group(composite, SWT.NONE);
		grpGraphique.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGraphique.setLayout(new FillLayout());
		grpGraphique.setText("Graphique :");
		
		cpcc =new CategoriePieChartComposite(grpGraphique,_controler);
		
		
		grpGraphique.setLayout(new FillLayout(SWT.HORIZONTAL));
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		_tableViewer.refresh();
		_comboViewer.refresh();

		return container;
	}

	
	@Override
	protected IDialogSettings getDialogBoundsSettings() {
	 IDialogSettings settings = Activator.getDefault().getDialogSettings();
	 IDialogSettings section = settings.getSection(DIALOG_SETTINGS);
	 if (section == null) {
	  section = settings.addNewSection(DIALOG_SETTINGS);
	 }
	 return section;
	}
	
	
	@Override
	protected int getDialogBoundsStrategy() {

		return DIALOG_PERSISTSIZE;
	}

	
	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Fermer", true);
	}

}
