package es.ucm.fdi.model.events;

import org.junit.Assert;
import org.junit.Test;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewDirtEvent;
import es.ucm.fdi.model.events.NewLaneEvent;
import es.ucm.fdi.model.events.NewRoadEvent;
import es.ucm.fdi.model.events.NewRoadEventBuilder;

/** 
 * La clase NewRoadEventBuilderTest se encarga de probar que NewRoadEventBuilder funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class NewRoadEventBuilderTest {
	
	@Test
	public void parseTest(){
		NewRoadEventBuilder b = new NewRoadEventBuilder();
		IniSection sv = new IniSection("new_vehicle");
		IniSection sj = new IniSection("new_road");
		sj.setValue("time", "2");
		sj.setValue("id", "r5");
		sj.setValue("src", "j5");
		sj.setValue("dest", "j7");
		sj.setValue("max_speed", "20");
		sj.setValue("length", "40");
		sj.setValue("lanes", "3");
		
		Event e = b.parse(sv);
		Assert.assertEquals("Debería ser null, puesto que sv es una inisection de un vehiculo", null, e);
		
		e = b.parse(sj);
		Event f = new NewRoadEvent(2, "r5", "j5", "j7", 20, 40);
		Assert.assertEquals("Comprueba si ha parseado bien el tiempo", f.getTime(), e.getTime());
		Assert.assertEquals("Comprueba si ha construido el tipo de evento correcto", f.getClass(), e.getClass());
		
		sj.setValue("type", "dirt");
		
		e = b.parse(sj);
		f = new NewDirtEvent(2, "r5", "j5", "j7", 20, 40);
		Assert.assertEquals("Comprueba si ha parseado bien el tiempo", f.getTime(), e.getTime());
		Assert.assertEquals("Comprueba si ha construido el tipo de evento correcto", f.getClass(), e.getClass());
		
		sj.setValue("type", "lanes");
		
		e = b.parse(sj);
		f = new NewLaneEvent(2, "r5", "j5", "j7", 20, 40, 3);
		Assert.assertEquals("Comprueba si ha parseado bien el tiempo", f.getTime(), e.getTime());
		Assert.assertEquals("Comprueba si ha construido el tipo de evento correcto", f.getClass(), e.getClass());
	}

	@Test
	public void isValidIdTest(){
		NewRoadEventBuilder b = new NewRoadEventBuilder();
		String s = "r5";
		Assert.assertTrue("La ID es válida, luego isValidId da true", b.isValidId(s));
		
		s = "v5";
		Assert.assertTrue("La ID no es válida, luego isValidId da false", !b.isValidId(s));
	}
	
	@Test
	public void typeTest(){
		NewRoadEventBuilder b = new NewRoadEventBuilder();
		String s = "new_road";
		Assert.assertEquals("Devuelve el tipo adecuado", b.type(), s);
	}
	
	@Test
	public void parseIntTest(){
		NewRoadEventBuilder b = new NewRoadEventBuilder();
		IniSection sj = new IniSection("new_road");
		sj.setValue("time", "2");
		Assert.assertEquals("Comprueba que parsea bien el entero", b.parseInt(sj, "time"), 2);
	}
	
	@Test
	public void parseIdListTest(){
		NewRoadEventBuilder b = new NewRoadEventBuilder();
		IniSection sj = new IniSection("new_road");
		sj.setValue("lista", "r1,r2,r3");
		String[] s = {"r1", "r2", "r3"};
		Assert.assertArrayEquals("Comprueba que parsea bien la lista", b.parseIdList(sj, "lista"), s);
	}
	
}