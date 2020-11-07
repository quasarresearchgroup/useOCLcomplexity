package org.quasar.use.gui.plugins;

import javax.swing.JOptionPane;

import org.quasar.use.gui.plugins.complexity.EvalOCLDialog;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.main.Session;
import org.tzi.use.runtime.gui.IPluginAction;
import org.tzi.use.runtime.gui.IPluginActionDelegate;

/**
 * This is the Plugin Action class. It provides the Action which will be
 * performed if the corresponding Plugin Action Delegate in the application is
 * called.
 * 
 * @author Maria Sales
 */
public class OCLMetricsExtent implements IPluginActionDelegate {

	public OCLMetricsExtent() {
	}

	public void performAction(IPluginAction pluginAction) {
		if (!pluginAction.getSession().hasSystem()) {
			JOptionPane.showMessageDialog(pluginAction.getParent(), "No model loaded. Please load a model first.",
					"No Model", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Getting Session object from Proxy
		Session session = pluginAction.getSession();

		// Getting MainWindow object from Proxy
		final MainWindow mainWindow = pluginAction.getParent();

		// Open EvalOCLDialog
		EvalOCLDialog dlg = new EvalOCLDialog(session, mainWindow, mainWindow.getClassDiagrams());
		dlg.setVisible(true);
	}
}
