package compta.core.presentation.dialogs.date;

import java.util.Calendar;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class DateChooserDialog extends Dialog {

	private Calendar _selectedDate;
	private DateTime _dateTime;
	private final String _text;

	public DateChooserDialog(String text_) {
		super(PlatformUI.getWorkbench().getDisplay().getActiveShell());
		setShellStyle(SWT.CLOSE | SWT.RESIZE | SWT.TITLE | SWT.APPLICATION_MODAL);

		_text = text_;
	}

	@Override
	protected void configureShell(Shell newShell_) {
		super.configureShell(newShell_);
		newShell_.setText("Sélection de date");
	}

	@Override
	protected Control createDialogArea(Composite parent_) {

		final Composite area = new Composite(parent_, SWT.NONE);
		area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		area.setLayout(new GridLayout(1, false));

		final Label lblNewLabel = new Label(area, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lblNewLabel.setBounds(0, 0, 55, 15);
		lblNewLabel.setText(_text);

		_dateTime = new DateTime(area, SWT.BORDER | SWT.CALENDAR | SWT.LONG);
		_dateTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		return area;
	}

	public Calendar getDate() {
		return _selectedDate;
	}

	@Override
	protected void okPressed() {
		final Calendar cal = Calendar.getInstance();
		cal.set(_dateTime.getYear(), _dateTime.getMonth(), _dateTime.getDay());
		_selectedDate = cal;
		super.okPressed();
	}
}
