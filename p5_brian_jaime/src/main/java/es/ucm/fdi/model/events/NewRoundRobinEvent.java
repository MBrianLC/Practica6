package es.ucm.fdi.model.events;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.ucm.fdi.model.simobjects.advanced.RoundRobin;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewRoundRobinEvent se encarga de crear un cruce circular
 * @author Jaime Fernández y Brian Leiva
*/

public class NewRoundRobinEvent extends NewJunctionEvent{
	private int maxTimeSlice, minTimeSlice;
	private String type;
	
	private static final Logger logger = Logger.getLogger(NewRoundRobinEvent.class.getName());
	
	/** 
	 * Constructor de la clase NewRoundRobinEvent
	 * @param time Entero que representa el momento en el que ocurrirá el evento.
	 * @param id String con el identificador del camino
	 * @param max Máximo valor del intervalo de tiempo
	 * @param min Mínimo valor del intervalo de tiempo
	*/

	public NewRoundRobinEvent(int time, String id, int maxTimeSlice, int minTimeSlice) {
		super(time, id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
		type = "New RoundRobin " + id;
	}
	
	/** 
	 * Método que devuelve un String con el tipo de evento (Cola de eventos)
	 * @return El tipo de evento 
	*/	
	
	public String getType() {
		return type;
	}
	
	/** 
	 * Método que ejecuta el evento de creación de un nuevo cruce circular
	 * @param map El mapa de carreteras e intersecciones.
	 * @throws SimulatorException 
	*/

	public void execute(RoadMap map) throws SimulatorException {
		try{
			map.addJunction(new RoundRobin(id, maxTimeSlice, minTimeSlice));
		} catch(IllegalArgumentException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			throw new SimulatorException("RoundRobin " + id + ": id already exists");
		}
	}

}
