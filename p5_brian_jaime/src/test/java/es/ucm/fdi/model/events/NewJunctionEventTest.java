package es.ucm.fdi.model.events;

import org.junit.Assert;
import org.junit.Test;

import es.ucm.fdi.model.events.NewJunctionEvent;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewJunctionEventTest se encarga de probar que NewJunctionEvent funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class NewJunctionEventTest {
	
	@Test
	public void testExecute() throws SimulatorException{
		RoadMap m = new RoadMap();
		NewJunctionEvent j = new NewJunctionEvent(3, "j7");
		
		j.execute(m);
		
		Junction x = m.getJunctions().get(m.getJunctions().size() - 1);
		Assert.assertEquals("El ID del cruce creado es correcto", "j7", x.getID());
	}
}

