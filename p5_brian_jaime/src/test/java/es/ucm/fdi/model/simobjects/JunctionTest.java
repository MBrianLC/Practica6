package es.ucm.fdi.model.simobjects;

import java.util.ArrayList;

import org.junit.Test;

import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.Vehicle;

import java.util.List;
import static org.junit.Assert.*;

public class JunctionTest {
	
	Junction j1 = new Junction("j1");
	Junction j2 = new Junction("j2");
	Junction j3 = new Junction("j3");
	Junction j4 = new Junction("j4");
	Junction j5 = new Junction("j5");
	Road r1 = new Road("r1", 15, 20, j1, j3);
	Road r2 = new Road("r2", 20, 20, j2, j3);
	Road r3 = new Road("r3", 20, 20, j3, j4);
	Road r4 = new Road("r4", 30, 20, j3, j5);

	/**
	 * Inicializa Junction.
	*/
	public void inicio() {
		j1.addSale(r1);
		j2.addSale(r2);
		j3.addEntra(r1);
		j3.addEntra(r2);
		j3.addSale(r3);
		j3.addSale(r4);
		j4.addEntra(r3);
		j5.addEntra(r4);
	}
	
	/**
	 * Método que prueba las carreteras entrantes y salientes.
	*/
	@Test
	public void carreterasTest() {
		inicio();
		assertTrue("Se introdocen las carreteras entrantes", j3.entrantes.contains(r1) && j3.entrantes.contains(r2));
		assertTrue("Se introdocen las carreteras salientes", j3.salientes.contains(r3) && j3.salientes.contains(r4));
		assertTrue("El mapa de carreteras salientes debe dar la carretera correcta en función de un cruce",
				   j3.mapSaliente.get(j4) == r3 && j3.mapSaliente.get(j5) == r4);
	}
	
	/**
	 * Método que prueba los semáforos en un Junction.
	*/
	@Test
	public void semaforoTest() {
		inicio();
		j3.avanza();
		assertTrue("El semáforo de la primera carretera entrante r1 debe ser verde (y el de r2 rojo)", r1.getSemaforo() && !r2.getSemaforo());
		j3.avanza();
		assertTrue("El semáforo de la segunda carretera entrante r2 debe ser verde (y el de r1 rojo)", r2.getSemaforo() && !r1.getSemaforo());
	}
	
	/**
	 * Método que prueba la entrada de vehículos en la cola de las carreteras.
	*/
	@Test
	public void entraVehiculoTest() {
		List<Junction> itinerario = new ArrayList<>();
		inicio();
		itinerario.add(j1);
		itinerario.add(j3);
		itinerario.add(j5);
		Vehicle v1 = new Vehicle("v1", 12, itinerario);
		v1.moverASiguienteCarretera(r1);
		j3.entraVehiculo(v1);
		assertTrue("El vehículo v1 debe estar en la cola de r1", r1.getQueue().contains(v1));
	}
	
	@Test
	public void avanzaTest() {
		List<Junction> itinerario = new ArrayList<>();
		inicio();
		itinerario.add(j1);
		itinerario.add(j3);
		itinerario.add(j5);
		Vehicle v1 = new Vehicle("v1", 12, itinerario);
		Vehicle v2 = new Vehicle("v2", 15, itinerario);
		v1.moverASiguienteCarretera(r1);
		v2.moverASiguienteCarretera(r1);
		v1.setPos(15);
		v2.setPos(15);
		r1.avanza();
		j3.avanza();
		j5.avanza();
		
		r1.avanza();
		j3.avanza();
		assertTrue("El vehículo v1 debe estar en la carretera r4", v1.getCarretera() == r4 && r1.vehiculos.sizeOfValues() == 1);
		
		j3.avanza();
		assertTrue("El vehículo v2 sigue en la cola", r1.getQueue().size() == 1);
		
		v1.setPos(30);
		r4.avanza();
		j5.avanza();
		assertTrue("El vehículo v1 debe haber llegado a su destino j5", v1.fin());
	}
}
