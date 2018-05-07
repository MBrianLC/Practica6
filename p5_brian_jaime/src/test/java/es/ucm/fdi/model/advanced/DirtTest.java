package es.ucm.fdi.model.advanced;

import java.util.ArrayList;

import org.junit.Test;

import es.ucm.fdi.model.simobjects.advanced.Dirt;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Vehicle;

import java.util.List;
import static org.junit.Assert.*;

public class DirtTest {

	@Test
	public void testExecute() {
		List<Junction> itinerario = new ArrayList<>();
		itinerario.add(new Junction("j1"));
		itinerario.add(new Junction("j2"));
		Dirt l1 = new Dirt("l1", 50, 15, itinerario.get(0), itinerario.get(1));
		Vehicle v1 = new Vehicle("v1", 10, itinerario);
		Vehicle v2 = new Vehicle("v2", 12, itinerario);
		Vehicle v3 = new Vehicle("v3", 15, itinerario);
		v1.moverASiguienteCarretera(l1);
		v2.moverASiguienteCarretera(l1);
		v3.moverASiguienteCarretera(l1);
		
		l1.avanza();
		v1.setTiempoAveria(1);
		l1.avanza();
		assertTrue("Los vehículos tienen posiciones incorrectas", v1.getPos() == 10 && v2.getPos() == 19 && v3.getPos() == 22);

	}
}