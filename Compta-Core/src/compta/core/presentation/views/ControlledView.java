
package compta.core.presentation.views;

import compta.core.presentation.controllers.ControllerEvent;

public interface ControlledView {

	void processControlEvent(ControllerEvent event);

}
