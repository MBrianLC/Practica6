package es.ucm.fdi.model.simobjects.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.Vehicle;

/** 
 * La clase RoundRobin representa un cruce circular del simulador.
 * @author Jaime Fernández y Brian Leiva
*/

public class RoundRobin extends Junction{
	private int maxValorIntervalo, minValorIntervalo, unidadesDeTiempoUsadas, usos;
	private List<Integer> intervaloDeTiempo;

	/** 
	 * Constructor de la clase RoundRobin.
	 * @param ident : Identificador
	*/
	public RoundRobin(String ident, int max, int min) {
		super(ident);
		maxValorIntervalo = max;
		minValorIntervalo = min;
		unidadesDeTiempoUsadas = 0;
		usos = 0;
		intervaloDeTiempo = new ArrayList<>();
	}
	
	/** 
	 * Añade una carretera entrante.
	 * @param r : Carretera
	*/
	public void addEntra(Road r) {
		intervaloDeTiempo.add(maxValorIntervalo);
		super.addEntra(r);
	}
	
	/** 
	 * Devuelve el intervalo de tiempo de una carretera entrante.
	 * @param i : Índice de la carretera
	 * @return El intervalo de tiempo de la carretera entrante i
	*/
	public int getIntervaloDeTiempo(int i) {
		return intervaloDeTiempo.get(i);
	}
	
	/**
	 * Informe de RoundRobin
	 * @param out : Mapa con los datos de RoundRobin
	 */
	protected void fillReportDetails(Map<String, String> out){
		StringBuilder s = new StringBuilder();
		if (!entrantes.isEmpty()) {
			for (int i = 0; i < entrantes.size(); ++i){
				s.append("(" + entrantes.get(i).getID() + ",");
				if (entrantes.get(i).getSemaforo()) {
					s.append("green:" + (intervaloDeTiempo.get(i) - unidadesDeTiempoUsadas) + ",[");
				} else {
					s.append("red,[");
				}
				if (!entrantes.get(i).getQueue().isEmpty()) {
					for (Vehicle v : entrantes.get(i).getQueue()){
						s.append(v.getID() + ",");
					}
					s.deleteCharAt(s.length() - 1);
				}
				s.append("]),");
			}
			s.deleteCharAt(s.length() - 1);
		}
		out.put("queues", s.toString());
		out.put("type", "rr");
	}
	
	/** 
	 * Método avanza para RoundRobin.
	*/	
	public void avanza(){
		if (k == -1) {
			k = 0;
		} else if (!entrantes.isEmpty()) {
			if (!entrantes.get(k).getQueue().isEmpty()) {
				++usos;
				Vehicle v = entrantes.get(k).getQueue().getFirst();
				if (!v.fin()) {
					Road r = road(v);
					v.moverASiguienteCarretera(r);
				} else {
					v.moverASiguienteCarretera(null);
				}
				entrantes.get(k).getQueue().pop();
			}
			++unidadesDeTiempoUsadas;
			if (unidadesDeTiempoUsadas == intervaloDeTiempo.get(k)) {
				entrantes.get(k).setSemaforo(false);
				if (usos == unidadesDeTiempoUsadas){ 
					intervaloDeTiempo.set(k, Math.min((int) intervaloDeTiempo.get(k) + 1, maxValorIntervalo));
				} else if (usos == 0) { 
					intervaloDeTiempo.set(k, Math.max((int) intervaloDeTiempo.get(k) - 1, minValorIntervalo));
				}
				unidadesDeTiempoUsadas = 0;
				usos = 0;
				k++;
				if (k == entrantes.size()) {
					k = 0;
				}
				entrantes.get(k).setSemaforo(true);
			}
		}
	}
	
}
