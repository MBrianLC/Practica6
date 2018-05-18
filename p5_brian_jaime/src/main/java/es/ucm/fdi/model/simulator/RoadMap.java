package es.ucm.fdi.model.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.SimObject;
import es.ucm.fdi.model.simobjects.Vehicle;

/** 
 * La clase RoadMap representa entorno del simulador.
 * @author Jaime Fernández y Brian Leiva
*/

public class RoadMap {

	private Map<String, SimObject> simObjects;
 
	private List<Junction> junctions = new ArrayList<>();
	private List<Road> roads = new ArrayList<>();
	private List<Vehicle> vehicles = new ArrayList<>();
	
	private static final Logger logger = Logger.getLogger(RoadMap.class.getName());
	
	/** 
	 * Constructor de la clase RoadMap.
	*/
	public RoadMap() {
		logger.fine("Creando mapa de carreteras");
		this.vehicles = new ArrayList<>();
		this.roads = new ArrayList<>();
		this.junctions = new ArrayList<>();
		this.simObjects = new HashMap<>();
	}
	
	/** 
	 * Devuelve un objeto SimObject del mapa.
	 * @param id : Identificador de SimObject
	 * @return SimObject asociado al identificador
	*/
	public SimObject getSimObject(String id) {
		if (!simObjects.containsKey(id)){
			throw new NullPointerException("The object with ID " + id + " doesn't exist.");
		}
		return simObjects.get(id);
	}
	
	/** 
	 * Devuelve un vehículo del mapa.
	 * @param id : Identificador de Vehicle
	 * @return Vehicle asociado al identificador
	*/
	public Vehicle getVehicle(String id) {
		if (!simObjects.containsKey(id)) {
			throw new NullPointerException("The vehicle with ID " + id + " doesn't exist.");
		}
		return (Vehicle) getSimObject(id);
	}
	
	/** 
	 * Devuelve una carretera del mapa.
	 * @param id : Identificador de Road
	 * @return Road asociado al identificador
	*/
	public Road getRoad(String id) {
		if (!simObjects.containsKey(id)) {
			throw new NullPointerException("The road with ID " + id + " doesn't exist.");
		}
		return (Road) getSimObject(id);
	}
	
	/** 
	 * Devuelve un cruce del mapa.
	 * @param id : Identificador de Junction
	 * @return Junction asociado al identificador
	*/
	public Junction getJunction(String id) {
		if (!simObjects.containsKey(id)) {
			throw new NullPointerException("The junction with ID " + id + " doesn't exist.");
		}
		return (Junction) getSimObject(id);
	}
	
	/** 
	 * Método get para vehicles.
	 * @return Lista de vehículos del mapa
	*/
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	
	/** 
	 * Método get para roads.
	 * @return Lista de carreteras del mapa
	*/	
	public List<Road> getRoads() {
		return roads;
	}
	
	/** 
	 * Método get para junctions.
	 * @return Lista de cruces del mapa
	*/	
	public List<Junction> getJunctions() {
		return junctions;
	}
	
	/** 
	 * Devuelve una lista con todos los objetos simulados del mapa.
	 * @return Lista de objetos simulados (junctions, roads y vehicles)
	*/	
	public List<SimObject> getSimObjects() {
		List<SimObject> list = new ArrayList<>();
		list.addAll(junctions);
		list.addAll(roads);
		list.addAll(vehicles);
		return list;
	}
	
	/** 
	 * Añade un vehículo a su lista y al mapa.
	 * @param v: Vehículo
	*/	
	public void addVehicle(Vehicle v) {
		if (simObjects.containsKey(v.getID())) {
			throw new IllegalArgumentException("The vehicle with ID " + v.getID() + " already exists.");
		}
		simObjects.put(v.getID(), v);
		vehicles.add(v);
		logger.finer("Vehículo añadido");
	}
	
	/** 
	 * Añade una carretera a su lista y al mapa.
	 * @param r: Carretera
	*/
	public void addRoad(Road r) {
		if (simObjects.containsKey(r.getID())) {
			throw new IllegalArgumentException("The road with ID " + r.getID() + " already exists.");
		}
		simObjects.put(r.getID(), r);
		roads.add(r);
		logger.finer("Carretera añadida");
	}
	
	/** 
	 * Añade un cruce a su lista y al mapa.
	 * @param c: Cruce
	*/
	public void addJunction(Junction j) {
		if (simObjects.containsKey(j.getID())) {
			throw new IllegalArgumentException("The junction with ID " + j.getID() + " already exists.");
		}
		simObjects.put(j.getID(), j);
		junctions.add(j);
		logger.finer("Cruce añadido");
	}
	
}
