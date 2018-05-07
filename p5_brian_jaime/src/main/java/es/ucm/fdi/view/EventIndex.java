package es.ucm.fdi.view;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.simobjects.Describable;

/** 
 * La clase EventIndex representa un evento con índice.
 * @author Jaime Fernández y Brian Leiva
*/

public class EventIndex implements Describable{
	
	private int time, actual;
	private Event evento;
	
	/** 
	 * Constructor de EventIndex.
	 * @param index: Índice
	 * @param event: Evento
	*/

	public EventIndex(int index, Event event) {
		actual = index;
		time = event.getTime();
		evento = event;
	}
	
	/** 
	 * Pasa los datos de EventIndex a un mapa.
	 * @return Mapa con los datos de EventIndex
	*/

	public Map<String, String> describe() {
		Map<String, String> out = new HashMap<>();
		out.put("#", String.valueOf(actual));
		out.put("Time", String.valueOf(time));
		out.put("Type", evento.getType());
		return out;
	}

}
