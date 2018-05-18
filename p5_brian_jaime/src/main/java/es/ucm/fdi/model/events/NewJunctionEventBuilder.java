package es.ucm.fdi.model.events;

import java.util.Arrays;

import es.ucm.fdi.ini.IniSection;

/** 
 * La clase NewJunctionEventBuilder se encarga de crear el NewJunctionEvent
 * @author Jaime Fernández y Brian Leiva
*/

public class NewJunctionEventBuilder implements EventBuilder {
	
	/** 
	 * Método para reconocer si la sección representa un evento new junction y crear el evento
	 * @param sec : La sección del evento
	 * @return El evento creado.
	*/
	public Event parse(IniSection sec) {
		if (!sec.getTag().equals("new_junction")) {
			return null;
		}
		String[] parJ = {"time", "id"};
		if (!sec.getKeys().containsAll(Arrays.asList(parJ))) {
			throw new IllegalArgumentException("Invalid new_junction section.");
		}
		if (!sec.getKeys().contains("type")) {
			return new NewJunctionEvent(parseInt(sec, "time"), sec.getValue("id"));
		} else if (sec.getValue("type").equals("mc")) {
			return new NewMostCrowdedEvent(parseInt(sec, "time"), sec.getValue("id"));
		}
		String[] parRR = {"max_time_slice", "min_time_slice"};
		if (!sec.getKeys().containsAll(Arrays.asList(parRR))) {
			throw new IllegalArgumentException("Invalid new_junction section.");
		}
		return new NewRoundRobinEvent(parseInt(sec, "time"), sec.getValue("id"), 
									  parseInt(sec, "max_time_slice"), 
									  parseInt(sec, "min_time_slice"));
	}
	
	/** 
	 * Método para comprobar si la id representa un Junction.
	 * @param id : El identificador del objeto
	 * @return Booleano que indica si id es válida.
	*/	
	public boolean isValidId(String id){
		return id.charAt(0) == 'j';
	}
	
	/** 
	 * Método que devuelve el tipo de evento que construye la clase.
	 * @return String con el nombre del evento.
	*/
	public String type(){
		return "new_junction";
	}
}
