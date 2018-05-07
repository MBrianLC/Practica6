package es.ucm.fdi.model.events;

import org.junit.Assert;
import org.junit.Test;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.VehicleFaultyEvent;
import es.ucm.fdi.model.events.VehicleFaultyEventBuilder;

/** 
 * La clase VehicleFaultyEventBuilderTest se encarga de probar que VehicleFaultyEventBuilder funciona correctamente.
 * @author Jaime Fernández y Brian Leiva
*/

public class VehicleFaultyEventBuilderTest {
	
	@Test
	public void parseTest(){
		VehicleFaultyEventBuilder b = new VehicleFaultyEventBuilder();
		IniSection sv = new IniSection("new_vehicle");
		IniSection sj = new IniSection("make_vehicle_faulty");
		sj.setValue("time", "2");
		sj.setValue("duration", "5");
		sj.setValue("vehicles", "v2,v3");
		
		Event e = b.parse(sv);
		Assert.assertEquals("Debería ser null, puesto que sv es una inisection de un vehiculo", null, e);
		
		e = b.parse(sj);
		String[] st = {"v2","v3"};
		Event f = new VehicleFaultyEvent(2, st, 5);
		Assert.assertEquals("Comprueba si ha parseado bien el tiempo", f.getTime(), e.getTime());
		Assert.assertEquals("Comprueba si ha construido el tipo de evento correcto", f.getClass(), e.getClass());
	}
	
	@Test
	public void isValidIdTest(){
		VehicleFaultyEventBuilder b = new VehicleFaultyEventBuilder();
		String s = "v5";
		Assert.assertTrue("La ID es válida, luego isValidId da true", b.isValidId(s));
		
		s = "j5";
		Assert.assertTrue("La ID no es válida, luego isValidId da false", !b.isValidId(s));
	}
	
	@Test
	public void typeTest(){
		VehicleFaultyEventBuilder b = new VehicleFaultyEventBuilder();
		String s = "make_vehicle_faulty";
		Assert.assertEquals("Devuelve el tipo adecuado", b.type(), s);
	}
	
	@Test
	public void parseIntTest(){
		VehicleFaultyEventBuilder b = new VehicleFaultyEventBuilder();
		IniSection sj = new IniSection("make_vehicle_faulty");
		sj.setValue("time", "2");
		Assert.assertEquals("Comprueba que parsea bien el entero", b.parseInt(sj, "time"), 2);
	}
	
	@Test
	public void parseIdListTest(){
		VehicleFaultyEventBuilder b = new VehicleFaultyEventBuilder();
		IniSection sj = new IniSection("make_vehicle_faulty");
		sj.setValue("lista", "j1,j2,j3");
		String[] s = {"j1", "j2", "j3"};
		Assert.assertArrayEquals("Comprueba que parsea bien la lista", b.parseIdList(sj, "lista"), s);
	}
	
}