package org.quasar.use.gui.plugins.complexity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.tzi.use.config.Options;
import org.tzi.use.gui.util.CloseOnEscapeKeyListener;
import org.tzi.use.gui.util.TextComponentWriter;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;
import org.tzi.use.main.ChangeEvent;
import org.tzi.use.main.ChangeListener;
import org.tzi.use.main.Session;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.TeeWriter;

/**
 * USE - UML based specification environment Copyright (C) 1999-2004 Mark
 * Richters, University of Bremen Original author: Mark Richters
 * 
 * A dialog for entering and evaluating OCL expressions.
 * 
 * @author Maria Sales
 */
@SuppressWarnings("serial")
public class EvalOCLDialog extends JDialog {
	
	private MSystem fSystem;

	private final JTextArea fTextIn;
	private final JTextArea fTextOut;

	private final JButton btnEval;

	private OCLComplexityHelpDialog complexityHelpDialog;
	
	private final ChangeListener sessionChangeListener = new ChangeListener() {
		@Override
		public void stateChanged(ChangeEvent e) {
			Session session = (Session) e.getSource();
			fSystem = getSystem(session);
		}
	};

	public EvalOCLDialog(final Session session, JFrame parent, List<ClassDiagramView> classDiagrams) {
		super(parent, "Evaluate OCL expression with metrics");
		fSystem = getSystem(session);
		session.addChangeListener(sessionChangeListener);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// unregister from session on close
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				session.removeChangeListener(sessionChangeListener);
			}
		});

		// Use font specified in the settings
		Font evalFont = Font.getFont("use.gui.evalFont", getFont());

		// create text components and labels
		fTextIn = new JTextArea();
		fTextIn.setFont(evalFont);
		JLabel textInLabel = new JLabel("Enter OCL expression:");
		textInLabel.setDisplayedMnemonic('O');
		textInLabel.setLabelFor(fTextIn);

		fTextOut = new JTextArea();
		fTextOut.setEditable(false);
		fTextOut.setLineWrap(true);
		fTextOut.setFont(evalFont);

		JLabel textOutLabel = new JLabel("Result:");
		textOutLabel.setLabelFor(fTextOut);

		// create panel on the left and add text components
		JPanel textPane = new JPanel();
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new BorderLayout());
		p.add(textInLabel, BorderLayout.NORTH);
		p.add(new JScrollPane(fTextIn), BorderLayout.CENTER);
		textPane.add(p);
		textPane.add(Box.createRigidArea(new Dimension(0, 5)));

		p = new JPanel(new BorderLayout());
		p.add(textOutLabel, BorderLayout.NORTH);
		p.add(new JScrollPane(fTextOut), BorderLayout.CENTER);
		textPane.add(p);
		textPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// create panel on the right containing buttons
		JPanel btnPane = new JPanel();
		btnEval = getEvaluateButton();
		JButton btnClear = getClearButton();
		JButton btnClose = getCloseButton();
		JButton helpBtn = getHelpButton(parent);
		
		btnPane.setLayout(new BoxLayout(btnPane, BoxLayout.Y_AXIS));
		btnPane.add(Box.createVerticalGlue());
		btnPane.add(btnEval);
		btnPane.add(Box.createRigidArea(new Dimension(0, 5)));
		btnPane.add(Box.createRigidArea(new Dimension(0, 5)));
		btnPane.add(btnClear);
		btnPane.add(Box.createRigidArea(new Dimension(0, 5)));
		btnPane.add(helpBtn);
		btnPane.add(btnClose);
		btnPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

		JComponent contentPane = (JComponent) getContentPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		contentPane.add(btnPane, BorderLayout.EAST);
		
		pack();
		setSize(new Dimension(500, 200));
		setLocationRelativeTo(parent);
		fTextIn.requestFocus();

		setupEscapeListener();
	}

	private JButton getClearButton() {
		Dimension dim;
		JButton btnClear = new JButton("Clear");
		btnClear.setMnemonic('C');
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fTextOut.setText(null);
			}

		});
		dim = btnClear.getMaximumSize();
		dim.width = Short.MAX_VALUE;
		btnClear.setMaximumSize(dim);
		return btnClear;
	}

	private JButton getCloseButton() {
		Dimension dim;
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		dim = btnClose.getMaximumSize();
		dim.width = Short.MAX_VALUE;
		btnClose.setMaximumSize(dim);
		return btnClose;
	}

	private JButton getEvaluateButton() {
		Dimension dim;
		JButton complexityBtn = new JButton("Evaluate OCL Complexity");
		complexityBtn.setMnemonic('O');
		complexityBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getRootPane().setDefaultButton(complexityBtn);
				evaluateOclComplexity(fTextIn.getText());
			}
		});
		dim = complexityBtn.getMaximumSize();
		dim.width = Short.MAX_VALUE;
		complexityBtn.setMaximumSize(dim);
		return complexityBtn;
	}

	private JButton getHelpButton(JFrame parent) {
		Dimension dim;
		JButton btn = new JButton("Help: OCL Complexity");
		btn.setMnemonic('H');
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (complexityHelpDialog == null) {
					complexityHelpDialog = new OCLComplexityHelpDialog(parent);
				}
				complexityHelpDialog.setVisible(true);
			}

		});
		dim = btn.getMaximumSize();
		dim.width = Short.MAX_VALUE;
		btn.setMaximumSize(dim);
		return btn;
	}

	/**
	 * Returns the session's system if available or an empty model.
	 * 
	 * @param session
	 *            current session
	 * @return a system to evaluate expressions in
	 */
	private MSystem getSystem(Session session) {
		if (session.hasSystem()) {
			return session.system();
		} else {
			MModel model = new ModelFactory().createModel("empty model");
			return new MSystem(model);
		}
	}

	private void closeDialog() {
		setVisible(false);
		dispose();
	}
	
	private void setupEscapeListener() {
		CloseOnEscapeKeyListener ekl = new CloseOnEscapeKeyListener(this);
		addKeyListener(ekl);
		fTextIn.addKeyListener(ekl);
		fTextOut.addKeyListener(ekl);
	}

	private boolean evaluateOclComplexity(String in) {
		if (this.fSystem == null) {
			fTextOut.setText("No system!");
			return false;
		}

		// clear previous results
		fTextOut.setText(null);

		// send error output to result window and msg stream
		StringWriter msgWriter = new StringWriter();
		PrintWriter out = new PrintWriter(new TeeWriter(new TextComponentWriter(fTextOut), msgWriter), true);

		// compile query
		Expression expr = OCLCompiler.compileExpression(fSystem.model(), fSystem.state(), in, "Error", out,
				fSystem.varBindings());

		out.flush();
		fTextIn.requestFocus();

		// compile errors?
		if (expr == null) {
			// try to parse error message and set caret to error position
			String msg = msgWriter.toString();
			int colon1 = msg.indexOf(':');
			if (colon1 != -1) {
				int colon2 = msg.indexOf(':', colon1 + 1);
				int colon3 = msg.indexOf(':', colon2 + 1);

				try {
					int line = Integer.parseInt(msg.substring(colon1 + 1, colon2));
					int column = Integer.parseInt(msg.substring(colon2 + 1, colon3));
					int caret = 1 + StringUtil.nthIndexOf(in, line - 1, Options.LINE_SEPARATOR);
					caret += column;

					// sanity check
					caret = Math.min(caret, fTextIn.getText().length());
					fTextIn.setCaretPosition(caret);
				} catch (NumberFormatException ex) {
				}
			}
			return false;
		}

		try {
			// evaluate it with current system state
			IComplexityMetric complexityMetric = new ComplexityMetric();
			expr.processWithVisitor(new ExpressionComplexityVisitor(complexityMetric));

			// print result
			fTextOut.setText(getOclComplexity(complexityMetric));

			return true;
		} catch (MultiplicityViolationException e) {
			fTextOut.setText("Could not evaluate. " + e.getMessage());
		}
		return false;
	}

	private String getOclComplexity(IComplexityMetric complexityMetric) {
		IComplexityMetricResult result = complexityMetric.getResult();
		return String.format("NNR: %d | NAN: %d | WNO: %d | NNC: %d | NUCA: %d | NUCO: %d | WNN: %d | DN: %d | WCO: %d",
				result.getNNR(), result.getNAN(), result.getWNO(), result.getNNC(), result.getNUCA(), result.getNUCO(),
				result.getWNN(), result.getDN(), result.getWCO());
	}

}
