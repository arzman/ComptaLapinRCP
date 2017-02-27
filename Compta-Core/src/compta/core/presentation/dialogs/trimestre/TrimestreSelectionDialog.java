package compta.core.presentation.dialogs.trimestre;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.TrimestreManager;
import compta.core.presentation.contentprovider.trimestre.TrimestreCP;
import compta.core.presentation.labelprovider.trimestre.TrimestreLP;

public class TrimestreSelectionDialog extends Dialog {

	private Identifier _selected = null;

	public TrimestreSelectionDialog() {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);

	}

	@Override
	protected void configureShell(Shell newShell_) {
		super.configureShell(newShell_);
		newShell_.setText("Sélection de trimestre");
	}

	@Override
	protected Control createDialogArea(Composite parent_) {

		final Composite area = new Composite(parent_, SWT.NONE);

		final FillLayout layout = new FillLayout();
		area.setLayout(layout);
		area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Group grpTrimestre = new Group(area, SWT.NONE);
		grpTrimestre.setText("Trimestre");
		grpTrimestre.setLayout(new RowLayout(SWT.HORIZONTAL));

		final TableViewer _table = new TableViewer(grpTrimestre);
		final TableColumn colNom = new TableColumn(_table.getTable(), SWT.READ_ONLY);

		_table.getTable().setHeaderVisible(true);
		_table.getTable().setLinesVisible(true);

		_table.setContentProvider(new TrimestreCP());
		_table.setLabelProvider(new TrimestreLP());
		_table.setSorter(new ViewerSorter() {

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

		_table.setInput(new Object());

		_table.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event_) {

				final ISelection s = _table.getSelection();

				if (s instanceof IStructuredSelection) {

					final IStructuredSelection iss = (IStructuredSelection) s;
					if (iss.getFirstElement() instanceof Identifier) {
						_selected = (Identifier) iss.getFirstElement();
					}

				}

			}
		});
		colNom.setText("Date début/fin");
		colNom.setResizable(true);
		colNom.setWidth(200);

		return area;
	}

	public Identifier getSelected() {
		return _selected;
	}

}
