package es.ucm.fdi.model.events;

import es.ucm.fdi.ini.IniSection;

/** 
 * La clase EventBuilder es una interfaz que implementan los builders de los eventos.
 * @author Jaime Fernández y Brian Leiva
*/

public interface EventBuilder {
	
	/** 
	 * Método para reconocer qué tipo de evento representa la sección.
	 * @param sec La sección del evento
	*/
	public Event parse(IniSection sec);
	
	/** 
	 * Método para comprobar si la id representa el objeto adecuado.
	 * @param id El identificador del objeto
	*/
	
	public boolean isValidId(String id);
	
	/** 
	 * Método que devuelve el tipo de evento que construye la clase.
	*/
	
	public String type();
	
	/** 
	 * Método que convierte una key de una sección en un entero
	 * @param sec La sección.
	 * @param key La clave que va a ser convertida en entero.
	 * @return El entero obtenido a partir de la key.
	*/
	
	public default int parseInt(IniSection sec, String key){
		return Integer.parseInt(sec.getValue(key));
	}
	
	/** 
	 * Método que convierte una key de una sección en un array de strings.
	 * @param sec : La sección.
	 * @param key : La clave que va a ser convertida en entero.
	 * @return El array de strings.
	*/
	public default String[] parseIdList(IniSection sec, String key){
		String[] s = sec.getValue(key).split(",");
		return s;
	}
	
	/** 
	 * Método que convierte una key de una sección en un array de strings.
	 * @param sec La sección.
	 * @param key La clave que va a ser convertida en entero.
	 * @return El array de strings.
	*/
	
}
