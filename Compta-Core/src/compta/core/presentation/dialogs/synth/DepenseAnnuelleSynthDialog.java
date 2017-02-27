package compta.core.presentation.dialogs.synth;

import java.util.Calendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
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
import org.eclipse.ui.PlatformUI;

import compta.core.Activator;
import compta.core.presentation.composite.DepenseAnnuelleChartComposite;
import compta.core.presentation.contentprovider.synth.AnneeSynthCP;
import compta.core.presentation.contentprovider.synth.CategorieAnneeSynthCP;
import compta.core.presentation.controllers.DepenseAnnuelleSynthDialogController;
import compta.core.presentation.labelprovider.operation.CategorieLP;
import compta.core.presentation.labelprovider.stat.AnneeLP;

public class DepenseAnnuelleSynthDialog extends Dialog {

	private final DepenseAnnuelleSynthDialogController _controler;

	private ComboViewer _yearComboViewer;

	private Group grpGraphique;

	private DepenseAnnuelleChartComposite dacc;

	private ComboViewer _catComboViewer;
	
	
	private final String DIALOG_SETTINGS = "dasd.setting";
	 
	
	
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public DepenseAnnuelleSynthDialog() {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
		_controler = new DepenseAnnuelleSynthDialogController();

	}

	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText("Synthèse des dépenses Annuelles");
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
		grpSlectionDuMois.setText("S\u00E9lection de l'année :");
		grpSlectionDuMois.setLayout(new FillLayout(SWT.HORIZONTAL));

		_yearComboViewer = new ComboViewer(grpSlectionDuMois, SWT.NONE);
		_yearComboViewer.setContentProvider(new AnneeSynthCP(_controler));
		_yearComboViewer.setLabelProvider(new AnneeLP());
		_yearComboViewer.setInput(new Object());
		_yearComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				if (_yearComboViewer.getSelection() instanceof IStructuredSelection) {

					if (((IStructuredSelection) _yearComboViewer.getSelection()).getFirstElement() instanceof Calendar) {

						_controler.setSelectedAnnee((Calendar) ((IStructuredSelection) _yearComboViewer.getSelection()).getFirstElement());
						_catComboViewer.refresh();
						dacc.reDoChart();

					}

				}

			}
		});

		_yearComboViewer.setSorter(new ViewerSorter() {

			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {

				return ((Calendar) e1).compareTo((Calendar) e2);
			}

		});

		Group grpDpenses = new Group(composite, SWT.NONE);
		grpDpenses.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpDpenses.setText("D\u00E9penses :");
		grpDpenses.setLayout(new FillLayout(SWT.HORIZONTAL));

		_catComboViewer = new ComboViewer(grpDpenses, SWT.NONE);
		_catComboViewer.setContentProvider(new CategorieAnneeSynthCP(_controler));
		_catComboViewer.setLabelProvider(new CategorieLP());
		_catComboViewer.setInput(new Object());

		_catComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				if (_catComboViewer.getSelection() instanceof IStructuredSelection) {

					if (((IStructuredSelection) _catComboViewer.getSelection()).getFirstElement() instanceof String) {

						_controler.setSelectedCategorie((String) ((IStructuredSelection) _catComboViewer.getSelection()).getFirstElement());
						dacc.reDoChart();

					}

				}

			}
		});

		grpGraphique = new Group(composite, SWT.NONE);
		grpGraphique.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGraphique.setLayout(new FillLayout());
		grpGraphique.setText("Graphique :");

		if (_controler != null) {
			dacc = new DepenseAnnuelleChartComposite(grpGraphique, _controler);
		}

		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		_yearComboViewer.refresh();

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
