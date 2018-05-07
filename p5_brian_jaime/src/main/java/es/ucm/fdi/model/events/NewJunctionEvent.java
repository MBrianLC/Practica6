package es.ucm.fdi.model.events;

import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewJunctionEvent se encarga de crear un cruce
 * @author Jaime Fernández y Brian Leiva
*/

public class NewJunctionEvent extends Event{
	protected String id;
	private String type;
	
	/** 
	 * Constructor de la clase NewJunctionEvent
	 * @param time Entero que representa el momento en el que ocurrirá el evento.
	 * @param id String con el identificador del cruce
	*/
	
	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
		type = "New Junction " + id;
	}
	
	/** 
	 * Método que devuelve un String con el tipo de evento (Cola de eventos)
	 * @return El tipo de evento 
	*/	
	
	public String getType() {
		return type;
	}
	
	/** 
	 * Método que ejecuta el evento de creación de un nuevo cruce
	 * @param map El mapa de carreteras e intersecciones.
	 * @throws SimulatorException 
	*/
	
	public void execute(RoadMap map) throws SimulatorException {
		try {
			map.addJunction(new Junction(id));
		} catch(IllegalArgumentException e) {
			throw new SimulatorException("Junction " + id + ": id already exists");
		}
	}

}
