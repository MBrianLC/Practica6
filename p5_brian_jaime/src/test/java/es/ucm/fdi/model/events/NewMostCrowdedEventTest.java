package es.ucm.fdi.model.events;

import org.junit.Assert;
import org.junit.Test;

import es.ucm.fdi.model.events.NewMostCrowdedEvent;
import es.ucm.fdi.model.exceptions.SimulatorException;
import es.ucm.fdi.model.simobjects.Junction;
import es.ucm.fdi.model.simulator.RoadMap;

/** 
 * La clase NewMostCrowdedEventTest se encarga de probar que NewMostCrowdedEvent funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class NewMostCrowdedEventTest {
	
	@Test
	public void testExecute() throws SimulatorException{
		RoadMap m = new RoadMap();
		NewMostCrowdedEvent j = new NewMostCrowdedEvent(3, "j9");
		
		j.execute(m);
		
		Junction x = m.getJunctions().get(m.getJunctions().size() - 1);
		Assert.assertEquals("El ID del cruce creado es correcto", "j9", x.getID());
	}
}