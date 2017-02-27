
package compta.core.presentation.dialogs.budget;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import compta.core.application.identifier.Identifier;
import compta.core.application.manager.BudgetManager;
import compta.core.common.ComptaException;

public class EditBudgetDialog extends Dialog {

	private Text nomText;

	private Text soldeText;

	private final Identifier _budgetId;

	private String _nom;

	private double _objectif;

	private double _utilise;
	private Text _text;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public EditBudgetDialog(Identifier compteId) {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);
		
		_budgetId = compteId;

		try {
			if (_budgetId != null) {
				_nom = BudgetManager.getInstance().getNomBudget(_budgetId);
				_objectif = BudgetManager.getInstance().getObjectifBudget(_budgetId);
				_utilise = BudgetManager.getInstance().getUtiliseBudget(_budgetId);
			} else {
				_nom = "";
				_objectif = 0.0;
				_utilise = 0.0;
			}
		} catch (final ComptaException ex) {
			_nom = "";
			_objectif = 0.0;
			_utilise = 0.0;
		}

	}

	@Override
	protected void configureShell(Shell newShell) {

		newShell.setText("Budget");
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
		lblSoldeDuCompte.setText("Objectif :");

		soldeText = new Text(container, SWT.BORDER);
		soldeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		soldeText.setText(String.valueOf(_objectif));
		soldeText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if (!soldeText.getText().trim().isEmpty()) {
					try {
						_objectif = Double.parseDouble(soldeText.getText().trim());
					} catch (final NumberFormatException ex) {
						soldeText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					}
				} else {
					soldeText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				}

			}
		});

		final Label lblUtilis = new Label(container, SWT.NONE);
		lblUtilis.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUtilis.setText("Utilis\u00E9 :");

		_text = new Text(container, SWT.BORDER);
		_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_text.setText(String.valueOf(_utilise));
		_text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {

				if (!_text.getText().trim().isEmpty()) {
					try {
						_utilise = Double.parseDouble(_text.getText().trim());
					} catch (final NumberFormatException ex) {
						_text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					}
				} else {
					_text.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
				}

			}
		});

		return container;
	}

	public String getNom() {
		return _nom;
	}

	public double getObjectif() {
		return _objectif;
	}

	public double getUtilise() {

		return _utilise;
	}

}
