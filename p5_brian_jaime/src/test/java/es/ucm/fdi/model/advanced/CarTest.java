package es.ucm.fdi.model.advanced;

import java.util.ArrayList;

import org.junit.Test;

import es.ucm.fdi.model.simobjects.advanced.Car;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;

import java.util.List;
import static org.junit.Assert.*;

public class CarTest {
	List<Junction> itinerario = new ArrayList<>();
	
	@Test
	public void testExecute() {
		itinerario.add(new Junction("j1"));
		itinerario.add(new Junction("j2"));
		Road r1 = new Road("r1", 15, 20, itinerario.get(0), itinerario.get(1));
		Car c1 = new Car("c1", 15, itinerario, 10, 0.7, 3, 1892);
		
		c1.moverASiguienteCarretera(r1);
		c1.setVelocidadActual(20);
		c1.avanza();
		c1.avanza();
		assertTrue("El vehículo debería haberse averiado", c1.getAveria());

	}
}
