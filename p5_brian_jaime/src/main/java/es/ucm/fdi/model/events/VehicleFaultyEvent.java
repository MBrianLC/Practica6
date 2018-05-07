package es.ucm.fdi.model.events;

import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase VehicleFaultyEvent lleva a cabo la avería de los vehículos
 * @author Jaime Fernández y Brian Leiva
*/

public class VehicleFaultyEvent extends Event{
	private int duration;
	private String[] vehicles;
	private String type;
	
	/** 
	 * Constructor de la clase VehicleFaultyEvent
	 * @param time Entero que representa el momento en el que ocurrirá el evento.
	 * @param vehicles Array de strings con los vehículos que han de ser averiados
	 * @param duration Entero con la duración de la avería de los vehículos a averiar.
	*/

	public VehicleFaultyEvent(int t, String[] vehicles, int duration) {
		super(t);
		this.duration = duration;
		this.vehicles = vehicles;
		type = "Break Vehicles [";
		for (int i = 0; i < vehicles.length; ++i) {
			type += vehicles[i] + ",";
		}
		type = type.substring(0, type.length() - 1);
		type += "]";
	}
	
	/** 
	 * Método que devuelve un String con el tipo de evento (Cola de eventos)
	 * @return El tipo de evento 
	*/	
	
	public String getType() {
		return type;
	}
	
	/** 
	 * Método que ejecuta el evento de avería de vehículos.
	 * @param map El mapa de carreteras e intersecciones.
	 * @throws SimulatorException 
	*/

	public void execute(RoadMap map) throws SimulatorException {
		try {
			for (String v : vehicles) {
				map.getVehicle(v).setTiempoAveria(duration);
			}
		} catch(NullPointerException e) {
			throw new SimulatorException("VehicleFaulty " + ": invalid vehicles");
		}
	}
	
}
