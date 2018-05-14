package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.control.SimulatorAction;
import es.ucm.fdi.extra.dialog.ReportWindow;
import es.ucm.fdi.extra.graphlayout.RoadMapGraph;
import es.ucm.fdi.extra.popupmenu.PopUpMenu;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simulator.Listener;
import es.ucm.fdi.model.simulator.RoadMap;
import es.ucm.fdi.model.simulator.TrafficSimulator;
import es.ucm.fdi.model.simulator.TrafficSimulator.UpdateEvent;

/** 
 * La clase MainWindowSim representa la interfaz del simulador.
 * @author Jaime Fernández y Brian Leiva
*/

@SuppressWarnings("serial")
public class MainWindowSim extends JFrame implements Listener {
	private Controller contr;
	private TrafficSimulator tsim;
	private RoadMap map;
	private List<EventIndex> events;
	private int time;
	private OutputStream reportsOutputStream;
	
	private TableSim tableSim;
	private RoadMapGraph rmGraph;
	private ReportWindow reports;
	private PopUpMenu popUpMenu;
	private JPanel mainPanel;
	private JPanel stateBar;
	private JPanel editorPanel;
	private JPanel reportsPanel;
	private JPanel mapPanel;
	private JMenu fileMenu;
	private JMenu simulatorMenu;
	private JMenu reportsMenu;
	private JLabel statusBarText;
	private JToolBar toolBar;
	private JFileChooser fc;
	private File currentFile;
	private JSpinner stepsSpinner;
	private JSpinner delaySpinner;
	private JTextField timeViewer;
	private JTextArea eventsEditor; // editor de eventos
	private JTextArea reportsArea; // zona de informes
	
	private Action exit = new SimulatorAction(Command.Quit, "exit.png", "Exit",
			KeyEvent.VK_E, "control shift E", () -> System.exit(0));
	private Action load = new SimulatorAction(Command.Load, "open.png", "Load a file",
			KeyEvent.VK_L, "control shift L", () -> loadFile());
	private Action save = new SimulatorAction(Command.Save, "save.png", "Save a file",
			KeyEvent.VK_S, "control shift S", () -> saveFile());
	private Action clear = new SimulatorAction(Command.Clear, "clear.png", "Clear events",
			KeyEvent.VK_C, "control shift C", () -> eventsEditor.setText(""));
	private Action checkIn = new SimulatorAction(Command.CheckIn, "events.png", "Insert an event",
			KeyEvent.VK_C, "control shift I", () -> checkInEvent());
	private Action run = new SimulatorAction(Command.Run, "play.png", "Run simulation",
			KeyEvent.VK_R, "control shift P", () -> runSim());	
	private Action saveReport = new SimulatorAction(Command.SaveReport, "save_report.png", "Save reports",
			KeyEvent.VK_S, "control shift R", () -> saveReport());
	private Action genReport = new SimulatorAction(Command.GenReport, "report.png", "Generate reports",
			KeyEvent.VK_R, "control shift F", () -> genReport());
	private Action clearReport = new SimulatorAction(Command.ClearReport, "delete_report.png", "Clear reports",
			KeyEvent.VK_C, "control shift M", () -> reportsArea.setText(""));
	private Action reset = new SimulatorAction(Command.Reset, "reset.png", "Reset simulation",
			KeyEvent.VK_R, "control shift N", () -> resetSim());
	private Action output = new SimulatorAction(Command.Output, null, "Redirect simulation's output to text area",
			KeyEvent.VK_R, "control shift O", () -> redirectOutput());
	private Action stop = new SimulatorAction(Command.Output, "stop.png", "Stops simulation",
			KeyEvent.VK_R, "alt shift S", () -> stopSim());	
	private Action[] actions = {exit, load, save, clear, checkIn, run, saveReport, genReport,
								clearReport, reset, output, stop};
	
	private static final Logger logger = Logger.getLogger(MainWindowSim.class.getName());
	
	/** 
	 * Constructor de la clase MainWindowSim.
	 * @param tsim: Simulador
	 * @param inFileName: Nombre del archivo de entrada inicial
	 * @param contr: Controlador
	*/
	
	public MainWindowSim(TrafficSimulator tsim, String inFileName, Controller contr) {
		super("Traffic Simulator");
		this.contr = contr;
		this.tsim = tsim;
		map = tsim.getMap();
		events = new ArrayList<>();
		for (int i = 0; i < tsim.getEventQueue().size(); ++i) {
			events.add(new EventIndex(i, tsim.getEventQueue().get(i)));
		}
		currentFile = inFileName != null ? new File(inFileName) : null;
		logger.info("Iniciando interfaz");
		initGUI();
		reportsOutputStream = new JTextAreaOutputStream(reportsArea);
		contr.setOutputStream(reportsOutputStream);
		this.tsim.addSimulatorListener(this);
		this.tsim.addSimulatorListener(tableSim);
		this.tsim.addSimulatorListener(rmGraph);
	}

	/** 
	 * Inicia la interfaz.
	*/
	
	public void initGUI(){
		fc = new JFileChooser();
		mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		addMenuBar(); // barra de menus
		addToolBar(); // barra de herramientas
		addEventsEditor();
		
		tableSim = new TableSim(map, events);
		
		addReportsArea(); // zona de informes
		addMap(); // mapa de carreteras
		addStatusBar(); // barra de estado
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2,BoxLayout.X_AXIS));
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3,BoxLayout.X_AXIS));
		JPanel panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4,BoxLayout.Y_AXIS));
		
		panel1.add(panel2);
		panel1.add(panel3);
		panel1.add(stateBar);
		panel2.add(editorPanel);
		panel2.add(tableSim.getEventPanel());
		panel2.add(reportsPanel);
		panel3.add(panel4);
		panel3.add(mapPanel);
		panel4.add(tableSim.getVehiclesPanel());
		panel4.add(tableSim.getRoadsPanel());
		panel4.add(tableSim.getJunctionsPanel());
		
		mainPanel.add(panel1);
		logger.info("Verificando si hay archivo inicial");
		if (currentFile != null) {
			try {
				String s = readFile(currentFile);
				eventsEditor.setText(s);
				editorPanel.setBorder(BorderFactory.createTitledBorder("Events: " + currentFile.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 1000);
		this.setVisible(true);
	}
	
	/** 
	 * La clase JTextAreaOutputStream controla la salida de datos.
	*/
	
	private class JTextAreaOutputStream extends OutputStream{
		
		private JTextArea textArea;

		public JTextAreaOutputStream(JTextArea textArea) {
			this.textArea = textArea;
		}

		public void write(int b) throws IOException {
			textArea.append(String.valueOf((char)b));
	        textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		
	}
	
	private void addMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.add(load);
		fileMenu.add(save);
		fileMenu.addSeparator();
		fileMenu.add(saveReport);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		
		simulatorMenu = new JMenu("Simulator");
		menuBar.add(simulatorMenu);
		simulatorMenu.add(run);
		simulatorMenu.add(stop);
		simulatorMenu.add(reset);
		simulatorMenu.add(output);
		
		reportsMenu = new JMenu("Reports");
		menuBar.add(reportsMenu);
		reportsMenu.add(genReport);
		reportsMenu.add(clearReport);
		
		this.setJMenuBar(menuBar);
		logger.fine("Añadida barra de menú");
	}
	
	private void addToolBar() {   
		toolBar = new JToolBar();    
		mainPanel.add(toolBar, BorderLayout.PAGE_START);  
		
		toolBar.add(load);
		toolBar.add(save);
		toolBar.add(checkIn);
		toolBar.add(run);
		toolBar.add(stop);
		toolBar.add(reset);
		
		toolBar.add(new JLabel(" Delay: "));   
		delaySpinner = new JSpinner(new SpinnerNumberModel(300, 1, 1000, 1));
		delaySpinner.addChangeListener(new ChangeListener() {
    		public void stateChanged(ChangeEvent e) {
    	    	//contr.setDelay((int)delaySpinner.getValue());
			}
		});
		toolBar.add(delaySpinner);
		
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
		toolBar.add(genReport);
		toolBar.add(clearReport);
		toolBar.add(saveReport);
		toolBar.addSeparator(); 
		toolBar.add(exit);
		
		logger.fine("Añadida barra de herramientas");
	}
	
	private void addEventsEditor(){
		editorPanel = new JPanel(new BorderLayout());
		editorPanel.setBorder(BorderFactory.createTitledBorder("Events: " + currentFile.getName()));
		eventsEditor = new JTextArea("");
		eventsEditor.setEditable(true);
		eventsEditor.setLineWrap(true);
		eventsEditor.setWrapStyleWord(true);
		JScrollPane area = new JScrollPane(eventsEditor);
		area.setPreferredSize(new Dimension(500, 500));
		editorPanel.add(area);
		
		eventsEditor.getActionMap().put(Command.Load, load);
		eventsEditor.getActionMap().put(Command.Save, save);
		eventsEditor.getActionMap().put(Command.Clear, clear);		
		popUpMenu = new PopUpMenu();
		popUpMenu.addEditor(eventsEditor);
		
		logger.fine("Añadido editor de eventos");

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
		logger.fine("Añadida zona de informes");
	}
	
	private void addStatusBar() {  
		stateBar = new JPanel(new BorderLayout());
		statusBarText = new JLabel("Welcome to the simulator!");    
		stateBar.add(statusBarText);
		mainPanel.add(stateBar);
		logger.fine("Añadida barra de estado");
	}
	
	private void addMap() {  
		mapPanel = new JPanel(new BorderLayout());
		rmGraph = new RoadMapGraph(new RoadMap());
		JScrollPane sp = new JScrollPane(rmGraph._graphComp);
		sp.setPreferredSize(new Dimension(500, 500));
		mapPanel.add(sp);
		logger.fine("Añadido mapa");
	}
	
	private void saveFile() {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				writeFile(file, eventsEditor.getText());
			} catch (IOException e) {
				statusBarText.setText("ERROR: The file has not been saved");
				logger.severe("No se ha podido guardar el archivo");
			}
		}
		statusBarText.setText("The file have been saved!"); 
		logger.fine("Archivo guardado");
	}
	
	private void loadFile() {
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String s;
			try {
				s = readFile(file);
				eventsEditor.setText(s);
				statusBarText.setText("Events have been loaded to the simulator!"); 
				currentFile = file;
				editorPanel.setBorder(BorderFactory.createTitledBorder("Events: " + currentFile.getName()));
				
			} catch (IOException e) {
				e.printStackTrace();
				statusBarText.setText("ERROR: File not found");
				logger.severe("No se ha encontrado el archivo");
			}
		}
		logger.fine("Archivo cargado");
	}
	
	private void saveReport() {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				writeFile(file, reportsArea.getText());
			} catch (IOException e) {
				statusBarText.setText("ERROR: The reports have not been saved"); 
				logger.severe("No se han podido guardar los informes");
			}
		}
		statusBarText.setText("All reports have been saved!");
		logger.fine("Informes guardados");
	}
	
	private void runSim() {
		try {
			tsim.resetEvents();
			contr.setTime((int)stepsSpinner.getValue());
			for (Action a: actions) {
				if (a != stop) a.setEnabled(false);
			}
			contr.execute(tsim);
			for (Action a: actions) {
				a.setEnabled(true);
			}
			tableSim = new TableSim(map, events);
			statusBarText.setText("Simulator has run succesfully!");
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.severe("Fallo al leer los eventos");
		} catch (SimulatorException ex) {
			ex.printStackTrace();
			logger.severe("Fallo de simulación");
		}
	}
	
	private void checkInEvent() {
		try {
			contr.setIni(new Ini(new ByteArrayInputStream(eventsEditor.getText().getBytes())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		statusBarText.setText("Events have been inserted into the simulator!");
		logger.fine("Eventos insertados en el simulador");
	}
	
	private void resetSim(){
		time = 0;
		reportsArea.setText("");
		tsim.resetSim();
		statusBarText.setText("Simulation reseted!");
		logger.fine("Simulación reseteada");
	}
	
	private void redirectOutput() {
		if (reportsOutputStream != null) {
			reportsOutputStream = null;
			statusBarText.setText("Simulation's output not redirected to text area");
		}
		else {
			reportsOutputStream = new JTextAreaOutputStream(reportsArea);
			statusBarText.setText("Simulation's output is now redirected to text area!");
		}
		contr.setOutputStream(reportsOutputStream);
		logger.fine("Salida del simulador redirigida");
	}
	
	private void genReport(){
		reports = new ReportWindow(map, time);
		if (reports.getReport() != null) {
			reportsArea.setText(reports.getReport());
			statusBarText.setText("Your reports have been generated!");
			logger.fine("Informes generados");
		}
		else statusBarText.setText("Operation cancelled");
	}
	
	private void stopSim() {

	}
	
	/** 
	 * Lee de un archivo.
	 * @param file : Archivo
	*/
	
	public static String readFile(File file) throws IOException {
		String s = "";
		try {
			Scanner sc = new Scanner(file);
			s = sc.useDelimiter("\\A").next();
			sc.close();
		} catch (FileNotFoundException e) {
			throw new IOException();
		}
		logger.fine("Archivo leído");
		return s;
	}
	
	/** 
	 * Escribe en un archivo.
	 * @param file: Archivo
	 * @param contet: String con el texto que se quiere escribir
	*/

	public static void writeFile(File file, String content) throws IOException {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			throw new IOException();
		}
		logger.fine("Se ha escrito en el archivo");
	}
	
	/** 
	 * Actualiza la interfaz si se produce un evento.
	 * @param ue: Nuevo evento
	 * @param error: String con el tipo de error (si ue es de tipo ERROR)
	*/
	
	public void update(UpdateEvent ue, String error) {
		switch (ue.getEvent()){
		case ADVANCED:{
			time = ue.getCurrentTime();
			timeViewer.setText(String.valueOf(time));
			map = ue.getRoadMap();
			break;
		}
		case RESET:{
			timeViewer.setText("0");
			map = ue.getRoadMap();
			events = new ArrayList<EventIndex>();
			break;
		}
		case NEWEVENT:{
			statusBarText.setText("New event inserted");
			break;
		}
		case ERROR:{
			statusBarText.setText(error);
			break;
		}
		default:
			break;
	}
	}
	
}
