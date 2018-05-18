package es.ucm.fdi.model.events;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Vehicle;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewVehicleEvent se encarga de crear un vehículo
 * @author Jaime Fernández y Brian Leiva
*/

public class NewVehicleEvent extends Event{
	protected String id;
	protected String[] cruces;
	protected int max;
	private String type;
	
	private static final Logger logger = Logger.getLogger(NewVehicleEvent.class.getName());
	
	/** 
	 * Constructor de la clase NewVehicleEvent
	 * @param time Entero que representa el momento en el que ocurrirá el evento.
	 * @param id String con el identificador del cruce
	 * @param max Entero con la velocidad máxima del vehículo
	 * @param cruces Array de strings con el itinerario de cruces del vehículo
	*/
	
	public NewVehicleEvent(int time, String id, int max, String[] cruces) {
		super(time);
		this.id = id;
		this.max = max;
		this.cruces = cruces;
		type = "New Vehicle " + id;
	}
	
	/** 
	 * Método que devuelve un String con el tipo de evento (Cola de eventos)
	 * @return El tipo de evento 
	*/	
	
	public String getType() {
		return type;
	}	
	/** 
	 * Método que ejecuta el evento de creación de un nuevo vehículo.
	 * @param map El mapa de carreteras e intersecciones.
	 * @throws SimulatorException 
	*/
	
	public void execute(RoadMap map) throws SimulatorException {
		
		List<Junction> itinerario = new ArrayList<>();
		try {
			for (String n : cruces) {
				itinerario.add(map.getJunction(n));
			}
			Vehicle v = new Vehicle(id, max, itinerario);
			v.moverASiguienteCarretera(itinerario.get(0).road(v));
			map.addVehicle(v);
		} catch(NullPointerException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			throw new SimulatorException("Vehicle " + id + ": invalid itinerary");
		} catch(IllegalArgumentException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			throw new SimulatorException("Vehicle " + id + ": id already exists");
		}
	}
}
