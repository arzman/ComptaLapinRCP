package compta.core.presentation.viewersorter;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;

public abstract class AbstractViewerSorter extends ViewerSorter {

	
	/**
	 * Colonne sur laquelle le tri est fait
	 */
	protected int propertyIndex;
	/**
	 * Sens descendant
	 */
	protected static final int DESCENDING = 1;
	
	/**
	 * direction du tri 1/0/-1
	 */
	protected int direction = DESCENDING;

	public AbstractViewerSorter() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}

	/**
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public abstract int  compare(Viewer viewer, Object e1, Object e2) ;

	/**
	 * Retourne le sens du tri
	 * @return
	 */
	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}
	
}
