package es.ucm.fdi.model.simobjects;

import java.util.Map;

/** 
 * La clase SimObject es una clase abstracta de la cual heredan los objetos de la simulación.
 * @author Jaime Fernández y Brian Leiva
*/

public abstract class SimObject {
	protected String id;
	
	/** 
	 * Constructor de la clase SimObject.
	 * @param ident : Identificador
	*/	
	public SimObject(String id) {
		this.id = id;
	}
	
	/** 
	 * Método get para id.
	 * @return Identificador del objeto
	*/	
	public String getID(){
		return id;
	}
	
	/** 
	 * Método que hace avanzar la parte de la simulación relacionada con el objeto
	*/
	public abstract void avanza();
	
	/** 
	 * Método que rellena un mapa <String, String> con los detalles del objeto
	*/	
	protected abstract void fillReportDetails(Map<String, String> out);
	
	/** 
	 * Método que devuelve el encabezado del reporte del objeto.
	*/	
	protected abstract String getReportHeader(); 
	
	/** 
	 * Método que comprueba si dos SimObject son el mismo, comparando sus identificadores.
	 * @param a : SimOject 1
	 * @param b : SimOject 2
	 * @return Booleano que indica si los SimObject son iguales
	*/	
	public static boolean equals(SimObject a, SimObject b){
		return a.getID() == b.getID();
	}
	
	/** 
	 * Método que rellena un mapa <String, String> con el reporte del objeto
	 * @param time : Tiempo en el que se desarrolla el informe
	 * @param m : Mapa para salida de datos
	*/
	public void report(int time, Map<String, String> m) {
		m.put("", getReportHeader());
		m.put("id", id);
		m.put("time", String.valueOf(time));
		fillReportDetails(m);
	}

}
