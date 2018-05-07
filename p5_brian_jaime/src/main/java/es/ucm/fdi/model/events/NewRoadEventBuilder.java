package es.ucm.fdi.model.events;

import java.util.Arrays;

import es.ucm.fdi.ini.IniSection;

/** 
 * La clase NewRoadEventBuilder se encarga de crear el NewRoadEvent
 * @author Jaime Fernández y Brian Leiva
*/

public class NewRoadEventBuilder implements EventBuilder {
	
	/** 
	 * Método para reconocer si la sección representa un evento new road y crear el evento
	 * @param sec : La sección del evento
	 * @return El evento creado.
	*/
	public Event parse(IniSection sec) {
		if (!sec.getTag().equals("new_road")) {
			return null;
		}
		String[] parR = {"time", "id", "src", "dest", "max_speed", "length"};
		if (!sec.getKeys().containsAll(Arrays.asList(parR))) {
			throw new IllegalArgumentException();
		}
		if (!sec.getKeys().contains("type")) {
			return new NewRoadEvent(parseInt(sec, "time"), sec.getValue("id"),
									sec.getValue("src"), sec.getValue("dest"),
									parseInt(sec, "max_speed"), parseInt(sec, "length"));
		}
		if (sec.getValue("type").equals("dirt")) {
			return new NewDirtEvent(parseInt(sec, "time"), sec.getValue("id"),
									sec.getValue("src"), sec.getValue("dest"),
									parseInt(sec, "max_speed"), parseInt(sec, "length"));
		}
		if (!sec.getKeys().contains("lanes")) {
			throw new IllegalArgumentException();
		}
		return new NewLaneEvent(parseInt(sec, "time"), sec.getValue("id"), 
								sec.getValue("src"), sec.getValue("dest"),
								parseInt(sec, "max_speed"),parseInt(sec, "length"),
								parseInt(sec, "lanes"));
	}
	
	/** 
	 * Método para comprobar si la id representa un Road.
	 * @param id : El identificador del objeto
	 * @return Booleano que indica si id es válida.
	*/
	public boolean isValidId(String id){
		return id.charAt(0) == 'r';
	}
	
	/** 
	 * Método que devuelve el tipo de evento que construye la clase.
	 * @return Tipo de evento
	*/
	public String type(){
		return "new_road";
	}
}
