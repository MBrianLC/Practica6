package es.ucm.fdi.model.events;

import org.junit.Assert;
import org.junit.Test;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewJunctionEvent;
import es.ucm.fdi.model.events.NewJunctionEventBuilder;
import es.ucm.fdi.model.events.NewMostCrowdedEvent;
import es.ucm.fdi.model.events.NewRoundRobinEvent;

/** 
 * La clase NewJunctionEventBuilderTest se encarga de probar que NewJunctionEventBuilder funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class NewJunctionEventBuilderTest {
	
	@Test
	public void parseTest(){
		NewJunctionEventBuilder b = new NewJunctionEventBuilder();
		IniSection sv = new IniSection("new_vehicle");
		IniSection sj = new IniSection("new_junction");
		sj.setValue("time", "2");
		sj.setValue("id", "j5");
		sj.setValue("max_time_slice", "3");
		sj.setValue("min_time_slice", "7");
		
		Event e = b.parse(sv);
		Assert.assertEquals("Debería ser null, puesto que sv es una inisection de un vehiculo", null, e);
		
		e = b.parse(sj);
		Event f = new NewJunctionEvent(2, "j5");
		Assert.assertEquals("Comprueba si ha parseado bien el tiempo", f.getTime(), e.getTime());
		Assert.assertEquals("Comprueba si ha construido el tipo de evento correcto", f.getClass(), e.getClass());
		
		sj.setValue("type", "mc");
		
		e = b.parse(sj);
		f = new NewMostCrowdedEvent(2, "j5");
		Assert.assertEquals("Comprueba si ha parseado bien el tiempo", f.getTime(), e.getTime());
		Assert.assertEquals("Comprueba si ha construido el tipo de evento correcto", f.getClass(), e.getClass());
		
		sj.setValue("type", "rr");
		
		e = b.parse(sj);
		f = new NewRoundRobinEvent(2, "j5", 3, 7);
		Assert.assertEquals("Comprueba si ha parseado bien el tiempo", f.getTime(), e.getTime());
		Assert.assertEquals("Comprueba si ha construido el tipo de evento correcto", f.getClass(), e.getClass());
	}
	
	@Test
	public void isValidIdTest(){
		NewJunctionEventBuilder b = new NewJunctionEventBuilder();
		String s = "j5";
		Assert.assertTrue("La ID es válida, luego isValidId da true", b.isValidId(s));
		
		s = "v5";
		Assert.assertTrue("La ID no es válida, luego isValidId da false", !b.isValidId(s));
	}
	
	@Test
	public void typeTest(){
		NewJunctionEventBuilder b = new NewJunctionEventBuilder();
		String s = "new_junction";
		Assert.assertEquals("Devuelve el tipo adecuado", b.type(), s);
	}
	
	@Test
	public void parseIntTest(){
		NewJunctionEventBuilder b = new NewJunctionEventBuilder();
		IniSection sj = new IniSection("new_junction");
		sj.setValue("time", "2");
		Assert.assertEquals("Comprueba que parsea bien el entero", b.parseInt(sj, "time"), 2);
	}
	
	@Test
	public void parseIdListTest(){
		NewJunctionEventBuilder b = new NewJunctionEventBuilder();
		IniSection sj = new IniSection("new_junction");
		sj.setValue("lista", "j1,j2,j3");
		String[] s = {"j1", "j2", "j3"};
		Assert.assertArrayEquals("Comprueba que parsea bien la lista", b.parseIdList(sj, "lista"), s);
	}
	
}