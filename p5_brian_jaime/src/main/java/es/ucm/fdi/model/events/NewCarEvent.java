package es.ucm.fdi.model.events;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.model.simobjects.advanced.Car;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Vehicle;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewCarEvent se encarga de crear un coche
 * @author Jaime Fernández y Brian Leiva
*/

public class NewCarEvent extends NewVehicleEvent{
	
	private int resistance, maxFaultDuration;
	private double faultProbability;
	private long seed;
	private String type;
	
	/** 
	 * Constructor de la clase NewCarEvent
	 * @param time Entero que representa el momento en el que ocurrirá el evento.
	 * @param id String con el identificador del vehiculo
	 * @param max Velocidad maxima del coche
	 * @param cruces Array de strings con el itinerario de cruces
	 * @param resistance Entero con la resistencia del coche
	 * @param faultProbability Entero con la probabilidad de avería del coche
	 * @param maxFaultDuration Entero con la duración máxima de la avería
	 * @param seed Semilla
	*/

	public NewCarEvent(int time, String id, int max, String[] cruces, int resistance, double faultProbability, int maxFaultDuration, long seed) {
		super(time, id, max, cruces);
		this.resistance = resistance;
		this.faultProbability = faultProbability;
		this.maxFaultDuration = maxFaultDuration;
		this.seed = seed;
		type = "New Car " + id;
	}
	
	/** 
	 * Método que devuelve un String con el tipo de evento (Cola de eventos)
	 * @return El tipo de evento 
	*/	
	
	public String getType() {
		return type;
	}
	
	/** 
	 * Método que ejecuta el evento de creación de un nuevo coche
	 * @param map El mapa de carreteras e intersecciones.
	 * @throws SimulatorException 
	*/
	
	public void execute(RoadMap map) throws SimulatorException {
		
		List<Junction> itinerario = new ArrayList<>();
		try {
			for (String n : cruces) {
				itinerario.add(map.getJunction(n));
			}
			Vehicle v = new Car(id, max, itinerario, resistance, faultProbability, maxFaultDuration, seed);
			v.moverASiguienteCarretera(itinerario.get(0).road(v));
			map.addVehicle(v);
		} catch(NullPointerException e) {
			throw new SimulatorException("Car " + id + ": invalid itinerary");
		} catch(IllegalArgumentException e) {
			throw new SimulatorException("Car " + id + ": id already exists");
		}
	}

}
