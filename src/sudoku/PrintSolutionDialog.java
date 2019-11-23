/*
 * Copyright (C) 2008-12  Bernhard Hobiger
 *
 * This file is part of HoDoKu.
 *
 * HoDoKu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * HoDoKu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with HoDoKu. If not, see <http://www.gnu.org/licenses/>.
 */

package sudoku;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import solver.SudokuSolver;
import solver.SudokuSolverFactory;

/**
 *
 * @author hobiwan
 */
@SuppressWarnings("serial")
public class PrintSolutionDialog extends javax.swing.JDialog implements Printable {
	private static final double LINE_HEIGHT = 1.2;
	private static final long serialVersionUID = 1L;

	private SolutionStep[] steps = null;
	private boolean[] selected = null;
	private String initialState = "";
	private PageFormat pageFormat = null;
	private PrinterJob job = null;
	private int imageSize = 0;

	// attributes for high res print
	private Font bigFont;
	private Font smallFont;
	private int imagePrintSize;
	private SudokuPanel panel;
	private Sudoku2 sudoku;
	private Sudoku2 oldSudoku;
	private SudokuSolver solver;
	private int printIndex;
	private boolean compressedStarted;
	private boolean isSingles;
	private int lastPageSeen = -1;
	private String pageEntryState;
	private int printIndexES;
	private boolean compressedStartedES;
	private boolean isSinglesES;
	private boolean printDone;

	/**
	 * Creates new form PrintSolutionDialog
	 * 
	 * @param parent
	 * @param modal
	 * @param stepsAsList
	 * @param initialState
	 */
	@SuppressWarnings("unchecked")
	public PrintSolutionDialog(java.awt.Frame parent, boolean modal, List<SolutionStep> stepsAsList,
			String initialState) {
		super(parent, modal);
		initComponents();

		getRootPane().setDefaultButton(printButton);

		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		getRootPane().getActionMap().put("ESCAPE", escapeAction);

		steps = new SolutionStep[stepsAsList.size()];
		selected = new boolean[steps.length];
		for (int i = 0; i < steps.length; i++) {
			steps[i] = stepsAsList.get(i);
		}
		this.initialState = initialState;

		stepList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		stepList.setCellRenderer(new CheckBoxRenderer());
		// DefaultListModel model = new DefaultListModel();
		stepList.setListData(steps);

		NumbersOnlyDocument doc = new NumbersOnlyDocument();
		widthTextField.setDocument(doc);
		widthTextField.setText("80");

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		optionPanel = new javax.swing.JPanel();
		widthLabel = new javax.swing.JLabel();
		widthTextField = new javax.swing.JTextField();
		compressSinglesCheckBox = new javax.swing.JCheckBox();
		compressSSTSCheckBox = new javax.swing.JCheckBox();
		titleLabel = new javax.swing.JLabel();
		titleTextField = new javax.swing.JTextField();
		stepsPanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		stepList = new javax.swing.JList();
		printButton = new javax.swing.JButton();
		copyButton = new javax.swing.JButton();
		pageOptionsButton = new javax.swing.JButton();
		closeButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog"); // NOI18N
		setTitle(bundle.getString("PrintSolutionDialog.title")); // NOI18N

		optionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("optionPanel.border.txt"))); // NOI18N

		widthLabel.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog")
				.getString("PrintSolutionDialog.widthLabel.mnemonic").charAt(0));
		widthLabel.setLabelFor(widthTextField);
		widthLabel.setText(bundle.getString("PrintSolutionDialog.widthLabel.text")); // NOI18N

		widthTextField.setText(bundle.getString("PrintSolutionDialog.widthTextField.text")); // NOI18N

		compressSinglesCheckBox.setMnemonic(java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog")
				.getString("PrintSolutionDialog.compressSinglesCheckBox.mnemonic").charAt(0));
		compressSinglesCheckBox.setText(bundle.getString("PrintSolutionDialog.compressSinglesCheckBox.text")); // NOI18N
		compressSinglesCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				compressSinglesCheckBoxActionPerformed(evt);
			}
		});

		compressSSTSCheckBox.setMnemonic(java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog")
				.getString("PrintSolutionDialog.compressSSTSCheckBox.mnemonic").charAt(0));
		compressSSTSCheckBox.setText(bundle.getString("PrintSolutionDialog.compressSSTSCheckBox.text")); // NOI18N
		compressSSTSCheckBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				compressSSTSCheckBoxActionPerformed(evt);
			}
		});

		titleLabel.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog")
				.getString("PrintSolutionDialog.titleLabel.mnemonic").charAt(0));
		titleLabel.setLabelFor(titleTextField);
		titleLabel.setText(bundle.getString("PrintSolutionDialog.titleLabel.text")); // NOI18N

		titleTextField.setText(bundle.getString("PrintSolutionDialog.titleTextField.text")); // NOI18N

		javax.swing.GroupLayout optionPanelLayout = new javax.swing.GroupLayout(optionPanel);
		optionPanel.setLayout(optionPanelLayout);
		optionPanelLayout.setHorizontalGroup(optionPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(optionPanelLayout.createSequentialGroup().addContainerGap()
						.addGroup(optionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(titleLabel).addComponent(widthLabel))
						.addGap(26, 26, 26)
						.addGroup(optionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(optionPanelLayout.createSequentialGroup()
										.addComponent(widthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 61,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18).addComponent(compressSinglesCheckBox))
								.addGroup(optionPanelLayout.createSequentialGroup().addGap(79, 79, 79)
										.addComponent(compressSSTSCheckBox))
								.addComponent(titleTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 344,
										Short.MAX_VALUE))
						.addContainerGap()));
		optionPanelLayout.setVerticalGroup(optionPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(optionPanelLayout.createSequentialGroup()
						.addGroup(optionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(titleLabel).addComponent(titleTextField,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(optionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(widthLabel)
								.addComponent(widthTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(compressSinglesCheckBox))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(compressSSTSCheckBox)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		stepsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("stepsPanel.border.text"))); // NOI18N

		stepList.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		stepList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				stepListMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(stepList);

		javax.swing.GroupLayout stepsPanelLayout = new javax.swing.GroupLayout(stepsPanel);
		stepsPanel.setLayout(stepsPanelLayout);
		stepsPanelLayout
				.setHorizontalGroup(stepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(stepsPanelLayout.createSequentialGroup().addContainerGap()
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
								.addContainerGap()));
		stepsPanelLayout
				.setVerticalGroup(stepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(stepsPanelLayout.createSequentialGroup()
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
								.addContainerGap()));

		printButton.setMnemonic(java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog")
				.getString("PrintSolutionDialog.printButton.mnemonic").charAt(0));
		printButton.setText(bundle.getString("PrintSolutionDialog.printButton.text")); // NOI18N
		printButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				printButtonActionPerformed(evt);
			}
		});

		copyButton.setMnemonic(java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog")
				.getString("PrintSolutionDialog.copyButton.mnemonic").charAt(0));
		copyButton.setText(bundle.getString("PrintSolutionDialog.copyButton.text")); // NOI18N
		copyButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				copyButtonActionPerformed(evt);
			}
		});

		pageOptionsButton.setMnemonic(java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog")
				.getString("PrintSolutionDialog.pageOptionsButton.mnemonic").charAt(0));
		pageOptionsButton.setText(bundle.getString("PrintSolutionDialog.pageOptionsButton.text")); // NOI18N
		pageOptionsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pageOptionsButtonActionPerformed(evt);
			}
		});

		closeButton.setMnemonic(java.util.ResourceBundle.getBundle("intl/PrintSolutionDialog")
				.getString("PrintSolutionDialog.closeButton.mnemonic").charAt(0));
		closeButton.setText(bundle.getString("PrintSolutionDialog.closeButton.text")); // NOI18N
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(stepsPanel, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(optionPanel, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup().addComponent(printButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(copyButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(pageOptionsButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(closeButton)))
						.addContainerGap()));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { closeButton, copyButton, printButton });

		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap()
						.addComponent(optionPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(stepsPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(closeButton).addComponent(pageOptionsButton).addComponent(copyButton)
								.addComponent(printButton))
						.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void stepListMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_stepListMouseClicked
		int index = stepList.locationToIndex(evt.getPoint());
		if (index == stepList.getSelectedIndex()) {
			if (compressSinglesCheckBox.isSelected() && SolutionType.isSingle(steps[index].getType())
					|| compressSSTSCheckBox.isSelected() && SolutionType.isSSTS(steps[index].getType())) {
				// ignore the click
			} else {
				// toggle selection
				selected[index] = !selected[index];
			}
			stepList.repaint();
		}
	}// GEN-LAST:event_stepListMouseClicked

	private void compressSinglesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_compressSinglesCheckBoxActionPerformed
		stepList.repaint();
	}// GEN-LAST:event_compressSinglesCheckBoxActionPerformed

	private void compressSSTSCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_compressSSTSCheckBoxActionPerformed
		if (compressSSTSCheckBox.isSelected()) {
			compressSinglesCheckBox.setEnabled(false);
		} else {
			compressSinglesCheckBox.setEnabled(true);
		}
		stepList.repaint();
	}// GEN-LAST:event_compressSSTSCheckBoxActionPerformed

	private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonActionPerformed
		setVisible(false);
	}// GEN-LAST:event_closeButtonActionPerformed

	private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_copyButtonActionPerformed
		copyToClipboard();
	}// GEN-LAST:event_copyButtonActionPerformed

	private void pageOptionsButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_pageOptionsButtonActionPerformed
		if (job == null) {
			job = PrinterJob.getPrinterJob();
		}
		if (pageFormat == null) {
			pageFormat = job.defaultPage();
		}
		pageFormat = job.pageDialog(pageFormat);
	}// GEN-LAST:event_pageOptionsButtonActionPerformed

	private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_printButtonActionPerformed

//        PageAttributes pa = new PageAttributes();
//        pa.setPrinterResolution(300);
//
//        // get printer job
//        JobAttributes ja = new JobAttributes();
//        ja.setDialog(JobAttributes.DialogType.NATIVE);
//        PrintJob pj = Toolkit.getDefaultToolkit().getPrintJob(parent, "Solution", ja, pa);
//        Graphics g = pj.getGraphics();
//        try {
//            System.out.println(pj.getPageDimension() + "/" + pj.getPageResolution());
//            print(g, pageFormat, 0);
//            pj.end();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

		imageSize = 80;
		try {
			imageSize = Integer.parseInt(widthTextField.getText());
		} catch (NumberFormatException ex) {
			// ignore it!
		}
		if (job == null) {
			job = PrinterJob.getPrinterJob();
		}
		if (pageFormat == null) {
			pageFormat = job.defaultPage();
		}
		try {
			job.setPrintable(this, pageFormat);
			if (job.printDialog()) {
				lastPageSeen = -1;
				printDone = false;
				job.print();
			}
		} catch (PrinterException ex) {
			JOptionPane.showMessageDialog(this, ex.toString(),
					java.util.ResourceBundle.getBundle("intl/MainFrame").getString("MainFrame.error"),
					JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_printButtonActionPerformed

	/**
	 * Copies the solution as pure text to the system clipboard.
	 */
	private void copyToClipboard() {
		// Title
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(new BufferedWriter(writer));
		String title = titleTextField.getText();
		if (!title.isEmpty()) {
			out.println(title);
			out.println();
		}
		// Construct Sudoku2
		sudoku = new Sudoku2();
		sudoku.setSudoku(initialState);
		solver = SudokuSolverFactory.getDefaultSolverInstance();
		oldSudoku = solver.getSudoku();
		solver.setSudoku(sudoku);
		// Givens
		out.println(ResourceBundle.getBundle("intl/PrintSolutionDialog").getString("PrintSolutionDialog.givens"));
		out.println(sudoku.getSudoku(ClipboardMode.CLUES_ONLY));
		out.println();
		// InitialState
		out.println(ResourceBundle.getBundle("intl/PrintSolutionDialog").getString("PrintSolutionDialog.initialState"));
		out.println(sudoku.getSudoku(ClipboardMode.PM_GRID));
		out.println();
		// now the solution
		compressedStarted = false;
		isSingles = true;
		for (int i = 0; i < steps.length; i++) {
			if (compressSinglesCheckBox.isSelected() && SolutionType.isSingle(steps[i].getType())
					|| compressSSTSCheckBox.isSelected() && SolutionType.isSSTS(steps[i].getType())) {
				// Singles or SSTS can be summarized to one line
				if (!compressedStarted) {
					compressedStarted = true;
					isSingles = true;
				}
				if (!SolutionType.isSingle(steps[i].getType())) {
					isSingles = false;
				}
			} else {
				// normal step
				if (compressedStarted) {
					// last entry was compressed -> output PM
					out.println();
					if (isSingles) {
						out.println(ResourceBundle.getBundle("intl/PrintSolutionDialog")
								.getString("PrintSolutionDialog.singlesTo"));
					} else {
						out.println(ResourceBundle.getBundle("intl/PrintSolutionDialog")
								.getString("PrintSolutionDialog.sstsTo"));
					}
					out.println(sudoku.getSudoku(ClipboardMode.PM_GRID));
					compressedStarted = false;
				}
				// now the step itself
				if (selected[i]) {
					out.println();
					out.println(sudoku.getSudoku(ClipboardMode.PM_GRID_WITH_STEP, steps[i]));
					out.println();
				} else {
					out.println(steps[i].toString(2));
				}
			}
			// now do the step
			solver.doStep(sudoku, steps[i]);
		}
		// summarization could be active
		if (compressedStarted) {
			out.println();
			if (isSingles) {
				out.println(ResourceBundle.getBundle("intl/PrintSolutionDialog")
						.getString("PrintSolutionDialog.singlesTo"));
			} else {
				out.println(
						ResourceBundle.getBundle("intl/PrintSolutionDialog").getString("PrintSolutionDialog.sstsTo"));
			}
			compressedStarted = false;
		}
		// print the solution
		out.println();
		out.println(ResourceBundle.getBundle("intl/PrintSolutionDialog").getString("PrintSolutionDialog.solution"));
		out.println(sudoku.getSudoku(ClipboardMode.PM_GRID));
		// ok, we are done -> copy to clipboard
		out.flush();
		try {
			Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection content = new StringSelection(writer.toString());
			clip.setContents(content, null);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error writing to clipboard", ex);
		}

		// reset everything
		solver.setSudoku(oldSudoku);
	}

	/**
	 * Prints the solution, uses a SudokuPanel for it
	 * 
	 * @param graphics
	 * @param pageFormat
	 * @param pageIndex
	 * @return
	 * @throws PrinterException
	 */
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
			if (pageIndex == lastPageSeen) {
				panel.setSudoku(pageEntryState, true);
				printIndex = printIndexES;
				compressedStarted = compressedStartedES;
				isSingles = isSinglesES;
				printDone = false;
			} else {
				printIndexES = printIndex;
				compressedStartedES = compressedStarted;
				isSinglesES = isSingles;
				pageEntryState = sudoku.getSudoku(ClipboardMode.LIBRARY);
			}
		}
		lastPageSeen = pageIndex;
		if (printDone) {
			solver.setSudoku(oldSudoku);
			return NO_SUCH_PAGE;
		}

		// Graphics2D-Objekt herrichten
		// CAUTION: The Graphics2D object is created with the native printer
		// resolution, but scaled down to 72dpi using an AffineTransform.
		// To print in high resolution this downscaling has to be reverted.
		Graphics2D printG2 = (Graphics2D) graphics;
		double scale = SudokuUtil.adjustGraphicsForPrinting(printG2);
//        AffineTransform at = printG2.getTransform();
//        double scale = at.getScaleX();
		int resolution = (int) (72.0 * scale);
//        AffineTransform newAt = new AffineTransform();
//        newAt.translate(at.getTranslateX(), at.getTranslateY());
//        newAt.shear(at.getShearX(), at.getShearY());
//        printG2.setTransform(newAt);
		printG2.translate((int) (pageFormat.getImageableX() * scale), (int) (pageFormat.getImageableY() * scale));
		int printWidth = (int) (pageFormat.getImageableWidth() * scale);
		int printHeight = (int) (pageFormat.getImageableHeight() * scale);
//        printG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        printG2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int y = 0;
		if (pageIndex == 0) {
			// create a few global attributes for that job
			// scale fonts up too fit the printer resolution
			Font tmpFont = Options.getInstance().getBigFont();
			bigFont = new Font(tmpFont.getName(), tmpFont.getStyle(), (int) (tmpFont.getSize() * scale));
			tmpFont = Options.getInstance().getSmallFont();
			smallFont = new Font(tmpFont.getName(), tmpFont.getStyle(), (int) (tmpFont.getSize() * scale));
			// size of images
			imagePrintSize = (int) (imageSize * resolution / 25.4);
			// Construct Sudoku2
			panel = new SudokuPanel(null);
			panel.setSudoku(initialState, true);
			sudoku = panel.getSudoku();
			solver = panel.getSolver();
			oldSudoku = solver.getSudoku();
			solver.setSudoku(sudoku);
			// start with the first step
			printIndex = 0;
			compressedStarted = false;

			// now start with the header
			// Title
			printG2.setFont(bigFont);
			FontMetrics metrics = printG2.getFontMetrics();
			String title = titleTextField.getText();
			if (!title.isEmpty()) {
				int textWidth = metrics.stringWidth(title);
				int textHeight = metrics.getHeight();
				y = (int) (LINE_HEIGHT * textHeight);
				printG2.drawString(title, (printWidth - textWidth) / 2, textHeight);
			}

			// initialize font
			printG2.setFont(smallFont);
			metrics = printG2.getFontMetrics();
			int lineHeight = (int) (LINE_HEIGHT * metrics.getHeight());

			// Givens
			y += lineHeight;
			printG2.drawString(
					ResourceBundle.getBundle("intl/PrintSolutionDialog").getString("PrintSolutionDialog.givens"), 0, y);
			String givens = sudoku.getSudoku(ClipboardMode.CLUES_ONLY);
			y += lineHeight;
			printG2.drawString(givens, 0, y);

			// InitialState (no checks here, has to fit page!)
			y += lineHeight;
			printG2.drawString(
					ResourceBundle.getBundle("intl/PrintSolutionDialog").getString("PrintSolutionDialog.initialState"),
					0, y);
			panel.setStep(null);
			BufferedImage img = panel.getSudokuImage(imagePrintSize);
			y += lineHeight / 2;
			printG2.drawImage(img, null, 0, y);
			y += imagePrintSize + lineHeight / 2;
		}

		// ok - normal draw
		// reset the font (hasnt been done yet except for the first page)
		printG2.setFont(smallFont);
		FontMetrics metrics = printG2.getFontMetrics();
		int lineHeight = (int) (LINE_HEIGHT * metrics.getHeight());

		// now print
		for (; printIndex < steps.length; printIndex++) {
			if (compressSinglesCheckBox.isSelected() && SolutionType.isSingle(steps[printIndex].getType())
					|| compressSSTSCheckBox.isSelected() && SolutionType.isSSTS(steps[printIndex].getType())) {
				// Singles or SSTS can be summarized to one line
				if (!compressedStarted) {
					compressedStarted = true;
					isSingles = true;
				}
				if (!SolutionType.isSingle(steps[printIndex].getType())) {
					isSingles = false;
				}
			} else {
				// normal step
				if (compressedStarted) {
					// last entry was compressed -> output PM
					if (y + 3 * lineHeight + imagePrintSize > printHeight) {
						return Printable.PAGE_EXISTS;
					}
					y += lineHeight;
					y += lineHeight / 2;
					if (isSingles) {
						printG2.drawString(ResourceBundle.getBundle("intl/PrintSolutionDialog")
								.getString("PrintSolutionDialog.singlesTo"), 0, y);
					} else {
						printG2.drawString(ResourceBundle.getBundle("intl/PrintSolutionDialog")
								.getString("PrintSolutionDialog.sstsTo"), 0, y);
					}
					panel.setStep(null);
					BufferedImage img = panel.getSudokuImage(imagePrintSize);
					y += lineHeight / 2;
					printG2.drawImage(img, null, 0, y);
					y += imagePrintSize + lineHeight;
					compressedStarted = false;
				}
				// now the step itself
				if (selected[printIndex]) {
					if (y + 2 * lineHeight + imagePrintSize > printHeight) {
						return Printable.PAGE_EXISTS;
					}
					panel.setStep(steps[printIndex]);
					BufferedImage img = panel.getSudokuImage(imagePrintSize);
					y += lineHeight / 2;
					printG2.drawImage(img, null, 0, y);
					y += imagePrintSize + lineHeight / 2;
					y += lineHeight / 2;
					printG2.drawString(steps[printIndex].toString(2), 0, y);
					y += lineHeight / 2;
				} else {
					if (y + lineHeight > printHeight) {
						return Printable.PAGE_EXISTS;
					}
					y += lineHeight;
					printG2.drawString(steps[printIndex].toString(2), 0, y);
				}
			}
			// now do the step
			solver.doStep(sudoku, steps[printIndex]);
		}
		// summarization could be active
		if (compressedStarted) {
			if (y + 1.5 * lineHeight > printHeight) {
				return Printable.PAGE_EXISTS;
			}
			y += lineHeight;
			y += lineHeight / 2;
			if (isSingles) {
				printG2.drawString(ResourceBundle.getBundle("intl/PrintSolutionDialog")
						.getString("PrintSolutionDialog.singlesToEnd"), 0, y);
			} else {
				printG2.drawString(
						ResourceBundle.getBundle("intl/PrintSolutionDialog").getString("PrintSolutionDialog.sstsToEnd"),
						0, y);
			}
			compressedStarted = false;
		}
		// print the solution
		if (y + 2.5 * lineHeight + imagePrintSize > printHeight) {
			return Printable.PAGE_EXISTS;
		}
		y += lineHeight;
		y += lineHeight / 2;
		printG2.drawString(
				ResourceBundle.getBundle("intl/PrintSolutionDialog").getString("PrintSolutionDialog.solution"), 0, y);
		panel.setStep(null);
		BufferedImage img = panel.getSudokuImage(imagePrintSize);
		y += lineHeight / 2;
		printG2.drawImage(img, null, 0, y);
		printDone = true;
		return Printable.PAGE_EXISTS;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				PrintSolutionDialog dialog = new PrintSolutionDialog(new javax.swing.JFrame(), true, null, null);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	@SuppressWarnings("rawtypes")
	class CheckBoxRenderer extends JCheckBox implements ListCellRenderer {
		private static final long serialVersionUID = 1L;

		CheckBoxRenderer() {
		}

		@Override
		public Component getListCellRendererComponent(JList listBox, Object obj, int index, boolean isSelected,
				boolean hasFocus) {
			SolutionStep step = (SolutionStep) obj;
			setText(step.toString(2));
			setSelected(selected[index]);
			if (compressSinglesCheckBox.isSelected() && SolutionType.isSingle(step.getType())
					|| compressSSTSCheckBox.isSelected() && SolutionType.isSSTS(step.getType())) {
				setEnabled(false);
			} else {
				setEnabled(true);
			}
			Color fg = Options.getInstance().getDifficultyLevels()[SolutionType.getStepConfig(step.getType())
					.getLevel()].getForegroundColor();
			if (isSelected) {
				fg = UIManager.getColor("List.selectionForeground");
				if (fg == null) {
					fg = UIManager.getColor("List[Selected].textForeground");
				}
				if (fg == null) {
					fg = Color.BLACK;
				}
			}
			if (!isEnabled()) {
				fg = UIManager.getColor("Button.disabledForeground");
				if (fg == null) {
					fg = UIManager.getColor("Button[Disabled].textForeground");
				}
				if (fg == null) {
					fg = Color.DARK_GRAY;
				}
			}
			if (isSelected) {
				Color bg = UIManager.getColor("List.selectionBackground");
				if (bg == null) {
					bg = UIManager.getColor("List[Selected].textBackground");
				}
				if (bg == null) {
					bg = Color.BLUE;
				}
				setBackground(bg);
				setForeground(fg);
//                System.out.println("SBG: " + bg);
//                System.out.println("SFG: " + fg);
			} else {
				setBackground(Options.getInstance().getDifficultyLevels()[SolutionType.getStepConfig(step.getType())
						.getLevel()].getBackgroundColor());
				setForeground(fg);
//                System.out.println("BG: " + UIManager.getColor("List.background"));
//                System.out.println("FG: " + UIManager.getColor("List.foreground"));
			}
			return this;
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton closeButton;
	private javax.swing.JCheckBox compressSSTSCheckBox;
	private javax.swing.JCheckBox compressSinglesCheckBox;
	private javax.swing.JButton copyButton;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JPanel optionPanel;
	private javax.swing.JButton pageOptionsButton;
	private javax.swing.JButton printButton;
	@SuppressWarnings("rawtypes")
	private javax.swing.JList stepList;
	private javax.swing.JPanel stepsPanel;
	private javax.swing.JLabel titleLabel;
	private javax.swing.JTextField titleTextField;
	private javax.swing.JLabel widthLabel;
	private javax.swing.JTextField widthTextField;
	// End of variables declaration//GEN-END:variables

}
