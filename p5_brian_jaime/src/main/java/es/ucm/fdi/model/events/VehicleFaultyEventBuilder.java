package es.ucm.fdi.model.events;

import java.util.Arrays;

import es.ucm.fdi.ini.IniSection;

/** 
 * La clase VehicleFaultyEventBuilder se encarga de crear el VehicleFaultyEvent
 * @author Jaime Fernández y Brian Leiva
*/

public class VehicleFaultyEventBuilder implements EventBuilder {
	
	/** 
	 * Método para reconocer si la sección representa un evento vehicle faulty y crear el evento
	 * @param sec La sección del evento
	 * @return El evento creado
	*/
	public Event parse(IniSection sec) {
		if ( ! sec.getTag().equals("make_vehicle_faulty")) {
			return null;
		}
		String[] parF = {"time", "vehicles", "duration"};
		if (!sec.getKeys().containsAll(Arrays.asList(parF))) {
			throw new IllegalArgumentException("Invalid make_vehicle_faulty section.");
		}
		return new VehicleFaultyEvent(parseInt(sec, "time"), parseIdList(sec, "vehicles"),
									  parseInt(sec, "duration"));
	}
	
	/** 
	 * Método para comprobar si la id representa un Vehicle.
	 * @param id : El identificador del objeto
	 * @return Booleano que indica si id es válida.
	*/
	public boolean isValidId(String id){
		return id.charAt(0) == 'v';
	}
	
	/** 
	 * Método que devuelve el tipo de evento que construye la clase.
	 * @return Tipo de evento
	*/
	public String type(){
		return "make_vehicle_faulty";
	}

}
