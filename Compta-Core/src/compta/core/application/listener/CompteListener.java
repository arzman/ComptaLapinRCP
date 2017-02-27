
package compta.core.application.listener;

import compta.core.application.event.CompteEvent;

public interface CompteListener {

	void processCompteEvent(CompteEvent event);

}
