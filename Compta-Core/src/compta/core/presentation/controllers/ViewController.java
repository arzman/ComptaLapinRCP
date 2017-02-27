
package compta.core.presentation.controllers;

import java.util.ArrayList;

import compta.core.presentation.views.ControlledView;

public class ViewController {

	private final ArrayList<ControlledView> _controlledViews;

	public ViewController() {

		_controlledViews = new ArrayList<ControlledView>();

	}

	public void addControlledView(final ControlledView view) {

		_controlledViews.add(view);

	}

	protected void fireControllerEvent(final ControllerEvent event) {

		for (final ControlledView view : _controlledViews) {
			view.processControlEvent(event);
		}

	}

	public void removeControlledView(final ControlledView view) {
		_controlledViews.remove(view);
	}

}
