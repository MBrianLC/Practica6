package es.ucm.fdi.launcher;

import static org.junit.Assert.*;
import org.junit.Test;

import es.ucm.fdi.launcher.Main;
import es.ucm.fdi.model.exceptions.SimulatorException;

/** 
 * La clase FullTest se encarga de probar los casos de prueba.
 * @author Jaime Fernández y Brian Leiva
*/

public class FullTest {

	private static final String BASE = "src/test/resources/";

	/**
	 * Método que prueba el caso de prueba con errores.
	*/
	@Test
	public void testError() throws Exception {
		try {
			Main.test(BASE + "examples/err");
			fail("Expected an exception while parsing bad ini file");
		} catch (SimulatorException e) {
			// Expected exception
		}
	}

	/**
	 * Método que prueba los casos de prueba de objetos básicos.
	*/
	@Test
	public void testBasic() throws Exception {
		Main.test(BASE + "examples/basic");
	}

	/**
	 * Método que prueba los casos de prueba de objetos avanzados.
	*/
	@Test
	public void testAdvanced() throws Exception {
		Main.test(BASE + "examples/advanced");
	}

}