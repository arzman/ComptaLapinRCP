package compta.core.presentation.dialogs.compte;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.CompteManager;
import compta.core.common.ComptaException;

public class EditCompteDialog extends Dialog {

	private Text nomText;

	private Text soldeText;

	private final Identifier _compteId;

	private String _nom;

	private double _solde;

	private boolean _isLivret;

	private boolean _budgetAllowed;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public EditCompteDialog(Identifier compteId) {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
		_compteId = compteId;

		try {

			if (_compteId != null) {
				_nom = CompteManager.getInstance().getNomCompte(_compteId);
				_solde = CompteManager.getInstance().getSoldeCompte(_compteId);
				_isLivret = CompteManager.getInstance().getCompteIsLivret(_compteId);
				_budgetAllowed = CompteManager.getInstance().getCompte(_compteId).isBudgetAllowed();
			} else {
				_nom = "";
				_solde = 0.0;
				_isLivret = false;
				_budgetAllowed = true;
			}
		} catch (final ComptaException ex) {
			_nom = "";
			_solde = 0.0;
			_isLivret = false;
			_budgetAllowed = true;
		}

	}

	@Override
	protected void configureShell(Shell newShell) {

		newShell.setText("Compte");
		super.configureShell(newShell);
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

		final Label lblNomDuCompte = new Label(container, SWT.NONE);
		lblNomDuCompte.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNomDuCompte.setText("Nom du compte :");

		nomText = new Text(container, SWT.BORDER);
		nomText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		nomText.setText(_nom);
		nomText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if (!nomText.getText().trim().isEmpty()) {
					_nom = nomText.getText().trim();
				} else {
					nomText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				}

			}
		});

		final Label lblSoldeDuCompte = new Label(container, SWT.NONE);
		lblSoldeDuCompte.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSoldeDuCompte.setText("Solde du compte :");

		soldeText = new Text(container, SWT.BORDER);
		soldeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		soldeText.setText(String.valueOf(_solde));
		soldeText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if (!soldeText.getText().trim().isEmpty()) {
					try {
						_solde = Double.parseDouble(soldeText.getText().trim());
					} catch (final NumberFormatException ex) {
						soldeText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					}
				} else {
					soldeText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				}

			}
		});

		final Button btnCompteLivret = new Button(container, SWT.CHECK);
		btnCompteLivret.setAlignment(SWT.CENTER);
		btnCompteLivret.setText("Compte Livret");
		btnCompteLivret.setSelection(_isLivret);
		new Label(container, SWT.NONE);
		btnCompteLivret.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				_isLivret = btnCompteLivret.getSelection();
			}

		});

		final Button btnBudget = new Button(container, SWT.CHECK);
		btnBudget.setText("Budget");
		new Label(container, SWT.NONE);
		btnBudget.setSelection(_budgetAllowed);
		btnBudget.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				_budgetAllowed = btnBudget.getSelection();
			}

		});

		return container;
	}

	public boolean getBudgetAllowed() {

		return _budgetAllowed;
	}

	public boolean getIsLivret() {

		return _isLivret;
	}

	public String getNom() {
		return _nom;
	}

	public double getSolde() {
		return _solde;
	}

}
