package es.ucm.fdi.model.simobjects;

import java.util.ArrayList;

import org.junit.Test;

import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simobjects.Vehicle;

import java.util.List;
import static org.junit.Assert.*;

/** 
 * La clase RoadTest se encarga de probar que Road funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class RoadTest {
	List<Junction> itinerario = new ArrayList<>();
	Junction j1 = new Junction("j1");
	Junction j2 = new Junction("j2");
	Junction j3 = new Junction("j3");
	Road r1 = new Road("r1", 15, 20, j1, j2);
	Road r2 = new Road("r2", 20, 20, j1, j2);

	/**
	 * Método que prueba la entrada y salida de vehículos de Road.
	*/
	@Test
	public void vehiculosTest() {
		itinerario.add(j1);
		itinerario.add(j2);
		itinerario.add(j3);
		Vehicle v1 = new Vehicle("v1", 20, itinerario);
		Vehicle v2 = new Vehicle("v2", 15, itinerario);
		v1.setCarretera(r1);
		v2.setCarretera(r1);
		r1.entraVehiculo(v1);
		r1.entraVehiculo(v2);
		assertTrue("Se introdocen los vehículos en la posición 0", r1.vehiculos.get(0).size() == 2);

		r1.saleVehiculo(v1);
		assertTrue("No debería salir ningún vehículo (siguen en la posición 0)", r1.vehiculos.get(0).size() == 2);

		r1.avanza();
		r1.avanza();
		r1.saleVehiculo(v1);
		assertTrue("v1 debe haber salido de r1", r1.vehiculos.sizeOfValues() == 1);
	}
	
	@Test
	public void avanzaTest() {
		
		itinerario.add(j1);
		itinerario.add(j2);
		itinerario.add(j3);
		Vehicle v1 = new Vehicle("v1", 12, itinerario);
		Vehicle v2 = new Vehicle("v2", 15, itinerario);
		v1.moverASiguienteCarretera(r1);
		v2.moverASiguienteCarretera(r1);
		
		r1.avanza();
		assertTrue("El vehículo va a una velocidad incorrecta (fallo al calcular velBase sin avería))", v1.getPos() == 11 && v2.getPos() == 11);
		
		r1.avanza();
		assertTrue("Los vehículos no han entrado en la cola", r1.getQueue().size() == 2);
		assertTrue("Los vehículos no se insertan de forma correcta en la cola", r1.getQueue().getFirst() == v1);

		v1.moverASiguienteCarretera(r2);
		r1.avanza();
		r2.avanza();
		assertTrue("v2 debe estar al final de r1", v2.getPos() == r1.longitud);
		
		v2.moverASiguienteCarretera(r2);
		v1.setTiempoAveria(2);
		r2.avanza();
		assertTrue("Los vehículos van a velocidades incorrectas (fallo al calcular velBase con avería))", v1.getPos() == 12 && v2.getPos() == 5);
		
		r2.avanza();
		r2.avanza();
		v1.moverASiguienteCarretera(null);
		v2.moverASiguienteCarretera(null);
		assertTrue("Los vehículos no han llegado a su destino (siguen en r2)", r1.vehiculos.sizeOfValues() == 0);
	}
}