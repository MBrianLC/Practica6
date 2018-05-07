package es.ucm.fdi.extra.texteditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.view.Command;
import es.ucm.fdi.view.MainWindowSim;

@SuppressWarnings("serial")
public class TextEditor extends JComponent{
	
	private JPanel reportsPanel;
	private JFileChooser fc;
	private JTextArea reportsArea;
	private File currentFile;
	private JMenu fileMenu;
	private JMenu simulatorMenu;
	private JMenu reportsMenu;
	private JSpinner stepsSpinner;
	private JTextField timeViewer;
	
	public TextEditor() {
		initGUI();
	}

	private void initGUI() {

		// text area
		addReportsArea();

		// tool bar
		createJToolBar();

		// menu bar
		this.setJMenuBar(createMenuBar());

	}
	
	private void addReportsArea(){
		reportsPanel = new JPanel(new BorderLayout());
		reportsPanel.setBorder(BorderFactory.createTitledBorder("Reports"));
		reportsArea = new JTextArea("");
		reportsArea.setEditable(false);
		reportsArea.setLineWrap(true);
		reportsArea.setWrapStyleWord(true);
		JScrollPane area = new JScrollPane(reportsArea);
		area.setPreferredSize(new Dimension(500, 500));
		reportsPanel.add(area);
	}

	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(getActionMap().get(Command.Load));
		fileMenu.add(getActionMap().get(Command.Save));
		fileMenu.addSeparator();
		fileMenu.add(getActionMap().get(Command.SaveReport));
		fileMenu.addSeparator();
		fileMenu.add(getActionMap().get(Command.Quit));
		
		simulatorMenu = new JMenu("Simulator");
		menuBar.add(simulatorMenu);
		simulatorMenu.add(getActionMap().get(Command.Run));
		simulatorMenu.add(getActionMap().get(Command.Reset));
		simulatorMenu.add(getActionMap().get(Command.Output));
		
		reportsMenu = new JMenu("Reports");
		menuBar.add(reportsMenu);
		reportsMenu.add(getActionMap().get(Command.GenReport));
		reportsMenu.add(getActionMap().get(Command.ClearReport));
		
		return menuBar;
	}

	public void createJToolBar(Controller contr) {
		
		JToolBar toolBar = new JToolBar();
		
		toolBar.add(getActionMap().get(Command.Load));
		toolBar.add(getActionMap().get(Command.Save));
		toolBar.add(getActionMap().get(Command.CheckIn));
		toolBar.add(getActionMap().get(Command.Run));
		toolBar.add(getActionMap().get(Command.Reset));
		toolBar.add(new JLabel(" Steps: "));   
		stepsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		stepsSpinner.addChangeListener(new ChangeListener() {
    		public void stateChanged(ChangeEvent e) {
    	    	contr.setTime((int)stepsSpinner.getValue());
			}
		});
		toolBar.add(stepsSpinner);
		toolBar.add(new JLabel(" Time: "));
		timeViewer = new JTextField("0", 5);  
		toolBar.add(timeViewer);
		toolBar.addSeparator(); 
		toolBar.add(getActionMap().get(Command.GenReport));
		toolBar.add(getActionMap().get(Command.ClearReport));
		toolBar.add(getActionMap().get(Command.SaveReport));
		toolBar.addSeparator(); 
		toolBar.add(getActionMap().get(Command.Quit));
	}
	
}
