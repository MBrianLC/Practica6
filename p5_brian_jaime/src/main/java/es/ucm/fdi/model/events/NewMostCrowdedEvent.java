package es.ucm.fdi.model.events;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.ucm.fdi.model.simobjects.advanced.MostCrowded;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewMostCrowdedEvent se encarga de crear un cruce congestionado
 * @author Jaime Fernández y Brian Leiva
*/

public class NewMostCrowdedEvent extends NewJunctionEvent{	
	
	private String type;
	
	private static final Logger logger = Logger.getLogger(NewMostCrowdedEvent.class.getName());

	/** 
	 * Constructor de la clase NewMostCrowdedEvent
	 * @param time : Entero que representa el momento en el que ocurrirá el evento.
	 * @param id : String con el identificador del camino
	*/

	public NewMostCrowdedEvent(int time, String id) {
		super(time, id);
		type = "New MostCrowded " + id;
	}
	
	/** 
	 * Método que devuelve un String con el tipo de evento (Cola de eventos)
	 * @return El tipo de evento 
	*/	
	
	public String getType() {
		return type;
	}
	
	/** 
	 * Método que ejecuta el evento de creación de un nuevo cruce congestionado
	 * @param map : El mapa de carreteras e intersecciones.
	 * @throws SimulatorException 
	*/

	public void execute(RoadMap map) throws SimulatorException {
		try {
			map.addJunction(new MostCrowded(id));
		} catch(IllegalArgumentException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
			throw new SimulatorException("MostCrowded " + id + ": id already exists");
		}
	}

}
