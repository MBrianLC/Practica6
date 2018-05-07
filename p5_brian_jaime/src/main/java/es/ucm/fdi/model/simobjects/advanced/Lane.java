package es.ucm.fdi.model.simobjects.advanced;

import java.util.Map;

import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.Vehicle;
import es.ucm.fdi.util.MultiTreeMap;

/** 
 * La clase Lane representa una autopista del simulador.
 * @author Jaime Fernández y Brian Leiva
*/

public class Lane extends Road{
	
	private int numCarriles;
	
	/** 
	 * Constructor de la clase Lane.
	 * @param ident : Identificador
	 * @param l : Longitud de la autopista
	 * @param maxV : Velocidad máxima
	 * @param junctioni : Cruce inicial
	 * @param junctionf : Cruce final
	 * @param lanes : Carriles de la autopista
	*/
	public Lane(String ident, int l, int maxV, Junction junctioni, Junction junctionf, int lanes) {
		super(ident, l, maxV, junctioni, junctionf);
		numCarriles = lanes;
	}
	
	/**
	 * Rellena el informe de Lane
	 * @param out : Mapa con los datos de Lane
	 */
	protected void fillReportDetails(Map<String, String> out){
		out.put("type", "lanes");
		super.fillReportDetails(out);
	}
	
	/**
	 * Método que hace avanzar la simulación en la autopista
	 */
	public void avanza(){
		int velBase = Math.min(maxVel, maxVel * numCarriles / Math.max((int) vehiculos.sizeOfValues(), 1) + 1);
		int factorRed = 1, numObstaculos = 0;
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
						numObstaculos++;
						if (numObstaculos >= numCarriles) {
							factorRed = 2;
							break;
						}
					}
				}
				if (numObstaculos >= numCarriles){
					break;
				}
			}
		}
		for (int i = longitud - 1; i >= 0; --i){
			if (vehiculos.containsKey(i)){
				for(Vehicle v: vehiculos.get(i)){
					if (v.getAveria()) {
						v.setVelocidadActual(0);
					} else {
						v.setVelocidadActual(velBase / factorRed);
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