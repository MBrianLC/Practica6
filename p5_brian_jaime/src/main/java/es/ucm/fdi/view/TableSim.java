package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.simobjects.Describable;
import es.ucm.fdi.model.simulator.Listener;
import es.ucm.fdi.model.simulator.RoadMap;
import es.ucm.fdi.model.simulator.TrafficSimulator.UpdateEvent;

/** 
 * La clase TableSim representa las tablas de la interfaz.
 * @author Jaime Fernández y Brian Leiva
*/

public class TableSim implements Listener{
	
	private String[] fieldNamesQ = {"#", "Time", "Type"};
	private String[] fieldNamesV = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	private String[] fieldNamesR = {"ID", "Source", "Target", "Length", "Max Speed", "Vehicles"};
	private String[] fieldNamesJ = {"ID", "Green", "Red"};

	private ListOfMapsTableModel eTableMaps;
	private ListOfMapsTableModel vTableMaps;
	private ListOfMapsTableModel rTableMaps;
	private ListOfMapsTableModel jTableMaps;
	
	private JPanel eventsPanel;
	private JPanel vehiclesPanel;
	private JPanel roadsPanel;
	private JPanel junctionsPanel;
	
	/** 
	 * Constructor de TableSim.
	 * @param map: Mapa de carreteras
	 * @param events: Lista de eventos
	*/
	
	public TableSim(RoadMap map, List<EventIndex> events) {
		eTableMaps = new ListOfMapsTableModel(new ArrayList<Describable>(events), fieldNamesQ); 
		vTableMaps = new ListOfMapsTableModel(new ArrayList<Describable>(map.getVehicles()), fieldNamesV); 
		rTableMaps = new ListOfMapsTableModel(new ArrayList<Describable>(map.getRoads()), fieldNamesR); 
		jTableMaps = new ListOfMapsTableModel(new ArrayList<Describable>(map.getJunctions()), fieldNamesJ); 
		eventsPanel = addTable(eTableMaps, "Events Queue"); // cola de eventos
		vehiclesPanel = addTable(vTableMaps, "Vehicles"); // tabla de vehiculos
		roadsPanel = addTable(rTableMaps, "Roads"); // tabla de carreteras
		junctionsPanel = addTable(jTableMaps, "Junctions"); // tabla de cruces
	}
	
	/** 
	 * Método get para eventsPanel.
	 * @return Panel con la cola de eventos
	*/
	
	public JPanel getEventPanel() {
		return eventsPanel;
	}
	
	/** 
	 * Método get para vehiclesPanel.
	 * @return Panel con la tabla de vehículos
	*/
	
	public JPanel getVehiclesPanel() {
		return vehiclesPanel;
	}
	
	/** 
	 * Método get para roadsPanel.
	 * @return Panel con la tala de carreteras
	*/
	
	public JPanel getRoadsPanel() {
		return roadsPanel;
	}
	
	/** 
	 * Método get para junctionsPanel.
	 * @return Panel con la tabla de cruces
	*/
	
	public JPanel getJunctionsPanel() {
		return junctionsPanel;
	}
	
	/** 
	 * La clase ListOfMapsTableModel se usa para crear las tablas.
	*/
	
	@SuppressWarnings("serial")
	private class ListOfMapsTableModel extends AbstractTableModel{

		private List<Describable> elements;
		private String[] fieldNames;
		
		public ListOfMapsTableModel(List<Describable> objectList, String[] names) {
			elements = objectList;
			fieldNames = names;
		}
		
		@Override // fieldNames es un String[] con nombrs de col.
		public String getColumnName(int columnIndex) {
			return fieldNames[columnIndex];
		}
		@Override // elements contiene la lista de elementos
		public int getRowCount() {
			return elements.size();
		}
		@Override
		public int getColumnCount() {
			return fieldNames.length;
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return elements.get(rowIndex).describe().get(fieldNames[columnIndex]);
		}
		
		public void update(List<Describable> objectList) {
			elements = objectList;
		}
	}
	
	/** 
	 * Añade una tabla (cola de eventos, tabla de vehículos, de carreteras o de cruces).
	*/
	
	private JPanel addTable(ListOfMapsTableModel tableMaps, String title) {
		JPanel panel = new JPanel(new BorderLayout());
		JTable table = new JTable(tableMaps); 
		JScrollPane sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(500, 500));
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.add(sp);
		return panel;
	}

	/** 
	 * Crea una lista de EventIndex a partir de una lista de eventos.
	 * @param eventos: Lista de eventos
	 * @return La lista de eventos con índice
	*/
	
	private List<EventIndex> getEvents(List<Event> eventos){
		List<EventIndex> events = new ArrayList<>();
		for (int i = 0; i < eventos.size(); ++i) {
			events.add(new EventIndex(i, eventos.get(i)));
		}
		return events;
	}
	
	/** 
	 * Actualiza una tabla.
	 * @param tableMaps: Tabla que se quiere actualizar
	 * @param elements: Elementos de la nueva tabla
	*/
	
	private void updateTable(ListOfMapsTableModel tableMaps, List<Describable> elements) {
		tableMaps.update(elements);
		tableMaps.fireTableDataChanged();
	}
	
	/** 
	 * Actualiza las tablas si se produce un evento.
	 * @param ue: Nuevo evento
	 * @param error: String con el tipo de error (si ue es de tipo ERROR)
	*/
	
	public void update(UpdateEvent ue, String error) {
		switch (ue.getEvent()) {
			case ADVANCED:{
				updateTable(eTableMaps, new ArrayList<Describable>(getEvents(ue.getEventQueue())));
				updateTable(vTableMaps, new ArrayList<Describable>(ue.getRoadMap().getVehicles()));
				updateTable(rTableMaps, new ArrayList<Describable>(ue.getRoadMap().getRoads()));
				updateTable(jTableMaps, new ArrayList<Describable>(ue.getRoadMap().getJunctions()));
				break;
			}	
			case NEWEVENT:{
				updateTable(eTableMaps, new ArrayList<Describable>(getEvents(ue.getEventQueue())));
				break;
			}
			case RESET:{
				updateTable(eTableMaps, new ArrayList<Describable>());
				updateTable(vTableMaps, new ArrayList<Describable>());
				updateTable(rTableMaps, new ArrayList<Describable>());
				updateTable(jTableMaps, new ArrayList<Describable>());
				break;
			}
		default:
			break;
		}
	}
}
