package es.ucm.fdi.model.simobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * La clase Vehicle representa un vehículo del simulador.
 * @author Jaime Fernández y Brian Leiva
*/

public class Vehicle extends SimObject implements Describable{
	protected int velMaxima, velActual, distTotal;
	private int localizacion, k, tiempoAveria;
	private Road roadActual;
	private List<Junction> itinerario;
	private boolean haLlegado;
	
	/** 
	 * Constructor de la clase Vehicle.
	 * @param ident : Identificador
	 * @param maxV : Velocidad máxima del vehículo
	 * @param it: Itinerario de cruces que debe seguir el vehículo
	*/
	public Vehicle(String ident, int vmax, List<Junction> it){
		super(ident);
		velMaxima = vmax;
		velActual = 0;
		distTotal = 0;
		itinerario = it;
		k = 1;
		tiempoAveria = 0;
		haLlegado = false;
	}
	
	/** 
	 * Devuelve true si el vehículo presenta una avería (tiempoAveria > 0).
	 * @return Booleano que indica si hay avería
	*/
	public boolean getAveria(){
		return tiempoAveria > 0;
	}
	
	/** 
	 * Devuelve true si el vehículo ha llegado a su destino.
	 * @return Booleano que indica si se ha llegado al final
	*/
	public boolean fin(){
		return k == itinerario.size();
	}
	
	/** 
	 * Método get para localizacion.
	 * @return Posición del vehículo en su carretera actual
	*/
	public int getPos(){
		return localizacion;
	}
	
	/** 
	 * Método set para localizacion.
	 * @param n : Nueva posición del vehículo
	*/
	public void setPos(int n) {
		localizacion = n;
	}
	
	/** 
	 * Método get para roadActual.
	 * @return Carretera actual
	*/
	public Road getCarretera(){
		return roadActual;
	}
	
	/** 
	 * Método set para localizacion.
	 * @param r : Nueva carretera
	*/
	public void setCarretera(Road r) {
		roadActual = r;		
	}
	
	/** 
	 * Devuelve el siguiente cruce al que llegará el vehículo.
	 * @return Próximo cruce en el itinerario de Vehicle
	*/
	public Junction sigCruce() {
		return itinerario.get(k);
	}
	
	/** 
	 * Devuelve la cabecera del informe de Vehicle.
	 * @return Cabecera del informe
	*/
	protected String getReportHeader(){
		return "vehicle_report";
	}
	
	/** 
	 * Aumenta tiempoAveria.
	 * @param n: Tiempo de avería
	*/
	public void setTiempoAveria(int n) {
		tiempoAveria += n;
	}
	
	/** 
	 * Método set para velActual.
	 * @param v: Nueva velocidad
	*/
	public void setVelocidadActual(int v){
		if (v > velMaxima){
			velActual = velMaxima;
		} else {
			velActual = v;
		}
	}
	
	/** 
	 * Informe de Vehicle.
	 * @param out : Mapa para salida de datos
	*/
	protected void fillReportDetails(Map<String, String> out){
		String s;
		if (!haLlegado) {
			s = "(" + roadActual.getID() + "," + localizacion + ")";
		} else {
			s = "arrived";
		}
		out.put("speed", String.valueOf(velActual));
		out.put("kilometrage", String.valueOf(distTotal));
		out.put("faulty", String.valueOf(tiempoAveria));
		out.put("location", s);
	}
	
	/** 
	 * Tabla de Vehicle.
	 * @return Mapa para salida de datos
	*/
	public Map<String, String> describe(){
		Map<String, String> out = new HashMap<>();
		StringBuilder sb = new StringBuilder("[");
		out.put("ID", id);
		if (haLlegado){
			out.put("Road", "Arrived");
		} else {
			out.put("Road", roadActual.getID());
		}
		out.put("Location", String.valueOf(localizacion));
		out.put("Speed", String.valueOf(velActual));
		out.put("Km", String.valueOf(distTotal));
		out.put("Faulty Units", String.valueOf(tiempoAveria));
		for (int i = 0; i < itinerario.size(); ++i) {
			sb.append(itinerario.get(i).getID() + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		out.put("Itinerary", sb.toString());
		return out;
	}
	
	/** 
	 * Método avanza para Vehicle.
	*/
	public void avanza(){
		if (tiempoAveria > 0){
			--tiempoAveria;
		} else if (!haLlegado){
			localizacion += velActual;
			distTotal += velActual;
			if (localizacion >= roadActual.getLong()){
				distTotal -= (localizacion - roadActual.getLong());
				localizacion = roadActual.getLong();
				++k;
			}
		}
	}
	
	/** 
	 * Lleva al vehículo a otra carretera.
	 * @param r : Nueva carretera
	*/
	public void moverASiguienteCarretera(Road r){
		if (k > 1) {
			roadActual.saleVehiculo(this);
		}
		roadActual = r;
		localizacion = 0;
		velActual = 0;
		if (k == itinerario.size()) {
			haLlegado = true;
		} else {
			roadActual.entraVehiculo(this);
		}
	}
	
}
