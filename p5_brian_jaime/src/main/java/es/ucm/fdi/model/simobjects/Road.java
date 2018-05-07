package es.ucm.fdi.model.simobjects;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

/** 
 * La clase Road representa una carretera del simulador.
 * @author Jaime Fernández y Brian Leiva
*/

public class Road extends SimObject implements Describable{
	protected int longitud, maxVel;
	protected MultiTreeMap<Integer, Vehicle> vehiculos;
	private boolean semaforo;
	private ArrayDeque<Vehicle> cola;
	private Junction ini, fin;
	
	/** 
	 * Constructor de la clase Road.
	 * @param ident : Identificador
	 * @param l : Longitud de la carretera
	 * @param maxV : Velocidad máxima permitida en la carretera
	 * @param junctioni: Cruce inicial
	 * @param junctionf: Cruce final
	*/
	public Road(String ident, int l, int maxV, Junction junctioni, Junction junctionf){
		super(ident);
		longitud = l;
		maxVel = maxV;
		ini = junctioni;
		fin = junctionf;
		cola = new ArrayDeque<>();
		vehiculos = new MultiTreeMap<>((a, b) -> a - b);
	}
	
	/** 
	 * Método acedente para cola.
	 * @return Cola de vehículos que han llegado al final de la carretera
	*/
	public ArrayDeque<Vehicle> getQueue(){
		return cola;
	}
	
	/** 
	 * Método get para longitud.
	 * @return Longitud de la carretera
	*/
	public int getLong(){
		return longitud;
	}
	
	/** 
	 * Método get para ini.
	 * @return Cruce inicial
	*/
	public Junction getIni(){
		return ini;
	}
	
	/** 
	 * Método get para fin.
	 * @return Cruce final
	*/
	public Junction getFin(){
		return fin;
	}
	
	/** 
	 * Método get para semaforo.
	 * @return Semáforo del cruce final
	*/
	public boolean getSemaforo(){
		return semaforo;
	}
	
	/** 
	 * Método get para la lista de vehículos.
	 * @return Mapa con los vehículos asociados a su posición
	*/
	public MultiTreeMap<Integer, Vehicle> getVehicles(){
		return vehiculos;
	}
	
	/** 
	 * Método set para semaforo.
	 * @param b: Nuevo estado del semáforo (true: verde, false: rojo)
	*/
	public void setSemaforo (boolean b){
		semaforo = b;
	}
	
	/** 
	 * Añade un vehículo a la carretera.
	 * @param v: Vehículo
	*/
	public void entraVehiculo (Vehicle v){
		vehiculos.putValue(0, v);
	}
	
	/** 
	 * Elimina un vehículo de la carretera.
	 * @param v: Vehículo
	*/
	public void saleVehiculo (Vehicle v){
		vehiculos.removeValue(longitud, v);
	}
	
	/** 
	 * Devuelve la cabecera del informe de Road.
	 * @return Cabecera del informe
	*/
	protected String getReportHeader(){
		return "road_report";
	}
	
	/** 
	 * Informe de Road.
	 * @param out : Mapa para salida de datos
	*/
	protected void fillReportDetails(Map<String, String> out){
		StringBuilder sb = new StringBuilder();
		if (!vehiculos.isEmpty()) {
			for (int i = longitud; i >= 0; --i){
				if (vehiculos.containsKey(i)){
					for(Vehicle v: vehiculos.get(i)){
						sb.append("(" + v.getID() + "," + v.getPos() + "),");
					}
				}
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		out.put("state", sb.toString());
	}
	
	/** 
	 * Tabla de Road.
	 * @return Mapa para salida de datos
	*/
	public Map<String, String> describe(){
		Map<String, String> out = new HashMap<>();
		StringBuilder sb = new StringBuilder("[");
		out.put("ID", id);
		out.put("Source", ini.getID());
		out.put("Target", fin.getID());
		out.put("Length", String.valueOf(longitud));
		out.put("Max Speed", String.valueOf(maxVel));
		for (int i = longitud; i >= 0; --i){
			if (vehiculos.containsKey(i)){
				for(Vehicle v: vehiculos.get(i)){
					sb.append(v.getID() + ",");
				}
			}
		}
		if (sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		out.put("Vehicles", sb.toString());
		return out;
	}
	
	/** 
	 * Método avanza para Road.
	*/
	public void avanza (){ // ***
		int velBase = Math.min(maxVel, maxVel / Math.max((int) vehiculos.sizeOfValues(), 1) + 1);
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
						factorRed = 2;
						v.setVelocidadActual(0);
					} else {
						v.setVelocidadActual(velBase / factorRed);
					}
					v.avanza();
					if (v.getPos() == longitud) {
						v.setVelocidadActual(0);
						cola.add(v);
					}
					map.putValue(v.getPos(), v);
				}
			}
		}
		vehiculos = map;
	}
}
