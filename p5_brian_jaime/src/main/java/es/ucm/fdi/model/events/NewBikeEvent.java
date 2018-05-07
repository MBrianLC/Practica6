package es.ucm.fdi.model.events;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.model.simobjects.advanced.Bike;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Vehicle;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewBikeEvent se encarga de crear una bicicleta
 * @author Jaime Fernández y Brian Leiva
*/

public class NewBikeEvent extends NewVehicleEvent{
	
	String type;
	
	/** 
	 * Constructor de la clase NewBikeEvent
	 * @param time Entero que representa el momento en el que ocurrirá el evento.
	 * @param id String con el identificador del vehiculo
	 * @param max Velocidad maxima de la bicicleta
	 * @param cruces Array de strings con el itinerario de cruces
	*/
	public NewBikeEvent(int time, String id, int max, String[] cruces) {
		super(time, id, max, cruces);
		type = "New Bike " + id;
	}
	
	/** 
	 * Método que devuelve un String con el tipo de evento (Cola de eventos)
	 * @return El tipo de evento 
	*/
	
	public String getType() {
		return type;
	}
	
	/** 
	 * Método que ejecuta el evento de creación de una nueva Bicicleta
	 * @param map El mapa de carreteras e intersecciones.
	 * @throws SimulatorException 
	*/
	public void execute(RoadMap map) throws SimulatorException {
		
		List<Junction> itinerario = new ArrayList<>();
		try {
			for (String n : cruces) {
				itinerario.add(map.getJunction(n));
			}
			Vehicle v = new Bike(id, max, itinerario);
			v.moverASiguienteCarretera(itinerario.get(0).road(v));
			map.addVehicle(v);
		} catch(NullPointerException e) {
			throw new SimulatorException("Bike " + id + ": invalid itinerary");
		} catch(IllegalArgumentException e) {
			throw new SimulatorException("Bike " + id + ": id already exists");
		}
		
	}

}

