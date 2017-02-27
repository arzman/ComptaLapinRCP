package compta.core.common;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;

import compta.core.Activator;

public class ApplicationIcon {

	public static final ImageDescriptor REMOVE_ICON = ImageDescriptor.createFromURL(FileLocator.find(
			Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/remove_icon.png"), null));

	public static final ImageDescriptor ADD_ICON = ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
			new Path("icons/add_icon.gif"), null));

	public static final ImageDescriptor EDIT_ICON = ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
			new Path("icons/edit_icon.jpg"), null));

	public static final ImageDescriptor VALID_ICON = ImageDescriptor.createFromURL(FileLocator.find(
			Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/valid_icon.png"), null));

	public static final ImageDescriptor UP_ICON = ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
			new Path("icons/up_icon.jpg"), null));

	public static final ImageDescriptor DOWN_ICON = ImageDescriptor.createFromURL(FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID),
			new Path("icons/down_icon.png"), null));

	public static final ImageDescriptor CONFIG_ICON = ImageDescriptor.createFromURL(FileLocator.find(
			Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/config_icon.png"), null));
	
	public static final ImageDescriptor USE_BUDGET_ICON = ImageDescriptor.createFromURL(FileLocator.find(
			Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/use_budget_icon.gif"), null));
	
	public static final ImageDescriptor SHOW_BUDGET_ICON = ImageDescriptor.createFromURL(FileLocator.find(
			Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/use_budget_icon.gif"), null));
}
