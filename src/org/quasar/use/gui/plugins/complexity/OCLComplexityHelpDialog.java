package org.quasar.use.gui.plugins.complexity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * A dialog with information on OCL complexity metrics.
 * 
 * @author Maria Sales
 */
@SuppressWarnings("serial")
public class OCLComplexityHelpDialog extends JDialog {
	
	public OCLComplexityHelpDialog(JFrame parent) {
		super(parent, "Help: OCL Complexity");

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel infoBox = getInfoBox();
		
		JPanel textPane = new JPanel();
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new BorderLayout());
		p.add(new JScrollPane(infoBox), BorderLayout.CENTER);
		textPane.add(p);
		addSeparator(textPane);

		JComponent contentPane = (JComponent) getContentPane();
		contentPane.add(textPane, BorderLayout.CENTER);

		pack();
		setSize(new Dimension(500, 200));
		setLocationRelativeTo(parent);
	}

	private JPanel getInfoBox() {
		JPanel infoBox = new JPanel();
	    infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
	    infoBox.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	    addOCLMetricsInfo(infoBox);
		return infoBox;
	}

	private void addOCLMetricsInfo(JPanel infoBox) {
		addOCLComplexityTitle(infoBox);
        addNNR(infoBox);
        addNAN(infoBox);
        addWNO(infoBox);
        addNNC(infoBox);
        addWNM(infoBox);
        addNPT(infoBox);
        addNUCA(infoBox);
        addNUCO(infoBox);
        addDN(infoBox);
        addWNN(infoBox);
        addWCO(infoBox);
        addReference(infoBox);
	}

	private void addOCLComplexityTitle(JPanel infoBox) {
		infoBox.add(boldLine("OCL METRICS DEFINITION"));
        addSeparator(infoBox);
	}

	private void addNNR(JPanel infoBox) {
		infoBox.add(boldLine("NNR Metric (Number of Navigated Relationships):"));
        infoBox.add(line("Measures the total number of navigated relationships in an expression."));
        infoBox.add(line("Relationships are only counted once, even if they're navigated multiple times."));
        addSeparator(infoBox);
	}
	
	private void addNAN(JPanel infoBox) {
		infoBox.add(boldLine("NAN Metric (Number of Attributes referred through Navigations):"));
        infoBox.add(line("Counts the number of attributes referred through navigations."));
        addSeparator(infoBox);
	}

	private void addWNO(JPanel infoBox) {
		infoBox.add(boldLine("WNO Metric (Weighted Number of referred Operations through navigations):"));
        infoBox.add(line("sum of weighted operations (where the weight is defined by the number of"));
        infoBox.add(line("in/out parameters,  including the return type) mentioned through navigations."));
        addSeparator(infoBox);
	}

	private void addNNC(JPanel infoBox) {
		infoBox.add(boldLine("NNC Metric (Number of Navigated Classes):"));
        infoBox.add(line("number of classes, association classes or interfaces referred through navigations."));
        addSeparator(infoBox);
	}

	private void addWNM(JPanel infoBox) {
		infoBox.add(boldLine("WNM (Weighted Number of Messages):"));
        infoBox.add(line("sum of weighted messages (where the weight is defined by the number of parameters)"));
        infoBox.add(line("present in an expression."));
        infoBox.add(line("(Not available in this version)"));
        addSeparator(infoBox);
	}

	private void addNPT(JPanel infoBox) {
		infoBox.add(boldLine("NPT (Number of Parameters whose Types are classes defined in the Class Diagram):"));
        infoBox.add(line("number of different classes and interfaces used as in/out parameters or result."));
        infoBox.add(line("(Not available in this version)"));
        addSeparator(infoBox);
	}

	private void addNUCA(JPanel infoBox) {
		infoBox.add(boldLine("NUCA (Number of Utility Class Attributes used):"));
        infoBox.add(line("number of utility classes' attributes used in an expression; attributes that belong to"));
        infoBox.add(line("the same utility class are only counted once, even if they are referred multiple times."));
        addSeparator(infoBox);
	}

	private void addNUCO(JPanel infoBox) {
		infoBox.add(boldLine("NUCO (Number of Utility Class Operations used):"));
        infoBox.add(line("similar to the NUCA metric, but instead of attributes it considers operations."));
        addSeparator(infoBox);
	}

	private void addDN(JPanel infoBox) {
		infoBox.add(boldLine("DN (Depth of Navigations):"));
        infoBox.add(line("maximum depth of a navigation tree, where the root node represents the class name"));
        infoBox.add(line("of the contextual instance (self); for each navigation that starts from self,"));
        infoBox.add(line("we create a branch that connects to a new node, which represents the navigated class;"));
        infoBox.add(line("a new tree is created if a navigation contains a collection operation defined"));
        infoBox.add(line("in terms of new navigation(s), and is connected to the original tree through a"));
        infoBox.add(line("“definition connection”."));
        addSeparator(infoBox);
	}

	private void addWNN(JPanel infoBox) {
		infoBox.add(boldLine("WNN (Weighted Number of Navigations):"));
        infoBox.add(line("sum of weighted navigations presented in an expression, where the weight is defined"));
        infoBox.add(line("by the level on which the operation is used in an expression."));
        addSeparator(infoBox);
	}

	private void addWCO(JPanel infoBox) {
		infoBox.add(boldLine("WCO (Weighted Number of Collection Operations):"));
        infoBox.add(line("sum of weighted collection operations, where the weight is defined by the level"));
        infoBox.add(line("on which the operation is used in an expression."));
        addSeparator(infoBox);
	}

	private void addReference(JPanel infoBox) {
		infoBox.add(line("Reference: L.  Reynoso,  M.  Genero,  and  M.  Piattini,  “Measuring  Ocl  Expressions:  an"));
        infoBox.add(line("Approach  Based  on  Cognitive  Techniques,”  inMetrics  for  Software  ConceptualModels,"));
        infoBox.add(line("Imperial  College  Press,  Distributed  by  WorldScientific Publishing Co., Jan. 2005, pp. 161–206"));
        addSeparator(infoBox);
	}
	
	private JLabel boldLine(String text) {
		JLabel l = line(text);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        return l;
	}
	
    private JLabel line(String s) {
        JLabel l = new JLabel(s);
        l.setForeground(Color.black);
        return l;
    }
    
	private void addSeparator(JPanel infoBox) {
		infoBox.add(Box.createRigidArea(new Dimension(0,5)));
	}

}
