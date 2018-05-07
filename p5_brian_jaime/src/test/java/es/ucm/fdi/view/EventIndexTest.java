package es.ucm.fdi.view;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import es.ucm.fdi.model.events.NewJunctionEvent;

/** 
 * La clase EventIndexTest se encarga de probar que EventIndex funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class EventIndexTest {

	@Test
	public void testDescribe(){
		NewJunctionEvent ev = new NewJunctionEvent(3, "j7");
		EventIndex ei = new EventIndex(1, ev);
		
		Map<String, String> m = ei.describe();
		
		assertEquals("The index is correct", m.get("#"), "2");
		assertEquals("The time is correct", m.get("Time"), "3");
		assertEquals("The type is correct", m.get("Type"), "New Junction j7");
	}
	
}
