package compta.core.presentation.dialogs.trimestre;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.presentation.contentprovider.trimestre.TrimestreCP;
import compta.core.presentation.labelprovider.trimestre.TrimestreCellLP;

public class ManageTrimestreDialog extends Dialog {
	private Table _table;
	private TableViewer _tableViewer;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ManageTrimestreDialog() {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
	}

	@Override
	protected void configureShell(Shell newShell) {

		newShell.setText("Gérer les trimestres");
		super.configureShell(newShell);
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

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;

		final Group grpTrimestre = new Group(container, SWT.NONE);
		grpTrimestre.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTrimestre.setText("Trimestre");
		grpTrimestre.setLayout(new FillLayout(SWT.HORIZONTAL));

		_tableViewer = new TableViewer(grpTrimestre, SWT.BORDER | SWT.FULL_SELECTION);
		_table = _tableViewer.getTable();
		_table.setHeaderVisible(true);
		_table.setLinesVisible(true);

		_tableViewer.setContentProvider(new TrimestreCP());
		_tableViewer.setInput(new Object());
		_tableViewer.setSorter(new ViewerSorter() {

			@Override
			public int compare(Viewer viewer_, Object e1_, Object e2_) {

				int res = super.compare(viewer_, e1_, e2_);

				if ((e1_ instanceof Identifier) && (e2_ instanceof Identifier)) {

					res = TrimestreManager.getInstance().getDateDebutForExerciceIdendifier((Identifier) e1_)
							.compareTo(TrimestreManager.getInstance().getDateDebutForExerciceIdendifier((Identifier) e2_));

				}

				return res;
			}

		});

		final TableViewerColumn tableViewerColumn = new TableViewerColumn(_tableViewer, SWT.NONE);
		final TableColumn tblclmnDateDbutfin = tableViewerColumn.getColumn();
		tblclmnDateDbutfin.setWidth(300);
		tblclmnDateDbutfin.setText("Date d\u00E9but/fin");
		tableViewerColumn.setLabelProvider(new TrimestreCellLP());

		final Group grpActions = new Group(container, SWT.NONE);
		grpActions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpActions.setText("Actions");
		grpActions.setLayout(new GridLayout(1, false));

		final Button btnSupprimer = new Button(grpActions, SWT.NONE);
		btnSupprimer.setText("Supprimer");
		btnSupprimer.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				Identifier idSelected = null;

				if (_tableViewer.getSelection() instanceof IStructuredSelection) {

					if (((IStructuredSelection) _tableViewer.getSelection()).getFirstElement() instanceof Identifier) {

						idSelected = (Identifier) ((IStructuredSelection) _tableViewer.getSelection()).getFirstElement();
					}

				}

				if (idSelected != null) {
					if (!idSelected.equals(TrimestreManager.getInstance().getTrimestreCourantId())) {
						TrimestreManager.getInstance().removeTrimestre(idSelected);
						_tableViewer.refresh();
					} else {
						MessageDialog.openWarning(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Information",
								"Impossible de supprimer le trimestre courant");
					}
				}

			}
		});

		Button btnExporter = new Button(grpActions, SWT.NONE);
		btnExporter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		btnExporter.setText("Exporter");
		btnExporter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				Identifier idSelected = null;

				if (_tableViewer.getSelection() instanceof IStructuredSelection) {

					if (((IStructuredSelection) _tableViewer.getSelection()).getFirstElement() instanceof Identifier) {

						idSelected = (Identifier) ((IStructuredSelection) _tableViewer.getSelection()).getFirstElement();
					}

				}

				if (idSelected != null) {

					FileDialog diag = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.SAVE);

					String path = diag.open();

					if (path != null && !path.isEmpty()) {
						TrimestreManager.getInstance().exportTrimestre(idSelected, path);
						_tableViewer.refresh();
					}

				}

			}
		});

		Button btnImporter = new Button(grpActions, SWT.NONE);
		btnImporter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		btnImporter.setText("Importer");
		btnImporter.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

			@Override
			public void widgetSelected(SelectionEvent e) {

				FileDialog diag = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.OPEN);

				String path = diag.open();

				if (path != null && !path.isEmpty()) {
					TrimestreManager.getInstance().importTrimestre(path);
					_tableViewer.refresh();

				}

			}
		});

		_tableViewer.refresh();

		return container;
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
