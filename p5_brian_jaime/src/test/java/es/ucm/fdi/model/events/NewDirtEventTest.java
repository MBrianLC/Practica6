package es.ucm.fdi.model.events;

import org.junit.Assert;
import org.junit.Test;

import es.ucm.fdi.model.events.NewDirtEvent;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simobjects.Road;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewDirtEventTest se encarga de probar que NewDirtEvent funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class NewDirtEventTest {
	
	@Test
	public void testExecute() throws SimulatorException{
		RoadMap m = new RoadMap();
		Junction a = new Junction("j8");
		Junction b = new Junction("j1");
		m.addJunction(a);
		m.addJunction(b);
		NewDirtEvent r = new NewDirtEvent(3, "r9", "j8", "j1", 20, 60);
		
		r.execute(m);
		
		Road x = m.getRoads().get(m.getRoads().size() - 1);
		Assert.assertEquals("El ID del camino creado es correcto", "r9", x.getID());
		Assert.assertEquals("El cruce inicial es correcto", "j8", x.getIni().getID());
		Assert.assertEquals("El cruce final es correcto", "j1", x.getFin().getID());
	}
}
