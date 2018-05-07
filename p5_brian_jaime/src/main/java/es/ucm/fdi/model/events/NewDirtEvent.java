package es.ucm.fdi.model.events;

import es.ucm.fdi.model.simobjects.advanced.Dirt;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewDirtEvent se encarga de crear un camino
 * @author Jaime Fernández y Brian Leiva
*/

public class NewDirtEvent extends NewRoadEvent{
	
	String type;
	
	/** 
	 * Constructor de la clase NewDirtEvent
	 * @param time Entero que representa el momento en el que ocurrirá el evento.
	 * @param id String con el identificador del camino
	 * @param sr String con el identificador del cruce inicial
	 * @param dest String con el identificador del cruce final
	 * @param max Entero con la velocidad máxima permitida en el camino
	 * @param length Entero con la longitud del camino
	*/

	public NewDirtEvent(int time, String id, String sr, String dest, int max, int length) {
		super(time, id, sr, dest, max, length);
		type = "New Dirt " + id;
	}
	
	/** 
	 * Método que devuelve un String con el tipo de evento (Cola de eventos)
	 * @return El tipo de evento 
	*/	
	
	public String getType() {
		return type;
	}
	
	/** 
	 * Método que ejecuta el evento de creación de un nuevo camino
	 * @param map El mapa de carreteras e intersecciones.
	 * @throws SimulatorException 
	*/

	public void execute(RoadMap map) throws SimulatorException {
		try {
			Junction ini = map.getJunction(sr);
			Junction fin = map.getJunction(dest);
			Road r = new Dirt(id, length, max, ini, fin);
			ini.addSale(r);
			fin.addEntra(r);
			map.addRoad(r);
		} catch(NullPointerException e) {
			throw new SimulatorException("Dirt " + id + ": invalid source/destiny junctions");
		} catch(IllegalArgumentException e) {
			throw new SimulatorException("Dirt " + id + ": id already exists");
		}
	}

}
