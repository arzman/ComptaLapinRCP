package compta.core.presentation.dialogs.budget;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * {@link Dialog} permettant de saisir un montant, une date et un libellé qui
 * serviront à l'action "Utiliser Budget"
 * 
 * @author Arthur
 *
 */
public class UtiliseBudgetDialog extends Dialog {

	/**
	 * Le montant utilisé
	 */
	private double _montant;

	/**
	 * La date à laquelle le budget est utilisé
	 */
	private Calendar _date;

	/**
	 * Le libellé de l'opération
	 */
	private String _libelle;

	/**
	 * Le champ de saisie du montant
	 */
	private Text _montantFld;

	/**
	 * Champ de saisie de la date de l'opération
	 */
	private DateTime _dateTime;

	/**
	 * Champ de saisie du libelle
	 */
	private Text _libelleFld;

	public UtiliseBudgetDialog() {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);

		_montant = 0;
		_date = GregorianCalendar.getInstance();
		_libelle = "Dépense";

	}

	@Override
	protected void configureShell(Shell newShell) {

		newShell.setText("Utiliser le budget");
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

	@Override
	protected Control createDialogArea(Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));

		// Saisi du montant
		Label montantLbl = new Label(container, SWT.NONE);
		montantLbl.setText("Montant");
		montantLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		_montantFld = new Text(container, SWT.BORDER);
		_montantFld.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_montantFld.setText(String.valueOf(_montant));

		// Saisi du libelle
		Label libelleLbl = new Label(container, SWT.NONE);
		libelleLbl.setText("Libelle");
		libelleLbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		_libelleFld = new Text(container, SWT.BORDER);
		_libelleFld.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		_libelleFld.setText(_libelle);

		// Saisi de la date
		_dateTime = new DateTime(container, SWT.DATE);
		_dateTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		_dateTime.setDate(_date.get(Calendar.YEAR), _date.get(Calendar.MONTH), _date.get(Calendar.DAY_OF_MONTH));

		return container;
	}

	@Override
	protected void okPressed() {

		// parsing du montant
		try {
			_montant = Double.parseDouble(_montantFld.getText());
			_libelle = _libelleFld.getText();
			_date = new GregorianCalendar(_dateTime.getYear(), _dateTime.getMonth(), _dateTime.getDay());
			
			super.okPressed();
			
		} catch (NumberFormatException e) {

			MessageDialog.openError(getParentShell(), "Erreur", "Le montant saisi est invalide");
			
			
		}
		
	}

	/**
	 * Retourne le montant saisi
	 */
	public double getMontant() {
		return _montant;
	}

	/**
	 * Retourne le libelle saisi
	 * 
	 * @return
	 */
	public String getLibelle() {
		return _libelle;
	}

	/**
	 * Retourne la date saisie
	 * 
	 * @return
	 */
	public Calendar getDate() {

		return _date;
	}

}
