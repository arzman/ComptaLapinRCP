
package compta.core.presentation.dialogs.template;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CategorieManager;
import compta.core.application.template.TrimestreTemplateElementFrequence;
import compta.core.presentation.contentprovider.compte.CompteCP;
import compta.core.presentation.contentprovider.template.ComboFrequenceCP;
import compta.core.presentation.contentprovider.template.ComboOccurenceCP;
import compta.core.presentation.labelprovider.compte.NomCompteLP;
import compta.core.presentation.labelprovider.template.ComboFrequenceLP;
import compta.core.presentation.labelprovider.template.ComboOccurenceLP;

public class CreateDepenseTemplateEltDialog extends Dialog {

	private Text _textNom;

	private Text _textMontant;

	private Identifier _compteId;

	private String _nom = "";

	private double _montant = 0.0;

	private String _categorie = "";

	private TrimestreTemplateElementFrequence _freq = TrimestreTemplateElementFrequence.MENSUEL;

	private int _occurence = 0;

	private ComboViewer _comboOccurenceViewer;

	private ComboOccurenceLP _comboLp;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public CreateDepenseTemplateEltDialog() {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);

	}

	@Override
	protected void configureShell(Shell newShell) {

		super.configureShell(newShell);
		newShell.setText("Création d'une dépense");
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

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		final Label lblNom = new Label(container, SWT.NONE);
		lblNom.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNom.setText("Nom :");

		_textNom = new Text(container, SWT.BORDER);
		_textNom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_textNom.setText(_nom);

		_textNom.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if (!_textNom.getText().trim().isEmpty()) {
					_nom = _textNom.getText();
					_textNom.setBackground(null);
				} else {
					_textNom.setBackground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
					_nom = "";
				}

			}
		});

		final Label lblMontant = new Label(container, SWT.NONE);
		lblMontant.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMontant.setText("Montant :");

		_textMontant = new Text(container, SWT.BORDER);
		_textMontant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_textMontant.setText(String.valueOf(_montant));

		_textMontant.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if (!_textMontant.getText().trim().isEmpty()) {
					try {
						_montant = Double.parseDouble(_textMontant.getText());
						_textMontant.setBackground(null);
					} catch (final NumberFormatException ex) {
						_textMontant.setBackground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
						_montant = 0.0;
					}

				} else {
					_textMontant.setBackground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
					_montant = 0.0;
				}

			}
		});

		final Label lblCatgorie = new Label(container, SWT.NONE);
		lblCatgorie.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCatgorie.setText("Cat\u00E9gorie :");

		final CCombo combo = new CCombo(container, SWT.BORDER);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		for (final String cat : CategorieManager.getInstance().getCategories()) {
			combo.add(cat);
		}
		combo.select(combo.indexOf(_categorie));

		combo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if (!combo.getText().trim().isEmpty()) {
					_categorie = combo.getText();
					combo.setBackground(null);
				} else {
					combo.setBackground(PlatformUI.getWorkbench().getDisplay().getSystemColor(SWT.COLOR_RED));
					_categorie = "";
				}

			}
		});

		final Label lblCompte = new Label(container, SWT.NONE);
		lblCompte.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCompte.setText("Compte :");

		final ComboViewer comboViewer = new ComboViewer(container, SWT.NONE);
		final Combo combo_1 = comboViewer.getCombo();
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		comboViewer.setContentProvider(new CompteCP());
		comboViewer.setLabelProvider(new NomCompteLP());
		comboViewer.setInput(new Object());

		if (_compteId != null) {
			comboViewer.setSelection(new TreeSelection(new TreePath(new Object[] { _compteId })));
		}

		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				if (((IStructuredSelection) comboViewer.getSelection()).getFirstElement() != null) {
					_compteId = (Identifier) ((IStructuredSelection) comboViewer.getSelection()).getFirstElement();
				} else {
					_compteId = null;
				}

			}
		});

		final Label lblFrquence = new Label(container, SWT.NONE);
		lblFrquence.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFrquence.setText("Fr\u00E9quence :");

		final ComboViewer comboFreqViewer = new ComboViewer(container, SWT.NONE);
		final Combo combo_2 = comboFreqViewer.getCombo();
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		comboFreqViewer.setContentProvider(new ComboFrequenceCP());
		comboFreqViewer.setLabelProvider(new ComboFrequenceLP());
		comboFreqViewer.setInput(new Object());

		if (_freq != null) {
			comboFreqViewer.setSelection(new TreeSelection(new TreePath(new Object[] { _freq })));
		}

		comboFreqViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				if (((IStructuredSelection) comboFreqViewer.getSelection()).getFirstElement() != null) {
					_freq = (TrimestreTemplateElementFrequence) ((IStructuredSelection) comboFreqViewer.getSelection()).getFirstElement();
					_comboLp.setFreq(_freq);
					_comboOccurenceViewer.setInput(_freq);
				} else {
					_freq = TrimestreTemplateElementFrequence.MENSUEL;
				}

			}
		});

		final Label lblOccurence = new Label(container, SWT.NONE);
		lblOccurence.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOccurence.setText("Occurence :");

		_comboOccurenceViewer = new ComboViewer(container, SWT.NONE);
		final Combo combo_3 = _comboOccurenceViewer.getCombo();
		combo_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		_comboOccurenceViewer.setContentProvider(new ComboOccurenceCP());
		_comboLp = new ComboOccurenceLP(_freq);
		_comboOccurenceViewer.setLabelProvider(_comboLp);
		_comboOccurenceViewer.setInput(_freq);

		_comboOccurenceViewer.setSelection(new TreeSelection(new TreePath(new Object[] { _occurence })));

		_comboOccurenceViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {

				if (((IStructuredSelection) _comboOccurenceViewer.getSelection()).getFirstElement() != null) {
					_occurence = (Integer) ((IStructuredSelection) _comboOccurenceViewer.getSelection()).getFirstElement();
				} else {
					_occurence = 0;
				}

			}
		});

		return container;
	}

	public String getCategorie() {
		return _categorie;
	}

	public Identifier getCompteId() {
		return _compteId;
	}

	/**
	 * @return the freq
	 */
	public TrimestreTemplateElementFrequence getFreq() {
		return _freq;
	}

	public double getMontant() {
		return _montant;
	}

	public String getNom() {
		return _nom;
	}

	/**
	 * @return the occurence
	 */
	public int getOccurence() {
		return _occurence;
	}

	public void setCategorie(String categorie) {
		_categorie = categorie;
	}

	public void setCompteId(Identifier compteId) {
		_compteId = compteId;
	}

	/**
	 * @param freq
	 *            the freq to set
	 */
	public void setFreq(TrimestreTemplateElementFrequence freq) {
		_freq = freq;
	}

	public void setMontant(double montant) {
		_montant = montant;
	}

	public void setNom(String nom) {
		_nom = nom;
	}

	/**
	 * @param occurence
	 *            the occurence to set
	 */
	public void setOccurence(int occurence) {
		_occurence = occurence;
	}

}
