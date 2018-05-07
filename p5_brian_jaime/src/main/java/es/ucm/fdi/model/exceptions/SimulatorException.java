package es.ucm.fdi.model.exceptions;

/** 
 * La excepción SimulatorException se lanza cuando se detectan errores en el simulador.
 * @author Jaime Fernández y Brian Leiva
*/

@SuppressWarnings("serial")
public class SimulatorException extends Exception {
	
	public SimulatorException(String s) {
		super(s);
	}

}