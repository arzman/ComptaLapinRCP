package compta.core.presentation.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import compta.core.application.identifier.OperationIdentifier;
import compta.core.presentation.composite.ExerciceMensuelComposite;
import compta.core.presentation.controllers.ControllerEvent;
import compta.core.presentation.controllers.ExerciceViewController;

/**
*
 */

public class ExerciceView extends ViewPart implements ControlledView {

	private Group _grpTrimestreCourant;

	private final ExerciceMensuelComposite[] emCompTable;

	private IMemento _memento;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "Compta.view.ExerciceView";

	private static final String MEMENTO_COL_RESS = "exercice.ressource.col";

	private static final String MEMENTO_COL_DEP = "exercice.depense.col";

	private static final String MEMENTO_COL_TRANS = "exercice.transfert.col";

	public ExerciceView() {

		ExerciceViewController.getInstance().addControlledView(this);
		emCompTable = new ExerciceMensuelComposite[3];

	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		_grpTrimestreCourant = new Group(parent, SWT.NONE);
		_grpTrimestreCourant.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		_grpTrimestreCourant.setText("Trimestre courant :");

		createTrimestCourant();

		if (_memento != null) {

			for (int j = 0; j < 3; j++) {

				for (int i = 0; i < emCompTable[j].getTableRessource().getColumnCount(); i++) {

					if (_memento.getInteger(MEMENTO_COL_RESS + j + "_" + i) != null) {
						emCompTable[j].getTableRessource().getColumn(i).setWidth(_memento.getInteger(MEMENTO_COL_RESS + j + "_" + i));
					}
				}

				for (int i = 0; i < emCompTable[j].getTableDepense().getColumnCount(); i++) {

					if (_memento.getInteger(MEMENTO_COL_DEP + j + "_" + i) != null) {
						emCompTable[j].getTableDepense().getColumn(i).setWidth(_memento.getInteger(MEMENTO_COL_DEP + j + "_" + i));
					}
				}

				for (int i = 0; i < emCompTable[j].getTableTransfert().getColumnCount(); i++) {

					if (_memento.getInteger(MEMENTO_COL_TRANS + j + "_" + i) != null) {
						emCompTable[j].getTableTransfert().getColumn(i).setWidth(_memento.getInteger(MEMENTO_COL_TRANS + j + "_" + i));
					}
				}
			}
		}

	}

	/**
	 * Crée un tableau par exercice mensuel contenu dans l'exercice multimensuel
	 * courant.
	 */
	private void createTrimestCourant() {

		final GridLayout layout = new GridLayout(3, true);
		_grpTrimestreCourant.setLayout(layout);

		if (ExerciceViewController.getInstance().getCurrentTrimestre() != null) {

			for (int i = 0; i < 3; i++) {

				final ExerciceMensuelComposite comp = new ExerciceMensuelComposite(_grpTrimestreCourant, i);
				final GridData gbc = new GridData(SWT.FILL, SWT.FILL, true, true);
				comp.setLayoutData(gbc);
				emCompTable[i] = comp;

			}
		}

	}

	@Override
	public void dispose() {

		ExerciceViewController.getInstance().removeControlledView(this);
		ExerciceViewController.getInstance().dipose();
		super.dispose();

	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (memento != null) {
			_memento = memento;
		}
	}

	@Override
	public void processControlEvent(ControllerEvent event) {

		if (event.getName().compareTo(ExerciceViewController.SET_TRIMESTRE) == 0) {

			refreshView();

		}

		if (event.getName().compareTo(ExerciceViewController.OPERATION_EDITED) == 0) {

			refreshOperation((OperationIdentifier) event.getValue());

		}

		if (event.getName().compareTo(ExerciceViewController.OPERATION_REMOVED) == 0) {

			refreshComp((OperationIdentifier) event.getValue());

		}

		if (event.getName().compareTo(ExerciceViewController.OPERATION_ADD) == 0) {

			refreshComp((OperationIdentifier) event.getValue());

		}

	}

	private void refreshComp(OperationIdentifier id) {

		if (id != null) {
			emCompTable[id.getNumMoi()].refreshViewer();
		} else {
			for (final ExerciceMensuelComposite comp : emCompTable) {

				comp.refreshViewer();
			}

		}

	}

	private void refreshOperation(OperationIdentifier id) {

		emCompTable[id.getNumMoi()].refreshOperation(id);

	}

	/**
	 * Rafraichit les composants de la vue.
	 */
	public void refreshView() {

		// on supprime l'ancien contenu
		for (final Control child : _grpTrimestreCourant.getChildren()) {
			child.dispose();
		}
		// on recrée le contenu
		createTrimestCourant();
		// refresh
		_grpTrimestreCourant.layout();
	}

	@Override
	public void saveState(IMemento memento) {

		for (int j = 0; j < 3; j++) {

			if (emCompTable[j] != null) {
				for (int i = 0; i < emCompTable[j].getTableRessource().getColumnCount(); i++) {
					memento.putInteger(MEMENTO_COL_RESS + j + "_" + i, emCompTable[j].getTableRessource().getColumn(i).getWidth());
				}

				for (int i = 0; i < emCompTable[j].getTableDepense().getColumnCount(); i++) {
					memento.putInteger(MEMENTO_COL_DEP + j + "_" + i, emCompTable[j].getTableDepense().getColumn(i).getWidth());
				}

				for (int i = 0; i < emCompTable[j].getTableTransfert().getColumnCount(); i++) {
					memento.putInteger(MEMENTO_COL_TRANS + j + "_" + i, emCompTable[j].getTableTransfert().getColumn(i).getWidth());
				}
			}
		}

		super.saveState(memento);

	}

	@Override
	public void setFocus() {

	}
}