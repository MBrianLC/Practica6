package es.ucm.fdi.model.simobjects.advanced;

import java.util.List;
import java.util.Map;

import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Vehicle;

/** 
 * La clase Bike representa una bicicleta del simulador.
 * @author Jaime Fernández y Brian Leiva
*/

public class Bike extends Vehicle{
	
	/** 
	 * Constructor de la clase Bike.
	 * @param ident : Identificador
	 * @param vmax : Velocidad máxima
	 * @param it : Itinerario
	*/
	public Bike(String ident, int vmax, List<Junction> it) {
		super(ident, vmax, it);
	}
	
	/**
	 * Informe de Bike
	 * @param out : Mapa con los datos de Bike
	*/
	protected void fillReportDetails(Map<String, String> out){
		out.put("type", "bike");
		super.fillReportDetails(out);
	}
	
	/**
	 * Método que ajusta el tiempo de avería
	 * @param n : Tiempo de avería
	*/
	public void setTiempoAveria(int n) {
		if (2 * velActual > velMaxima) {
			super.setTiempoAveria(n);
		}
	}

}