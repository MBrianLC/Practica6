package es.ucm.fdi.model.events;

import org.junit.Assert;
import org.junit.Test;

import es.ucm.fdi.model.events.NewRoundRobinEvent;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewRoundRobinEventTest se encarga de probar que NewRoundRobinEvent funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class NewRoundRobinEventTest {
	
	@Test
	public void testExecute() throws SimulatorException{
		RoadMap m = new RoadMap();
		NewRoundRobinEvent j = new NewRoundRobinEvent(3, "j8", 2, 4);
		
		j.execute(m);
		
		Junction x = m.getJunctions().get(m.getJunctions().size() - 1);
		Assert.assertEquals("El ID del cruce creado es correcto", "j8", x.getID());
	}
}