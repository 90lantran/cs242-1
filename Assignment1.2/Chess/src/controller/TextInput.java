package controller;

/**
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * - Neither the name of Oracle or the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.*;

import model.Chess;

/**
 * TextInput.java uses these additional files: SpringUtilities.java ...
 */
public class TextInput extends JPanel implements ActionListener, FocusListener {
	private static final long serialVersionUID = -673030337179571462L;

	static JFrame frame;
	JTextField playerName1, playerName2;
	Font regularFont, italicFont;
	final static int GAP = 10;

	/**
	 * Input constructor. Creates a item to add to the ending frame.
	 * Don't allow us to stretch vertically.
	 */
	public TextInput() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		JPanel leftHalf = new JPanel() {
			private static final long serialVersionUID = 4726960303166565507L;

			public Dimension getMaximumSize() {
				Dimension pref = getPreferredSize();
				return new Dimension(Integer.MAX_VALUE, pref.height);
			}
		};
		
		leftHalf.setLayout(new BoxLayout(leftHalf, BoxLayout.PAGE_AXIS));
		leftHalf.add(createEntryFields());
		leftHalf.add(createButtons());

		add(leftHalf);

		playerName1.setText("Player 1");
		playerName2.setText("Player 2");
	}

	/**
	 * Generates the buttons for the text input prompt.
	 * Match the SpringLayout's gap, subtracting 5 to make
	 * up for the default gap FlowLayout provides.
	 * @return the new backgrond component for the text box.
	 */
	protected JComponent createButtons() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		JButton button = new JButton("Okay");
		button.addActionListener(this);
		button.setActionCommand("save");
		panel.add(button);

		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, GAP - 5, GAP - 5));
		return panel;
	}

	/**
	 * Called when the user clicks the button or presses Enter in a text field.
	 * @param e the action event triggered by the user
	 */
	public void actionPerformed(ActionEvent e) {
		if ("clear".equals(e.getActionCommand())) {
			playerName1.setText("Player 1");
			playerName2.setText("Player 2");
		} else if ("save".equals(e.getActionCommand())) {

			JOptionPane optionPane = new JOptionPane("Names Saved.",
					JOptionPane.INFORMATION_MESSAGE);
			JDialog dialog = optionPane.createDialog(null, "");
			dialog.setLocation(200, 200);
			dialog.setVisible(true);
			frame.setVisible(false);
			String [] names = {playerName1.getText(),playerName2.getText()};
			Chess.setPlayerNames(names);
		}
	}

	/**
	 * A convenience method for creating a MaskFormatter.
	 * @param s the string for creating a new formatter
	 * @return the resulting formatter
	 */
	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	/**
	 * Called when one of the fields gets the focus so that we can select the
	 * focused field.
	 * @param e an event that occurs when 
	 * 			text is added to the input pane
	 */
	public void focusGained(FocusEvent e) {
		Component c = e.getComponent();
		if (c instanceof JFormattedTextField) {
			selectItLater(c);
		} else if (c instanceof JTextField) {
			((JTextField) c).selectAll();
		}
	}

	/**
	 * Workaround for formatted text field focus side effects.
	 * @param c the component that has been selected.
	 */
	protected void selectItLater(Component c) {
		if (c instanceof JFormattedTextField) {
			final JFormattedTextField ftf = (JFormattedTextField) c;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					ftf.selectAll();
				}
			});
		}
	}

	/**
	 * IGNORE THIS.
	 * Needed for FocusListener interface. 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 * @param e the focus event that has been lost
	 */
	public void focusLost(FocusEvent e) {} 

	/**
	 * Associate label/field pairs, add everything, and lay it out.
	 * Add listeners to each field.
	 * @return the component containing the labels for the text input.
	 */
	protected JComponent createEntryFields() {
		JPanel panel = new JPanel(new SpringLayout());

		String[] labelStrings = { "Player 1 Name:", "Player 2 Name:"};

		JLabel[] labels = new JLabel[labelStrings.length];
		JComponent[] fields = new JComponent[labelStrings.length];
		int fieldNum = 0;

		playerName1 = new JTextField();
		playerName1.setColumns(10);
		fields[fieldNum++] = playerName1;
		
		playerName2 = new JTextField();
		playerName2.setColumns(10);
		fields[fieldNum++] = playerName2;

		addFieldLabels(panel, labelStrings, labels, fields);
		SpringUtilities.makeCompactGrid(panel, labelStrings.length, 2, GAP,
				GAP, 
				GAP, GAP / 2);
		return panel;
	}

	/**
	 * A helper function to add strings to their
	 * matching text boxes in the input panel.
	 * @param panel the underlying window
	 * @param label Strings the labels for each text field
	 * @param labels the the label object for each field
	 * @param fields the text entry fields
	 */
	private void addFieldLabels(JPanel panel, String[] labelStrings,
			JLabel[] labels, JComponent[] fields) {
		for (int i = 0; i < labelStrings.length; i++) {
			labels[i] = new JLabel(labelStrings[i], JLabel.TRAILING);
			labels[i].setLabelFor(fields[i]);
			panel.add(labels[i]);
			panel.add(fields[i]);

			JTextField tf = null;
			tf = (JTextField) fields[i];
			tf.addActionListener(this);
			tf.addFocusListener(this);
		}
	}

	/**
	 * A function to generate input storage for the text.
	 * @param x the number of values to generate
	 * @return a new string array of values
	 */
	public String[] getValues(int x) {
		String[] values = new String[x + 1];
		for (int i = 0; i < values.length; i++) {
			values[i] = String.valueOf(i);
		}
		return values;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 * Create and set up the window.
	 * Add contents to the window.
	 * Display the window.
	 */
	public static void createAndShowGUI() {
		frame = new JFrame("Settings");

		frame.add(new TextInput());
		frame.setLocation(200,200);
		
		frame.pack();
		frame.setVisible(true);
	}
}
