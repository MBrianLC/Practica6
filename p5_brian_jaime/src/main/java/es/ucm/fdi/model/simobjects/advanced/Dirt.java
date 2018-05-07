package es.ucm.fdi.model.simobjects.advanced;

import java.util.Map;

import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.Vehicle;
import es.ucm.fdi.util.MultiTreeMap;

/** 
 * La clase Dirt representa un camino del simulador.
 * @author Jaime Fernández y Brian Leiva
*/

public class Dirt extends Road{
	
	/** 
	 * Constructor de la clase Dirt.
	 * @param ident : Identificador
	 * @param l : Longitud del camino
	 * @param maxV : Velocidad máxima
	 * @param junctioni : Cruce inicial
	 * @param junctionf : Cruce final
	*/
	public Dirt(String ident, int l, int maxV, Junction junctioni, Junction junctionf) {
		super(ident, l, maxV, junctioni, junctionf);
	}
	
	/**
	 * Rellena el informe de Dirt
	 * @param out : Mapa con los datos de Dirt
	 */
	protected void fillReportDetails(Map<String, String> out){
		out.put("type", "dirt");
		super.fillReportDetails(out);
	}
	
	/**
	 * Método que hace avanzar la simulación en el camino
	 */
	public void avanza(){
		int factorRed = 1;
		MultiTreeMap<Integer, Vehicle> map = new MultiTreeMap<>((a, b) -> a - b);
		if (vehiculos.containsKey(longitud)){
			for(Vehicle v: vehiculos.get(longitud)){
				map.putValue(longitud, v);
			}
		}
		for (int i = longitud - 1; i >= 0; --i){
			if (vehiculos.containsKey(i)){
				for(Vehicle v: vehiculos.get(i)){
					if (v.getAveria()) {
						factorRed++;
					}
				}
			}
		}
		for (int i = longitud - 1; i >= 0; --i){
			if (vehiculos.containsKey(i)){
				for(Vehicle v: vehiculos.get(i)){
					if (v.getAveria()) {
						v.setVelocidadActual(0);
					} else {
						v.setVelocidadActual(maxVel / factorRed);
					}
					v.avanza();
					if (v.getPos() == longitud) {
						v.setVelocidadActual(0);
						getQueue().add(v);
					}
					map.putValue(v.getPos(), v);
				}
			}
		}
		vehiculos = map;
	}

}
