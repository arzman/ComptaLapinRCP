
package compta.core.common;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ApplicationFormatter {

	public static NumberFormat montantFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);

	public static NumberFormat pourcentFormat = NumberFormat.getPercentInstance(Locale.FRANCE);
	
	public static SimpleDateFormat moiAnneedateFormat = new SimpleDateFormat("MMMM yyyy");

}
