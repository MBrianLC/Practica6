package es.ucm.fdi.model.advanced;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.model.simobjects.advanced.MostCrowded;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.Vehicle;

/** 
 * La clase MostCrowdedTest se encarga de probar que MostCrowded funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class MostCrowdedTest {
	
	Junction j1 = new Junction("j1");
	Junction j2 = new Junction("j2");
	Junction j3 = new Junction("j3");
	MostCrowded j4 = new MostCrowded("j4");
	Junction j5 = new Junction("j5");
	Road r1 = new Road("r1", 15, 20, j1, j4);
	Road r2 = new Road("r2", 20, 20, j2, j4);
	Road r3 = new Road("r3", 20, 20, j3, j4);
	Road r4 = new Road("r4", 30, 20, j4, j5);
	List<Junction> itinerario1 = new ArrayList<>();
	List<Junction> itinerario2 = new ArrayList<>();
	List<Junction> itinerario3 = new ArrayList<>();

	/**
	 * Inicializa MostCrowded.
	*/
	public void inicio() {
		j1.addSale(r1);
		j2.addSale(r2);
		j3.addSale(r3);
		j4.addEntra(r1);
		j4.addEntra(r2);
		j4.addEntra(r3);
		j4.addSale(r4);
		j5.addEntra(r4);

		itinerario1.add(j1);
		itinerario1.add(j4);
		itinerario1.add(j5);
		itinerario2.add(j2);
		itinerario2.add(j4);
		itinerario2.add(j5);
		itinerario3.add(j3);
		itinerario3.add(j4);
		itinerario3.add(j5);
	}
	
	/**
	 * Método que prueba los intervalos de tiempo de MostCrowded.
	*/
	@Test
	public void intervaloTiempoTest() {
		inicio();
		Vehicle v1 = new Vehicle("v1", 15, itinerario1);
		Vehicle v2 = new Vehicle("v2", 15, itinerario3);
		Vehicle v3 = new Vehicle("v3", 15, itinerario3);
		Vehicle v4 = new Vehicle("v4", 15, itinerario3);
		Vehicle v5 = new Vehicle("v5", 15, itinerario3);
		v1.moverASiguienteCarretera(r1);
		v2.moverASiguienteCarretera(r3);
		v3.moverASiguienteCarretera(r3);
		v4.moverASiguienteCarretera(r3);
		v5.moverASiguienteCarretera(r3);
		
		v1.setPos(15);
		r1.avanza();
		j4.avanza();
		assertTrue("El semáforo de r1 pasa a estar verde, con 1 segundo de tiempo", r1.getSemaforo() && j4.getIntervaloDeTiempo() == 1);
		
		v2.setPos(20);
		v3.setPos(20);
		v4.setPos(20);
		v5.setPos(20);
		r3.avanza();
		j4.avanza();
		assertTrue("El semáforo de r3 pasa a estar verde, con 2 segundos de tiempo", r3.getSemaforo() && j4.getIntervaloDeTiempo() == 2);
		
	
	}
	
	/**
	 * Método que prueba el método avanza de MostCrowded.
	*/
	@Test
	public void avanzaTest() {
		
		inicio();
		Vehicle v1 = new Vehicle("v1", 15, itinerario1);
		Vehicle v2 = new Vehicle("v2", 15, itinerario1);
		Vehicle v3 = new Vehicle("v3", 15, itinerario2);
		Vehicle v4 = new Vehicle("v4", 15, itinerario3);
		v1.moverASiguienteCarretera(r1);
		v2.moverASiguienteCarretera(r1);
		v3.moverASiguienteCarretera(r2);
		v4.moverASiguienteCarretera(r3);
		
		v3.setPos(20);
		r2.avanza();
		j4.avanza(); // Se inicializan los semáforos
		j4.avanza();
		assertTrue("El semáforo de r2 pasa a estar verde, y el de r1 rojo", r2.getSemaforo() && !r1.getSemaforo());
		
		v1.setPos(15);
		v2.setPos(15);
		v4.setPos(20);
		r1.avanza();
		r3.avanza();
		r2.avanza();
		j4.avanza();
		assertTrue("El vehículo v3 debe estar en la carretera r4", v3.getCarretera() == r4);
		
		j4.avanza();
		assertTrue("El vehículo v1 debe estar en la carretera r4 (primero de la carretera más congestionada)", v1.getCarretera() == r4);

	}
}
