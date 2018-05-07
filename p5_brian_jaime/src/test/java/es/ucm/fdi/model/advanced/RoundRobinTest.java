package es.ucm.fdi.model.advanced;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.model.simobjects.advanced.RoundRobin;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.Vehicle;

/** 
 * La clase RoundRobinTest se encarga de probar que RoundRobin funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class RoundRobinTest {
	
	Junction j1 = new Junction("j1");
	Junction j2 = new Junction("j2");
	RoundRobin j3 = new RoundRobin("j3", 3, 1);
	Junction j4 = new Junction("j4");
	Road r1 = new Road("r1", 15, 20, j1, j3);
	Road r2 = new Road("r2", 20, 20, j2, j3);
	Road r3 = new Road("r3", 30, 20, j3, j4);
	List<Junction> itinerario1 = new ArrayList<>();
	List<Junction> itinerario2 = new ArrayList<>();

	/**
	 * Inicializa RoundRobin.
	*/
	public void inicio() {
		j1.addSale(r1);
		j2.addSale(r2);
		j3.addEntra(r1);
		j3.addEntra(r2);
		j3.addSale(r3);
		j4.addEntra(r3);

		itinerario1.add(j1);
		itinerario1.add(j3);
		itinerario1.add(j4);
		itinerario2.add(j2);
		itinerario2.add(j3);
		itinerario2.add(j4);
	}
	
	/**
	 * Método que prueba el método avanza de RoundRobin.
	*/
	@Test
	public void avanzaTest() {
		inicio();
		Vehicle v1 = new Vehicle("v1", 15, itinerario1);
		Vehicle v2 = new Vehicle("v2", 15, itinerario2);
		Vehicle v3 = new Vehicle("v3", 15, itinerario2);
		Vehicle v4 = new Vehicle("v4", 15, itinerario2);
		v1.moverASiguienteCarretera(r1);
		v2.moverASiguienteCarretera(r2);
		v3.moverASiguienteCarretera(r2);
		v4.moverASiguienteCarretera(r2);
		
		v1.setPos(15);
		r1.avanza();
		j3.avanza(); // Se inicializan los semáforos
		for (int i = 0; i < 3; ++i) j3.avanza(); // 1 semáforo
		assertTrue("El semáforo de r1 debe pasar a estar rojo, sin modificar el intervalo", 
				   !r1.getSemaforo() && j3.getIntervaloDeTiempo(0) == 3);
		
		for (int i = 0; i < 3; ++i) j3.avanza();
		assertTrue("El semáforo de r2 debe pasar a estar rojo, con 2 como nuevo intervalo (cruce sin usar)", 
				   !r2.getSemaforo() && j3.getIntervaloDeTiempo(1) == 2);
	
		v2.setPos(20);
		v3.setPos(20);
		v4.setPos(20);
		r2.avanza();
		for (int i = 0; i < 6; ++i) j3.avanza(); // 2 semáforos
		assertTrue("El semáforo de r2 debe pasar a estar rojo, con 3 como nuevo intervalo (cruce completamente usado)", 
				   !r2.getSemaforo() && j3.getIntervaloDeTiempo(1) == 3);
	
	}
}